import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Rsa {
	
	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("usage: java Rsa input output");
			System.exit(-1);
		}
		KulcsPar kulcs = new KulcsPar();
		String tisztaszoveg;

		try{
			tisztaszoveg = new String (Files.readAllBytes( Paths.get(args[0])));
			File ki = new File(args[1]);
		
			PrintWriter kiir = new PrintWriter(args[1]);
			
			tisztaszoveg = tisztaszoveg.toLowerCase();

			for( int i = 0; i<tisztaszoveg.length(); i++){
				String szoveg = tisztaszoveg.substring(i, i+1);
				byte[] buffer = szoveg.getBytes();
				java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
				byte[] output = new byte[buffer.length];
				
				for( int j = 0; j< titkos.length; j++){
					titkos[j] = new java.math.BigInteger(new byte[] {buffer[j]});
					titkos[j] = titkos[j].modPow(kulcs.e, kulcs.m);
					output[j] = titkos[j].byteValue();
					kiir.print(titkos[j]);
				}
				kiir.println();
			}				
		}
		catch(IOException e){
			System.out.println("hiba " + e);
		}
	}
}


class KulcsPar {
	java.math.BigInteger d,e,m;
	public KulcsPar() {
		int meretBitekben = 700 * (int) (java.lang.Math.log((double) 10) / java.lang.Math.log((double) 2));

		java.math.BigInteger p = new java.math.BigInteger(meretBitekben, 100, new java.util.Random());
		java.math.BigInteger q = new java.math.BigInteger(meretBitekben, 100, new java.util.Random());

		m = p.multiply(q);
		java.math.BigInteger z = p.subtract(java.math.BigInteger.ONE).multiply(q.subtract(java.math.BigInteger.ONE));

		do {
			do {
				d = new java.math.BigInteger(meretBitekben, new java.util.Random());
			} while (d.equals(java.math.BigInteger.ONE));
		} while (!z.gcd(d).equals(java.math.BigInteger.ONE));
		e = d.modInverse(z);
	}
}
