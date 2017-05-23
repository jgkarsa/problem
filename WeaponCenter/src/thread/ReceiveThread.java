package thread;

import java.util.jar.Manifest;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import storage.Storage;
import writeFile.Log;

public class ReceiveThread extends Thread {

	private Agent mainAgent;
	private Storage storage;
	private Log log;

	public ReceiveThread(Storage storage, Agent mainAgent) {

		this.storage = storage;
		this.mainAgent = mainAgent;
		log = new Log();

	}

	@Override
	public void run() {
		while (true) {
			ACLMessage msg = mainAgent.blockingReceive();
			log.writeLog(msg.getContent());
			if (msg != null) {
				System.out.println(msg.getContent());

				String[] line = msg.getContent().split(" ");

				switch (line[0]) {

				case "AnsToCenter":
					storage.composeAns(line[2], line[3]);
					break;
				case "PingToCenter":
					storage.setAgentState(line);
					break;

				case "RunToCenter":
					storage.setAgentRunState(line);
					break;

				}

			}

		}

	}

}
