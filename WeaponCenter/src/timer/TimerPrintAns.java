package timer;

import java.util.Timer;
import java.util.TimerTask;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import readFile.Attribute;
import storage.Storage;
import thread.SendThread;

public class TimerPrintAns  {

	private Storage storage;
	private Timer timer;
	private Attribute AT;
	private int count;
	private int threshold;
	public TimerPrintAns(Attribute AT, Storage storage) {

		this.AT = AT;
		timer = new Timer();
		this.storage = storage;

	}

	public void timerStart(){

		count = 0;
		switch(AT.getAlgorithm()){
		
		case "genetic":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun()/AT.getOneSecondGenetic()/AT.getPeriodTime());
			break;			
		case "particleswarm":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun()/AT.getOneSecondPSO()/AT.getPeriodTime());
			break;
		case "artificialbee":
			threshold = (int)(AT.getExchangeNum() * AT.getTotalRun()/AT.getOneSecondABC()/AT.getPeriodTime());
			break;
		case "randomalgorithm":
			threshold =(int)(AT.getExchangeNum() * AT.getTotalRun() / AT.getOneSecondRandom() / AT.getPeriodTime());
			break;
		}
		
		//more than agent delay time for wait for agent send first ans
		long delayTime = Math.round(AT.getDelayTime()* 1000);
		long periodTime =Math.round(AT.getPeriodTime()* 1000);
		
		threshold++;
	
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				count++;
				storage.printComposAns();
						
				if(count == threshold){					
					storage.printAnsEnd();
					cancel();
				}
				
				
			}
		}, delayTime, periodTime);
		
	}

}
