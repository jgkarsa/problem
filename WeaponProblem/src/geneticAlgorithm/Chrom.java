package geneticAlgorithm;

import algorithm.Instance;

public class Chrom implements Instance{

	private int [][] ans;
	private double value;

	public void setAns(int [][] ans){
		int arrX = ans.length;
		int arrY = ans[0].length;
		this.ans = new int[arrX][arrY];
		for(int i =0;i<arrX;i++){
			for(int j =0;j<arrY;j++){
				this.ans[i][j] = ans[i][j];
			}
		}
	}
	public void setValue(double value){
		
		this.value = value;
	}

	
	public int[][] getAns(){
		return ans;
		
	}
	public double getValue(){
		
		return value;
	}
	@Override
	public double[] getBestPos() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getBestValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
