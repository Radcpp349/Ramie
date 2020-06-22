package com.company;
import java.lang.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;



// Główna klasa obsługi programu
public class Ramie extends JFrame {

	/*Zmienne pomocnicze, odpowiedzialne za poszczególne funkcje*/
	
    private Timer zegar = new Timer(); 
    private boolean CzyNagrywa = false;
    private boolean przenoszenie = false;
    private boolean odtworz_ruch = false;
    private boolean CzyDzwiek = true;
    
    //Przesuwanie obiektu
    public float coordX = 0.0f;
    public float coordY = 0.0f;
    public float coordZ = 0.0f;
        
	// katy rotacji części robota
    private float rot_podstawy = 0f; // kąt przesunięcia bazy robota
    private float rot_przedramienia = 0f; // kat przesuniecia ramienia1
    private float rot_ramienia = 0f;
    
    // Lista położeń robota do odtwarzania "nagrania"
    ArrayList <PozycjaRobota> nagranie = new ArrayList<PozycjaRobota>();
    int czas = 0;
    
    // Obiekty na które wpływa program             
    private Sphere sphere2;
    private Sphere KulaDolna;
    private Box ram2;
    
    // Transformaty obiektów
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
    
    
    // Konstruktor
	public Ramie(){

		
		//tworzenie obiektów i nakładanie tekstur//
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
		
		//zapamiętywanie pozycji robota//
		//zegar do odświeżania ekranu//
		
        zegar.scheduleAtFixedRate(new Poruszanie(), 0, 10);
        new Timer().scheduleAtFixedRate(new OdegranieRuchu(), 50, 50);
        
	}
	
	//Główna funkcja programu, odpowiezdzialna za stworzenie a następnie "aktualizowanie" robota//
	BranchGroup Robot() {

		BranchGroup wezel_scena = new BranchGroup();
		
		//Początkowe transformacje służące stworzeniu robota
		Transform3D p_gen = new Transform3D();
		p_gen.setTranslation(new Vector3f(-4.0f, -4.0f, 0.0f));
		TransformGroup t_gen = new TransformGroup (p_gen);
		t_gen.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

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

		//Podstawa ruchoma robota, walec, tworzenie odpowiednich transformacji i dodawanie ich do robota//
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

		//Zakonczenie walca, zaaokrąglenie końcówki, poprawa wizualna ---------------------------------------------------------//
		Sphere sphere1 = new Sphere(0.8f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,wygladCylindra);
		Transform3D p_sphere1 = new Transform3D();
		p_sphere1.setTranslation(new Vector3f(0.0f, 3.7f, 0.0f));
		TransformGroup t_sphere1 = new TransformGroup (p_sphere1);
		t_sphere1.addChild(sphere1);
		t_walca.addChild(t_sphere1);

		//ramię robota, nałożenie tekstur, utworzenie transformaci i dodanie do podstawy robota//

		Appearance  wygladRamienia1 = new Appearance();
		Texture texRamie1 = new TextureLoader( "blacha.jpeg", this).getTexture();
		if (texRamie1 != null)
		{wygladRamienia1.setTexture(texRamie1); }

		Cylinder zaok1_ram = new Cylinder(0.6f, 1.36f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD,wygladRamienia1);

		//Dodanie pomocniczych transformacji, w celu poprawnego działania obrotów //
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
		
		//Stworzenie ramienia i dodanie mu odpowiednich transformacji //
		Box ram = new Box(2.8f, 0.6f, 0.18f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladRamienia1);

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
		
		//Stworzenie przedramięnia----------------------------------------------------------------------//
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

		//zaokrąglenie przedramienia, poprawa wizualna------------------------------------------------------------//
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

		//Dodanie robota do głównego brancha wywoływanego w World.java //
		wezel_scena.addChild(t_gen);
		
		//KULA DOLNA, obiekt którym możemy manipulować za pomocą robota--------------------------------------------------//
		Appearance  wygladObiektu= new Appearance();
		Texture texObiekt1 = new TextureLoader( "ball.jpeg", this).getTexture();
		if (texObiekt1 != null)
		{wygladObiektu.setTexture(texObiekt1); }
		
		//Początkowe położenie kuli //
		coordX = 11.4f;
        coordY = 1.8f;
        coordZ = 0.3f;
        
		KulaDolna = new Sphere(1.6f,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS | Primitive.GENERATE_NORMALS_INWARD, wygladObiektu);		
		       
        p_KuliDolnej   = new Transform3D();
  		p_KuliDolnej.set(new Vector3f(coordX,coordY,coordZ));
		transformacja_s2 = new TransformGroup(p_KuliDolnej);
		transformacja_s2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		transformacja_s2.addChild(KulaDolna);
		t_gen.addChild(transformacja_s2);

		///KONIEC KODU RAMIENIA ROBOTA

		Point3d point = new Point3d(11.4d, 1.8d, 0.3d); ///Zmieniłem!!!
		
		return wezel_scena;

	}
		
