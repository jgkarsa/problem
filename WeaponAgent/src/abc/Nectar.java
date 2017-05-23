package abc;

import other.Instance;

public class Nectar implements Instance{
	
	private double []bestPos;
	private double bestValue;
	private int arrX;
	private int originFrequency;
	private int remainFrequency;
	
	public Nectar(int arrX,int limNumber) {
		// TODO Auto-generated constructor stub
		this.arrX = arrX;
		bestPos = new double[arrX];
		originFrequency = limNumber;
	}
	
	public void setBestPos(double[] pos){		
		for(int i =0;i<arrX;i++){
			bestPos[i] = pos[i];
		}		
	}
	public void setBestValue(double value){
		bestValue = value;
		
	}
	
	public double[] getBestPos(){
		return bestPos;
	}
	
	public double getBestValue(){
		return bestValue;
	}
	public void setFrequency(int fre){
		
		remainFrequency = fre;
		
	}
	
	public void initialFequency(){
		remainFrequency = originFrequency;
		
	}
	public void minusFrequency(){
		remainFrequency -= 1;
		
	}
	public int getFrequency(){
		return remainFrequency;
		
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
		
		return remainFrequency;
	}

}
