package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeValue;

public class GeneticDuplicate implements WeaponAlgorithm {

	private ComputeValue comV;
	private ArrayList<ChromOneD> parentChromList;
	private ArrayList<ChromOneD> childChromList;
	private ChromOneD bestChrom;
	private Attribute AT;

	private int arrX;
	private int arrY;
	private int populationNum;
	private int terminateCondition;
	private double crossoverRate = 0.8;
	private double mutationRate = 0.4;
	private int reProduceNumber;
	private int TN;

	public GeneticDuplicate(double[][] atkPro, int[] tarVal, Attribute AT, int TN) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);
		this.AT = AT;
		this.TN = TN;
		bestChrom = new ChromOneD(arrX);
		bestChrom.setValue(Double.MAX_VALUE);
		parentChromList = new ArrayList<ChromOneD>();
		childChromList = new ArrayList<ChromOneD>();

	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {

			if (AT.getExchangeNum() == 1) {

				if (i % 100 == 0) {
					System.out.println(bestChrom.getBestValue());
				}
			}

			reproduce();

			crossover();

			mutation();
			
			selection();
			
			
		}

	}

	// System.out.println(bestChrom.getBestValue());

	@Override
	public void setPopulation(double[][] population) {
		// TODO Auto-generated method stub
		parentChromList.clear();
		populationNum = population.length;
		reProduceNumber = populationNum / 10;
		// System.out.println(ChromList.size());
		for (int i = 0; i < populationNum; i++) {
			ChromOneD chrom = new ChromOneD(arrX);
			chrom.setAns(population[i]);
			chrom.setValue(comV.computeVOneDD(population[i]));

			parentChromList.add(chrom);
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
		for (ChromOneD chrom : parentChromList) {
			population.add(chrom);
		}

		return population;
	}

	public void reproduce() {

		// clear childChromList
		childChromList.clear();

		int leng = parentChromList.size();
		for (int i = 0; i < leng - 1; i++) {
			for (int j = 0; j < leng - i - 1; j++) {
				if (parentChromList.get(j).getBestValue() > parentChromList.get(j + 1).getBestValue()) {
					Collections.swap(parentChromList, j, j + 1);
				}
			}
		}

		for (int i = 0; i < reProduceNumber; i++) {

			ChromOneD chromOneD = new ChromOneD(arrX);
			double[] tmp = new double[arrX];
			for (int j = 0; j < arrX; j++) {

				tmp[j] = parentChromList.get(i).getBestPos()[j];

			}
			chromOneD.setAns(tmp);
			chromOneD.setValue(parentChromList.get(i).getBestValue());
			childChromList.add(chromOneD);
		}

	}

	public void crossover() {
		
		int parentLen = parentChromList.size();

		int runTime = populationNum / 2;
		for (int i = 0; i < runTime; i++) {

			// decide crossover
			double deicdeCrossover = Math.random();
			if (deicdeCrossover <= crossoverRate) {

				int parentOne;
				int parentTwo;
				int randPosition;

				// find two parent
				parentOne = (int) (Math.random() * parentLen);
				parentTwo = (int) (Math.random() * parentLen);
				randPosition = (int) (Math.random() * arrX);

				// find no repeat parent
				while (parentOne == parentTwo) {
					parentTwo = (int) (Math.random() * parentLen);
				}
				// find position not o
				while (randPosition == 0) {
					randPosition = (int) (Math.random() * arrX);
				}

				double[] arr1 = new double[arrX];
				double[] arr2 = new double[arrX];
				for (int j = 0; j < arrX; j++) {
					// get two parents
					arr1[j] = parentChromList.get(parentOne).getBestPos()[j];
					arr2[j] = parentChromList.get(parentTwo).getBestPos()[j];
				}

				// use crossover to generate two child
				for (int j = 0; j < arrX; j++) {
					if (j >= randPosition) {
						double tmp = arr1[j];
						arr1[j] = arr2[j];
						arr2[j] = tmp;
					}
				}
				addInList(arr1);
				addInList(arr2);
			} // end if
		} // end for

	}

	public void mutation() {

		for (ChromOneD chrom : parentChromList) {

			double decideMutation = Math.random();
			if (decideMutation <= mutationRate) {

				double[] mutArr = new double[arrX];
				int randomPosition = (int) (Math.random() * arrX);
				int mutationNumber = (int) (Math.random() * arrY);
				// avoid not mutation
				while (mutArr[randomPosition] == mutationNumber) {
					mutationNumber = (int) (Math.random() * arrY);
				}

				for (int j = 0; j < arrX; j++) {
					mutArr[j] = chrom.getBestPos()[j];
				}

				// mutate attack target
				mutArr[randomPosition] = mutationNumber;

				addInList(mutArr);

			} // end if
		} // end for

	}

	public void selection() {

		double totalValue = 0;
		parentChromList.clear();

		for (ChromOneD chrom : childChromList) {
			totalValue += (1 / chrom.getBestValue());
		}

		// use roulette wheel selection
		for (int i = 0; i < populationNum; i++) {
			double rand = Math.random();
			for (int j = 0; j < childChromList.size(); j++) {
				rand -= (1 / childChromList.get(j).getBestValue()) / totalValue;
				if (rand < 0) {
					// add chromosome behind original chromosome
					parentChromList.add(childChromList.get(j));

					break;
				}
			}
		}

		compareBest();

	}

	public int[][] oneToTwoDim(double ans[]) {

		int[][] ansTwoD = new int[arrX][arrY];

		for (int i = 0; i < arrX; i++) {
			ansTwoD[i][(int) ans[i]] = 1;
		}
		return ansTwoD;

	}

	public void addInList(double[] arr) {

		// add in chromList
		ChromOneD chrom = new ChromOneD(arrX);
		chrom.setAns(arr);
		chrom.setValue(comV.computeVOneDD(arr));
		childChromList.add(chrom);

	}

	public void compareBest() {

		for (ChromOneD chrom : parentChromList) {
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
