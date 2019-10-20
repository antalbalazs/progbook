import java.io.*;

public class RsaTores {
    public static void main(String[] args) {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(args[0]));
            int lines = 0;

            String line[] = new String[10000];

            while((line[lines] = inputStream.readLine()) != null) {
                lines++;
            }

            inputStream.close();

            KulcsPar kp[] = new KulcsPar[100];

            boolean volt = false;
            kp[0] = new KulcsPar(line[0]);
            int db = 1;

            for(int i = 1; i < lines; i++) {
                volt = false;
                for(int j = 0; j < db; j++) {
                    if(kp[j].getValue().equals(line[i])) {
                        kp[j].incFreq();
                        volt = true;
                        break;
                    }
                }

                if(volt == false) {
                    kp[db] = new KulcsPar(line[i]);
                    db++;
                }
            }


            for(int i = 0; i < db; i++) {
                for(int j = i + 1; j < db; j++) {
                    if(kp[i].getFreq() < kp[j].getFreq() ) {
                        KulcsPar temp = kp[i];
                        kp[i] = kp[j];
                        kp[j] = temp;
                    }
                }
            }





            FileReader f = new FileReader("betugyakorisag.txt");

            char[] key = new char[60];
            int kdb=0;
            int k;

            while((k = f.read()) != -1) {
                if((char)k != '\n') {
                    key[kdb] = (char)k;
                    kdb++;
                }
            }

            f.close();

	    for(int i = 0; i < kdb; i++) {
                kp[i].setKey(key[i]);
            }


            for(int i = 0; i < lines; i++) {
                for(int j = 0; j < db; j++) {
                    if(line[i].equals(kp[j].getValue())) {
                        System.out.print(kp[j].getKey());
                    }
                }
            }
	    System.out.println("");

        } catch(IOException e) {
        }

    }
}


class KulcsPar{
    private String values;
    private char key = '_';
    private int freq = 0;
    
    public KulcsPar(String str, char k){
        this.values = str;
        this.key = k;
    }

    public KulcsPar(String str){
        this.values = str;
    }

    public void setValue(String str){
        this.values = str;
    }

    public void setKey(char k){
        this.key = k;
    }

    public String getValue(){
        return this.values;
    }

    public char getKey(){
        return this.key;
    }

    public void incFreq(){
        freq += 1;
    }

    public int getFreq(){
        return freq;
    }

}

