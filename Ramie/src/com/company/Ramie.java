package com.company;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;

public class Ramie {

	public Ramie(){
		BranchGroup robot = Robot();
		
	}
	
	
	BranchGroup Robot() {
		
		BranchGroup wezel_scena = new BranchGroup();
		
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
	      
	      
	      //walec nieruchomy-----------------------------------------------------------//
	      Appearance  wygladCylindra = new Appearance();
	      wygladCylindra.setMaterial(niebieski);
	      Transform3D p_walca = new Transform3D();
	      p_walca.setTranslation(new Vector3f(0.0f, 3.4f, 0.0f));
	      TransformGroup t_walca = new TransformGroup (p_walca);
	      Cylinder walec = new Cylinder(0.7f, 6.4f, wygladCylindra);
	      t_walca.addChild(walec);
	      t_podstawy.addChild(t_walca);
	      
	      //ramię----------------------------------------------------------------------//
	      Appearance  wygladRamienia1 = new Appearance();
	      wygladRamienia1.setMaterial(zielonkawy);
	      com.sun.j3d.utils.geometry.Box ram = new com.sun.j3d.utils.geometry.Box(2.8f, 0.18f, 0.6f, wygladRamienia1);
	      
	      Transform3D p_przesuniety1_ram = new Transform3D();
	      p_przesuniety1_ram.setTranslation(new Vector3f(2.0f, 3.0f, 0.8f));
	      
	      TransformGroup t_przesunieta1_ram = new TransformGroup (p_przesuniety1_ram);
	      
	      Transform3D p_przesuniety0_ram = new Transform3D();
	      p_przesuniety0_ram.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      TransformGroup t_przesunieta0_ram = new TransformGroup (p_przesuniety0_ram);
	      t_przesunieta0_ram.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      
	      Transform3D p_przesuniety2_ram = new Transform3D();
	      p_przesuniety2_ram.setTranslation(new Vector3f(2.8f, 0.f, 0.0f));
	      p_przesuniety2_ram.rotX(Math.PI/2.0);
	      
	      
	      TransformGroup t_przesunieta2_ram = new TransformGroup (p_przesuniety2_ram);
	      
	      
	      
	      t_walca.addChild(t_przesunieta1_ram);
	      t_przesunieta1_ram.addChild(t_przesunieta0_ram);
	      t_przesunieta0_ram.addChild(t_przesunieta2_ram);
	      
	      t_przesunieta2_ram.addChild(ram);
	      
	      //zaokrąglenie ramienia------------------------------------------------------------//
	      Cylinder zaok1_ram = new Cylinder(0.6f, 0.36f, wygladRamienia1);
	      Transform3D p_zaok1_ram = new Transform3D();
	      p_zaok1_ram.setTranslation(new Vector3f(-2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok1_ram = new TransformGroup (p_zaok1_ram);
	      t_zaok1_ram.addChild(zaok1_ram);
	      t_przesunieta2_ram.addChild(t_zaok1_ram);
	      
	      Cylinder zaok2_ram = new Cylinder(0.6f, 0.36f, wygladRamienia1);
	      Transform3D p_zaok2_ram = new Transform3D();
	      p_zaok2_ram.setTranslation(new Vector3f(2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok2_ram = new TransformGroup (p_zaok2_ram);
	      t_zaok2_ram.addChild(zaok2_ram);
	      t_przesunieta2_ram.addChild(t_zaok2_ram);
	     
	      //przedramię----------------------------------------------------------------------// 
	      com.sun.j3d.utils.geometry.Box ram2 = new com.sun.j3d.utils.geometry.Box(2.8f, 0.18f, 0.6f, wygladRamienia1);
	      
	      Transform3D p_przesuniety1_ram2 = new Transform3D();
	      p_przesuniety1_ram2.setTranslation(new Vector3f(2.8f, 0.0f, 0.0f));
	      TransformGroup t_przesunieta1_ram2 = new TransformGroup (p_przesuniety1_ram2);
	      
	      Transform3D p_przesuniety0_ram2 = new Transform3D();
	      p_przesuniety0_ram2.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      TransformGroup t_przesunieta0_ram2 = new TransformGroup (p_przesuniety0_ram2);
	      t_przesunieta0_ram2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      
	      Transform3D p_przesuniety2_ram2 = new Transform3D();
	      p_przesuniety2_ram2.setTranslation(new Vector3f(2.8f, -0.36f, 0.0f));
	      TransformGroup t_przesunieta2_ram2 = new TransformGroup (p_przesuniety2_ram2);
	      
	      t_przesunieta2_ram.addChild(t_przesunieta1_ram2);
	      t_przesunieta1_ram2.addChild(t_przesunieta0_ram2);
	      t_przesunieta0_ram2.addChild(t_przesunieta2_ram2);
	      
	      t_przesunieta2_ram2.addChild(ram2);
	      
	      //zaokrąglenie przedramienia------------------------------------------------------------//
	      Cylinder zaok1_ram2 = new Cylinder(0.6f, 0.36f, wygladRamienia1);
	      Transform3D p_zaok1_ram2 = new Transform3D();
	      p_zaok1_ram2.setTranslation(new Vector3f(-2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok1_ram2 = new TransformGroup (p_zaok1_ram2);
	      t_zaok1_ram2.addChild(zaok1_ram2);
	      t_przesunieta2_ram2.addChild(t_zaok1_ram2);
	      
	      Cylinder zaok2_ram2 = new Cylinder(0.6f, 0.36f, wygladRamienia1);
	      Transform3D p_zaok2_ram2= new Transform3D();
	      p_zaok2_ram2.setTranslation(new Vector3f(2.8f, 0.f, 0.0f));
	      TransformGroup t_zaok2_ram2 = new TransformGroup (p_zaok2_ram2);
	      t_zaok2_ram2.addChild(zaok2_ram2);
	      t_przesunieta2_ram2.addChild(t_zaok2_ram2);

	      
	      //walec ruchomy-----------------------------------------------------------//
	      /*
	      Cylinder walec2 = new Cylinder(0.24f, 6.5f,wygladCylindra);
	      
	      Transform3D p_przesuniety1_walca = new Transform3D();
	      p_przesuniety1_walca.setTranslation(new Vector3f(2.8f, 0.0f, 0.0f));
	      TransformGroup t_przesunieta1_walca = new TransformGroup (p_przesuniety1_walca);
	      
	      Transform3D p_przesuniety0_walca = new Transform3D();
	      p_przesuniety0_walca.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
	      TransformGroup t_przesunieta0_walca = new TransformGroup (p_przesuniety0_walca);
	      t_przesunieta0_walca.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	      
	      t_przesunieta2_ram2.addChild(t_przesunieta1_walca);
	      t_przesunieta1_walca.addChild(t_przesunieta0_walca);
	      
	      t_przesunieta0_walca.addChild(walec2);
	      
	 
	      
	      */
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
	    
	    
		return wezel_scena;
		
		
	}
}
