/**
 * 
 */

/**
 * @author User
 *
 */
public class S5 implements SBox {
	private String name = "DES-S5";
	static public final int INPUTBITS = 6, OUTPUTBITS = 4;
	
	static int sbox[][] =
		{
			{2,	12,	4,	1,	7,	10,	11,	6,	8,	5,	3,	15,	13,	0,	14,	9},
			{14,11,	2,	12,	4,	7,	13,	1,	5,	0,	15,	10,	3,	9,	8,	6},
			{4,	2,	1,	11,	10,	13,	7,	8,	15,	9,	12,	5,	6,	3,	0,	14},
			{11,8,	12,	7,	1,	14,	2,	13,	6,	15,	0,	9,	10,	4,	5,	3}
		};
	
	public int run(int n) {
		int col, row;
		col = n;
		row = 0;
		if (n >= 32) {	//If leftmost bit is set
			row += 2;	//Adds 2 to the decimal value of the row
						//(since leftmost bit of the row in the s-box is set)
			col -= 32;	//Remove the leftmost bit (if it is set)
		}
		if (n % 2 == 1)	//If rightmost bit is set
			row += 1;	//Adds 1 to the decimal value of the row
						//(since the rightmost bit of the row in the s-box is set)
		
		col = col >> 1;	//Remove the rightmost bit of the column
		
		return sbox[row][col];
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getInputBits() {
		// TODO Auto-generated method stub
		return INPUTBITS;
	}

	@Override
	public int getOutputBits() {
		// TODO Auto-generated method stub
		return OUTPUTBITS;
	}
}
