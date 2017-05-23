package algorithmInterface;

import abc.ArtificialBee;
import genProblem.GenProblemFile;
import geneticAlgorithm.GeneticBit;
import geneticAlgorithm.GeneticDuplicate;
import geneticAlgorithm.GeneticOneD;
import pso.PSO;
import randomAlgorithm.RandomAlgorithm;
import readFile.Attribute;
import storage.Storage;

public class AlgorithmFactory {

	// private GenProblemRand genP;
	private GenProblemFile genP;
	private Attribute AT;

	private int[] tarVal;
	private double[][] atkPro;

	private int arrX;
	private int arrY;
	private int TN;
	private Storage storage;

	public AlgorithmFactory(Attribute AT,Storage storage) {
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

		this.storage = storage;
		
	}

	public WeaponAlgorithm getAlgorithm() {

		switch (AT.getAlgorithm()) {

		case "genetic":		
			return new GeneticDuplicate(atkPro, tarVal, AT,storage);				
		case "particleswarm":
			return new PSO(atkPro, tarVal, AT,storage);
		case "artificialbee":
			return new ArtificialBee(atkPro, tarVal, AT,storage);
		case "randomalgorithm":
			return new RandomAlgorithm(atkPro, tarVal, AT,storage);			
		case "geneticbit":
			return new GeneticBit(atkPro, tarVal, AT);
		case "default":
			System.out.println("not correct algorithm");
			break;
		}

		return null;

	}

}
