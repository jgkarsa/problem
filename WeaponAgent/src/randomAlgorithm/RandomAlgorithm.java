package randomAlgorithm;

import java.util.ArrayList;

import algorithmInterface.WeaponAlgorithm;
import computeValue.ComputeValue;
import other.Instance;
import readFile.Attribute;
import storage.Storage;


public class RandomAlgorithm implements WeaponAlgorithm {

	private ComputeValue comV;
	private RandomInstance randombest;
	private Attribute AT;

	private ArrayList<RandomInstance> randomList;
	private int populationNum;
	private int terminateCondition;
	private int arrX;
	private int arrY;
	private int TN;
	private Storage storage ;

	public RandomAlgorithm(double[][] atkPro, int[] tarVal, Attribute AT,Storage storage) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);
		this.AT = AT;
		this.TN = AT.getWeaponNum();
		randombest = new RandomInstance(arrX);
		randombest.setValue(Double.MAX_VALUE);
		randomList = new ArrayList<RandomInstance>();

		this.storage = storage;
	}

	@Override
	public void findAnswer() {
		
		for (int i = 0; i <= terminateCondition; i++) {
			
			
		//	if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(randombest.getBestValue());

					synchronized (storage) {
						storage.setLocalBestAns(randombest);				
					}
				}
	//		}

			randomListUpdate();

			compareBest();
		
		}
		// System.out.println(randombest.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {
		
		randomList.clear();

		for (Instance instance:population) {

			RandomInstance randomInstance = new RandomInstance(arrX);
			randomInstance.setRandomPos(instance.getBestPos());
			randomInstance.setValue(instance.getBestValue());
			
			randomList.add(randomInstance);

		}
		compareBest();
	}

	@Override
	public void setStopCondition(int time) {

		terminateCondition = time;
	}

	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub

		ArrayList<Instance> population = new ArrayList<Instance>();
		for (RandomInstance random : randomList) {
			population.add(random);
		}

		return population;
	}

	public void compareBest() {

		for (RandomInstance random : randomList) {

			if (random.getBestValue() < randombest.getBestValue()) {

				randombest.setRandomPos(random.getBestPos());
				randombest.setValue(random.getBestValue());

			}

		}

	}

	public void randomListUpdate() {

		for (RandomInstance random : randomList) {

			for (int i = 0; i < arrX; i++) {

				random.getBestPos()[i] = (int) (Math.random() * arrY);
			}
			random.setValue(comV.computeVOneDD(random.getBestPos()));
		}

	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		
		
		return randombest.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return randombest;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}

}
