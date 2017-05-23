package genProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import readFile.Attribute;


public class GenProblemFile implements GenProblemInter {

	private BufferedReader br;
	private Attribute AT;
	
	public GenProblemFile(Attribute AT) {
		this.AT = AT;		
	}

	@Override
	public void genTarVal(int[] arr) {
		// TODO Auto-generated method stub

		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/readFile/tarVal/tarVal"+AT.getArrX()+"_"+AT.getArrY()+".txt"));
			int i = 0;
			for (String s : br.readLine().split(" ")) {

				arr[i] = Integer.parseInt(s);
				i++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void genAtkPro(double[][] arr) {
		// TODO Auto-generated method stub

		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/readFile/atkPro/atkPro"+AT.getArrX()+"_"+AT.getArrY()+".txt"));
			int i = 0;
			int j = 0;
			String line;
			while((line = br.readLine()) !=null){
				for (String s : line.split(" ")) {

					arr[i][j] = Double.parseDouble(s);
					j++;				
				}	
				j=0;
				i++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
