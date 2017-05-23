package storage;

import java.util.ArrayList;

import org.w3c.dom.Attr;

import abc.Nectar;
import computeValue.ComputeValue;
import genProblem.GenProblemFile;
import geneticAlgorithm.ChromOneD;
import other.Instance;
import other.InstanceCreate;
import pso.Particle;
import randomAlgorithm.RandomInstance;
import readFile.Attribute;

public class Solution {

	
	private Instance localBestAns;
	private Attribute AT;

	private ArrayList<Instance> otherBestAns;
	private int localNum;
	private int arrX;
	private int arrY;
	private int populationNum;
	private ComputeValue computeValue;
	private double initialVelocityEdge = 1;
	private int limitNumber = 1400;

	public Solution(Attribute AT) {

		this.AT = AT;
		otherBestAns = new ArrayList<Instance>();

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

	public void setLocalNum(int localNum) {

		this.localNum = localNum;
	}

	public void setLocalBestAns(Instance localBestAns) {

		this.localBestAns = localBestAns;

	}

	public void addOtherBestAns(String ans) {

		String[] anStrings = ans.split("-");
		int len = anStrings.length;
		double[] ansDouble = new double[len];
		for (int i = 0; i < len; i++) {

			ansDouble[i] = Double.parseDouble(anStrings[i]);
		}

		Instance instance = getInstane(ansDouble, computeValue.computeVOneDD(ansDouble));

		otherBestAns.add(instance);

	}

	public boolean checkOtherBestAns() {

		if (otherBestAns.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean checkLocalBestAns() {

		if (localBestAns != null) {
			return true;
		} else {
			return false;
		}
	}

	public int getLocalNum() {

		return localNum;

	}

	public String getLocalBestAns() {

		String ans = localBestAns.getBestPos()[0] + "-";
		int len = localBestAns.getBestPos().length;
		for (int j = 1; j < len; j++) {
			if (j == len - 1) {
				ans = ans + localBestAns.getBestPos()[j];
				break;
			}
			ans = ans + localBestAns.getBestPos()[j] + "-";
		}

		return ans;
	}

	public ArrayList<Instance> getOtherBestAns() {

		return otherBestAns;
	}

	public void clearOtherBestAns() {

		otherBestAns.clear();
	}

	public void initialSolution() {

		localBestAns = null;

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
