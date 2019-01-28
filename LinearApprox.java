import java.util.BitSet;
import java.util.Vector;


public class LinearApprox {

	/**
	 * @param args
	 */
	public Vector<BitSet> input, output;
	public Vector<Vector<Integer>> table;
	
	public static void main(String[] args) {
		
		SBox s1 = new S1();
		
		//processAllInputs(0, 63, s1);
		//processAllInputs(s1.getInputBits(), s1);
		
		//getTable(0, 64, 0, 16);
		
		LinearApprox linearApprox = new LinearApprox();
		
		linearApprox.processAllInputs(s1.getInputBits(), s1);
		
		linearApprox.getTable(s1.getInputBits(), s1.getOutputBits());
		
		/*for (int k = 0; k < table.size(); k++) {
			for (int l = 0; l < table.get(k).size(); l++)
				System.out.print((table.get(k).get(l) - 32) + "\t");
			System.out.println();
		}*/
		System.out.print(linearApprox.tableToString(s1));
	}
	
	public String tableToString(SBox s) {
		StringBuffer sTable = new StringBuffer();
		int expected = 1 << s.getInputBits() - 1;
		
		sTable.append("\t");
		for (int i = 0; i < table.get(0).size(); i++) {
			sTable.append(i + "\t");
		}
		sTable.append("\n");
		sTable.append("\t");
		for (int i = 0; i < table.get(0).size(); i++) {
			sTable.append("---" + "\t");
		}
		sTable.append("\n");
		
		for (int k = 0; k < table.size(); k++) {
			sTable.append(k + "\t");
			for (int l = 0; l < table.get(k).size(); l++)
				sTable.append((table.get(k).get(l) - expected) + "\t");
			sTable.append("\n");
		}
		
		return sTable.toString();
	}
	
	public void getTable(int inputBits, int outputBits) {
		table = new Vector<Vector<Integer>>();
		
		int inputEnd = 1 << inputBits;
		int outputEnd = 1 << outputBits;
		
		//Initializes the table
		for (int i = 0; i < inputEnd; i++) {
			table.add(new Vector<Integer>());
			for (int j = 0; j < outputEnd; j++) {
				table.get(i).add(0);
			}
		}
		
		//Starts
		for (int i = 0; i < inputEnd; i++) {
			
			for (int j = 0; j < outputEnd; j++) {
				
				for (int k = 0; k < input.size(); k++) {

					BitSet rowtemp = decimalToBitSet(i, 64);
					BitSet coltemp = decimalToBitSet(j, 64);

					rowtemp.and(input.get(k));
					coltemp.and(output.get(k));
					
					if (xorSum(rowtemp) == xorSum(coltemp))
						table.get(i).set(j, table.get(i).get(j) + 1);
				}
			}
		}
	}
	
	public void processAllInputs (int bits, SBox s) {
		input = new Vector<BitSet>();
		output = new Vector<BitSet>();
		
		int end = 1 << bits;
		
		for (int i = 0; i < end; i++) {
			input.add(decimalToBitSet(i, 64));
			output.add(decimalToBitSet(s.run(i), 64));
		}
	}
	
	public static boolean xorSum(BitSet bs) {
		int sum = 0;
		for (int i = 0; i < bs.length(); i++) {
			if (bs.get(i) == true)
				sum += 1;
		}
		
		if (sum % 2 == 1)
			return true;
		return false;
	}
	
	public static int bitSetToDecimal(BitSet bs) {
		int dec = 0;
		if (bs.get(0) == true)
			dec = 1;
		for (int i = 1; i < bs.length(); i++) {
			if (bs.get(i) == true)
				dec = dec + (2 << i - 1);
		}
		return dec;
	}
	
	public static BitSet decimalToBitSet(int dec, int size) {
		BitSet bs = new BitSet(size);
		int i = 0;
		while (dec > 0) {
			if (dec % 2 == 1)
				bs.set(i);
			i++;
			dec = dec >> 1;
		}
		
		return bs;
	}

}
