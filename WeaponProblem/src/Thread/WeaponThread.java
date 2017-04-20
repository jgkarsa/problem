package Thread;

import algorithm.AlgorithmFactory;
import algorithm.Attribute;
import algorithm.Instance;
import algorithm.WeaponAlgorithm;
import genPopulation.populationManager;

public class WeaponThread extends Thread {

	private ThreadControl TC;
	private Attribute AT;
	private int exchangeNum;
	private int totalRun;
	private int TN;
	private boolean initialSolutionSet;
	private Instance localBestInstance;
	private int count;

	public WeaponThread(ThreadControl TC, Attribute AT, int TN) {
		// TODO Auto-generated constructor stub
		this.TC = TC;
		this.exchangeNum = AT.getExchangeNum();
		this.totalRun = AT.getTotalRun();
		this.initialSolutionSet = AT.getInitialSolutionSet();
		this.AT = AT;
		this.TN = TN;
	}

	@Override
	public void run() {

		// set algorithm
		AlgorithmFactory AF = new AlgorithmFactory(AT, TN);
		WeaponAlgorithm weaponAlgorithm = AF.getAlgorithm();

		// set attribute of population
		populationManager populationM = new populationManager(AT);

		for (int i = 0; i < exchangeNum; i++) {
			// set algorithmRunTime
			weaponAlgorithm.setStopCondition(totalRun);

			// set population of first run time
			if (i == 0) {
				// if use random population
				if (initialSolutionSet) {
					weaponAlgorithm.setPopulation(populationM.randomPopulation());
				}
				// use regular population
				else {
					weaponAlgorithm.setPopulation(populationM.filePopulation());
				}
				// set population of not first time
			} else {
				// get population from populationManger and compute
				weaponAlgorithm.setPopulation(populationM.getPopulation());
			}
			
			weaponAlgorithm.findAnswer();
			
			// connection not stable
			if (AT.getErrorRate() != 0) {

				TC.composePos(weaponAlgorithm.getIndividualPos(), TN);
			}
			// add population in the populationManget and wait other thread
			// finish		
			localBestInstance = weaponAlgorithm.getBestLocalAnswer();
			TC.addPopulation(localBestInstance);

			// if greedy and stop
			if (AT.getAlgorithm().equals("greedy") || AT.getAlgorithm().equals("bruteforce")) {
				return;
			}

			// get population from this thread compute
			populationM.storePopulation(weaponAlgorithm.getPopulation());

			// add population from the other thread and minus population of bad
			// performance
			count = 0;	
			for (Instance instance : TC.getInstance()) {
				
				// add instance into our population except original instance
				if (count == 0 && localBestInstance.getBestValue() == instance.getBestValue()) {
					count++;
				} else {
					// according to error rate decide add or not
					if (AT.getErrorRate() <= Math.random()) {

						if (AT.getAlgorithm().equals("particleswarm")) {
							
							weaponAlgorithm.setGlobalBestParticle(instance);
							break;
							
						} else {
							// add instance from other node of best
							populationM.addPopulation(instance);
						}

					}
				}

			}
			
			TC.waitOtherThread();

		}
	}

}
