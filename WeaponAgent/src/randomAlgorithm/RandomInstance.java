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

	@Override
	public double[] getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getParticlePos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getParticleValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLimNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
