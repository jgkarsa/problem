package algorithm;

import artificialAlogorithm.ArtificialBee;
import genProblem.GenProblemFile;
import geneticAlgorithm.GeneticBit;
import geneticAlgorithm.GeneticDuplicate;
import geneticAlgorithm.GeneticNotDuplicate;
import geneticAlgorithm.GeneticOneD;
import particleAlogrithm.PSO;
import randomAlgorithm.RandomAlgorithm;

public class AlgorithmFactory {

	// private GenProblemRand genP;
	private GenProblemFile genP;
	private Attribute AT;

	private int[] tarVal;
	private double[][] atkPro;

	private int arrX;
	private int arrY;
	private int TN;

	public AlgorithmFactory(Attribute AT,int TN) {
		// TODO Auto-generated constructor stub
		this.AT = AT;
		this.TN = TN;
		
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

		case "bruteforce":
			return new BruteForce(atkPro, tarVal, AT);
		case "geneticDuplicate":		
			return new GeneticDuplicate(atkPro, tarVal, AT, TN);		
		case "geneticNotDuplicate":		
			return new GeneticNotDuplicate(atkPro, tarVal, AT, TN);	
		case "genetic":
			return new GeneticOneD(atkPro, tarVal, AT, TN);
		case "particleswarm":
			return new PSO(atkPro, tarVal, AT, TN);
		case "artificialbee":
			return new ArtificialBee(atkPro, tarVal, AT, TN);
		case "randomalgorithm":
			return new RandomAlgorithm(atkPro, tarVal, AT, TN);			
		case "greedy":
			return new Greedy(atkPro, tarVal, AT);
		case "geneticbit":
			return new GeneticBit(atkPro, tarVal, AT, TN);
		case "default":
			System.out.println("not correct algorithm");
			break;
		}

		return null;

	}

}
