package main;

import java.io.Console;
import java.util.ArrayList;

import Thread.ThreadControl;
import Thread.WeaponThread;
import algorithm.Attribute;
import computeValue.ComputeValue;
import genProblem.GenProblemFile;

public class WeaponMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// read attribute
		Attribute AT = new Attribute();

		//total experimentTime
		for (int k = 0; k < AT.getExperimentTime(); k++) {
			//reload attribute
			AT.initialAttribute();
			
			int totalThread = AT.getTotalThread();
			// threadControl set threadNum
			ThreadControl TC = new ThreadControl(AT);

			
			for (int j = 0; j < AT.getAllRunTime(); j++) {

				// current time
				// System.out.println(System.currentTimeMillis());
				ArrayList<WeaponThread> arrayThread = new ArrayList<WeaponThread>();

				for (int i = 0; i < totalThread; i++) {
					WeaponThread WT = new WeaponThread(TC, AT, i);
					arrayThread.add(WT);
					WT.start();
					
				}

				for (WeaponThread WT : arrayThread) {
					try {
						// wait all thread complete
						WT.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 // time of complete all thread
				// System.out.println(System.currentTimeMillis());
				 System.out.println("*********************************");
				 TC.initialAttribute();
			}
			
			
			System.out.println("------------------------------");
		}

	
	}
}
