package algorithm;

import java.util.ArrayList;

import computeValue.ComputeValue;

public class BruteForce implements WeaponAlgorithm {

	private int[][] tmpAns;
	private int[][] finAns;
	private int weaponNum;
	private double minV = Double.MAX_VALUE;
	private ComputeValue computeV;
	private Attribute AT;
	private int arrX;
	private int arrY;

	public BruteForce(double[][] atkPro, int[] tarVal,Attribute AT) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		computeV = new ComputeValue(atkPro, tarVal);
		this.AT = AT;

	}

	@Override
	public void findAnswer() {
		// TODO Auto-generated method stub
		tmpAns = new int[arrX][arrY];
		finAns = new int[arrX][arrY];

		weaponNum = 0;
		System.out.println(System.currentTimeMillis() / 1000);
		bruteMethod(weaponNum);
		System.out.println(System.currentTimeMillis() / 1000);

		for (int i = 0; i < arrX; i++) {
			for (int j = 0; j < arrY; j++) {
				System.out.print(finAns[i][j]);

			}
			System.out.println(" ");
		}
		System.out.println(minV);
	}

	@Override
	public void setPopulation(double [][] population) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void bruteMethod(int weaponNum) {

		for (int i = 0; i < arrY; i++) {
			if (weaponNum != arrX - 1) {
				if (i != 0) {
					tmpAns[weaponNum][i - 1] = 0;
				} else if (i == 0) {
					tmpAns[weaponNum][arrY - 1] = 0;
				}
				tmpAns[weaponNum][i] = 1;
				bruteMethod(weaponNum + 1);

			} else {
				if (i != 0) {
					tmpAns[weaponNum][i - 1] = 0;
				} else if (i == 0) {
					tmpAns[weaponNum][arrY - 1] = 0;
				}
				tmpAns[weaponNum][i] = 1;
				double val = computeV.computeV(tmpAns);
				if (minV > val) {
					minV = val;
					for (int m = 0; m < arrX; m++) {
						for (int n = 0; n < arrY; n++) {
							finAns[m][n] = tmpAns[m][n];
						}
					}
				}
			}
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
