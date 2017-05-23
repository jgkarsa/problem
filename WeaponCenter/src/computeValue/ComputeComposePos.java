package computeValue;


import genProblem.GenProblemFile;
import readFile.Attribute;

public class ComputeComposePos {

	private int arrX;
	private int arrY;
	private int[] tarVal;
	private double[][] atkPro;
	private GenProblemFile genP;
	private ComputeValue comV;

	public ComputeComposePos(Attribute AT) {

		// read attribute and load arrX,arrY
		arrX = AT.getArrX();
		arrY = AT.getArrY();

		// initial input size
		tarVal = new int[arrY];
		atkPro = new double[arrX][arrY];

		// initial input value
		genP = new GenProblemFile(AT);
		// genP = new GenProblemRand();
		genP.genTarVal(tarVal);
		genP.genAtkPro(atkPro);
		
		comV = new ComputeValue(atkPro, tarVal);

	}
	
	public void computeComposeValue(double[] pos){
		
		System.out.println(comV.computeVOneDD(pos));
		
	}

}
