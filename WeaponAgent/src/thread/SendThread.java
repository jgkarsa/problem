package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import jade.core.AID;
import jade.core.Agent;
import jade.core.IMTPException;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import readFile.Attribute;
import readFile.ReadAddress;
import storage.Solution;
import storage.Storage;

public class SendThread extends Thread {

	private ReadAddress readAddress;
	private String[] addressTable;
	private String type = null;
	private Storage storage;
	private Attribute AT;
	private Agent mainAgent;
	private Date date;
	private SimpleDateFormat dateFormat;

	public SendThread(Attribute AT, Storage storage, Agent mainAgent) {

		this.storage = storage;
		this.AT = AT;
		this.mainAgent = mainAgent;
		readAddress = new ReadAddress();
		addressTable = readAddress.getAddressTable();

		date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd(hh:ss)");
		dateFormat.format(date);

	}

	@Override
	public void run() {

		switch (type) {

		case "PingToCenter":
			PingToCenter();
			break;

		case "RunToCenter":
			RunToCenter();
			break;

		case "AnsToPeer":
			AnsToPeer();
			break;

		case "AnsToCenter":
			ansToCenter();
			break;

		}
	}

	public void PingToCenter() {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		AID receiver = new AID("agent" + readAddress.getCenterNum() + "@platform" + readAddress.getCenterNum(),
				AID.ISGUID);
		receiver.addAddresses("http://" + readAddress.getCenerAddress() + ":7778/acc");

		message.addReceiver(receiver);
		message.setLanguage("chinese");
		message.setOntology("test");
		message.setContent("PingToCenter " + dateFormat.format(date) + " " + AT.getWeaponNum());

		mainAgent.send(message);

	}

	public void RunToCenter() {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		AID receiver = new AID("agent" + readAddress.getCenterNum() + "@platform" + readAddress.getCenterNum(),
				AID.ISGUID);
		receiver.addAddresses("http://" + readAddress.getCenerAddress() + ":7778/acc");

		message.addReceiver(receiver);
		message.setLanguage("chinese");
		message.setOntology("test");
		message.setContent("RunToCenter " + dateFormat.format(date) + " " + AT.getWeaponNum());

		mainAgent.send(message);

	}

	public void AnsToPeer() {

		int addressTableLength = addressTable.length;
		for (int i = 0; i < addressTableLength; i++) {
			if ((i + 1) != AT.getWeaponNum()) {

				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				AID receiver = new AID("agent" + String.valueOf(i + 1) + "@platform" + String.valueOf(i + 1),
						AID.ISGUID);
				receiver.addAddresses("http://" + addressTable[i] + ":7778/acc");

				message.addReceiver(receiver);
				message.setLanguage("chinese");
				message.setOntology("test");

				message.setContent("AnsToPeer " + dateFormat.format(date) + " " + AT.getWeaponNum() + " "
						+ storage.getLocalBestAns());

				if (storage.checkLocalBestAns()) {
					mainAgent.send(message);
				}

			}
		}
	}

	public void ansToCenter() {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		AID receiver = new AID("agent" + readAddress.getCenterNum() + "@platform" + readAddress.getCenterNum(),
				AID.ISGUID);
		receiver.addAddresses("http://" + readAddress.getCenerAddress() + ":7778/acc");

		message.addReceiver(receiver);
		message.setLanguage("chinese");
		message.setOntology("test");

		message.setContent(
				"AnsToCenter " + dateFormat.format(date) + " " + AT.getWeaponNum() + " " + storage.getLocalBestAns());

		mainAgent.send(message);

	}

	public void setSendType(String type) {

		this.type = type;
	}

}
