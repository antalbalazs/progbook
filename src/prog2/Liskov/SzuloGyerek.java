public class SzuloGyerek{

static class Madar{
	protected int szarnyhossz;
	public void setSzarnyhossz(int szarnyhossz) {
	this.szarnyhossz = szarnyhossz;
	}

}

static class Sas extends Madar{
	public int getSzarnyhossz(){
		return szarnyhossz;
	}
}

	public static void main(String args[]){
		Madar sas = new Sas();
		sas.setSzarnyhossz(80);

		Sas sas2 = new Sas();
		sas2.setSzarnyhossz(50);
		
		System.out.println(sas2.getSzarnyhossz() + " " + sas.getSzarnyhossz() );
	}

}
