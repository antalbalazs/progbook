import java.io.FileNotFoundException;
import java.io.PrintWriter;

public aspect BinFa{
	int melyseg = 0;


	public pointcut hivas(LzwBinFa.Csomopont n, PrintWriter os): call(void LzwBinFa.kiir(LzwBinFa.Csomopont, PrintWriter)) && args(n,os);

	after(LzwBinFa.Csomopont n, PrintWriter os) : hivas(n, os){

		try{
			PrintWriter pre = new PrintWriter("preorder");
			kiirPre(n, pre);
			pre.close();
		}
		catch(FileNotFoundException e) {
			System.out.println(e);
		}

		melyseg = 0;

		try{
			PrintWriter post = new PrintWriter("postorder");
			kiirPost(n, post);
			post.close();
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}

	}


	public void kiirPost(LzwBinFa.Csomopont elem, java.io.PrintWriter os) {

	 if(elem != null) {
		++melyseg;

		kiirPost(elem.nullasGyermek(), os);
		kiirPost(elem.egyesGyermek(),os);

		for(int i = 0; i < melyseg; i++){
			
			os.print("---");
		}
		os.print(elem.getBetu());
		os.print("(");
		os.print(melyseg -1);
		os.println(")");

		
		--melyseg;
	 }
	}

	public void kiirPre(LzwBinFa.Csomopont elem, java.io.PrintWriter os) {

	 if(elem != null) {
		++melyseg;

		for(int i = 0; i < melyseg; i++){
			os.print("---");
		}
		os.print(elem.getBetu());
		os.print("(");
		os.print(melyseg -1);
		os.println(")");

		kiirPre(elem.nullasGyermek(), os);
		kiirPre(elem.egyesGyermek(),os);		
		--melyseg;
	 }
	}

}
