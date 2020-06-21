package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Matrix3f;
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
    private Timer zegar = new Timer(); 
    private boolean CzyNagrywa = false;
    private boolean przenoszenie = false;
    private boolean odtworz_ruch = false;
    private boolean CzyDzwiek = true;
    
    //Przesuwanie kostki
    public float pozycjaX = 0.0f;
    public float pozycjaY = 0.0f;
    public float pozycjaZ = 0.0f;
        
	// katy przesunięć robota
    private float α_podstawa = 0f; // kąt przesunięcia bazy robota
    private float α_przegub = 0f; // kat przesuniecia ramienia1
    private float α_przegub2 = 0f;
    
    ArrayList <PozycjaRobota> nagranie = new ArrayList<PozycjaRobota>();
    int klatka = 0;
                 
    private Sphere sphere2;
    private Sphere KulaDolna;
    private Box ram2;
    
    private Transform3D p_walca_2 = new Transform3D();
    private Transform3D p_zaok1_ram_2 = new Transform3D();
    private Transform3D p_przesuniety0_ram2 = new Transform3D();
    private TransformGroup t_przesunieta0_ram2 = new TransformGroup (p_przesuniety0_ram2);
    private TransformGroup t_zaok1_ram_2 = new TransformGroup (p_zaok1_ram_2);
    private TransformGroup t_walca_2 = new TransformGroup (p_walca_2);
   
    private Transform3D p_sphere2 = new Transform3D();
    private TransformGroup t_sphere2 = new TransformGroup(p_sphere2);
    private Transform3D  p_KuliDolnej   = new Transform3D();
    private TransformGroup transformacja_s2 = new TransformGroup(p_KuliDolnej);
    
	public Ramie(){

		this.klawisze = new boolean[6];
		Appearance  wygladChwytak = new Appearance();

		Texture texChwytak= new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texChwytak != null)
		{wygladChwytak.setTexture(texChwytak); }
		this.sphere2 = new Sphere(0.6f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladChwytak);
		
		Appearance  wygladRamienia2 = new Appearance();

		Texture texRamie2 = new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texRamie2 != null)
		{wygladRamienia2.setTexture(texRamie2); }
		this.ram2 = new Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia2);
		//zapamiętywanie pozycji robota
		 //zegar aby odświeżać ekran
        zegar.scheduleAtFixedRate(new Poruszanie(), 0, 10);
        new Timer().scheduleAtFixedRate(new OdegranieRuchu(), 50, 50);
        
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
		
		p_walca_2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
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
		
		p_zaok1_ram_2.setTranslation(new Vector3f(0.0f, 0.f, 0.0f));
		t_zaok1_ram_2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D p_zaok1_ram_3 = new Transform3D();
		p_zaok1_ram_3.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		TransformGroup t_zaok1_ram_3 = new TransformGroup (p_zaok1_ram_3);

		t_walca.addChild(t_zaok1_ram);
		t_zaok1_ram.addChild(t_zaok1_ram_2);
		t_zaok1_ram_2.addChild(t_zaok1_ram_3);

		t_zaok1_ram_3.addChild(zaok1_ram);

		Box ram = new com.sun.j3d.utils.geometry.Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia1);

		Transform3D p_przesuniety1_ram = new Transform3D();
		p_przesuniety1_ram.setTranslation(new Vector3f(2.0f, 0.0f, 0.5f));
		TransformGroup t_przesunieta1_ram = new TransformGroup (p_przesuniety1_ram);


		Transform3D p_przesuniety0_ram = new Transform3D();
		p_przesuniety0_ram.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));

		TransformGroup t_przesunieta0_ram = new TransformGroup (p_przesuniety0_ram);

		Transform3D p_przesuniety2_ram = new Transform3D();
		p_przesuniety2_ram.setTranslation(new Vector3f(0.8f, 0.f, 0.0f));
		TransformGroup t_przesunieta2_ram = new TransformGroup (p_przesuniety2_ram);

		t_zaok1_ram_3.addChild(t_przesunieta1_ram);
		t_przesunieta1_ram.addChild(t_przesunieta0_ram);
		t_przesunieta0_ram.addChild(t_przesunieta2_ram);

		t_przesunieta2_ram.addChild(ram);
		
		//przedramię----------------------------------------------------------------------//
		Appearance  wygladRamienia2 = new Appearance();

		Texture texRamie2 = new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texRamie2 != null)
		{wygladRamienia2.setTexture(texRamie2); }
		ram2 = new Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia2);

		Transform3D p_przesuniety1_ram2 = new Transform3D();
		p_przesuniety1_ram2.setTranslation(new Vector3f(2.8f, 0.0f, 0.9f));
		TransformGroup t_przesunieta1_ram2 = new TransformGroup (p_przesuniety1_ram2);
		
		p_przesuniety0_ram2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		t_przesunieta0_ram2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D p_przesuniety2_ram2 = new Transform3D();
		p_przesuniety2_ram2.setTranslation(new Vector3f(2.6f, 0.0f, 0.0f));
		TransformGroup t_przesunieta2_ram2 = new TransformGroup (p_przesuniety2_ram2);

		t_przesunieta2_ram.addChild(t_przesunieta1_ram2);
		t_przesunieta1_ram2.addChild(t_przesunieta0_ram2);
		t_przesunieta0_ram2.addChild(t_przesunieta2_ram2);

		t_przesunieta2_ram2.addChild(ram2);

		//zaokrąglenie przedramienia------------------------------------------------------------//
		Sphere zaok1_ram2 = new Sphere(0.65f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia1);
		Transform3D p_zaok1_ram2 = new Transform3D();
		p_zaok1_ram2.setTranslation(new Vector3f(-2.8f, 0.0f, -0.4f));
		TransformGroup t_zaok1_ram2 = new TransformGroup (p_zaok1_ram2);
		t_zaok1_ram2.addChild(zaok1_ram2);
		
		t_przesunieta2_ram2.addChild(t_zaok1_ram2);
		
		t_przesunieta2_ram2.setBounds(new BoundingBox(new Point3d(0.0d,3.2d, 0.0d), new Point3d(5.0d ,3.6d, 0.4d)));
		
		p_sphere2 = new Transform3D();
		p_sphere2.setTranslation(new Vector3f(3.0f, 0.f, 0.0f));
		t_sphere2 = new TransformGroup (p_sphere2);
		t_sphere2.setBounds(new BoundingSphere(new Point3d(5.4d,3.6d,1.0d),0.4d));
		t_sphere2.addChild(sphere2);
		t_przesunieta2_ram2.addChild(t_sphere2);


		wezel_scena.addChild(t_gen);
		//KULA DOLNA (nieruchoma)--------------------------------------------------zmieniłem!!!//
		Appearance  wygladObiektu= new Appearance();
		Texture texObiekt1 = new TextureLoader( "ball.jpeg", this).getTexture();
		if (texObiekt1 != null)
		{wygladObiektu.setTexture(texObiekt1); }

		pozycjaX = 11.4f;
        pozycjaY = 1.8f;
        pozycjaZ = 0.3f;
        
		KulaDolna = new Sphere(1.6f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladObiektu);		
		       
        p_KuliDolnej   = new Transform3D();
  		p_KuliDolnej.set(new Vector3f(pozycjaX,pozycjaY,pozycjaZ));
		transformacja_s2 = new TransformGroup(p_KuliDolnej);
		transformacja_s2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		transformacja_s2.addChild(KulaDolna);
		t_gen.addChild(transformacja_s2);

		///KONIEC KODU

		Point3d point = new Point3d(11.4d, 1.8d, 0.3d); ///Zmieniłem!!!

		KulaDolna.setBounds(new BoundingSphere(point, 0.1d));
		Coll coll = new Coll(KulaDolna, KulaDolna.getBounds());
		wezel_scena.addChild(coll);
		
		return wezel_scena;
		
		/*
		 * żebym nie zapomniał
		 * jutro zmienić collision, pobierać pozycję kulki i podłogi i jeśli robot na to najedzie to mu zabraniać,
		 * zamiast sfery zrobić boxa/cuba, będzie łatwiej blokować ruch
		 * 
		 * względem nagrywania po prostu odtworzyć sekwencje ruchów, nie bawić sie w timery,
		 * wwalić to do wektora/arraya, najlepiej jakoś z odpowiednimi ruchami tj.
		 * który się poruszył i w którą stronę
		 * 
		 * wyswietlac napis ktory powie czy mozemy zlapac kostke czy nie
		 * addchild i wgl do kostki
		 * */

	}
	
	
	
	
	

	public Canvas3D canv_KeyListener(Canvas3D canvas3D) {



		canvas3D.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				
				if (CzyNagrywa) {
	                nagranie.add(new PozycjaRobota(α_podstawa, α_przegub, α_przegub2, przenoszenie));
	            }
				//jesli to zniknie to wrociles do swojego programu debilu
				switch(e.getKeyChar()){
					case 'q':      klawisze[0] = true; if(α_podstawa<3.14) 
														α_podstawa += 0.03f; break;
														
					case 'w':      klawisze[1] = true; if(α_przegub2<2.85) 
														α_przegub2 += 0.03f; break;
														
					case 'e':      klawisze[2] = true; if(α_podstawa>-3.14) 
														α_podstawa -= 0.03f;break;
														
					case 'a':      klawisze[3] = true; if(α_przegub<2.85) 
														α_przegub += 0.03f; break;
														
					case 's':      klawisze[4] = true; if(α_przegub2>-2.85) 
														α_przegub2 -= 0.03f; break;
														
					case 'd':      klawisze[5] = true; if(α_przegub>-2.85) 
														α_przegub -= 0.03f; break;
														
					//recording
					case 'm':	   { CzyNagrywa = true;
     			   					nagranie.clear();
     			   					klatka = 0;
     			   					System.out.println("nagrywanie "); break;}
					case 'n':       {CzyNagrywa = false;
									odtworz_ruch = true; 
									System.out.println("Odtwarzanie"); break;}
					case 'r':	    odtworz_ruch = !odtworz_ruch; System.out.println("Stop odtwarzania"); break;
					case 'p':		Reset_robota(); break;
					case 'k':		przenoszenie = !przenoszenie; break;
								   
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
	
	public void Reset_robota(){

        α_przegub2 = 0.0f;
        α_przegub = 0.0f;
        α_podstawa = 0.0f;

    }

    public void nagrywajmy(){
    	CzyNagrywa = true;
        nagranie.clear();
        klatka = 0;
        System.out.println("nagrywanie ");
        }

    public void odtwarzajmy(){
        CzyNagrywa = false;
        odtworz_ruch = true;
        System.out.println("Odtwarzanie");
    }

    public void koniec_odtwarzania(){
        odtworz_ruch = !odtworz_ruch; 
        System.out.println("Stop odtwarzania");
    }
	

	
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
			if(criteria.hasMoreElements()){
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
	
	
	 class PozycjaRobota {
	        
	        float α_podstawa;
	        float α_przegub;
	        float α_przegub2;
	        boolean przenoszenie;
	        
	            public PozycjaRobota(float α_podstawa, float α_przegub, float α_przegub2, boolean przenoszenie) {
	                this.α_podstawa = α_podstawa;
	                this.α_przegub = α_przegub;
	                this.α_przegub2 = α_przegub2;
	                this.przenoszenie = przenoszenie;
	                
	            }
	                                      
	    
	    }
	  
	  class OdegranieRuchu extends TimerTask {

	        @Override
	        public void run() {
	            if (!odtworz_ruch || nagranie.isEmpty())
	                return;
	            if (klatka >= nagranie.size()){
	                klatka = 0;
	               
	            }
	            
	            PozycjaRobota NumerKlatki = nagranie.get(klatka);
	            α_podstawa = NumerKlatki.α_podstawa;
	            α_przegub = NumerKlatki.α_przegub;
	            α_przegub2 = NumerKlatki.α_przegub2;
	            przenoszenie = NumerKlatki.przenoszenie; 
	            klatka++;
	            
	            
	           
	        }
	    }
	 
	//tutaj należy ustawić pozycję początkową
		 private class Poruszanie extends TimerTask {

			 	Transform3D obrot = new Transform3D();
			 	Transform3D obrot2 = new Transform3D();
			 	Transform3D obrot3 = new Transform3D();
			 	Transform3D kula3D = new Transform3D();
		        @Override
		        public void run() {

		        	//if(odtworz_ruch) {
		        	
		          obrot.rotY(α_podstawa);
		          obrot2.rotZ(α_przegub);
		          obrot3.rotZ(α_przegub2);
		          
		          t_walca_2.setTransform(obrot);
		          t_zaok1_ram_2.setTransform(obrot3);
		          t_przesunieta0_ram2.setTransform(obrot2);
		          
		         
		          
		          transformacja_s2.setTransform(kula3D);
		          sphere2.getLocalToVworld(p_sphere2);
		          ram2.getLocalToVworld(p_przesuniety0_ram2);
		          Vector3f position = new Vector3f();
		          p_sphere2.get(position);
		          Vector3f positionramie = new Vector3f();
		          p_przesuniety0_ram2.get(positionramie);
		           		         
		          if (przenoszenie) {
		                /*float f1 = 2.89f;
		                float f2 = 1.04f;
		                
		               
		                System.out.print(positionramie);
		                
		                pozycjaX = position.x * f1 - f2 * positionramie.x;
		                pozycjaY = position.y * f1 - f2 * positionramie.y;
		                pozycjaZ = position.z * f1 - f2 * positionramie.z;*/
		        	  System.out.print(position);
		        	  System.out.println('\n');
		        	    pozycjaX = position.x;
		        	    pozycjaY = position.y;
		        	    pozycjaZ = position.z;
		                kula3D.setTranslation(new Vector3f(pozycjaX+0.8f, pozycjaY+0.8f, pozycjaZ));

		            } else {
		                if (pozycjaY > 1.79f) {
		                    pozycjaY -= 0.04f;
		                    kula3D.setTranslation(new Vector3f(pozycjaX+0.8f, pozycjaY+0.8f, pozycjaZ));

		                	}
		              }
		        }
		  }
}