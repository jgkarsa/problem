package other;

public interface Instance{

	public double[] getBestPos();
	public double getBestValue();
	
	//only particle Swarm optimization
	public double[] getVelocity();
	public double[] getParticlePos();
	public double getParticleValue();
	
	//only artificial bee colony
	public int getLimNumber();
	
}
