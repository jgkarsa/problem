package randomAlgorithm;

import other.Instance;

public class RandomInstance implements Instance{

	private double [] randomPos;
	private double value;
	private int arrX;
	
	public RandomInstance(int arrX) {
		// TODO Auto-generated constructor stub
		this.arrX = arrX;
		randomPos = new double[arrX];
	}
	
	public void setRandomPos(double [] pos){
		
		for (int i = 0; i < arrX; i++) {
			randomPos[i] = pos[i];
		}

		
	}
	public void setValue(double value){
		this.value = value;
		
	}
		
	@Override
	public double[] getBestPos() {
		// TODO Auto-generated method stub	
		return randomPos;	
	}

	@Override
	public double getBestValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
