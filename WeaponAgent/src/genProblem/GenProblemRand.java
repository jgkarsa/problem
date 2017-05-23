package genProblem;

public class GenProblemRand implements GenProblemInter{

	@Override
	public void genTarVal(int[] arr) {

		int len = arr.length;	
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 10 + 1);

		}
	}
	@Override
	public void genAtkPro(double[][] arr) {
		double tmp;
		int arrX = arr.length;
		int arrY = arr[0].length;
		for (int i = 0; i < arrX; i++) {
			for (int j = 0; j < arrY; j++) {
				tmp = Math.round(Math.random() * 100);
				tmp = tmp / 100;
				arr[i][j] = tmp;
			}
		}
	}
	
	

}
