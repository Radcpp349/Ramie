package com.company;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;

import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.*;
import javax.vecmath.*;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;





public class World extends JFrame {

    java.net.URL bgImage = null;
    private Ramie ramie;
    //private boolean klawisze[];
    
    
     World() {
    	 
    	//klawisze = new boolean[6];
    	this.ramie = new Ramie();
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,900));
        /*adding buttons, doesnt seem to work tho xd---------------------------------------//
        JPanel rootPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JButton("Alpha"));
        controlPanel.add(new JButton("Beta"));
        controlPanel.add(new JButton("Gamma"));
        rootPanel.add(controlPanel, BorderLayout.NORTH);
        rootPanel.add(canvas3D, BorderLayout.CENTER);
        -------------------------------------------------------------------------------------*/
       //KeyListener, jednak trzeba to zrobić w ten sposób, tak wychodzi z programu Merty
        
        canvas3D = ramie.canv_KeyListener(canvas3D);
                
        ///Koniec KeyListenera
        add(canvas3D);
        pack();
        setVisible(true);
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        BranchGroup group = CreateGroup();
        BranchGroup robot = ramie.Robot();

        group.compile();
        universe.getViewingPlatform().setNominalViewingTransform();

        Transform3D przesuniecie_obserwatora = new Transform3D();


        // add mouse behaviors to the ViewingPlatform
       ViewingPlatform viewingPlatform = universe.getViewingPlatform();

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        universe.getViewingPlatform().setNominalViewingTransform();

        // add orbit behavior to the viewing platform
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ALL);
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 200.0);
        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        universe.addBranchGraph(group);
        universe.addBranchGraph(robot);

        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,15.0f));

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
    }


    BranchGroup CreateGroup(){

        BranchGroup wezel_scena = new BranchGroup();
        //wezel_scena.addChild(new ColorCube(0.5f));



        BoundingSphere bounds = new BoundingSphere();



        //ŚWIATŁA

        AmbientLight lightB = new AmbientLight(new Color3f(0.0f,0.0f,0.0f));
        lightB.setInfluencingBounds(bounds);
        wezel_scena.addChild(lightB);

        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bounds);
        lightD.setDirection(new Vector3f(0.0f, 0.0f, -10.0f));
        lightD.setColor(new Color3f(1.0f, 1.0f, 1.0f));
        wezel_scena.addChild(lightD);




        Transform3D  tmp_rot      = new Transform3D();
        Background background = new Background();
        background.setApplicationBounds(bounds);
        Appearance backgroundApp = new Appearance();
        

        /*
        Texture tex = new TextureLoader( "tlo.jpg", this).getTexture();
        if (tex != null)
        {backgroundApp.setTexture(tex);
            System.out.println("ok");}


        Box box = new Box(-10.0f,-10.0f,-10.0f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS |

                                Primitive.GENERATE_NORMALS_INWARD,backgroundApp);
	*/

        //wezel_scena.addChild(box);

        //Sphere sphere = new Sphere( 10.0f,
                //Primitive.GENERATE_TEXTURE_COORDS |

                      //  Primitive.GENERATE_NORMALS_INWARD, backgroundApp );
        //wezel_scena.addChild(sphere);

        return wezel_scena;

    }


}