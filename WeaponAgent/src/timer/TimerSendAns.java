package timer;

import java.util.Timer;
import java.util.TimerTask;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import readFile.Attribute;
import storage.Storage;
import thread.SendThread;
import thread.thread;

public class TimerSendAns extends thread {

	private Attribute AT;
	private Timer timer;
	private int count;
	private Storage storage;
	private Agent mainAgent;
	private int threshold;

	public TimerSendAns(Attribute AT, Storage storage, Agent mainAgent) {

		this.AT = AT;
		timer = new Timer();

		this.storage = storage;
		this.mainAgent = mainAgent;

	}

	@Override
	public void run() {

		long delayTime = Math.round(AT.getDelayTime() * 1000);
		long periodTime = Math.round(AT.getPeriodTime() * 1000);
		// timer.schedule(timeTask,delayTime , periodTime);

		count = 0;

		switch (AT.getAlgorithm()) {

		case "genetic":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun() / AT.getOneSecondGenetic() / AT.getPeriodTime());
			break;
		case "particleswarm":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun() / AT.getOneSecondPSO() / AT.getPeriodTime());
			break;
		case "artificialbee":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun() / AT.getOneSecondABC() / AT.getPeriodTime());
			break;
		case "randomalgorithm":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun() / AT.getOneSecondRandom() / AT.getPeriodTime());
			break;

		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				count++;				
				sendMessage();
				
				if (count == threshold) {

					cancel();
				}
			}
		}, delayTime, periodTime);

	}

	public void sendMessage() {

		SendThread sendToPeer = new SendThread(AT, storage, mainAgent);
		sendToPeer.setSendType("AnsToPeer");
		sendToPeer.start();
             
		SendThread sendToCenter = new SendThread(AT, storage, mainAgent);
		sendToCenter.setSendType("AnsToCenter");
		sendToCenter.start();

	}

}
