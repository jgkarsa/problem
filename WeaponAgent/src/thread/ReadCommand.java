package thread;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import readFile.Attribute;
import storage.Storage;

public class ReadCommand extends Thread {

	private Attribute AT;
	private Storage storage;
	private Agent mainAgent;
	private int waitPingTime = 2000;

	public ReadCommand(Attribute AT, Storage storage, Agent mainAgent) {

		this.AT = AT;
		this.storage = storage;
		this.mainAgent = mainAgent;

	}

	@Override
	public void run() {

		Scanner scanner = new Scanner(System.in);
		String str;

		while (scanner.hasNext()) {
			str = scanner.nextLine();

			switch (str) {

			case "check":
				break;
			case "getid":

				System.out.println(mainAgent.getAID().getAddressesArray()[0]);

			}

		}

	}

}
