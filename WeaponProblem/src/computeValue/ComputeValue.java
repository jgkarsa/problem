package computeValue;

public class ComputeValue {	
	
	private int arrX ;
	private int arrY ;
	private double [][] comAtkPro;
	private int [] comTarVal;
	
	public ComputeValue(double [][] atkPro, int[] tarVal) {
		// TODO Auto-generated constructor stub
		arrX = atkPro.length;
		arrY = tarVal.length;
		comAtkPro = atkPro;
		comTarVal = tarVal;
	}
	
	public double computeV(int[][] ans) {
		
		// caculate total
		double sum = 0;
		double oneTarSum = 1;
		for (int i = 0; i < arrY; i++) {
			for (int j = 0; j < arrX; j++) {
				//calculate each target survival rate
				if (comAtkPro[j][i] != 0 ) {					
					if (ans[j][i] == 1){
						oneTarSum = oneTarSum * (1-comAtkPro[j][i]);
					}
				}
			}			
			oneTarSum = oneTarSum * comTarVal[i];
			sum = sum + oneTarSum;
		//	System.out.println(sum);
			oneTarSum = 1;
		}
		
		return sum;

	}
	//compute value with one dimension
	public double computeVOneD(int []ans){
		
		int [][]ansTwoD = new int[arrX][arrY];
		for(int i=0;i<arrX;i++){
			ansTwoD[i][ans[i]] = 1;		
		}
		return computeV(ansTwoD);
		
	}
	//compute one dimension and double of ans
	public double computeVOneDD(double[]ans){
		int [][]ansTwoD = new int[arrX][arrY];
		
		for(int i =0;i<arrX;i++){
			
			ansTwoD[i][(int) (Math.round(ans[i]))] =1;
			//ansTwoD[i][(int)(ans[i])] =1;
		}
		
		return computeV(ansTwoD);
		
	}


}