	/*Key listener programu wyczekujący na input użytkownika(wciśnięcie odpowiedniego przycisku na klawiaturze*/
	public Canvas3D canv_KeyListener(Canvas3D canvas3D) {

		canvas3D.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){

				//Zapisujemy współrzędne środka Kuli w wektorze aby stworzyć kolizje z nią

				Vector3f centrum = new Vector3f();
				KulaDolna.getLocalToVworld(p_KuliDolnej);
				p_KuliDolnej.get(centrum);
				float radius;
				float distance;
				radius=KulaDolna.getRadius();

				//zapisujemy pozycje chwytaka
				Vector3f positionprzedramie = new Vector3f();
				p_sphere2.get(positionprzedramie);
				Vector3f oddalenie = new Vector3f((centrum.getX()-positionprzedramie.getX())*(centrum.getX()-positionprzedramie.getX()),(centrum.getY()-positionprzedramie.getY())*(centrum.getY()-positionprzedramie.getY())
				,(centrum.getZ()-positionprzedramie.getZ())*(centrum.getZ()-positionprzedramie.getZ()));

				distance=oddalenie.length();
				
				//warunek sprawdzający czy progra ma nagrywac ruchy robota
				if (CzyNagrywa) {
	                nagranie.add(new PozycjaRobota(rot_podstawy, rot_przedramienia, rot_ramienia, przenoszenie));
	            }


				switch(e.getKeyChar()){
					//Klawisze odpowiedzialne za ruch robota
					case 'q':
									if(rot_podstawy<3.14)
									 rot_podstawy += 0.03f; break;
														
					case 'w':       if(rot_ramienia<2.85)
									 rot_ramienia += 0.03f; break;
														
					case 'e':

						            if(rot_podstawy>-3.14)
									 rot_podstawy -= 0.03f;break;
														
					case 'a':       if(rot_przedramienia<1.05) 
									 rot_przedramienia += 0.03f; break;

					case 's':       if(blokada()){rot_ramienia += 0.03f;}
									if(blokada_z_obiektem()){rot_ramienia += 0.03f;}
								    if(rot_ramienia>-2.85)
								     rot_ramienia -= 0.03f; break;

					case 'd':      if(blokada()){rot_przedramienia += 0.03f;}
					 				if(blokada_z_obiektem()){rot_przedramienia += 0.03f;}
						 		   if(rot_przedramienia>-2.85)
						 			 rot_przedramienia -= 0.03f; break;
														
					//Klawisze odpowiedzialne za nagrywanie, odtwarzanie, reset robota, oraz podnoszenie obiektu //
					case 'm':	   { CzyNagrywa = true;
     			   					nagranie.clear();
     			   					czas = 0;
     			   					System.out.println("nagrywanie "); break;}
					case 'n':       {CzyNagrywa = false;
									odtworz_ruch = true; 
									System.out.println("Odtwarzanie"); break;}
					case 'r':	    odtworz_ruch = !odtworz_ruch; System.out.println("Stop odtwarzania"); break;
					case 'p':		Reset_robota(); break;
					case 'k': if(!przenoszenie) {if(Czy_mozna_zlapac()&&(!blokada_lapania()))  { przenoszenie = !przenoszenie;}} else przenoszenie = !przenoszenie; break;

								   
				}
			}

			public void keyReleased(KeyEvent e){
				switch(e.getKeyChar()){
					case 'q':    break;
					case 'w':    break;
					case 'e':    break;
					case 'a':    break;
					case 's':    break;
					case 'd':    break;
				}
			}

