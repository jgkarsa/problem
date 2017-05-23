package thread;

import java.util.Scanner;

import jade.core.Agent;
import readFile.Attribute;
import storage.Storage;
import timer.TimerPrintAns;

public class ReadCommand extends Thread {

	private Attribute AT;
	private Storage storage;
	private Agent mainAgent;
	private int waitPingTime = 2000;
	private TimerPrintAns timerPrintAns;

	public ReadCommand(Attribute AT, Storage storage, Agent mainAgent) {

		this.AT = AT;
		this.storage = storage;
		this.mainAgent = mainAgent;
		timerPrintAns = new TimerPrintAns(AT, storage);

	}

	@Override
	public void run() {

		Scanner scanner = new Scanner(System.in);
		String str;

		while (scanner.hasNext()) {
			str = scanner.nextLine();

			switch (str) {

			case "pingAll":
				SendThread sendThread = new SendThread(AT, storage, mainAgent);
				sendThread.setSendType("PingToPeer");
				sendThread.start();

				try {
					sleep(waitPingTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				storage.showAgentState();

				break;
			case "runAll":

				runMain();

				break;
			case "getid":

				System.out.println(mainAgent.getAID().getAddressesArray()[0]);

			}

		}

	}

	public void runMain() {

		// total experimentTime
		for (int k = 0; k < AT.getExperimentTime(); k++) {
			// reload attribute , first time not reload

			if (k != 0) {
				AT.initialAttribute();
			}

			for (int j = 0; j < AT.getAllRunTime(); j++) {

				//initial value
				storage.initialComposValue();
				
				SendThread sendThread = new SendThread(AT, storage, mainAgent);
				sendThread.setSendType("ParameterToPeer");
				sendThread.start();

				//wait agent receive parameter
				try {
					sleep(waitPingTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SendThread sendThread2 = new SendThread(AT, storage, mainAgent);
				sendThread2.setSendType("RunToPeer");
				sendThread2.start();				
				
				timerPrintAns.timerStart();				
				
				try {
					long sleepTime = (long) (1+AT.getDelayTime()+(AT.getPeriodTime()*AT.getExchangeNum())*1000+15000);
					sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				storage.showAgentRunState();
				
				System.out.println("*********************************");

			}

			System.out.println("------------------------------");
		}

	}

}
