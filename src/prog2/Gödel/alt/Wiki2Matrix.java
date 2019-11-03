import static java.lang.System.out;

public class Wiki2Matrix {
	
	protected static void kiir(int[][] k){
		for (int[] is : k) {
			out.println();
			for (int i=0; i<is.length;i++) {
				out.print(is[i] + ", ");
			}
		}
	} 
	
	protected static int[] kiir2(int[][] k){
		int[] oszlop=new int[k.length];
		
		for (int i : oszlop) {
			oszlop[i]=0;
		}
		
		for (int[] is : k) {
			out.println();
			int o=0;
			for (int i=0; i<is.length;i++) {
				out.print(is[i] + ", ");
				oszlop[i]+=is[i];
				o+=is[i];
			}
			out.print("  " + o);
		}
		
		out.println();
		out.println();
		out.println();
		for (int sz=0; sz<oszlop.length;sz++) {
			out.print(oszlop[sz] + ", ");
		}
		
		return oszlop;
		
	} 
	
	protected static void kiir(int[][] k, int[] t){
		for (int i = 0; i < k.length; i++) {		      
		      out.print("\n{");
		      for (int j = 0; j < k[i].length; j++) {

		        if (t[j] != 0.0) {
		          System.out.print(k[i][j] * (1.0 / t[j]) + ", ");
		        } else {
		          System.out.print(k[i][j] + ", ");
		        }

		      }
		      out.print("},");
		    }
			
		 }

	public static void main(String[] args){
		
		int[][] tablazat= {
			      {0, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 3 },
			      {3, 0, 3, 2, 1, 2, 2, 1, 1, 1, 1, 3 },
			      {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			      {3, 2, 1, 0, 3, 3, 3, 3, 3, 1, 1, 3 },
			      {1, 1, 2, 3, 0, 1, 2, 1, 1, 1, 2, 3 },
			      {1, 2, 3, 1, 3, 0, 2, 1, 2, 1, 3, 2 },
			      {2, 1, 3, 1, 1, 2, 0, 3, 1, 1, 2, 1 },
			      {3, 1, 3, 1, 3, 3, 3, 0, 3, 1, 1, 3 },
			      {1, 3, 3, 2, 2, 1, 1, 1, 0, 1, 3, 3 },
			      {1, 1, 1, 1, 1, 3, 1, 2, 2, 0, 3, 1 },
			      {1, 1, 2, 1, 2, 1, 1, 3, 2, 1, 0, 1 },
			      {1, 3, 1, 1, 1, 1, 1, 3, 2, 3, 1, 0 }
			    };
		
		int [][] pontszerzes= new int [tablazat.length][tablazat.length];
		
		/*for (int i = 0; i < pontszerzes.length; i++) {
			for (int j = 0; j < pontszerzes[i].length; j++) {
				System.out.print(pontszerzes[i][j]);
			}
			System.out.println();
		}*/
		
		for (int[] is : pontszerzes) {
			for (int i=0; i<is.length;i++) {
				is[i]=0;
			}
		}
		
		/*for (int i = 0; i < pontszerzes.length; i++) {
			for (int j = 0; j < pontszerzes[i].length; j++) {
				System.out.print(pontszerzes[i][j]);
			}
			System.out.println();
		}*/
		for (int i=0; i<pontszerzes.length;i++) {
			for (int j=0; j<pontszerzes[i].length;j++) {
				switch (tablazat[i][j]) {
				case 0:
					
					break;
				case 1:
					pontszerzes[i][j]++;
					break;
				case 2:
					pontszerzes[i][j]++;
					pontszerzes[j][i]++;
					break;
				case 3:
					pontszerzes[j][i]++;
					break;

				default:
					out.println("That's all Folks!");
					break;
				}
			}
		}
		
		
		out.println("A x pontot szerez y tol matrix!");
		kiir(pontszerzes);
		
		out.println("Sor es oszloposszegekkel!");
		
		int[] oszlop=kiir2(pontszerzes);
		
		out.println("\nA \"link\" mαtrix");
		
		kiir(pontszerzes,oszlop);
		
	}
	
}























