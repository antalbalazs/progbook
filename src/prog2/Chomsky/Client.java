import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        String dontes;
        String mg;
        System.out.println("Egyjátékos vagy Többjátékos módban szeretnél játszani? ");
        dontes = sc.nextLine().toLowerCase();
        
        for(;;){
            if( dontes.equals("egyjátékos") || dontes.equals("egyjatekos") || dontes.equalsIgnoreCase("többjátékos") || dontes.equalsIgnoreCase("tobbjatekos")){
                break;
            }  
            
            else{
            System.out.println("Rosszul irtál valamit. A lehetséges válaszok: Egyjátékos Többjátékos");
            dontes = sc.nextLine().toLowerCase();
            }
            
        }
        
        if(dontes.equals("egyjátékos") || dontes.equals("egyjatekos")){
            egyjatekos();
        }
        
        else{
            try{
            tobbjatekos();
            }
            catch(IOException e){
                System.out.println("Hálózati hiba történt "+e);
            }
        }
    }
    
    public static void tobbjatekos() throws IOException{
        String mg;
        Socket socket = new Socket("127.0.0.1", PORT);
        socket.setKeepAlive(true);
        socket.setReuseAddress(true);
       
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream() );
        
        Scanner sc = new Scanner(System.in);
        
        int maxgondolhato;
        int korokszama;
        int gondolt;
        
       boolean tmpReader = dis.readBoolean();
       
        if(!tmpReader){
         System.out.println("Sikeresen kapcsolódtál a szerverhez. Jelenleg egy másik játékosra várunk.");
         
        }
        
        else{
            System.out.println("Sikeresen kapcsolódtál a szerverhez. Már csatlakozott egy másik játékos. Kezdődik a játék.");
        }  
        boolean tmpReader2 = dis.readBoolean();
        
        if(tmpReader2){
            System.out.println("Csatlakozott egy másik játékos. Kezdődik a játék.");
        }
        
        korokszama = dis.readInt();
        maxgondolhato = dis.readInt();
        
        System.out.println("A játék "+korokszama+" körös lesz.");
        System.out.println("A maximum szám amire gondolni lehet: "+maxgondolhato);
                
        
        boolean kijon;
        String allapot;
        String servermessage;
        String clientmessage;
        int tipp;
        int versustipp;
        int sajatscore = 0;
        int ellenfelscore = 0;
        boolean tippeltemar;
        
        outerloop:
        for(int i = korokszama; i> 0; i--){
            System.out.println("Ird be a számot amire gondolsz: ");

            for(;;){
                try{
                    mg = sc.nextLine();
                    gondolt = Integer.parseInt(mg);
                    if( gondolt > maxgondolhato){
                        System.out.println("A gondolt szám nem lehet nagyobb mint "+maxgondolhato);
                    }
                    else if( 1 > gondolt){
                        System.out.println("A gondolt szám nem lehet kissebb mint 1");
                    }
                    else{
                        dos.writeInt(gondolt);
                        System.out.println("Várakozás az ellenfél számjára...");
                        break;
                    }
                }
                
                catch(Exception e){
                     System.out.println("Rosszul irtál valamit. Irj be egy számot 1 és "+maxgondolhato+" között.");
                     
                }            
            }
            
            kijon = dis.readBoolean();
            for(;;){                
                if(kijon){
                    System.out.println("Te tippelsz. Mi a tipped?: ");
                    tipp(maxgondolhato, dos);
                    
                    System.out.println("Az ellenfélre várunk....");
                    allapot = dis.readUTF();
                    
                    if( allapot.equals("tefelad") ){
                        System.out.println("Feladtad a játékot, ezért vesztettél!");
                        break outerloop;
                    }
                    
                    if( allapot.equals("feladta") ){
                        System.out.println("Az ellenfeled feladta a játékot, ezért automatikusan nyertél!");
                        break outerloop;
                    }
                    
                    if( allapot.equals("hazudtal")){
                        ellenfelscore++;
                        System.out.println("Hazudtál, ezért ebben a körben vesztettél! A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }   
                    
                    if(allapot.equals("hazudott")){
                        sajatscore++;
                        System.out.println("Az ellenfeled hazudott, ezért ebben a körben nyertél! A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }
                    
                    if( allapot.equals("eltalalta")){
                        ellenfelscore++;
                        System.out.println("Az ellenfeled kitalálta a számod. A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }
                    
                    if(allapot.equals("eltalaltad")){
                        sajatscore++;
                        System.out.println("Kitaláltad az ellenfeled számát. A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }
                    
                    System.out.println("Az ellenfél száma "+allapot+" mint amire te gondoltál.");
                    
                    kijon = false;
                    continue;
                }
                else{
                    System.out.println("Az ellenfélre várunk....");
                    versustipp = dis.readInt();
                    
                    System.out.println("Az ellenfeled tippje: "+versustipp);
                    System.out.println("Helyesen tippelt az ellenfeled? A lehetséges válaszok: ");
                    System.out.println("Kissebb - Ha kissebb számra gondoltál, mint az ellenfeled tippje.");
                    System.out.println("Nagyobb - Ha nagyobb számra gondoltál, mint az ellenfeled tippje.");
                    System.out.println("Találat - Ha az ellenfeled kitalálta, hogy mire gondoltál.");
                    System.out.println("Feladom - Ha feladod ezt a kört.");
                    
                    valasz(gondolt, versustipp, dos);
                
                    allapot = dis.readUTF();
                    
                    if( allapot.equals("tefelad") ){
                        System.out.println("Feladtad a játékot, ezért vesztettél!");
                        break outerloop;
                    }
                    
                    if( allapot.equals("feladta") ){
                        System.out.println("Az ellenfeled feladta a játékot, ezért automatikusan nyertél!");
                        break outerloop;
                    }
                    
                    if( allapot.equals("hazudtal")){
                        ellenfelscore++;
                        System.out.println("Hazudtál, ezért ebben a körben vesztettél! A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }   
                    
                    if(allapot.equals("hazudott")){
                        sajatscore++;
                        System.out.println("Az ellenfeled hazudott, ezért ebben a körben nyertél! A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }
                    
                    if( allapot.equals("eltalalta")){
                        ellenfelscore++;
                        System.out.println("Az ellenfeled kitalálta a számod. A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }
                    
                    if(allapot.equals("eltalaltad")){
                        sajatscore++;
                        System.out.println("Kitaláltad az ellenfeled számát. A játék jelenlegi állása: Te: "+sajatscore+" Ellenfél: "+ellenfelscore);
                        break;
                    }                    
                    
                    kijon = true;
                }
            }
        }
        
        if(sajatscore > ellenfelscore){
            System.out.println("A játék végetért. Te nyertél. A pontos végeredmény:");
            System.out.println("Te: "+sajatscore);
            System.out.println("Ellenfél: "+ellenfelscore);
        }
        else if( ellenfelscore > sajatscore){
            System.out.println("A játék végetért. Az ellenfeled nyert. A pontos végeredmény: ");
            System.out.println("Te: "+sajatscore);
            System.out.println("Ellenfél: "+ellenfelscore);
        }
        else{
            System.out.println("A játék döntetlen lett. Mindketten "+sajatscore+" menetet nyertetek meg.");
        }
        
        
        dos.writeBoolean(true);
        System.out.println("A játék bezárul.");
        
        dos.close();
        dis.close();
        socket.close();
        System.exit(0);
    }
//---------------------------------------------------------------------------------
    public static void valasz( int gondolt, int ellenfeltipp, DataOutputStream dos){
        Scanner sc = new Scanner(System.in);
        String playervalasz = sc.nextLine().toLowerCase();
        
        for(;;){
            if(playervalasz.equals("kissebb") || playervalasz.equals("nagyobb") || playervalasz.equals("talalat") || playervalasz.equals("találat") || playervalasz.equals("feladom") ){
                break;
            }
            else{
                System.out.println("Helytelenül irtál valamit. A lehetséges válaszok: ");                
                System.out.println("Kissebb - Ha kissebb számra gondoltál, mint az ellenfeled tippje.");
                System.out.println("Nagyobb - Ha nagyobb számra gondoltál, mint az ellenfeled tippje.");
                System.out.println("Talalat - Ha az ellenfeled kitalálta, hogy mire gondoltál.");
                System.out.println("Feladom - Ha feladod ezt a kört.");
                playervalasz = sc.nextLine().toLowerCase();
                
            }                        
        }
        
        if(playervalasz.equals("feladom")){
            for(;;){
                try{
                    dos.writeUTF("feladom");
                    break;
                }
                catch(IOException e){
                }
            }
        }
        
        else if(playervalasz.equals("talalat") || playervalasz.equals("találat") ){
                for(;;){
                    try{
                        dos.writeUTF("talalat");
                        break;
                    }
                    catch(IOException e){
                    }
                }
            
        }
        
        else if(playervalasz.equals("kissebb")){
            if( ellenfeltipp > gondolt ){
                for(;;){
                    try{
                        dos.writeUTF("kissebb");
                        break;
                    }
                    catch(IOException e){
                    }
                }   
            }
            else{
                for(;;){
                    try{
                        dos.writeUTF("hazug");
                        break;
                        
                    }                    
                    catch(IOException e){
                    }
                }                
            }
        }
        
        else if(playervalasz.equals("nagyobb")){
            if( gondolt > ellenfeltipp ){
                for(;;){
                    try{
                        dos.writeUTF("nagyobb");                        
                        break;
                    }
                    
                    catch(IOException e){
                    }
                }   
            }
            else{
                for(;;){
                    try{
                        dos.writeUTF("hazug");
                        break;
                    }                    
                    catch(IOException e){
                    }
                }                
            }
        }
        
    }
    
//----------------------------------------------------------------------    
 public static void tipp(int max, DataOutputStream dos){
     Scanner sc = new Scanner(System.in);
     int tipp;
     String mg;
        for(;;){
            try{
                mg = sc.nextLine();
                tipp = Integer.parseInt(mg);
                if( tipp > max){
                    System.out.println("A gondolt szám nem lehet nagyobb mint "+max);
                }
                else if( 1 > max){
                    System.out.println("A gondolt szám nem lehet kissebb mint 1");
                }
                else{
                    dos.writeInt(tipp);
                    break;
                }
            }
            catch(Exception e){
                 System.out.println("Rosszul irtál valamit. Irj be egy számot 1 és "+max+" között.");
            }
        }
 }  
 

    
//------------------------------------------------------------------------------------------------------------------    
    
    //Egyjátékos mód
    public static void egyjatekos(){

	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	KeyListener listener = new KeyListener() {
		
		@Override
		public void keyPressed(KeyEvent event){

			if(event.getKeyCode() == KeyEvent.VK_ESCAPE)		
				System.exit(0);
		}

		@Override
		public void keyReleased(KeyEvent event){}

		@Override
		public void keyTyped(KeyEvent event){}
	};


        //Új frame létrehozása az egyjátékos módhoz
        JFrame options = new JFrame("");
        options.setTitle("Egyjátékos mód");
	    options.addKeyListener(listener);
       // options.setSize(600, 200);
        options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Textfieldek bekéréshez, gomb a kezdéshez és a felület kirajzolása
        JTextField maxszam = new JTextField("Adja meg, hogy maximum mekkora számra lehet gondolni");
        JTextField gondolt = new JTextField("Adja meg azt a számot amire gondolt");
        JButton start = new JButton("Start");
        JButton ujra = new JButton("Újra");
        options.getContentPane().add(BorderLayout.NORTH,maxszam);
        options.getContentPane().add(BorderLayout.CENTER,gondolt);
        options.getContentPane().add(BorderLayout.SOUTH,start);
        maxszam.addKeyListener(listener);
        gondolt.addKeyListener(listener);


	        maxszam.setSize(400, 200);
	gondolt.setSize(600, 600);
        
	if (gd.isFullScreenSupported()) {
		options.setUndecorated(true);
		gd.setFullScreenWindow(options);
	}
	else{
		System.err.println("Nem jó");
		options.setSize(600, 200);
		options.setVisible(true);
	}
	
	

        //ha megkaptuk a számot akkor elkezdődik a játék       
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(gondolt.getText()) > Integer.parseInt(maxszam.getText())){
                    
                    gondolt.setText("A gondolt szám nem lehet nagyobb mint a lehető legnagyobb szám. Gondoljon egy másik számra.");
                        
                }
                else{
                    //létrehozzuk a szükséges dolgokat
                int maxnumber = Integer.parseInt(maxszam.getText());
                final int user = Integer.parseInt(gondolt.getText());
                int gepvalasztott = (int)(Math.random()* maxnumber)+1;
                
                //Előző gombok eltávolitása
                start.setVisible(false);
                maxszam.setVisible(false);
                gondolt.setVisible(false);
             //   options.setSize(800,800);

                KeyListener listener = new KeyListener() {
        
        @Override
        public void keyPressed(KeyEvent event){

            if(event.getKeyCode() == KeyEvent.VK_ESCAPE)            
                System.exit(0);
        }

        @Override
        public void keyReleased(KeyEvent event){}

        @Override
        public void keyTyped(KeyEvent event){}
    };
                
                //textfieldek
                JPanel gombok = new JPanel();
                JTextField usertipp = new JTextField("Ide ird a tipped");
                JTextField info = new JTextField("Most te tippelsz");
                info.setEditable(false);
                JTextField geptipp = new JTextField("");
                geptipp.setEditable(false);
                gombok.addKeyListener(listener);
                usertipp.addKeyListener(listener);
                info.addKeyListener(listener);
                geptipp.addKeyListener(listener);
                
                //gombok
                JButton tipp = new JButton("Tippelek");
                JButton talalat = new JButton("Találat");
                JButton kissebb = new JButton("Kissebbre Gondoltam");
                JButton nagyobb = new JButton("Nagyobbra Gondoltam");
                JButton exit = new JButton("Kilépés");
                JButton felad = new JButton("Feladom");
                tipp.addKeyListener(listener);
                talalat.addKeyListener(listener);
                kissebb.addKeyListener(listener);
                nagyobb.addKeyListener(listener);
                exit.addKeyListener(listener);
                felad.addKeyListener(listener);
                
                //gombokat beletesszük egy panelbe, hogy könnyebb legyen feltenni
                gombok.add(tipp);
                gombok.add(kissebb);
                gombok.add(talalat);
                gombok.add(nagyobb);
                gombok.add(felad);
                
                //felrajzoljuk a játék kezdetét
                options.getContentPane().add(BorderLayout.NORTH,usertipp);
                options.getContentPane().add(BorderLayout.CENTER,info);
                options.getContentPane().add(BorderLayout.EAST,geptipp);
                options.getContentPane().add(BorderLayout.SOUTH,gombok);
                kissebb.setVisible(false);
                talalat.setVisible(false);
                nagyobb.setVisible(false);
                geptipp.setVisible(false);
                
                    //tipp gombra kattintás kezelése
                    tipp.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            
                          tipp.setVisible(false);
                          kissebb.setVisible(true);
                          talalat.setVisible(true);
                          nagyobb.setVisible(true);
                          
                          //ellenőrizzók hogy jó e a tipp. ha igen akkor vége a játéknak
                          if( Integer.parseInt(usertipp.getText()) == gepvalasztott ){
                              tipp.setVisible(false);
                              kissebb.setVisible(false);
                              talalat.setVisible(false);
                              nagyobb.setVisible(false);                              
                              usertipp.setVisible(false);
                              geptipp.setVisible(false);
                              felad.setVisible(false);
                              
                              info.setText("Te nyertél! Gratuálok!");
                              options.getContentPane().add(BorderLayout.SOUTH,exit);
                              exit.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                              }}); 
                          }
                          
                          //ha a tippelt szám nagyobb mint amit tippeltünk, akkor azt mondjuk meg, és tippel egyet a gép
                          else if( Integer.parseInt(usertipp.getText()) > gepvalasztott ){                              
                              int geptip=(int)(Math.random()* maxnumber)+1;
                              geptipp.setText(Integer.toString(geptip));
                              info.setText("A tippelt szám nagyobb, mint amire a gép gondolt."
                                      + "Te erre a számra gondoltál? : "+geptip);
                        
                          } 
                          
                          //ha viszont a tippelt szám kissebb, akkor azt adjuk vissza <<<<<<< és szintén tippel a gép
                          else if( Integer.parseInt(usertipp.getText()) < gepvalasztott ){
                              int geptip=(int)(Math.random()* maxnumber)+1;
                              geptipp.setText(Integer.toString(geptip));
                              info.setText("A tippelt szám kissebb, mint amire a gép gondolt."
                                      + "Te erre a számra gondoltál? : "+geptip);
                          }
                        }
                     }); 
                    
                    //Kissebb gombra kattintás
                   kissebb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            
                            //kinézet állitás
                          tipp.setVisible(true);
                          kissebb.setVisible(false);
                          talalat.setVisible(false);
                          nagyobb.setVisible(false);
                          
                            //ellenőrzés hogy igazat mondtunk e
                            if(Integer.parseInt(geptipp.getText()) == user ){
                                tipp.setVisible(false);                                                                
                                usertipp.setVisible(false);
                                geptipp.setVisible(false);
                                felad.setVisible(false);
                              
                                info.setText("Hazudtál! Ezért automatikusan vesztettél!");
                                options.getContentPane().add(BorderLayout.SOUTH,exit);
                                exit.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        System.exit(0);
                                }});                                                         
                            }
                          
                            //ellenőrzés hogy igazat mondtunk e 2.0
                            else if(Integer.parseInt(geptipp.getText()) < user ){
                              tipp.setVisible(false);                              
                              usertipp.setVisible(false);
                              geptipp.setVisible(false);
                              felad.setVisible(false);
                              
                              info.setText("Hazudtál! Ezért automatikusan vesztettél!");
                              options.getContentPane().add(BorderLayout.SOUTH,exit);
                              exit.addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                              }});                                                         
                            }
                            
                            else{
                                usertipp.setText("Ide ird a tipped");
                                info.setText("Most te tippelsz");
                            }
                        }
                     });
                   
                   //találat gomb kezelése
                   talalat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {                                                                                 
                            tipp.setVisible(false);
                            kissebb.setVisible(false);
                            talalat.setVisible(false);
                            nagyobb.setVisible(false);                              
                            usertipp.setVisible(false);
                            geptipp.setVisible(false);
                            felad.setVisible(false);
                              
                            info.setText("Vesztettél! A gép nyert!");
                            options.getContentPane().add(BorderLayout.SOUTH,exit);
                            exit.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }});
                        }
                     });
                   
                   //nagyobb gombra kattintás kezelése
                   nagyobb.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {                            
                        tipp.setVisible(true);
                        kissebb.setVisible(false);
                        talalat.setVisible(false);
                        nagyobb.setVisible(false);
                          
                        //ellenőrzés hogy igazat mondtunk e
                        if(Integer.parseInt(geptipp.getText()) == user ){
                            tipp.setVisible(false);                                                                
                            usertipp.setVisible(false);
                            geptipp.setVisible(false);
                            felad.setVisible(false);
                              
                            info.setText("Hazudtál! Ezért automatikusan vesztettél!");
                            options.getContentPane().add(BorderLayout.SOUTH,exit);
                            exit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                            }});                                                         
                        }
                          
                        //ellenőrzés hogy igazat mondtunk e 2.0
                        else if(Integer.parseInt(geptipp.getText()) > user ){
                            tipp.setVisible(false);                              
                            usertipp.setVisible(false);
                            geptipp.setVisible(false);
                            felad.setVisible(false);
                              
                            info.setText("Hazudtál! Ezért automatikusan vesztettél!");
                            options.getContentPane().add(BorderLayout.SOUTH,exit);
                            exit.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                              System.exit(0);
                            }});                                                         
                        }
                        
                        else{
                                usertipp.setText("Ide ird a tipped");
                                info.setText("Most te tippelsz");
                            }
                        }
                     });
                   
                   //találat
                    felad.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {                                                                                 
                            tipp.setVisible(false);
                            kissebb.setVisible(false);
                            talalat.setVisible(false);
                            nagyobb.setVisible(false);                              
                            usertipp.setVisible(false);
                            geptipp.setVisible(false);
                            felad.setVisible(false);
                              
                            info.setText("Feladtad a Játékot, ezért a gép nyert!");
                            options.getContentPane().add(BorderLayout.SOUTH,exit);
                            exit.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }});
                        }
                     });   
                
                }                  
            }
        });  
    }
    
}
