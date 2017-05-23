package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import computeValue.ComputeValue;
import genProblem.GenProblemFile;
import other.Instance;
import readFile.Attribute;

public class populationManager {

	private BufferedReader br;
	private Attribute AT;
	private int arrX;
	private int arrY;
	private int populationNum;
	private ArrayList<Instance> arrayPopulation;
	private ComputeValue computeValue;

	public populationManager(Attribute AT) {
		// TODO Auto-generated constructor stub
		arrayPopulation = new ArrayList<Instance>();
		arrX = AT.getArrX();
		arrY = AT.getArrY();
		populationNum = AT.getPopulation();
		this.AT = AT;

		// initial input size
		int[] tarVal = new int[arrY];
		double[][] atkPro = new double[arrX][arrY];

		// initial input value
		GenProblemFile genP = new GenProblemFile(AT);
		// genP = new GenProblemRand();
		genP.genTarVal(tarVal);
		genP.genAtkPro(atkPro);

		computeValue = new ComputeValue(atkPro, tarVal);

	}

	public double[][] randomPopulation() {

		double population[][] = new double[populationNum][arrX];
		ArrayList<Double> compareValue = new ArrayList<Double>();

		for (int i = 0; i < populationNum; i++) {
			for (int j = 0; j < arrX; j++) {
				population[i][j] = (int) (Math.random() * arrY);

			}
			double populationValue = computeValue.computeVOneDD(population[i]);
			if (compareValue.size() == 0) {
				compareValue.add(populationValue);
			} else {
				boolean same = false;
				// compareSame
				for (double value : compareValue) {

					if (Double.compare(value, populationValue) == 0) {
						same = true;
						break;
					}

				}
				if (same) {
					i = i - 1;
				} else {
					compareValue.add(populationValue);

				}
			}

		}

		return population;
	}

	public double[][] filePopulation() {

		double population[][] = new double[populationNum][arrX];

		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/population/population"
					+ AT.getArrX() + "_" + AT.getArrY() + ".txt"));

			for (int i = 0; i < populationNum; i++) {
				String[] str = br.readLine().split(" ");
				for (int j = 0; j < arrX; j++) {
					population[i][j] = Double.parseDouble(str[j]);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return population;

	}

	public void storePopulation(ArrayList<Instance> population) {

		arrayPopulation.clear();
		for (Instance instance : population) {
			arrayPopulation.add(instance);
		}
		int leng = arrayPopulation.size();
		// sort population
		for (int i = 0; i < leng - 1; i++) {
			for (int j = 0; j < leng - i - 1; j++) {
				if (arrayPopulation.get(j).getBestValue() > arrayPopulation.get(j + 1).getBestValue()) {
					Collections.swap(arrayPopulation, j, j + 1);
				}
			}
		}

	}

	public void addPopulation(Instance instance) {

		if (!AT.getDuplicate()) {

			boolean same = false;
			for (Instance instance2 : arrayPopulation) {

				if (instance2.getBestValue() == instance.getBestValue()) {
					same = true;

					break;
				}

			}
			if (!same) {

				minusPopulation();
				arrayPopulation.add(0, instance);
			}
		} else {

			minusPopulation();
			arrayPopulation.add(0, instance);

		}
	}

	public void minusPopulation() {
		arrayPopulation.remove(arrayPopulation.size() - 1);
	}

	public double[][] getPopulation() {

		double[][] tmp = new double[populationNum][arrX];
		for (int i = 0; i < populationNum; i++) {
			tmp[i] = arrayPopulation.get(i).getBestPos();
		}

		return tmp;

	}

}
