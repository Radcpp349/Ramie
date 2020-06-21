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

import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.vecmath.Vector3f;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




//Klasa odpowiedzalna za wyświetlanie tłą, robota i wszystkich innych elementów
public class World extends JFrame {


    private Ramie ramie;     //deklarujemy ramię robota, aby wyświetlać je w oknie
    private Buttons buttons;  //Deklarujemy panel z przyciskami



    //parametry służące do regulacji wielkości tła
    private float size;
    private float height;
    private float xz;

    private Transform3D przesuniecie_obserwatora = new Transform3D();
    private Vector3f dystans = new Vector3f(5.5f,5.0f,37.0f);
    public SimpleUniverse universe;


    //Na potrzeby obrotu kamery i świateł dodajemy BoundingSphere
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 300.0);


    //konstruktor klasy World
    public World() {

        //ustawiammy parametry wielkości tła
        this.size=55.0f;
        this.height= 0.5f*size-3;
        this.xz = 6.0f;

        //Wyświetlamy okno programu
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ramie = new Ramie();
        this.buttons = new Buttons();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1200,900));
        Container content;
        setLayout(null);
        content = getContentPane();
        content.setLayout(new BorderLayout());
        JPanel controlPanel = buttons.DodajPanel();

        content.add(controlPanel, BorderLayout.NORTH);
        content.add(canvas3D, BorderLayout.CENTER);

        //KeyListener

        canvas3D = ramie.canv_KeyListener(canvas3D);

        ///Koniec KeyListenera


        //Tworzymy iluzję obrotu kamery, synchronizując ruch tła i robota
        add(canvas3D);
        pack();
        setVisible(true);
       this.universe = new SimpleUniverse(canvas3D);

        BranchGroup group = CreateGroup(universe);
        BranchGroup robot = ramie.Robot();

        group.compile();
        universe.getViewingPlatform().setNominalViewingTransform();




        // Dodajemy sterowanie obrotem za pomocą myszki
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        universe.getViewingPlatform().setNominalViewingTransform();

        // Tworzymy ruch kamerą jako orbitowanie wokół punktu
        OrbitBehavior orbit = new OrbitBehavior(canvas3D);

        orbit.setSchedulingBounds(bounds);
        viewingPlatform.setViewPlatformBehavior(orbit);

        //Przyporzoądkowujemy dwa główne Branchgroupy do naszego świata

        universe.addBranchGraph(group);
        universe.addBranchGraph(robot);

        //Ustawiamy optymalne startowe przesunięcie kamery, a robot był dobrze widoczny

        przesuniecie_obserwatora.set(dystans);

        universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        //Zwiększamy zasię renderowania, aby uniknąć ,,czarnych plam"
        universe.getViewer().getView().setBackClipDistance(1000);


    }


    BranchGroup CreateGroup(SimpleUniverse universe){

        BranchGroup wezel_scena = new BranchGroup();


        //ŚWIATŁA

        AmbientLight lightB = new AmbientLight(new Color3f(1.0f,1.0f,1.0f));

        lightB.setInfluencingBounds(bounds);

        wezel_scena.addChild(lightB);

        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bounds);
        lightD.setDirection(new Vector3f(12.0f, -12.0f, -12.0f));
        lightD.setColor(new Color3f(1.0f, 1.0f, 1.0f));
        wezel_scena.addChild(lightD);


// Wersja tła jako 6 płaskich boxów


        //Dla każdej ściany z sześciu tworzymy indwidualny wygląd
        Appearance backgroundApp = new Appearance();
        Appearance backgroundApp1 = new Appearance();
        Appearance backgroundApp2 = new Appearance();
        Appearance backgroundApp3 = new Appearance();
        Appearance backgroundApp4 = new Appearance();
        Appearance backgroundApp5 = new Appearance();


//Tworzymy sześć tekstur, każej przyporządkowujemy odpowiedni obrazek i ustawimy do stworzonego przed chwilą wyglądu
        Texture tex = new TextureLoader( "right2r.jpeg", this).getTexture();
        if (tex != null)
        {backgroundApp.setTexture(tex);
            System.out.println("ok");}

        Texture tex1 = new TextureLoader( "left2r.jpeg", this).getTexture();
        if (tex1 != null)
        {backgroundApp1.setTexture(tex1);
            System.out.println("ok");}
        Texture tex2 = new TextureLoader( "top2r.jpeg", this).getTexture();
        if (tex2 != null)
        {backgroundApp2.setTexture(tex2);
            System.out.println("ok");}
        Texture tex3 = new TextureLoader( "bottom2r.jpeg", this).getTexture();
        if (tex3 != null)
        {backgroundApp3.setTexture(tex3);
            System.out.println("ok");}
        Texture tex4 = new TextureLoader( "back2r.jpeg", this).getTexture();
        if (tex4 != null)
        {backgroundApp4.setTexture(tex4);
            System.out.println("ok");}

        Texture tex5 = new TextureLoader( "front2r.jpeg", this).getTexture();
        if (tex5 != null)
        {backgroundApp5.setTexture(tex5);
            System.out.println("ok");}

