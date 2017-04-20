package algorithm;

import java.util.ArrayList;

import computeValue.ComputeValue;

public class Greedy implements WeaponAlgorithm{
	
	private ComputeValue comV;
	private Attribute AT;
	private int  arrX;
	private int  arrY;
	private double[][] atkPro;
	private int [] tarVal;
	
	
	public Greedy(double[][] atkPro,int[] tarVal,Attribute AT) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		this.atkPro = atkPro;
		this.tarVal = tarVal;
		comV = new ComputeValue(atkPro, tarVal);
		this.AT = AT;
	}
	
	
	public int[][] targetNOverlap(){
		
		//calculate greedy value
	    int []arrT = new int [arrY];
	    int [][] ans = new int[arrX][arrY];
	    double max;
	    int tmpJ;
	    for(int i=0;i<arrX;i++){
	    	max = Double.MIN_VALUE;
	    	tmpJ = 0;
	    	for(int j =0;j<arrY;j++){
	    		
	    		if (max < atkPro[i][j] *tarVal[j] && arrT[j] != 1){
	    			
	    			max = atkPro[i][j] *tarVal[j];
	    			tmpJ = j;
	    		}    			    		
	    	}    	
	    	arrT[tmpJ] = 1;
	    	ans[i][tmpJ] = 1;
	    }
	    
	    return ans;
		
	}
	
	public int [][] targetHasOverlap(){
		
		double max;
		int tmpJ;
		int [][] ans= new int[arrX][arrY];
		//calculate greedy value
		for(int i=0;i<arrX;i++){
	    	max = Double.MIN_VALUE;
	    	tmpJ = 0;
	    	for(int j =0;j<arrY;j++){
	    		
	    		if (max < atkPro[i][j] *tarVal[j]){
	    			
	    			max = atkPro[i][j] *tarVal[j];
	    			tmpJ = j;
	    		}    			    		
	    	}    	
	    	ans[i][tmpJ] = 1;
	    }	    
		return ans;
	}


	@Override
	public void findAnswer() {
		// TODO Auto-generated method stub
		
		double max;
		int tmpJ;
		int [][] ans= new int[arrX][arrY];
		//calculate greedy value
		for(int i=0;i<arrX;i++){
	    	max = Double.MIN_VALUE;
	    	tmpJ = 0;
	    	for(int j =0;j<arrY;j++){
	    		
	    		if (max < atkPro[i][j] *tarVal[j]){
	    			
	    			max = atkPro[i][j] *tarVal[j];
	    			tmpJ = j;
	    		}    			    		
	    	}    	
	    	ans[i][tmpJ] = 1;
	    }	    
		System.out.println(comV.computeV(ans));
	}


	@Override
	public void setPopulation(double[][] population) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setStopCondition(int time) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Instance> getPopulation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getIndividualPos() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Instance getBestLocalAnswer() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setGlobalBestParticle(Instance instance) {
		// TODO Auto-generated method stub
		
	}
	
	

}
