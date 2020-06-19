package com.company;


import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.swing.*;

public class Main {



    public static void main(String[] args) {

        System.setProperty("sun.awt.noerasebackground","true");
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager ttm=ToolTipManager.sharedInstance();
        ttm.setLightWeightPopupEnabled(false);

        World obiekt = new World();
    }
}
