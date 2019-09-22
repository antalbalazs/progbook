public class liskovj{
static class Madar{
	public void repul(){
	System.out.println("Repülök");
	}
}

static class Sas extends Madar{
	
}

static class Pingvin extends Madar{

}

public static void main(String args[]){
	Madar madár = new Madar();
	Sas sas = new Sas();
	Pingvin pingvin = new Pingvin();
	
	System.out.print("Mit csinál a madár?:");
	madár.repul();
	
	System.out.print("\nMit csinál a sas?:");
	sas.repul();

	System.out.print("\nMit csinál a pingvin?:");
	pingvin.repul();
	System.out.println("\nDe a pingvin nem tud repülni, szóval sérül a liskov elv.");

}

}
