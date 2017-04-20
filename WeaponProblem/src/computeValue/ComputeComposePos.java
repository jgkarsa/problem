package computeValue;

import algorithm.Attribute;
import genProblem.GenProblemFile;

public class ComputeComposePos {

	private int arrX;
	private int arrY;
	private ComputeValue comV;

	public ComputeComposePos(Attribute AT) {

		// read attribute and load arrX,arrY
		arrX = AT.getArrX();
		arrY = AT.getArrY();

		// initial input size
		int[] tarVal = new int[arrY];
		double[][] atkPro = new double[arrX][arrY];

		// initial input value
		GenProblemFile genP = new GenProblemFile(AT);
		// genP = new GenProblemRand();
		genP.genTarVal(tarVal);
		genP.genAtkPro(atkPro);
		
		comV = new ComputeValue(atkPro, tarVal);

	}
	
	public void computeComposeValue(double[] pos){
		
		System.out.println(comV.computeVOneDD(pos));
		
	}

}
