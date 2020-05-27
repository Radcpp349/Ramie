package com.company;


import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BranchGroup;
import javax.swing.*;

public class Main {

    public Main(){
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup group = new BranchGroup();
        group.addChild(new ColorCube(0.5f));
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }

    public static void main(String[] args) {

        System.setProperty("sun.awt.noerasebackground","true");
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager ttm=ToolTipManager.sharedInstance();
        ttm.setLightWeightPopupEnabled(false);
        new Main();
    }
}
