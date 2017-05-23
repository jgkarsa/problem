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

		case "PingToPeer":
			storage.clearAgentState();
			PingToPeer();
			break;
		case "RunToPeer":
			storage.clearAgentRunState();
			RunToPeer();
			break;
		case "ParameterToPeer":
			parameterToPeer();
			break;

		}
	}

	public void parameterToPeer(){
		
		int addressTableLength = addressTable.length;
		
		for (int i = 0; i < addressTableLength; i++) {

			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			AID receiver = new AID("agent" + String.valueOf(i + 1) + "@platform" + String.valueOf(i + 1), AID.ISGUID);
			receiver.addAddresses("http://" + addressTable[i] + ":7778/acc");

			message.addReceiver(receiver);
			message.setLanguage("chinese");
			message.setOntology("test");
			message.setContent("ParameterToPeer "+dateFormat.format(date)+
					" "+AT.getDelayTime()+
					" "+AT.getPeriodTime()+
					" "+AT.getExchangeNum()+
					" "+AT.getTotalRun()+
					" "+AT.getAlgorithm()+
					" "+AT.getErrorRate() );

			mainAgent.send(message);

		}

		
		
	}
	
	
	public void PingToPeer() {

		int addressTableLength = addressTable.length;
		for (int i = 0; i < addressTableLength; i++) {

			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			AID receiver = new AID("agent" + String.valueOf(i + 1) + "@platform" + String.valueOf(i + 1), AID.ISGUID);
			receiver.addAddresses("http://" + addressTable[i] + ":7778/acc");

			message.addReceiver(receiver);
			message.setLanguage("chinese");
			message.setOntology("test");
			message.setContent("PingToPeer "+dateFormat.format(date));

			mainAgent.send(message);

		}


	}

	public void RunToPeer() {

		int addressTableLength = addressTable.length;
		for (int i = 0; i < addressTableLength; i++) {

			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			AID receiver = new AID("agent" + String.valueOf(i + 1) + "@platform" + String.valueOf(i + 1), AID.ISGUID);
			receiver.addAddresses("http://" + addressTable[i] + ":7778/acc");

			message.addReceiver(receiver);
			message.setLanguage("chinese");
			message.setOntology("test");
			message.setContent("RunToPeer "+dateFormat.format(date));

			mainAgent.send(message);

		}

	}


	public void setSendType(String type) {

		this.type = type;
	}

}
