package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;






public class Ramie extends JFrame {

	private boolean klawisze[];

	public Ramie(){

		this.klawisze = new boolean[6];
		//BranchGroup robot = Robot();

	}


	BranchGroup Robot() {

		BranchGroup wezel_scena = new BranchGroup();


		/* ustawiamy materiały, póiej zmiana w tekstury--------------------------------------------*/
		Material zielonkawy = new Material();
		zielonkawy.setDiffuseColor(0.9f,0.3f,0.3f);
		zielonkawy.setEmissiveColor(0.0f,0.5f,0.1f);
		zielonkawy.setSpecularColor(0.2f,0.1f,0.1f);
		zielonkawy.setShininess(50f);

		Material niebieski = new Material();
		niebieski.setDiffuseColor(0.0f, 0.0f, 1.0f);
		niebieski.setEmissiveColor(0.28f,0.0f,0.5f);
		niebieski.setSpecularColor(0.2f,0.1f,0.1f);
		niebieski.setShininess(50f);

		Transform3D p_gen = new Transform3D();
		p_gen.setTranslation(new Vector3f(-4.0f, -4.0f, 0.0f));
		TransformGroup t_gen = new TransformGroup (p_gen);
		t_gen.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


		///KOD TESTOWY OD KAMILA, TRZEBA POUSTAWIAĆ

		//podstawa------------------------------------------------------------------------//
		Appearance  wygladPodstawy = new Appearance();
		Texture texPodstawa = new TextureLoader( "DarkMetal.jpeg", this).getTexture();
		if (texPodstawa != null)
		{wygladPodstawy.setTexture(texPodstawa);
			System.out.println("kk");}

		Transform3D p_podstawy = new Transform3D();

		p_podstawy.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		TransformGroup t_podstawy = new TransformGroup (p_podstawy);

		t_podstawy.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Box podstawa = new Box(20.2f, 0.18f, 20.2f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladPodstawy);
		t_podstawy.addChild(podstawa);
		t_gen.addChild(t_podstawy);



		//walec ruchomy-----------------------------------------------------------//
		Appearance  wygladCylindra = new Appearance();

		Texture texWalec = new TextureLoader( "blacha2.jpeg", this).getTexture();
		if (texWalec != null)
		{wygladCylindra.setTexture(texWalec); }

		Transform3D p_walca = new Transform3D();
		p_walca.setTranslation(new Vector3f(0.0f, 3.5f, 0.0f));
		TransformGroup t_walca = new TransformGroup (p_walca);
		Cylinder walec = new Cylinder(0.7f, 6.4f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladCylindra);

		Transform3D p_walca_2 = new Transform3D();
		p_walca_2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		TransformGroup t_walca_2 = new TransformGroup (p_walca_2);
		t_walca_2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


		t_walca_2.addChild(t_walca);
		t_podstawy.addChild(t_walca_2);
		t_walca.addChild(walec);

		//Zakonczenie walca ---------------------------------------------------------//
		Sphere sphere1 = new Sphere(0.8f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,wygladCylindra);
		Transform3D p_sphere1 = new Transform3D();
		p_sphere1.setTranslation(new Vector3f(0.0f, 3.7f, 0.0f));
		TransformGroup t_sphere1 = new TransformGroup (p_sphere1);
		t_sphere1.addChild(sphere1);
		t_walca.addChild(t_sphere1);

		//ramię----------------------------------------------------------------------//



		Appearance  wygladRamienia1 = new Appearance();
		Texture texRamie1 = new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texRamie1 != null)
		{wygladRamienia1.setTexture(texRamie1); }


		Cylinder zaok1_ram = new Cylinder(0.6f, 1.36f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,wygladRamienia1);

		Transform3D p_zaok1_ram = new Transform3D();
		p_zaok1_ram.setTranslation(new Vector3f(0.0f, 3.2f, 1.3f));
		TransformGroup t_zaok1_ram = new TransformGroup (p_zaok1_ram);



		Transform3D p_zaok1_ram_2 = new Transform3D();
		p_zaok1_ram_2.setTranslation(new Vector3f(0.0f, 0.f, 0.0f));
		TransformGroup t_zaok1_ram_2 = new TransformGroup (p_zaok1_ram_2);
		t_zaok1_ram_2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D p_zaok1_ram_3 = new Transform3D();
		p_zaok1_ram_3.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		TransformGroup t_zaok1_ram_3 = new TransformGroup (p_zaok1_ram_3);



