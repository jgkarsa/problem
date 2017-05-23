package algorithmInterface;

import abc.ArtificialBee;
import genProblem.GenProblemFile;
import geneticAlgorithm.GeneticBit;
import geneticAlgorithm.GeneticDuplicate;
import geneticAlgorithm.GeneticNew;
import geneticAlgorithm.GeneticOneD;
import pso.PSO;
import randomAlgorithm.RandomAlgorithm;
import readFile.Attribute;

public class AlgorithmFactory {

	// private GenProblemRand genP;
	private GenProblemFile genP;
	private Attribute AT;

	private int[] tarVal;
	private double[][] atkPro;

	private int arrX;
	private int arrY;
	private int TN;

	public AlgorithmFactory(Attribute AT) {
		// TODO Auto-generated constructor stub
		this.AT = AT;
		this.TN = AT.getWeaponNum();
		
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

	}

	public WeaponAlgorithm getAlgorithm() {

		switch (AT.getAlgorithm()) {

		case "genetic":		
			return new GeneticDuplicate(atkPro, tarVal, AT);		
		case "geneticnew":
			return new GeneticNew(atkPro, tarVal, AT);			
		case "particleswarm":
			return new PSO(atkPro, tarVal, AT);
		case "artificialbee":
			return new ArtificialBee(atkPro, tarVal, AT);
		case "randomalgorithm":
			return new RandomAlgorithm(atkPro, tarVal, AT);			
		case "geneticbit":
			return new GeneticBit(atkPro, tarVal, AT);
		case "default":
			System.out.println("not correct algorithm");
			break;
		}

		return null;

	}

}