//Tworzymy sześć transofrmGrup (po jednej na każdą ścianę) aby porozstawiać ściany
        TransformGroup boxy = new TransformGroup();
        TransformGroup boxy1 = new TransformGroup();
        TransformGroup boxy2 = new TransformGroup();
        TransformGroup boxy3 = new TransformGroup();
        TransformGroup boxy4 = new TransformGroup();
        TransformGroup boxy5 = new TransformGroup();

        //Tworzymy sześć Transformacji (po jednej na każdą ścianę) aby porozstawiać ściany
        Transform3D boxt = new Transform3D();
        boxt.set(new Vector3f(size+xz,0.0f+height,0.0f+xz));

        Transform3D boxt1 = new Transform3D();
        boxt1.set(new Vector3f(-size+xz,0.0f+height,0.0f+xz));

        Transform3D boxt2 = new Transform3D();
        boxt2.set(new Vector3f(0.0f+xz,size+height,0.0f+xz));

        Transform3D boxt3 = new Transform3D();
        boxt3.set(new Vector3f(0.0f+xz,-size+height,0.0f+xz));

        Transform3D boxt4 = new Transform3D();
        boxt4.set(new Vector3f(0.0f+xz,0.0f+height,size+xz));

        Transform3D boxt5 = new Transform3D();
        boxt5.set(new Vector3f(0.0f+xz,0.0f+height,-size+xz));

        //Przyporządkowujemy transformacje do TransformGrup

        boxy.setTransform(boxt);
        boxy1.setTransform(boxt1);
        boxy2.setTransform(boxt2);
        boxy3.setTransform(boxt3);
        boxy4.setTransform(boxt4);
        boxy5.setTransform(boxt5);


        //Tworzymy sześć skrajnie cienkich boxów, ustawiamy im wyglądy z tekstur i dodajemy do odpowiednich Transformgroup aby je porozstawiać. W ten sposób otrzymujemy tło w postaci kostki z nałożonymi teksturami

        Box box = new Box(0.022f,size,size,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp);
        boxy.addChild(box);
        Box box1 = new Box(0.022f,size,size,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp1);
        boxy1.addChild(box1);
        Box box2= new Box(size,0.022f,size,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp2);
        boxy2.addChild(box2);
        Box box3 = new Box(size,0.022f,size,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp3);
        boxy3.addChild(box3);
        Box box4 = new Box(size,size,0.022f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp4);
        boxy4.addChild(box4);
        Box box5 = new Box(size,size,0.022f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,backgroundApp5);
        boxy5.addChild(box5);

        //Dodajemy TransfromGrupy do węzła scena, aby wyświetlić tło w oknie
        wezel_scena.addChild(boxy);
        wezel_scena.addChild(boxy1);
        wezel_scena.addChild(boxy2);
        wezel_scena.addChild(boxy3);
        wezel_scena.addChild(boxy4);
        wezel_scena.addChild(boxy5);



        return wezel_scena;

    }

    public class Buttons extends JFrame {
        private JButton przyciski[] = null;    //Tworzymy panel z przyciskami
        private JFrame ref_okno;
        private boolean czy_reset = false;

        public Buttons(){
            this.przyciski = new JButton[4];
            przyciski[0] = new JButton("Reset Kamery");
            przyciski[0].addActionListener(new ObslugaPrzycisku(ref_okno));
            przyciski[1] = new JButton("Nagrywanie");
            przyciski[1].addActionListener(new ObslugaPrzycisku(ref_okno));
            przyciski[2] = new JButton("Stop nagrywania");
            przyciski[2].addActionListener(new ObslugaPrzycisku(ref_okno));
            przyciski[3] = new JButton("Instrukcja");
            przyciski[3].addActionListener(new ObslugaPrzycisku(ref_okno));}

        private class ObslugaPrzycisku implements ActionListener {

            ObslugaPrzycisku(JFrame okno) {ref_okno = okno; }

            public void actionPerformed(ActionEvent e) {

                JButton bt = (JButton)e.getSource();
                if(bt == przyciski[0])
                {
                    przesuniecie_obserwatora.set(dystans);
                   universe.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
                }

                else if (bt == przyciski[1])
                {   System.out.println("czaczacza");}
                else if (bt == przyciski[2])
                { }
                else if (bt == przyciski[3])
                { JOptionPane.showInternalMessageDialog(ref_okno, "Schemat sterowania robotem:\n" +
                                "Q - Obrót podstawy robota przeciwnie do ruchu wskazówek zegara (patrzac z góry) \n" +
                                "E - Obrót podstawy robota zgdonie z ruchem wskazówek zegara (patrząc z góry) \n" +
                                "W - Ruch ramienia w górę \n" +
                                "S - Ruch ramienia w dół \n" +
                                "A - Ruch przedramienia w górę \n" +
                                "D - Ruch przedramienia w dół \n",
                        "Instrukcja", JOptionPane.INFORMATION_MESSAGE);
                   }

            }
        }



        public JPanel DodajPanel()  {
            JPanel controlPanel = new JPanel(new FlowLayout());

            controlPanel.add(przyciski[0]);
            controlPanel.add(przyciski[1]);
            controlPanel.add(przyciski[2]);
            controlPanel.add(przyciski[3]);


            return controlPanel;
        }

        public void resetkamery (){
            czy_reset = true;
        }

        public boolean czy_resetujemy (){
            return czy_reset;
        }
        public void zresetowano (){
            czy_reset = false;
        }


    }


}