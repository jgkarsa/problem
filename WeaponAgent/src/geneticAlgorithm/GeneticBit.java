package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;

import algorithmInterface.WeaponAlgorithm;
import computeValue.ComputeValue;
import other.Instance;
import readFile.Attribute;

public class GeneticBit implements WeaponAlgorithm {

	private ComputeValue comV;
	private ArrayList<ChromOneD> ChromList;
	private ChromOneD bestChrom;
	private Attribute AT;

	private int arrX;
	private int arrY;
	private int populationNum;
	private int terminateCondition;
	private double crossoverRate = 0.7;
	private double mutationRate = 0.1;
	private int bitLength = 15;
	private int TN;

	public GeneticBit(double[][] atkPro, int[] tarVal, Attribute AT) {

		this.AT = AT;
		this.TN = AT.getWeaponNum();

		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);

		bestChrom = new ChromOneD(arrX);
		bestChrom.setValue(Double.MAX_VALUE);
		ChromList = new ArrayList<ChromOneD>();

		populationNum = AT.getPopulation();

	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {

			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(bestChrom.getBestValue());

				}
			}

			selection();

			crossover();

			mutation();

		}

		// System.out.println(bestChrom.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		// TODO Auto-generated method stub
		ChromList.clear();

		for (Instance instance : population) {

			ChromOneD chrom = new ChromOneD(arrX);
			chrom.setAns(instance.getBestPos());
			chrom.setValue(instance.getBestValue());

			ChromList.add(chrom);

		}
		compareBest();

	}

	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub
		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {

		ArrayList<Instance> population = new ArrayList<Instance>();
		for (ChromOneD chrom : ChromList) {
			population.add(chrom);
		}

		return population;
	}

	public void selection() {

		double totalValue = 0;

		compareBest();

		for (ChromOneD chrom : ChromList) {
			totalValue += (1 / chrom.getBestValue());
		}
		// use roulette wheel selection
		for (int i = 0; i < populationNum; i++) {
			double rand = Math.random();
			for (int j = 0; j < ChromList.size(); j++) {
				rand -= (1 / ChromList.get(j).getBestValue()) / totalValue;
				if (rand < 0) {
					// add chromosome behind original chromosome
					ChromList.add(ChromList.get(j));
					break;
				}
			}
		}

		// remove original chromosome
		int tmp = ChromList.size();
		for (int i = 0; i < tmp - populationNum; i++) {
			ChromList.remove(0);
		}

	}

	public void crossover() {
		int finishChrom = 0;
		int parentOne;
		int parentTwo;
		int randPosition;
		int completeCrossover = 0;
		double deicdeCrossover;

		while (finishChrom != populationNum) {
			// find two parent not repeat
			parentOne = (int) (Math.random() * populationNum);
			parentTwo = (int) (Math.random() * populationNum);
			randPosition = (int) (Math.random() * arrX);
			while (parentOne == parentTwo) {
				parentTwo = (int) (Math.random() * populationNum);
			}

			double[] arr1 = new double[arrX];
			double[] arr2 = new double[arrX];
			for (int i = 0; i < arrX; i++) {
				// get two parents
				arr1[i] = ChromList.get(parentOne).getBestPos()[i];
				arr2[i] = ChromList.get(parentTwo).getBestPos()[i];
			}

			deicdeCrossover = Math.random();

			// use crossover to generate two child
			if (deicdeCrossover <= crossoverRate) {
				completeCrossover = 1;
				// int changePos = (int) (Math.random() * arrX);
				for (int changePos = 0; changePos < arrX; changePos++) {

					randPosition = (int) (Math.random() * bitLength);
					while (randPosition == 0) {
						randPosition = (int) (Math.random() * bitLength);
					}

					char[] tmp1 = valueToBit(arr1[changePos]);
					char[] tmp2 = valueToBit(arr2[changePos]);

					for (int j = 0; j < bitLength; j++) {
						if (j >= randPosition) {
							char tmp = tmp1[j];
							tmp1[j] = tmp2[j];
							tmp2[j] = tmp;
						}
					} // for

					arr1[changePos] = bitToDouble(tmp1);
					arr2[changePos] = bitToDouble(tmp2);
				}

			}

			if (completeCrossover == 1) {
				// add child if child is odd
				if (populationNum - finishChrom == 1) {

					ChromOneD chrom = new ChromOneD(arrX);
					chrom.setAns(arr1);
					chrom.setValue(comV.computeVOneDD(arr1));
					ChromList.add(chrom);
					finishChrom += 1;
					// add child if child is even
				} else {
					ChromOneD chrom = new ChromOneD(arrX);
					chrom.setAns(arr1);
					chrom.setValue(comV.computeVOneDD(arr1));
					ChromList.add(chrom);

					ChromOneD chrom2 = new ChromOneD(arrX);
					chrom2.setAns(arr2);
					chrom2.setValue(comV.computeVOneDD(arr2));
					ChromList.add(chrom2);

					finishChrom += 2;

				}
			}
		}

		compareBest();

	}

	public void mutation() {

		double decideMutation;
		double[] tmp = new double[arrX];

		for (ChromOneD chrom : ChromList) {
			for (int i = 0; i < arrX; i++) {
				tmp[i] = chrom.getBestPos()[i];
			}

			for (int i = 0; i < arrX; i++) {
				decideMutation = Math.random();
				if (decideMutation <= mutationRate) {
					// mutate attack target
					double mutationNumber = (int) (Math.random() * arrY);
					tmp[i] = mutationNumber;
				}
			}
			chrom.setAns(tmp);
			chrom.setValue(comV.computeVOneDD(tmp));
		}
	}

	public char[] valueToBit(double input) {

		int in = (int) Math.round(input);
		String bitString = null;

		char[] bitChar = new char[bitLength];
		char initial = '0';

		Arrays.fill(bitChar, initial);

		bitString = Integer.toString(in, 2);
		int StringLen = bitString.length();

		for (int i = 0; i < bitLength; i++) {

			if (StringLen - 1 - i >= 0) {
				bitChar[bitLength - i - 1] = bitString.charAt(StringLen - 1 - i);
			}
		}

		return bitChar;
	}

	public double bitToDouble(char[] bit) {

		double num = 0;

		String tmp = String.valueOf(bit);

		double returnNum = Integer.valueOf(tmp, 2);

		// if not do this move will generate small num

		returnNum = Math.round(returnNum);

		if (returnNum > arrY - 1) {
			returnNum = arrY - 1;
		} else if (returnNum < 0) {
			returnNum = 0;
		}

		return returnNum;
	}

	public void compareBest() {

		for (ChromOneD chrom : ChromList) {
			if (chrom.getBestValue() < bestChrom.getBestValue()) {
				bestChrom.setValue(chrom.getBestValue());
				bestChrom.setAns(chrom.getBestPos());
			}
		}

	}

	@Override
	public double getIndividualPos() {

		return bestChrom.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return bestChrom;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub

	}

}
