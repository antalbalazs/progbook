package stlmap;
import java.io.*;

public class Stlmap{
	
	public static void main(String args[] ){
		
	RandomAccessFile raf;
        String sor;
        Map[] tomb;
        int db;

        try
        {
            raf = new RandomAccessFile("stlmap/feladat.txt","r");
            db = 0;
            
            for( sor = raf.readLine(); sor!= null; sor = raf.readLine() )
            {
                db++;
            }

            tomb = new Map[db];
            db = 0;
            raf.seek(0);
            
            for( sor = raf.readLine(); sor != null; sor = raf.readLine() )
            {
                tomb[db] = new Map(sor.split(","));
                db++;
            }
            
            raf.close();
            
            System.out.println("eredeti értékek: ");

            for( Map i : tomb )
            {
                System.out.println(i.toString());
            }

            for( int gap = db / 2; gap > 0; gap /=2){
                for( int i = gap; i< db; i++){
                    Map temp= tomb[i];
                    int j;
                    for( j = i; j >= gap && tomb[j - gap].getErtek() > temp.getErtek(); j -= gap){
                        tomb[j] = tomb[j - gap];
                    }

                    tomb[j] = temp;
                }
            }

            System.out.println("\nRendezett értékek:");

            for( Map i : tomb){
                System.out.println(i.toString());
            }

        }
        catch(IOException e){
        	System.out.println("Hiba a beolvasas soran: "+e);
        }

	}

}
