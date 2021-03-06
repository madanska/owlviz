package org.coode.owlviz.ui.options;

import org.coode.owlviz.ui.OWLVizPreferences;
import org.protege.editor.core.ui.preferences.PreferencesLayoutPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
* Copyright (C) 2007, University of Manchester
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 10, 2008<br><br>
 */
public class ModeOptions {



    private JRadioButton trackerMode;

    private JRadioButton configureMode;

    private JSpinner trackerRadiusSpinner;

    ActionListener l = event -> trackerRadiusSpinner.setEnabled(trackerMode.isSelected());

    public ModeOptions(PreferencesLayoutPanel layoutPanel) {

        final int radius = OWLVizPreferences.getInstance().getDefaultTrackerRadius();
        final boolean isTrackerMode = OWLVizPreferences.getInstance().isTrackingModeDefault();


        trackerRadiusSpinner = new JSpinner(new SpinnerNumberModel(radius, 1, 100, 1));
        trackerRadiusSpinner.setEnabled(isTrackerMode);

        trackerMode = new JRadioButton("Track selection with radius", isTrackerMode);
        configureMode = new JRadioButton("Create graph manually", !isTrackerMode);
        configureMode.setAlignmentX(0.0f);

        ButtonGroup bg = new ButtonGroup();
        bg.add(trackerMode);
        bg.add(configureMode);

        layoutPanel.addGroup("Tracking");

        layoutPanel.addGroupComponent(trackerMode);
        layoutPanel.addIndentedGroupComponent(trackerRadiusSpinner);
        layoutPanel.addVerticalPadding();
        layoutPanel.addGroupComponent(configureMode);

        trackerMode.addActionListener(l);
        configureMode.addActionListener(l);

    }

    public boolean isTrackerMode() {
        return trackerMode.isSelected();
    }

    public void setTrackerMode(boolean tracker) {
        trackerMode.setSelected(tracker);
        configureMode.setSelected(!tracker);
    }

    public int getTrackerRadius() {
        return (Integer) trackerRadiusSpinner.getValue();
    }

    public void setTrackerRadius(int trackerRadius) {
        trackerRadiusSpinner.setValue(trackerRadius);
    }
}
