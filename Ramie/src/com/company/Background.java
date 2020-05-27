package com.company;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;


public class Background extends JFrame {


    public Background() {
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,900));

        add(canvas3D);
        pack();
        setVisible(true);
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        BranchGroup group = CreateGroup();

        group.compile();
        universe.getViewingPlatform().setNominalViewingTransform();

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,3.0f));

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        // add mouse behaviors to the ViewingPlatform
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        universe.getViewingPlatform().setNominalViewingTransform();

        // add orbit behavior to the viewing platform
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        universe.addBranchGraph(group);
    }

    BranchGroup CreateGroup(){

        BranchGroup wezel_scena = new BranchGroup();
        wezel_scena.addChild(new ColorCube(0.5f));



        BoundingSphere bounds = new BoundingSphere();



        //ŚWIATŁA

        AmbientLight lightA = new AmbientLight();
        lightA.setInfluencingBounds(bounds);
        wezel_scena.addChild(lightA);

        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bounds);
        lightD.setDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        lightD.setColor(new Color3f(1.0f, 1.0f, 1.0f));
        wezel_scena.addChild(lightD);

        Transform3D  tmp_rot      = new Transform3D();


        return wezel_scena;

    }


}