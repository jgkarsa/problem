package storage;

import computeValue.ComputeValue;
import genProblem.GenProblemFile;
import readFile.Attribute;
import writeFile.Log;

public class ComposePos {

	private Attribute AT;
	private ComputeValue computeValue;
	private double[] comPos;
	private int[] completeNum;
	private int arrX;
	private int arrY;
	private int[] tarVal;
	private double[][] atkPro;
	private Log log;

	public ComposePos(Attribute AT) {

		this.AT = AT;
		GenProblemFile genProblemFile = new GenProblemFile(AT);

		arrX = AT.getArrX();
		arrY = AT.getArrY();

		// initial input size
		tarVal = new int[arrY];
		atkPro = new double[arrX][arrY];
		genProblemFile.genTarVal(tarVal);
		genProblemFile.genAtkPro(atkPro);

		computeValue = new ComputeValue(atkPro, tarVal);

		comPos = new double[AT.getArrX()];
	
		log = new Log();
		
		initialCompos();

	}
	
	public void initialCompos(){
		
		for(int i =0;i<AT.getArrX();i++){
			comPos[i] = 0;
		}
	}

	public void composeAns(String weaponNum, String solution) {

		String[] solutionArr = solution.split("-");
		comPos[Integer.parseInt(weaponNum) - 1] = Double.parseDouble(solutionArr[Integer.parseInt(weaponNum) - 1]);
		
	}
	
	public void printComposAns(){
	
		System.out.println(computeValue.computeVOneDD(comPos));
		log.writeResult(String.valueOf(computeValue.computeVOneDD(comPos)));
	}
	
	public void printEnd(){
		
		log.writeResultEnd();
		
	}
}
