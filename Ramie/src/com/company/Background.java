package com.company;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.swing.*;
import javax.media.j3d.BranchGroup;
import javax.vecmath.Vector3f;
import java.awt.*;


public class Background extends JFrame {


    public Background() {
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));

        add(canvas3D);
        pack();
        setVisible(true);
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        BranchGroup group = new BranchGroup();
        group.addChild(new ColorCube(0.5f));
        group.compile();
        universe.getViewingPlatform().setNominalViewingTransform();

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,3.0f));

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        universe.addBranchGraph(group);
    }


}