package thread;

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

	private Storage solution;
	private Attribute AT;
	private int exchangeNum;
	private int totalRun;
	private int TN;
	private boolean initialSolutionSet;
	private Agent mainAgent;

	public ComputeThread(Attribute AT, Storage solution,Agent mainAgent) {

		this.mainAgent = mainAgent;
		this.solution = solution;
		this.AT = AT;
		this.exchangeNum = AT.getExchangeNum();
		this.totalRun = AT.getTotalRun();
		this.initialSolutionSet = AT.getInitialSolutionSet();		
		this.TN = AT.getWeaponNum();
		
	}

	@Override
	public void run() {

		// set algorithm
		AlgorithmFactory AF = new AlgorithmFactory(AT);
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

				SendThread sendThread = new SendThread(AT, solution,mainAgent);
				sendThread.setSendType("AnsToCenter");
				sendThread.start();

			}

			// add population in the populationManget and wait other thread
			// finish
			synchronized (solution) {
				solution.setLocalBestAns(weaponAlgorithm.getBestLocalAnswer());
			}
			// get population from this thread compute
			populationM.storePopulation(weaponAlgorithm.getPopulation());

			// add population from the other thread and minus population of bad
			// performance

			synchronized (solution) {
				if (solution.checkOtherBestAns()) {

					for (Instance instance : solution.getOtherBestAns()) {

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
				solution.clearOtherBestAns();

			}

		}

	}

}
