package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import abc.Nectar;
import computeValue.ComputeValue;
import genProblem.GenProblemFile;
import geneticAlgorithm.ChromOneD;
import other.Instance;
import pso.Particle;
import randomAlgorithm.RandomInstance;
import readFile.Attribute;

public class populationManager {

	private BufferedReader br;
	private Attribute AT;
	private int arrX;
	private int arrY;
	private int populationNum;
	private ArrayList<Instance> arrayPopulation;
	private ComputeValue computeValue;
	private double initialVelocityEdge = 1;
	private int limitNumber = 1400;

	public populationManager(Attribute AT) {
		// TODO Auto-generated constructor stub
		arrayPopulation = new ArrayList<Instance>();
		this.AT = AT;
		arrX = AT.getArrX();
		arrY = AT.getArrY();
		populationNum = AT.getPopulation();
		

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

	public ArrayList<Instance> randomPopulation() {

		arrayPopulation.clear();
		double ans[] = new double[arrX];
		ArrayList<Double> compareValue = new ArrayList<Double>();
		for (int i = 0; i < populationNum; i++) {
			for (int j = 0; j < arrX; j++) {
				ans[j] = (int) (Math.random() * arrY);

			}
			double costValue = computeValue.computeVOneDD(ans);
			Instance instance = getInstane(ans, costValue);

			if (compareValue.size() == 0) {
				compareValue.add(costValue);
				arrayPopulation.add(instance);

			} else {
				boolean same = false;
				// compareSame
				for (double value : compareValue) {

					if (Double.compare(value, costValue) == 0) {
						same = true;
						break;
					}

				}
				if (same) {
					i = i - 1;
				} else {
					compareValue.add(costValue);
					arrayPopulation.add(instance);
				}
			}

		}
		return arrayPopulation;
	}

	public ArrayList<Instance> filePopulation() {

		arrayPopulation.clear();

		double ans[] = new double[arrX];

		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/population/population"
					+ AT.getArrX() + "_" + AT.getArrY() + ".txt"));

			for (int i = 0; i < populationNum; i++) {
				String[] str = br.readLine().split(" ");
				for (int j = 0; j < arrX; j++) {
					ans[j] = Double.parseDouble(str[j]);
				}
				double value = computeValue.computeVOneDD(ans);
				Instance instance = getInstane(ans, value);
				arrayPopulation.add(instance);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayPopulation;

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

	public ArrayList<Instance> getPopulation() {

		return arrayPopulation;

	}

	public Instance getInstane(double[] pos, double value) {

		switch (AT.getAlgorithm()) {

		case "genetic":

			ChromOneD chromOneD = new ChromOneD(arrX);
			chromOneD.setAns(pos);
			chromOneD.setValue(value);
			return chromOneD;
		case "particleswarm":
			Particle particle = new Particle(arrX);
			particle.setParticlePos(pos);
			particle.setValue(value);
			particle.setParticleBestPos(pos);
			particle.setParticleBestValue(value);
			particle.initialVelocity(initialVelocityEdge);
			return particle;
		case "artificialbee":
			Nectar nectar = new Nectar(arrX, limitNumber);
			nectar.setBestPos(pos);
			nectar.setBestValue(value);
			nectar.setFrequency(limitNumber);
			return nectar;
		case "randomalgorithm":
			RandomInstance randomInstance = new RandomInstance(arrX);
			randomInstance.setRandomPos(pos);
			randomInstance.setValue(value);
			return randomInstance;
		}
		return null;

	}

}
