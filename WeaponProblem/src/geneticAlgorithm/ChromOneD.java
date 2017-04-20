package geneticAlgorithm;

import algorithm.Instance;

public class ChromOneD implements Instance {

	private double[] ans;
	private double value;
	private int arrX;

	public ChromOneD(int arrX) {
		// TODO Auto-generated constructor stub
		this.arrX = arrX;
		ans = new double[arrX];

	}

	public void setAns(double[] ans) {
		for (int i = 0; i < arrX; i++) {
			this.ans[i] = ans[i];
		}

	}

	public void setValue(double value) {
		this.value = value;

	}

	public double[] getBestPos() {
		return ans;

	}

	public double getBestValue() {
		return value;

	}

}
