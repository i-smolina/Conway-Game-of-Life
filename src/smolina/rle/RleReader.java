package smolina.rle;

import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleReader {
	private static BitSet[] bits;
	
	public static BitSet[] read(String path) throws IOException {
		String line = readStringFromFile(path);
		
		Matcher matcher;
		String regex_XY = "x{1}\\s*=\\s*(?<valueX>\\d+).*y{1}\\s*={1}\\s*(?<valueY>\\d+)";
		String regex = "(?<digit>\\d*)(?<symb>\\D{1})";
		
		Pattern pattern = Pattern.compile(regex_XY);		
		matcher = pattern.matcher(line);
	
		int x = 0, y = 0;
		while (matcher.find()) {	
			try {
				x = Integer.parseInt(matcher.group("valueX"));
				y = Integer.parseInt(matcher.group("valueY"));
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				x = 0;
				y = 0;
			}
		}
		line = line.substring(line.indexOf("B3/S23") + 6);		
						
		// strike out '!' at the end
		line = line.substring(0, line.length() - 1);
		String[] arr = split(line, "[$]");
		//System.out.println(arr.length);
		
		pattern = Pattern.compile(regex);
		
		int count = 0;
		String buf;
		BitSet row;
		bits = new BitSet[y];
		
		int i;
		for (int j = 0; j < arr.length; j++) {
			row = new BitSet(x);	
			bits[j] = row;
		
			matcher = pattern.matcher(arr[j]);
			
			i = 0;
			while (matcher.find()) {	
				if (!(buf = matcher.group("digit")).equals("")) {	
					count = Integer.parseInt(buf);
				}			
				else count = 1;	
				
				if (matcher.group("symb").equals("b"))
					row.set(i, i + count, false);
				else 
					row.set(i, i + count, true);	
				i += count;
			}	
		}		
		return bits;
	}
	
	
	private static String readStringFromFile(String path) throws IOException {
		String line;
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		reader.close();
		return builder.toString();
	}
	
	private static String[] split(String str, String sep) {		
		String[] arr = str.split(sep);
				
//		for (int i = 0; i <arr.length; i++)
//			System.out.println(arr[i]);
		
		return arr;
	}
	
	public static int getX() {
		return bits[0].size();
	}
	
	public static int getY() {
		return bits.length;
	}
	
	static void test(BitSet[] bits) {
		for (int a = 0; a < bits.length; a++) {
			for (int b = 0; b < bits[a].length(); b++) {
				if (bits[a].get(b)) 
					System.out.print("*");
				else System.out.print(" ");
			}
			System.out.println();
		}
	}
	
		
	public static void main(String[] args) throws IOException {
		BitSet[] bits = read("C:\\Users\\SmolinaIS\\Desktop\\test.txt");
		test(bits);
	}
}
