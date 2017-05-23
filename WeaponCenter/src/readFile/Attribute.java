package readFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.basic.BasicComboPopup;

public class Attribute {

	private int arrX;
	private int arrY;
	private int population;
	private int totalThread ;
	private int exchangeNum;
	private int totalRun;
	private int experimentTime;
	private int allRunTime;
	private boolean initialSolutionSet;
	private String algorithm;	
	private int lineNum;
	private double errorRate;
	private int weaponNum;
	BufferedReader br;
	private double delayTime;
	private double periodTime;
	private boolean duplicate;
	private double oneSecondGenetic;
	private double oneSecondPSO;
	private double oneSecondABC;
	private double oneSecondRandom;

	public Attribute() {
		// TODO Auto-generated constructor stub
		String line;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/readFile/attribute.txt"));
			line = br.readLine();
			String[] str = line.split(" ");
			experimentTime = Integer.parseInt(str[1]);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initialAttribute() {
		lineNum = 0;
		readFile();

	}

	public void readFile() {
		String line;
		try {
			
			while (lineNum != 18) {
				line = br.readLine();
				lineNum++;
				String[] str = line.split(" ");

				switch (str[0]) {

				case "arrx:":
					arrX = Integer.parseInt(str[1]);
					break;
				case "arry:":
					arrY = Integer.parseInt(str[1]);
					break;
				case "population:":
					population = Integer.parseInt(str[1]);
					break;
				case "totalThread:":
					totalThread = Integer.parseInt(str[1]);
					break;
				case "exchangeNum:":
					exchangeNum = Integer.parseInt(str[1]);
					break;
				case "totalRun:":
					totalRun = Integer.parseInt(str[1]);
					break;
				case "algorithm:":
					algorithm = str[1];
					break;
				case "experimentTime:":
					experimentTime = Integer.parseInt(str[1]);
					break;
				case "allRunTime:":
					allRunTime = Integer.parseInt(str[1]);
					break;
				case "initialSolutionSet:":
					initialSolutionSet = Boolean.parseBoolean(str[1]);
					break;

				case "errorRate:":
					errorRate = Double.parseDouble(str[1]);				
					break;
				case "weaponNum:":
					weaponNum = Integer.parseInt(str[1]);
					break;
					
				case "delayTime:":
					delayTime = Double.parseDouble(str[1]);	
					break;
				case "periodTime:":
					periodTime = Double.parseDouble(str[1]);
					break;
				case "duplicate:":
					duplicate = Boolean.parseBoolean(str[1]);
					break;
				case "oneSecondGenetic:":
					oneSecondGenetic = Double.parseDouble(str[1]);
					break;
				case "oneSecondPSO:":
					oneSecondPSO = Double.parseDouble(str[1]);
					break;
				case "oneSecondABC:":
					oneSecondABC = Double.parseDouble(str[1]);
					break;
				case "oneSecondRandom:":
					oneSecondRandom = Double.parseDouble(str[1]);
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getArrX() {
		return arrX;
	}

	public int getArrY() {
		return arrY;
	}

	public int getPopulation() {
		return population;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public int getTotalRun() {
		return totalRun;
	}

	public int getExperimentTime() {
		return experimentTime;
	}

	public int getAllRunTime() {
		return allRunTime;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public boolean getInitialSolutionSet() {
		return initialSolutionSet;
	}
	public double getErrorRate(){
		return errorRate;
	}
	
	public int getWeaponNum(){
		return weaponNum;
	}
	public double getDelayTime(){
		return delayTime;
	}
	public double getPeriodTime(){
		return periodTime;
	}
	public boolean getDuplicate(){
		return duplicate;
		
	}
	public double getOneSecondGenetic(){
		return oneSecondGenetic;
	}
	public double getOneSecondPSO(){
		return oneSecondPSO;
	}
	public double getOneSecondABC(){
		return oneSecondABC;
	}
	public double getOneSecondRandom(){
		return oneSecondRandom;
	}

}
