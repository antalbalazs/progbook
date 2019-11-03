package stlmap;

public class Map 
{
    private String kulcs;
    private int ertek;
    
public Map( String[] tomb)
{
    kulcs = tomb[0];
    ertek = Integer.parseInt(tomb[1]);
    
}

    @Override
    public String toString() {
        return "kulcs=" + kulcs + ", ertek=" + ertek;
    }

    public String getKulcs() {
        return kulcs;
    }

    public int getErtek() {
        return ertek;
    }

    public void setKulcs(String kulcsra){
    	kulcs = kulcsra;
    }

    public void setErtek(int ertekre){
    	ertek = ertekre;
    }
    
}
