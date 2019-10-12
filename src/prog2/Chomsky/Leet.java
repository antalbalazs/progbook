public class Leet {

	public static void main(String[] args) throws Exception{
		if(args.length != 2){
			System.out.println("usage: inputfile outputfile");
			System.exit(-1);
		}

		java.io.FileReader file = new java.io.FileReader(args[0]);
		java.io.FileWriter fw = new java.io.FileWriter(args[1]);

		LeetCipher lc = new LeetCipher();
		int k = 0;

		while ((k=file.read()) != -1) {
			fw.write(lc.chiper((int)Character.toUpperCase((char)k)));
		}

		file.close();
		fw.close();
	}
}

class LeetCipher {
	private String[] leetchars = new String[]{
	"4","8","<","[)","3","|=","6","|-|","1","_|","|<","|","|V|","|\\|","O",
	"|>","0.","|2","5","7","|_|","\\/","\\X/","}{","`/","2"
	};

	private String[] leetnums = new String[]{
	"O","I","Z","E","A","S","G","T","B","g"
	};

	public String chiper(int ch) {
		if (ch >= 65 && ch <= 90){
			return leetchars[ch - 65];
		}

		else if (ch >= 48 && ch <= 57){
			return leetnums[ch - 48];
		}
		else {
			return String.valueOf((char)ch);
		}
	}
}
