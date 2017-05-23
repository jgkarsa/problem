package thread;

import java.lang.ref.SoftReference;

import algorithmInterface.AlgorithmFactory;
import algorithmInterface.WeaponAlgorithm;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import other.Instance;
import readFile.Attribute;
import storage.Storage;
import storage.populationManager;

public class ComputeThread extends Thread {

	private Storage storage;
	private Attribute AT;
	private Agent mainAgent;
	private int exchangeNum;
	private int totalRun;
	private int TN;
	private boolean initialSolutionSet;

	public ComputeThread(Attribute AT, Storage storage,Agent mainAgent) {

		this.storage = storage;
		this.AT = AT;
		this.exchangeNum = AT.getExchangeNum();
		this.totalRun = AT.getTotalRun();
		this.initialSolutionSet = AT.getInitialSolutionSet();		
		this.TN = AT.getWeaponNum();
		this.mainAgent = mainAgent;
		
	}

	@Override
	public void run() {

		// set algorithm
		AlgorithmFactory AF = new AlgorithmFactory(AT,storage);
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
			

			// add population in the populationManget and wait other thread
			// finish
			
			// get population from this thread compute
			populationM.storePopulation(weaponAlgorithm.getPopulation());

			// add population from the other thread and minus population of bad
			// performance
			
			synchronized (storage) {
				if (storage.checkOtherBestAns()) {

					for (Instance instance : storage.getOtherBestAns()) {

						// add instance into our population except original
						// instance

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
				// clear other solution
				storage.clearOtherBestAns();

			}
			System.out.println("*******************************");
		}

	}

}
