package com.company;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.media.j3d.BranchGroup;


public class Background {


    public Background() {
        SimpleUniverse universe = new SimpleUniverse();
        BranchGroup group = new BranchGroup();
        group.addChild(new ColorCube(0.5f));
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }
/*
public static void main(String[] args){
        new Background();
}

*/

}