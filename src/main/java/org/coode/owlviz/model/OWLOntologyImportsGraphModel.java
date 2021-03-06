package org.coode.owlviz.model;

import org.coode.owlviz.util.graph.model.GraphModel;
import org.coode.owlviz.util.graph.model.impl.AbstractGraphModel;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.*;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 03-Oct-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLOntologyImportsGraphModel extends AbstractGraphModel {

    private final Map<OWLOntology, Set<OWLOntology>> importMap = new HashMap<>();

    private OWLModelManager owlModelManager;

    public OWLOntologyImportsGraphModel(OWLModelManager owlModelManager) {
        this.owlModelManager = owlModelManager;
        rebuild();
    }

    public void rebuild() {
        importMap.clear();
        for (OWLOntology ont : owlModelManager.getOntologies()) {
            for (OWLOntology imp : ont.getDirectImports()) {
                getImportingOntologies(imp, true).add(ont);
            }
        }
    }

    private Set<OWLOntology> getImportingOntologies(OWLOntology ont, boolean add) {
        Set<OWLOntology> onts = importMap.get(ont);
        if (onts == null) {
            onts = new HashSet<OWLOntology>();
            if (add) {
                importMap.put(ont, onts);
            }
        }
        return onts;
    }

    public int getChildCount(Object obj) {
        return getImportingOntologies((OWLOntology) obj, true).size();
    }

    public Iterator getChildren(Object obj) {
        return getImportingOntologies((OWLOntology) obj, true).iterator();
    }

    public int getParentCount(Object obj) {
        return ((OWLOntology) obj).getImportsDeclarations().size();
    }

    public Iterator getParents(Object obj) {
        return ((OWLOntology) obj).getImportsDeclarations().iterator();
    }

    public boolean contains(Object obj) {
        return owlModelManager.getOntologies().contains((OWLOntology) obj);
    }

    public Object getRelationshipType(Object parentObject, Object childObject) {
        return "imports";
    }

    public int getRelationshipDirection(Object parentObject, Object childObject) {
        return GraphModel.DIRECTION_BACK;
    }

    public Iterator getRelatedObjectsToAdd(Object obj) {
        return Collections.emptySet().iterator();
    }

    public Iterator getRelatedObjectsToRemove(Object obj) {
        return Collections.emptySet().iterator();
    }

    public void dispose() {
    }
}
