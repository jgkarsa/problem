package agent;

import jade.core.Agent;
import readFile.Attribute;
import storage.Storage;
import thread.ComputeThread;
import thread.ReadCommand;
import thread.ReceiveThread;
import timer.TimerPrintAns;

public class ComputeAgent extends Agent {

	private ReceiveThread receiveThread;
	private ComputeThread computeThread;
	private Storage storage;
	private ReadCommand readCommand;
	
	
	
	public ComputeAgent() {
		Attribute AT = new Attribute();
		AT.initialAttribute();
		
		
		storage = new Storage(AT);
		receiveThread = new ReceiveThread(storage,this);
		computeThread = new ComputeThread(AT,storage,this);
		readCommand = new ReadCommand(AT,storage,this);
		
		
	}
	
	@Override
	protected void setup() {

		
		//readCommand
		readCommand.start();	
//
//		computeThread.start();
//		
		receiveThread.start();		
//		
		
		
		
		
		
		
	}
	
	
	
}