		t_walca.addChild(t_zaok1_ram);
		t_zaok1_ram.addChild(t_zaok1_ram_2);
		t_zaok1_ram_2.addChild(t_zaok1_ram_3);

		t_zaok1_ram_3.addChild(zaok1_ram);

		//t_przesunieta2_ram.addChild(t_zaok1_ram);
		com.sun.j3d.utils.geometry.Box ram = new com.sun.j3d.utils.geometry.Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia1);

		Transform3D p_przesuniety1_ram = new Transform3D();
		p_przesuniety1_ram.setTranslation(new Vector3f(2.0f, 0.0f, 0.5f));
		TransformGroup t_przesunieta1_ram = new TransformGroup (p_przesuniety1_ram);


		Transform3D p_przesuniety0_ram = new Transform3D();
		p_przesuniety0_ram.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));

		TransformGroup t_przesunieta0_ram = new TransformGroup (p_przesuniety0_ram);
		//t_przesunieta0_ram.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D p_przesuniety2_ram = new Transform3D();
		p_przesuniety2_ram.setTranslation(new Vector3f(0.8f, 0.f, 0.0f));
		TransformGroup t_przesunieta2_ram = new TransformGroup (p_przesuniety2_ram);


		//t_walca.addChild(t_przesunieta1_ram);
		t_zaok1_ram_3.addChild(t_przesunieta1_ram);
		t_przesunieta1_ram.addChild(t_przesunieta0_ram);
		t_przesunieta0_ram.addChild(t_przesunieta2_ram);

		t_przesunieta2_ram.addChild(ram);


		//zaokrąglenie ramienia------------------------------------------------------------//
		//Zmieniłem cylindry z 0.36f do 1.36f, obrotu e sie na siebie nakłądają niestety :(------------//
	      /*Cylinder zaok1_ram = new Cylinder(0.6f, 1.36f, wygladRamienia1);
	      Transform3D p_zaok1_ram = new Transform3D();
	      p_zaok1_ram.setTranslation(new Vector3f(-2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok1_ram = new TransformGroup (p_zaok1_ram);
	      t_zaok1_ram.addChild(zaok1_ram);
	      t_przesunieta2_ram.addChild(t_zaok1_ram);
	      */
	      /*Cylinder zaok2_ram = new Cylinder(0.6f, 1.36f, wygladRamienia1);
	      Transform3D p_zaok2_ram = new Transform3D();
	      p_zaok2_ram.setTranslation(new Vector3f(2.6f, 0.f, 0.0f));
	      TransformGroup t_zaok2_ram = new TransformGroup (p_zaok2_ram);
	      t_zaok2_ram.addChild(zaok2_ram);
	      t_przesunieta2_ram.addChild(t_zaok2_ram);*/

		//przedramię----------------------------------------------------------------------//
		Appearance  wygladRamienia2 = new Appearance();

		Texture texRamie2 = new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texRamie2 != null)
		{wygladRamienia2.setTexture(texRamie2); }
		Box ram2 = new Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia2);

		Transform3D p_przesuniety1_ram2 = new Transform3D();
		p_przesuniety1_ram2.setTranslation(new Vector3f(2.8f, 0.0f, 0.5f));
		TransformGroup t_przesunieta1_ram2 = new TransformGroup (p_przesuniety1_ram2);

		Transform3D p_przesuniety0_ram2 = new Transform3D();
		p_przesuniety0_ram2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		TransformGroup t_przesunieta0_ram2 = new TransformGroup (p_przesuniety0_ram2);
		t_przesunieta0_ram2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D p_przesuniety2_ram2 = new Transform3D();
		p_przesuniety2_ram2.setTranslation(new Vector3f(2.6f, 0.0f, 0.0f));
		TransformGroup t_przesunieta2_ram2 = new TransformGroup (p_przesuniety2_ram2);

		t_przesunieta2_ram.addChild(t_przesunieta1_ram2);
		t_przesunieta1_ram2.addChild(t_przesunieta0_ram2);
		t_przesunieta0_ram2.addChild(t_przesunieta2_ram2);

		t_przesunieta2_ram2.addChild(ram2);

		//zaokrąglenie przedramienia------------------------------------------------------------//
		Sphere zaok1_ram2 = new Sphere(0.7f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia1);
		Transform3D p_zaok1_ram2 = new Transform3D();
		p_zaok1_ram2.setTranslation(new Vector3f(-2.8f, 0.f, 0.1f));
		TransformGroup t_zaok1_ram2 = new TransformGroup (p_zaok1_ram2);
		t_zaok1_ram2.addChild(zaok1_ram2);
		t_przesunieta2_ram2.addChild(t_zaok1_ram2);

		Appearance  wygladChwytak = new Appearance();

		Texture texChwytak= new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texChwytak != null)
		{wygladChwytak.setTexture(texChwytak); }

		Sphere sphere2 = new Sphere(0.6f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladChwytak);
		Transform3D p_sphere2 = new Transform3D();
		p_sphere2.setTranslation(new Vector3f(3.0f, 0.f, 0.0f));
		TransformGroup t_sphere2 = new TransformGroup (p_sphere2);
		t_sphere2.setBounds(new BoundingSphere(new Point3d(5.0d,6.0d,0.0d),0.6d));
		t_sphere2.addChild(sphere2);
		t_przesunieta2_ram2.addChild(t_sphere2);


		wezel_scena.addChild(t_gen);
		//KULA DOLNA (nieruchoma)--------------------------------------------------zmieniłem!!!//
		Appearance  wygladObiektu= new Appearance();
		Texture texObiekt1 = new TextureLoader( "ball.jpeg", this).getTexture();
		if (texObiekt1 != null)
		{wygladObiektu.setTexture(texObiekt1); }

		Sphere KulaDolna = new Sphere(1.6f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladObiektu);

		Transform3D  p_KuliDolnej   = new Transform3D();
		p_KuliDolnej.set(new Vector3f(11.4f,1.8f,0.3f));
		TransformGroup transformacja_s2 = new TransformGroup(p_KuliDolnej);

		transformacja_s2.addChild(KulaDolna);
		t_gen.addChild(transformacja_s2);

		///KONIEC KODU

		/// Interakcja z robotem, obroty, nie działą obrót 1 ramienia tak jak powinien--------------------------//

		Obrot_podstawy_robota obrot_podstawy = new Obrot_podstawy_robota(t_walca_2);
		obrot_podstawy.setSchedulingBounds(new BoundingSphere());
		wezel_scena.addChild(obrot_podstawy);

		Obrot_ramienia obrot_ramienia = new Obrot_ramienia(t_zaok1_ram_2);
		obrot_ramienia.setSchedulingBounds(new BoundingSphere());
		wezel_scena.addChild(obrot_ramienia);

		Obrot_ramienia_2 obrot_ramienia_2 = new Obrot_ramienia_2(t_przesunieta0_ram2);
		obrot_ramienia_2.setSchedulingBounds(new BoundingSphere());
		wezel_scena.addChild(obrot_ramienia_2);

		Point3d point = new Point3d(9.2d, 0.7d, 0.3d); ///Zmieniłem!!!

		KulaDolna.setBounds(new BoundingSphere(point, 0.1d));
		Coll coll = new Coll(KulaDolna, KulaDolna.getBounds());
		wezel_scena.addChild(coll);

		return wezel_scena;


	}

	public Canvas3D canv_KeyListener(Canvas3D canvas3D) {



		canvas3D.addKeyListener(new KeyListener(){
									public void keyPressed(KeyEvent e){
										switch(e.getKeyChar()){
											case 'q':      klawisze[0] = true; break;
											case 'w':      klawisze[1] = true; break;
											case 'e':      klawisze[2] = true; break;
											case 'a':      klawisze[3] = true; break;
											case 's':      klawisze[4] = true; break;
											case 'd':      klawisze[5] = true; break;

										}
									}

									public void keyReleased(KeyEvent e){
										switch(e.getKeyChar()){
											case 'q':    klawisze[0] = false; break;
											case 'w':    klawisze[1] = false; break;
											case 'e':    klawisze[2] = false; break;
											case 'a':    klawisze[3] = false; break;
											case 's':    klawisze[4] = false; break;
											case 'd':    klawisze[5] = false; break;

										}
									}

									public void keyTyped(KeyEvent e){
									}
								}
		);

		return canvas3D;
	}

	//obrot podstawy robota prawo-lewo-------------------------------------------------------------------------------//
	public class Obrot_podstawy_robota extends Behavior{
		private TransformGroup ref_do_tg;
		private Transform3D obrot=new Transform3D();
		private float kat;

		Obrot_podstawy_robota(TransformGroup th){
			this.ref_do_tg=th;
		}
		@Override
		public void initialize() {
			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}
		@Override
		public void processStimulus(Enumeration enmrtn) {
			if(klawisze[0]&&(kat<3.14))
				kat+=0.03f;
			if(klawisze[2]&&(kat>-3.14))
				kat-=0.03f;
			obrot.rotY(kat);
			ref_do_tg.setTransform(obrot);
			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}
	}

	//obrot ramienia 2 robota góra-dół--------------------------------------------------------------------------------//
	public class Obrot_ramienia_2 extends Behavior{
		private TransformGroup ref_do_tg;
		private Transform3D obrot=new Transform3D();
		private float kat;

		Obrot_ramienia_2 (TransformGroup th){
			this.ref_do_tg=th;
		}
		@Override
		public void initialize() {
			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}
		@Override
		public void processStimulus(Enumeration enmrtn) {
			if(klawisze[3]&&(kat<2.85))
				kat+=0.03f;
			if(klawisze[5]&&(kat>-2.85))
				kat-=0.03f;
			obrot.rotZ(kat);

			ref_do_tg.setTransform(obrot);

			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}



	}

	/// Obrót pierwszego ramienia robota góra-dół-----------------------------------///
	public class Obrot_ramienia extends Behavior{
		private TransformGroup ref_do_tg;
		private Transform3D obrot=new Transform3D();

		private float kat;

		Obrot_ramienia(TransformGroup th){
			this.ref_do_tg = th;


		}
		@Override
		public void initialize() {
			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}
		@Override
		public void processStimulus(Enumeration enmrtn) {
			if(klawisze[1]&&(kat<3.14))
				kat+=0.03f;
			if(klawisze[4]&&(kat>-3.14))
				kat-=0.03f;

			obrot.rotZ(kat);

			ref_do_tg.setTransform(obrot);
			this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
		}
	}

	/**
	 *
	 * @author sawyera.2016
	 */
	public class Coll extends Behavior {
		/** The separate criteria used to wake up this beahvior. */
		protected WakeupCriterion[] theCriteria;

		/** The OR of the separate criteria. */
		protected WakeupOr oredCriteria;

		/** The shape that is watched for collision. */
		protected Shape3D collidingShape;

		protected Sphere theShape2;

		/**
		 * @param theShape
		 *            Shape3D that is to be watched for collisions.
		 * @param theBounds
		 *            Bounds that define the active region for this behaviour
		 */
		public Coll(Sphere theShape, Bounds theBounds) {
			theShape2 = theShape;
			setSchedulingBounds(theBounds);
		}


		/**
		 * This creates an entry, exit and movement collision criteria. These are
		 * then OR'ed together, and the wake up condition set to the result.
		 */
		public void initialize() {
			theCriteria = new WakeupCriterion[3];
			theCriteria[0] = new WakeupOnCollisionEntry(theShape2);
			theCriteria[1] = new WakeupOnCollisionExit(theShape2);
			theCriteria[2] = new WakeupOnCollisionMovement(theShape2);
			oredCriteria = new WakeupOr(theCriteria);
			wakeupOn(oredCriteria);
		}

		/**
		 * Where the work is done in this class. A message is printed out using the
		 * userData of the object collided with. The wake up condition is then set
		 * to the OR'ed criterion again.
		 */
		public void processStimulus(Enumeration criteria) {
			WakeupCriterion theCriterion = (WakeupCriterion) criteria.nextElement();
			if (theCriterion instanceof WakeupOnCollisionEntry) {
				Node theLeaf = ((WakeupOnCollisionEntry) theCriterion)
						.getTriggeringPath().getObject();
				System.out.println("Collided with " + theLeaf.getUserData());
			} else if (theCriterion instanceof WakeupOnCollisionExit) {
				Node theLeaf = ((WakeupOnCollisionExit) theCriterion)
						.getTriggeringPath().getObject();
				System.out.println("Stopped colliding with  "
						+ theLeaf.getUserData());
			} else {
				Node theLeaf = ((WakeupOnCollisionMovement) theCriterion)
						.getTriggeringPath().getObject();
				System.out.println("Moved whilst colliding with "
						+ theLeaf.getUserData());
			}
			wakeupOn(oredCriteria);
		}
	}

}