package thread;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import readFile.Attribute;
import storage.AgentState;
import storage.Storage;
import timer.TimerSendAns;
import writeFile.Log;

public class ReceiveThread extends Thread {

	private Agent mainAgent;
	private Storage storage;
	private Attribute AT;
	private Log log;

	public ReceiveThread(Attribute AT, Storage storage, Agent mainAgent) {

		this.storage = storage;
		this.mainAgent = mainAgent;
		this.AT = AT;
		log = new Log();
	}

	@Override
	public void run() {
		while (true) {
			ACLMessage msg = mainAgent.blockingReceive();

			if (msg != null) {
				log.writeLog(msg.getContent());

				String[] line = msg.getContent().split(" ");

				switch (line[0]) {

				case "AnsToPeer":
					synchronized (storage) {
						storage.addOtherBestAns(line[3]);
					}
					break;
				case "PingToPeer":
					SendThread sendThread = new SendThread(AT, storage, mainAgent);
					sendThread.setSendType("PingToCenter");
					sendThread.start();
					break;
				case "RunToPeer":
					SendThread sendThread2 = new SendThread(AT, storage, mainAgent);
					sendThread2.setSendType("RunToCenter");
					sendThread2.start();
					
					ComputeThread computeThread = new ComputeThread(AT, storage, mainAgent);
					TimerSendAns timerSendAns = new TimerSendAns(AT, storage, mainAgent);
					timerSendAns.start();
					computeThread.start();

					break;
				case "ParameterToPeer":

					synchronized (storage) {
						storage.initialSolutionAtribute();
						storage.clearOtherBestAns();
					}
					
					AT.setDelayTime(line[2]);
					AT.setPeriodTime(line[3]);
					AT.setExchangeNum(line[4]);
					AT.setTotalRun(line[5]);
					AT.setAlgorithm(line[6]);
					AT.setErrorRate(line[7]);
					break;
				}

			}

		}

	}

}
