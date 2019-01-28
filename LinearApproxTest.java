import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.BitSet;
import java.util.Scanner;
import java.util.Vector;

import org.junit.Test;


public class LinearApproxTest {

	LinearApprox linearApprox;
	//private Scanner sc;
	@Test
	public void testTableToString() {

		fail("Not yet implemented");
	}

	@Test
	public void testGetTable() {
		int[][] s5Table = new int[64][16];
		try {
			Scanner sc = new Scanner(new File("S5LinearApproxTable.txt"));
			String numArray[];
			int i = 0;
			while (sc.hasNextLine()) {
				numArray = sc.nextLine().split(" ");
				for (int j = 0; j < 16; j++)
					s5Table[i][j] = Integer.parseInt(numArray[j]);
				i++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
	}

	@Test
	public void testProcessAllInputs() {
		SBox s5 = new S5();
		LinearApprox la = new LinearApprox();
		
		la.processAllInputs(s5.getInputBits(), s5);
		
		Vector<BitSet> input = new Vector<BitSet>(la.input);
		Vector<BitSet> output = new Vector<BitSet>(la.output);
		
		if (input.size() != (1 << s5.getInputBits()))
			fail("Input bits size error");
		
		if (input.size() != output.size())
			fail("Total number of input is different from total number of output");
		
		int inputDec, outputDec;
		for (int i = 0; i < input.size(); i++) {
			inputDec = LinearApprox.bitSetToDecimal(input.get(i));
			outputDec = LinearApprox.bitSetToDecimal(output.get(i));
			if (s5.run(inputDec) != outputDec)
				fail("Input " + inputDec + " not consistent with output " + outputDec);
		}
	}

	@Test
	public void testXorSum() {
		BitSet b1, b2, b3;
		b1 = new BitSet();
		b2 = new BitSet();
		b3 = new BitSet();
		
		b1.set(0);
		b1.set(1);
		b1.set(3);	//b1 = 1011
		
		b2.set(1);
		b2.set(2);
		b2.set(3);	//b2 = 1110
		
		b3.set(3);
		b3.set(0);	//b3 = 1001
		
		if (!LinearApprox.xorSum(b1))
			fail("Failed to compute xor sum of 1011");
		if (!LinearApprox.xorSum(b2))
			fail("Failed to compute xor sum of 1110");
		if (LinearApprox.xorSum(b3))
			fail("Failed to compute xor sum of 1001");
	}

	@Test
	public void testBitSetToDecimal() {
		BitSet bs = new BitSet();
		int dec;
		
		bs.set(0,3);
		dec = 7;
		if (!(LinearApprox.bitSetToDecimal(bs) == dec))
			fail("Failed to convert 111 to decimal");
		
		bs.clear();
		dec = 0;
		if (!(LinearApprox.bitSetToDecimal(bs) == dec))
			fail("Failed to convert 0 to decimal");
		
		bs.set(2, 4);
		dec = 12;
		if (!(LinearApprox.bitSetToDecimal(bs) == dec))
			fail("Failed to convert 1100 to decimal");
	}

	@Test
	public void testDecimalToBitSet() {
		int dec;
		BitSet bs = new BitSet();
		
		linearApprox = new LinearApprox();
		
		dec = 15;
		bs.set(0, 4);
		if (!LinearApprox.decimalToBitSet(dec, 64).equals(bs))
			fail("Failed to convert 15");
		
		dec = 0;
		bs.clear();
		if (!LinearApprox.decimalToBitSet(dec, 64).equals(bs))
			fail("Failed to convert 0");
		
		dec = 5;
		bs.set(0);
		bs.set(2);
		if (!LinearApprox.decimalToBitSet(dec, 64).equals(bs))
			fail("Failed to convert 5");
	}

}
