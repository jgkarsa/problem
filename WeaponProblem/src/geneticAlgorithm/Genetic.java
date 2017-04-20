package geneticAlgorithm;

import java.util.ArrayList;
import algorithm.Instance;
import algorithm.RandCreate;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeValue;

public class Genetic implements WeaponAlgorithm {

	private ComputeValue comV;
	private RandCreate rand;
	private ArrayList<Chrom> ChromList;
	private Chrom bestChrom;
	
	private int arrX;
	private int arrY;
	
	private int population = 50;
	private int terminateCondition;
	private double crossoverRate = 0.8;
	private double mutationRate = 0.1;

	public Genetic(double[][] atkPro, int[] tarVal) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);
		rand = new RandCreate(arrX, arrY);
		bestChrom = new Chrom();
		bestChrom.setValue(Double.MAX_VALUE);
		ChromList = new ArrayList<Chrom>();
	}

	@Override
	public void findAnswer() {
		// TODO Auto-generated method stub		
		System.out.println(System.currentTimeMillis()/1000);
		
		initialPopulation();

		for(int i =0;i<terminateCondition;i++){

			selection();

			crossover();

			mutation();

		}
		
		System.out.println(bestChrom.getValue());
		System.out.println(System.currentTimeMillis()/1000);
	}
	

	@Override
	public void setPopulation(double[][] population) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub
		terminateCondition = time;
	}
	

	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialPopulation() {

//		Chrom chromGre = new Chrom();
//		int [][]tmp1 = gre.targetHasOverlap();
//		chromGre.setAns(tmp1);
//		chromGre.setValue(comV.computeV(tmp1));
//		ChromList.add(chromGre);
//		
//		Chrom chromGre1 = new Chrom();
//		int [][]tmp2 = gre.targetNOverlap();
//		chromGre1.setAns(tmp2);
//		chromGre1.setValue(comV.computeV(tmp2));
//		ChromList.add(chromGre1);
		
		
		for (int i = 0; i < population; i++) {
			Chrom chrom = new Chrom();
			int[][] tmp = new int[arrX][arrY];
			for(int j =0;j<arrX;j++){
				tmp[j] = rand.randNOverlap()[j];							
			}
			chrom.setAns(tmp);
			chrom.setValue(comV.computeV(tmp));
			ChromList.add(chrom);

		}

	}

	public void selection() {

		double totalValue = 0;
		for (Chrom chrom : ChromList) {

			totalValue += (1 / chrom.getValue());

			if (chrom.getValue() < bestChrom.getValue()) {
				bestChrom.setValue(chrom.getValue());				
				bestChrom.setAns(chrom.getAns());				
			}
		}

		for (int i = 0; i < population; i++) {
			double roulette = Math.random();
			for (int j = 0; j < population; j++) {
				roulette -= (1 / ChromList.get(j).getValue()) / totalValue;
				if (roulette < 0) {
					ChromList.add(ChromList.get(j));
					break;
				}
			}
		}
		// remove orign chrom
		for (int i = 0; i < population; i++) {
			ChromList.remove(0);
		}

	}

	public void crossover() {
		int finishChrom = 0;
		int parentOne;
		int parentTwo;
		int randPosition;
		double deicdeCrossover;

		while (finishChrom != population) {
			parentOne = (int) (Math.random() * population);
			parentTwo = (int) (Math.random() * population);
			randPosition = (int) (Math.random() * arrX);
			while (parentOne == parentTwo) {
				parentTwo = (int) (Math.random() * population);
			}
			while (randPosition == 0) {
				randPosition = (int) (Math.random() * arrX);
			}
			int[][] arr1 = new int[arrX][arrY];
			int[][] arr2 = new int[arrX][arrY];
			for (int i = 0; i < arrX; i++) {
				for (int j = 0; j < arrY; j++) {
					arr1[i][j] = ChromList.get(parentOne).getAns()[i][j];
					arr2[i][j] = ChromList.get(parentTwo).getAns()[i][j];
				}
			}
			deicdeCrossover = Math.random();

			if (deicdeCrossover <= crossoverRate) {
				for (int i = 0; i < arrX; i++) {
					for (int j = 0; j < arrY; j++) {
						if (i >= randPosition) {
							int tmp = arr1[i][j];
							arr1[i][j] = arr2[i][j];
							arr2[i][j] = tmp;
						}
					}
				}
			}

			if (population - finishChrom == 1) {

				Chrom chrom = new Chrom();
				chrom.setAns(arr1);
				chrom.setValue(comV.computeV(arr1));
				ChromList.add(chrom);
				finishChrom += 1;

			} else {
				Chrom chrom = new Chrom();
				chrom.setAns(arr1);
				chrom.setValue(comV.computeV(arr1));
				ChromList.add(chrom);

				Chrom chrom2 = new Chrom();
				chrom2.setAns(arr2);
				chrom2.setValue(comV.computeV(arr2));
				ChromList.add(chrom2);

				finishChrom += 2;
			}
		}
		// remove preceding chrom
		for (int i = 0; i < population; i++) {
			ChromList.remove(0);
		}
		for (Chrom chrom : ChromList) {
			if (chrom.getValue() < bestChrom.getValue()) {
				bestChrom.setValue(chrom.getValue());			
				bestChrom.setAns(chrom.getAns());							
			}
		}

	}

	public void mutation() {

		double decideMutation;
		int[][] tmp = new int[arrX][arrY];

		for (Chrom chrom : ChromList) {

			for (int i = 0; i < arrX; i++) {
				for (int j = 0; j < arrY; j++) {
					tmp[i][j] = chrom.getAns()[i][j];
				}
			}

			for (int i = 0; i < arrX; i++) {
				decideMutation = Math.random();
				if (decideMutation <= mutationRate) {
					int pos = (int) (Math.random() * arrY);
					for (int j = 0; j < arrY; j++) {
						if (j == pos) {
							tmp[i][j] = 1;
						} else {
							tmp[i][j] = 0;
						}
					}
				}
			}

			chrom.setAns(tmp);
			chrom.setValue(comV.computeV(tmp));

		}
	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}


}
