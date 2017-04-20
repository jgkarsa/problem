package particleAlogrithm;

import java.util.ArrayList;

import org.w3c.dom.Attr;

import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import computeValue.ComputeValue;

public class PSO implements WeaponAlgorithm {

	private ComputeValue comV;
	private ArrayList<Particle> particleList;
	private Particle globalBestparticle;
	private Attribute AT;

	private int arrX;
	private int arrY;
	private int terminateCondition;
	private int particleNumber;
	private double initialVelocityEdge = 1;
	private double c1 = 1;
	private double c2 = 1;
	private double weight = 1;
	private int TN;

	public PSO(double[][] atkPro, int[] tarVal, Attribute AT, int TN) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);
		this.AT = AT;
		this.TN = TN;
		globalBestparticle = new Particle(arrX);
		globalBestparticle.setParticleBestValue(Double.MAX_VALUE);
		particleList = new ArrayList<Particle>();
	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {
			if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(globalBestparticle.getBestValue());
				}
			}

			adjustBestValue();

			calculatePos();

		}

		// System.out.println(globalBestparticle.getBestValue());

	}

	@Override
	public void setPopulation(double[][] population) {
		// TODO Auto-generated method stub
		particleList.clear();
		particleNumber = population.length;

		for (int i = 0; i < particleNumber; i++) {
			Particle particle = new Particle(arrX);

			particle.setParticlePos(population[i]);
			particle.setValue(comV.computeVOneDD(population[i]));
			particle.setParticleBestPos(population[i]);
			particle.setParticleBestValue(comV.computeVOneDD(population[i]));
			particle.initialVelocity(initialVelocityEdge);
			particleList.add(particle);

		}
		compareBest();

	}

	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub
		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub
		ArrayList<Instance> population = new ArrayList<Instance>();
		for (Particle particle : particleList) {
			population.add(particle);
		}

		return population;
	}

	public void adjustBestValue() {

		for (Particle particle : particleList) {
			// update local best value
			if (particle.getParticleValue() < particle.getBestValue()) {
				particle.setParticleBestValue(particle.getParticleValue());
				particle.setParticleBestPos(particle.getParticlePos());
			}
		}
		compareBest();
	}

	public void calculatePos() {

		// calculate velocity
		for (Particle particle : particleList) {
			double[] tmpV = new double[arrX];
			double[] tmpP = new double[arrX];
			
			for (int i = 0; i < arrX; i++) {

				tmpV[i] = weight * particle.getVelocity()[i]
						+ c1 * Math.random() * (particle.getBestPos()[i] - particle.getParticlePos()[i])
						+ c2 * Math.random() * (globalBestparticle.getBestPos()[i] - particle.getParticlePos()[i]);

				// control velocity in [initialVelocityEdge ~
				// -initialVelocityEdge]
				if (tmpV[i] > initialVelocityEdge)
					tmpV[i] = initialVelocityEdge;
				if (tmpV[i] < -initialVelocityEdge)
					tmpV[i] = -initialVelocityEdge;

				tmpP[i] = particle.getParticlePos()[i] + tmpV[i];

				if (Math.round(tmpP[i]) > arrY -1)
					tmpP[i] = arrY - 1;
				if (Math.round(tmpP[i]) < 0)
					tmpP[i] = 0;

			}
//			for (int i = 0; i < arrX; i++) {
//			
//				tmpV[i] = particle.getVelocity()[i];
//				tmpP[i] = particle.getParticlePos()[i];
//			}
//
//			int randomChange = (int)(Math.random()*arrX);
//				tmpV[randomChange] = weight * particle.getVelocity()[randomChange]
//						+ c1 * Math.random() * (particle.getBestPos()[randomChange] - particle.getParticlePos()[randomChange])
//						+ c2 * Math.random() * (globalBestparticle.getBestPos()[randomChange] - particle.getParticlePos()[randomChange]);
//
//				// control velocity in [initialVelocityEdge ~
//				// -initialVelocityEdge]
//				if (tmpV[randomChange] > initialVelocityEdge)
//					tmpV[randomChange] = initialVelocityEdge;
//				if (tmpV[randomChange] < -initialVelocityEdge)
//					tmpV[randomChange] = -initialVelocityEdge;
//
//				tmpP[randomChange] = particle.getParticlePos()[randomChange] + tmpV[randomChange];
//
//				if (Math.round(tmpP[randomChange]) > arrY -1)
//					tmpP[randomChange] = arrY - 1;
//				if (Math.round(tmpP[randomChange]) < 0)
//					tmpP[randomChange] = 0;
//
//			
			
			
			
			particle.setVelocity(tmpV);
			particle.setParticlePos(tmpP);
			particle.setValue(comV.computeVOneDD(tmpP));
		}

	}

	public int[][] oneToTwoDim(double ans[]) {

		int[][] ansTwoD = new int[arrX][arrY];

		for (int i = 0; i < arrX; i++) {
			ansTwoD[i][(int) (Math.round(ans[i]))] = 1;
		}
		return ansTwoD;

	}

	public void compareBest() {

		for (Particle particle : particleList) {
			if (particle.getParticleValue() < globalBestparticle.getBestValue()) {
				globalBestparticle.setParticleBestValue(particle.getParticleValue());
				globalBestparticle.setParticleBestPos(particle.getBestPos());
			}
		}
	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return globalBestparticle.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return globalBestparticle;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		
		if(globalBestparticle.getBestValue() > instance.getBestValue()){
			
			globalBestparticle.setParticleBestValue(instance.getBestValue());
			globalBestparticle.setParticleBestPos(instance.getBestPos());
		}
	}

}
