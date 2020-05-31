package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;


public class Ramie {
	
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
	      Transform3D p_podstawy = new Transform3D();
	      p_podstawy.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      TransformGroup t_podstawy = new TransformGroup (p_podstawy);
	      t_podstawy.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      com.sun.j3d.utils.geometry.Box podstawa = new com.sun.j3d.utils.geometry.Box(1.2f, 0.2f, 1.2f, wygladPodstawy);
	      t_podstawy.addChild(podstawa);
	      t_gen.addChild(t_podstawy);
	      
	      
	      //walec ruchomy-----------------------------------------------------------//
	      Appearance  wygladCylindra = new Appearance();
	      wygladCylindra.setMaterial(niebieski);
	      Transform3D p_walca = new Transform3D();
	      p_walca.setTranslation(new Vector3f(0.0f, 3.4f, 0.0f));
	      TransformGroup t_walca = new TransformGroup (p_walca);
	      Cylinder walec = new Cylinder(0.7f, 6.4f, wygladCylindra);
	      
	      Transform3D p_walca_2 = new Transform3D();
	      p_walca_2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      TransformGroup t_walca_2 = new TransformGroup (p_walca_2);
	      t_walca_2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      
	     
	      t_walca_2.addChild(t_walca);
	      t_podstawy.addChild(t_walca_2);
	      t_walca.addChild(walec);
	      
	      //Zakonczenie walca ---------------------------------------------------------//
	      Sphere sphere1 = new Sphere(0.8f,wygladCylindra);
	      Transform3D p_sphere1 = new Transform3D();
	      p_sphere1.setTranslation(new Vector3f(0.0f, 3.2f, 0.0f));
	      TransformGroup t_sphere1 = new TransformGroup (p_sphere1);
	      t_sphere1.addChild(sphere1);
	      t_walca.addChild(t_sphere1);
	      
	      //ramię----------------------------------------------------------------------//
	      Appearance  wygladRamienia1 = new Appearance();
	      wygladRamienia1.setMaterial(zielonkawy);
	      com.sun.j3d.utils.geometry.Box ram = new com.sun.j3d.utils.geometry.Box(2.8f, 0.6f, 0.18f, wygladRamienia1);
	      
	      Transform3D p_przesuniety1_ram = new Transform3D();
	      p_przesuniety1_ram.setTranslation(new Vector3f(2.0f, 3.0f, 0.8f));
	      TransformGroup t_przesunieta1_ram = new TransformGroup (p_przesuniety1_ram);
	      
	     
	      Transform3D p_przesuniety0_ram = new Transform3D();
	      p_przesuniety0_ram.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      
	      TransformGroup t_przesunieta0_ram = new TransformGroup (p_przesuniety0_ram);
	      t_przesunieta0_ram.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      	      
	      Transform3D p_przesuniety2_ram = new Transform3D();
	      p_przesuniety2_ram.setTranslation(new Vector3f(0.8f, 0.f, 0.0f));
	      TransformGroup t_przesunieta2_ram = new TransformGroup (p_przesuniety2_ram);
	      
	      	      
	      t_walca.addChild(t_przesunieta1_ram);
	      t_przesunieta1_ram.addChild(t_przesunieta0_ram);
	      t_przesunieta0_ram.addChild(t_przesunieta2_ram);
	      
	      t_przesunieta2_ram.addChild(ram);
	      
	      //zaokrąglenie ramienia------------------------------------------------------------//
	      //Zmieniłem cylindry z 0.36f do 1.36f, obrotu e sie na siebie nakłądają niestety :(------------//
	      Cylinder zaok1_ram = new Cylinder(0.6f, 1.36f, wygladRamienia1);
	      Transform3D p_zaok1_ram = new Transform3D();
	      p_zaok1_ram.setTranslation(new Vector3f(-2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok1_ram = new TransformGroup (p_zaok1_ram);
	      t_zaok1_ram.addChild(zaok1_ram);
	      t_przesunieta2_ram.addChild(t_zaok1_ram);
	      
	      Cylinder zaok2_ram = new Cylinder(0.6f, 1.36f, wygladRamienia1);
	      Transform3D p_zaok2_ram = new Transform3D();
	      p_zaok2_ram.setTranslation(new Vector3f(2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok2_ram = new TransformGroup (p_zaok2_ram);
	      t_zaok2_ram.addChild(zaok2_ram);
	      t_przesunieta2_ram.addChild(t_zaok2_ram);
	     
	      //przedramię----------------------------------------------------------------------// 
	      com.sun.j3d.utils.geometry.Box ram2 = new com.sun.j3d.utils.geometry.Box(2.8f, 0.6f, 0.18f, wygladRamienia1);
	      
	      Transform3D p_przesuniety1_ram2 = new Transform3D();
	      p_przesuniety1_ram2.setTranslation(new Vector3f(2.8f, 0.0f, 0.0f));
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
	      Cylinder zaok1_ram2 = new Cylinder(0.6f, 1.36f, wygladRamienia1);
	      Transform3D p_zaok1_ram2 = new Transform3D();
	      p_zaok1_ram2.setTranslation(new Vector3f(-2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok1_ram2 = new TransformGroup (p_zaok1_ram2);
	      t_zaok1_ram2.addChild(zaok1_ram2);
	      t_przesunieta2_ram2.addChild(t_zaok1_ram2);
	      
	      Sphere sphere2 = new Sphere(0.6f, wygladRamienia1);
	      Transform3D p_sphere2 = new Transform3D();
	      p_sphere2.setTranslation(new Vector3f(3.0f, 0.f, 0.0f));
	      TransformGroup t_sphere2 = new TransformGroup (p_sphere2);
	      t_sphere2.addChild(sphere2);
	      t_przesunieta2_ram2.addChild(t_sphere2);
	      
	 
	      wezel_scena.addChild(t_gen);
	      //STOZEK DOLNY (nieruchomy)--------------------------------------------------//
	      Appearance  wygladStozkaDolnego = new Appearance();
	      wygladStozkaDolnego.setMaterial(zielonkawy);
	      Cone stozekDolny = new Cone(2.0f,3.0f, wygladStozkaDolnego);

	      Transform3D  p_stozkaDolnego   = new Transform3D();
	      p_stozkaDolnego.set(new Vector3f(11.2f,0.0f,0.0f));
	      TransformGroup transformacja_s2 = new TransformGroup(p_stozkaDolnego);
	    
	      transformacja_s2.addChild(stozekDolny);
	      t_gen.addChild(transformacja_s2);
	    
	    ///KONIEC KODU
	      
	      /// Interakcja z robotem, obroty, nie działą obrót 1 ramienia tak jak powinien--------------------------//
	      
	      Obrot_podstawy_robota obrot_podstawy = new Obrot_podstawy_robota(t_walca_2);
	      obrot_podstawy.setSchedulingBounds(new BoundingSphere());
	      wezel_scena.addChild(obrot_podstawy);
	      
	      Obrot_ramienia obrot_ramienia = new Obrot_ramienia(t_przesunieta0_ram);
	      obrot_ramienia.setSchedulingBounds(new BoundingSphere());
	      wezel_scena.addChild(obrot_ramienia);
	      
	      Obrot_ramienia_2 obrot_ramienia_2 = new Obrot_ramienia_2(t_przesunieta0_ram2);
	      obrot_ramienia_2.setSchedulingBounds(new BoundingSphere());
	      wezel_scena.addChild(obrot_ramienia_2);
	      
	      
	    
	    
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
	
}

