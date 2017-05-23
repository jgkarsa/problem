package abc;

import java.util.ArrayList;
import algorithmInterface.WeaponAlgorithm;
import computeValue.ComputeValue;
import other.Instance;
import other.RandCreate;
import readFile.Attribute;
import storage.Storage;

public class ArtificialBee implements WeaponAlgorithm {

	private ComputeValue comV;
	private RandCreate rand;

	private Nectar bestNectar;
	private Attribute AT;
	private ArrayList<Nectar> nectarList;
	private int arrX;
	private int arrY;
	private int terminateCondition;
	private int empoloyedBeeNumber;
	private int onlookerBeeNumber;
	private int freNumber = 1400;
	private int TN;
	private Storage storage;

	public ArtificialBee(double[][] atkPro, int[] tarVal, Attribute AT,Storage storage) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comV = new ComputeValue(atkPro, tarVal);
		rand = new RandCreate(arrX, arrY);
		this.AT = AT;
		this.TN = AT.getWeaponNum();
		bestNectar = new Nectar(arrX,freNumber);
		bestNectar.setBestValue(Double.MAX_VALUE);
		nectarList = new ArrayList<Nectar>();
		empoloyedBeeNumber = AT.getPopulation();
		onlookerBeeNumber = AT.getPopulation();

		this.storage = storage;
	}

	@Override
	public void findAnswer() {

		for (int i = 0; i <= terminateCondition; i++) {
	//		if (AT.getExchangeNum() == 1) {
				if (i % 100 == 0) {
					System.out.println(bestNectar.getBestValue());
					
					synchronized (storage) {
						storage.setLocalBestAns(bestNectar);
					
					}
				}
	//		}

			employedBee();

			onLookerBee();

			scoutBee();

			CompareBestNectar();
		}
		// System.out.println(bestBee.getBestValue());
	}

	@Override
	public void setPopulation(ArrayList<Instance> population) {

		nectarList.clear();

		for (Instance instance :population) {

			Nectar nectar = new Nectar(arrX,freNumber);
			nectar.setBestPos(instance.getBestPos());
			nectar.setBestValue(instance.getBestValue());
			nectar.setFrequency(instance.getLimNumber());
			
			nectarList.add(nectar);
		}
		CompareBestNectar();

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
		for (Nectar nectar : nectarList) {
			population.add(nectar);
		}
		return population;
	}

	public void employedBee() {
		int count = 0;
		for (Nectar nectar : nectarList) {
			changePos(nectar, count);
			count++;
		}
	}

	public void onLookerBee() {

		double sumValue = 0;
		for (Nectar nectar : nectarList) {
			sumValue += 1 / nectar.getBestValue();
		}
		// chose position to dispatch onlookerbee
		for (int i = 0; i < onlookerBeeNumber; i++) {
			double roulette = Math.random();
			int count = 0;
			for (Nectar nectar : nectarList) {
				roulette -= (1 / nectar.getBestValue()) / sumValue;
				count++;
				if (roulette < 0) {
					changePos(nectar, count);
					break;
				}
			}
		}
	}

	public void scoutBee() {

		for (Nectar nectar : nectarList) {
			nectar.minusFrequency();
			// find which bee frequency is lower zero , and change bee
			if (nectar.getFrequency() <= 0) {
				double[] tmp = new double[arrX];
				for (int j = 0; j < arrX; j++) {
					tmp[j] = rand.randNOverlapOneD()[j];
				}
				nectar.setBestPos(tmp);
				nectar.setBestValue(comV.computeVOneDD(tmp));
				nectar.setFrequency(freNumber);

			}
		}
		

	}

	// change position and if value is lower than before replace it
	public void changePos(Nectar nectar, int count) {

		double[] tmpPos = new double[arrX];

		int randNectar = (int) (Math.random() * empoloyedBeeNumber);
		while (randNectar == count) {

			randNectar = (int) (Math.random() * empoloyedBeeNumber);
		}
//		for (int i = 0; i < arrX; i++) {
//			tmpPos[i] = nectar.getBestPos()[i]
//					+ (Math.random() * 2 - 1) * (nectar.getBestPos()[i] 
//							- nectarList.get(randNectar).getBestPos()[i]);
//
//			if (tmpPos[i] < 0)
//				tmpPos[i] = 0;
//			if (tmpPos[i] > arrY - 1)
//				tmpPos[i] = arrY - 1;
//		}
		for (int i = 0; i < arrX; i++) {
			tmpPos[i] = nectar.getBestPos()[i];			
		}
		int randomWeapon = (int)(Math.random()*arrX);
		tmpPos[randomWeapon ] = nectar.getBestPos()[randomWeapon ]
				+ (Math.random() * 2 - 1) * (nectar.getBestPos()[randomWeapon ] 
						- nectarList.get(randNectar).getBestPos()[randomWeapon ]);

		if (tmpPos[randomWeapon ] < 0)
			tmpPos[randomWeapon ] = 0;
		if (tmpPos[randomWeapon ] > arrY - 1)
			tmpPos[randomWeapon ] = arrY - 1;
	
		
		

		double tmpValue = comV.computeVOneDD(tmpPos);
		if (nectar.getBestValue() > tmpValue) {

			// not same
			if (!compareSame(tmpValue)) {
				nectar.setBestPos(tmpPos);
				nectar.setBestValue(tmpValue);
				nectar.initialFequency();
			}
		}

	}

	// find which value is lower
	public void CompareBestNectar() {

		for (Nectar nectar : nectarList) {

			if (nectar.getBestValue() < bestNectar.getBestValue()) {

				bestNectar.setBestValue(nectar.getBestValue());
				bestNectar.setBestPos(nectar.getBestPos());
			}

		}

	}

	public boolean compareSame(double nectarValue) {

		boolean same = false;
		for (Nectar nectar2 : nectarList) {

			if (nectar2.getBestValue() == nectarValue) {
				same = true;
				break;
			}
		}
		if (same) {
			return true;
		} else {
			return false;
		}

	}

	public int[][] oneToTwoDim(double ans[]) {

		int[][] ansTwoD = new int[arrX][arrY];

		for (int i = 0; i < arrX; i++) {
			ansTwoD[i][(int) (Math.round(ans[i]))] = 1;
		}
		return ansTwoD;

	}

	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return bestNectar.getBestPos()[TN];
	}

	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return bestNectar;
	}

	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub

	}

}
