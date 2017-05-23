package pso;

import other.Instance;

public class Particle implements Instance{

	private double [] particlePosNow;
	private double particleValueNow;
	private double [] localBestPos;
	private double localBestValue;
	private double [] velocity;
	private int arrX;
	
	public Particle(int arrX) {
		// TODO Auto-generated constructor stub
		this.arrX = arrX;
		particlePosNow = new double [arrX];
		localBestPos = new double [arrX];
		velocity = new double [arrX];
	}
	
	public void setParticlePos(double [] pos){	
		for(int i =0;i<arrX;i++){
			particlePosNow[i] = pos[i];		
		}
	}
	public void setValue(double value){
		particleValueNow = value;
		
	}
	
	public double [] getParticlePos(){		
		return particlePosNow;
	}
	public double getParticleValue(){
		return particleValueNow;
		
	}
	public void setParticleBestValue(double value){
		localBestValue = value;
		
	}
	public void setParticleBestPos(double[] pos){		
		for(int i =0;i<arrX;i++){
			localBestPos[i] = pos[i];
			
		}
		
	}
	
	public double getBestValue(){
		return localBestValue;
		
	}
	public double [] getBestPos(){
		return localBestPos;
		
	}
	
	public void initialVelocity(double edge){
		
		for(int i =0;i<arrX;i++){
			velocity[i] = (Math.random()*(edge*2))-edge;
		}
		
	}
	public void setVelocity(double [] vel){
		
		for(int i =0;i<arrX;i++){
			velocity[i] = vel[i];	
		}
	}
	
	public double[] getVelocity(){
		return velocity;
		
	}

	@Override
	public int getLimNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