			public void keyTyped(KeyEvent e){
			}
		}
		);

		return canvas3D;
	}
	
	//metoda resetująca położenie robota do położenia początkowego //
	public void Reset_robota(){

        rot_ramienia = 0.0f;
        rot_przedramienia = 0.0f;
        rot_podstawy = 0.0f;

    }
	
	// Włączenie nagrywania //
    public void nagrywajmy(){
    	CzyNagrywa = true;
        nagranie.clear();
        czas = 0;
        System.out.println("nagrywanie ");
        }
    
    //Włączenie odtwarzania //
    public void odtwarzajmy(){
        CzyNagrywa = false;
        odtworz_ruch = true;
        System.out.println("Odtwarzanie");
    }
    
    //Zatrzymanie odtwarzania //
    public void koniec_odtwarzania(){
        odtworz_ruch = !odtworz_ruch; 
        System.out.println("Stop odtwarzania");
    }
    //blokada ruchu robota //
	public boolean blokada()
	{
		Vector3f positionprzedramie = new Vector3f();
		p_sphere2.get(positionprzedramie);
		if(positionprzedramie.getY()<=-3.0f){return true;}

		return false;
	}

	public boolean blokada_z_obiektem()
	{

		Vector3f positionprzedramie = new Vector3f();
		p_sphere2.get(positionprzedramie);

		if(przenoszenie){

			if(positionprzedramie.getY()<=-1.24f){return true;}

		}    return false;
	}

		public boolean blokada_lapania() {

			Vector3f positionprzedramie = new Vector3f();
			p_sphere2.get(positionprzedramie);
			if (positionprzedramie.getY() <= -1.24f) {
				return true;
			}


			return false;
		}

	public boolean Czy_mozna_zlapac()
	{
		float x;
		float y;
		float z;
		sphere2.getLocalToVworld(p_sphere2);
		KulaDolna.getLocalToVworld(p_KuliDolnej);
		Vector3f pozycja_chwytak = new Vector3f();
		p_sphere2.get(pozycja_chwytak);
		Vector3f pozycja_obiekt = new Vector3f();
		p_KuliDolnej.get(pozycja_obiekt);
		System.out.print(pozycja_obiekt.x);

		x = Math.abs(pozycja_obiekt.x-pozycja_chwytak.x);
		y = Math.abs(pozycja_obiekt.y-pozycja_chwytak.y);
		z = Math.abs(pozycja_obiekt.z-pozycja_chwytak.z);
		if(x<4.6f && y< 4.6f && z< 4.6f)
			return true;

		return false;
	}
	
	/*Klasa odpowiedzialna za pobieranie aktualnej pozycji robota do nagrania w celu odtworzenia ruchu*/
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
	  
	 /*Klasa odgrywająca ruch robota*/
	  class OdegranieRuchu extends TimerTask {

	        @Override
	        public void run() {
	            if (!odtworz_ruch || nagranie.isEmpty())
	                return;
	            if (czas >= nagranie.size()){
	                czas = 0;
	               
	            }
	            
	            PozycjaRobota NumerKlatki = nagranie.get(czas);
	            rot_podstawy = NumerKlatki.α_podstawa;
	            rot_przedramienia = NumerKlatki.α_przegub;
	            rot_ramienia = NumerKlatki.α_przegub2;
	            przenoszenie = NumerKlatki.przenoszenie; 
	            czas++;
	            	           
	        }
	    }
	 
	/*Klasa odpowiedzialna za ruch robota, główna funkcja, działa cały czas i "monitoruje" zmiany w położeniu robota oraz obiektu*/
		 private class Poruszanie extends TimerTask {

			 	Transform3D obrot = new Transform3D();
			 	Transform3D obrot2 = new Transform3D();
			 	Transform3D obrot3 = new Transform3D();
			 	Transform3D kula3D = new Transform3D();
		        @Override
		        public void run() {

		        	//Obroty robota //
		          obrot.rotY(rot_podstawy);
		          obrot2.rotZ(rot_przedramienia);
		          obrot3.rotZ(rot_ramienia);
		          
		          t_walca_2.setTransform(obrot);
		          t_zaok1_ram_2.setTransform(obrot3);
		          t_przesunieta0_ram2.setTransform(obrot2);
		          
		         
		          // Położenie kuli(obiektu) //
		          transformacja_s2.setTransform(kula3D);
		          sphere2.getLocalToVworld(p_sphere2);
		          ram2.getLocalToVworld(p_przesuniety0_ram2);
		          Vector3f position = new Vector3f();
		          p_sphere2.get(position);
		          Vector3f positionramie = new Vector3f();
		          p_przesuniety0_ram2.get(positionramie);
		           
		          //Kod odpowiedzialny za ruch obiektu po złapaniu go chwytakiem robota //
		          if (przenoszenie) {
		               
		        	    float f2 = 0.24f;
		        	    float f1 = 0.22f;
		        	    float f3 = 0.18f;
		        	    
		        	    coordX = position.x + 5.9f - f1 * (4.19631f - positionramie.x);
		        	    coordY = position.y + 3.9f -  f2 * (2.9459631f - positionramie.y);
		        	    coordZ = position.z - f3 * (2.6999998f - positionramie.z);
		                kula3D.setTranslation(new Vector3f(coordX, coordY, coordZ));

		            } else {
		                if (coordY > 1.799999f) {
		                    coordY -= 0.04f;
		                    kula3D.setTranslation(new Vector3f(coordX, coordY, coordZ));

		                	}
		              }
		        }
		  }
}