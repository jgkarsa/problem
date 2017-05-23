package other;

public class RandCreate {

	int arrX;
	int arrY;
	
	public RandCreate(int arrX,int arrY) {
		// TODO Auto-generated constructor stub
		this.arrX = arrX;
		this.arrY = arrY;
	}
	
	public int [][] randNOverlap(){
		
		int [][] ans = new int [arrX][arrY];
		int [] preventOverlap = new int [arrY];
		int tmpJ = (int)(Math.random()*arrY);
		for(int i =0;i<arrX;i++){
			while(preventOverlap[tmpJ] == 1){
				tmpJ = (int)(Math.random()*arrY);		    
			}
			preventOverlap[tmpJ] = 1;
			ans[i][tmpJ] = 1;
		}
		
		return ans;
	}
	
	public int[][]randOverlap(){
		
		int [][] ans = new int [arrX][arrY];
		for(int i =0;i<arrX;i++){
			ans[i][(int)(Math.random()*arrY)] = 1;
		}
		
		return ans;
		
	}
	
	public int [] randNOverlapOneD(){
		
		int [] ans = new int[arrX];
		int [] preventOverlap = new int [arrY];
		int tmpJ = (int)(Math.random()*arrY);
		for(int i =0;i<arrX;i++){
			while(preventOverlap[tmpJ] == 1){
				tmpJ = (int)(Math.random()*arrY);		    
			}
			preventOverlap[tmpJ] = 1;
			ans[i] = tmpJ;
		}	
		return ans;
	}
	
	public int[] randOverlapOneD(){
		
		int [] ans = new int[arrX];
		
		for(int i =0;i<arrX;i++){
			ans[i] = (int)(Math.random()*arrY);
		}
		return ans;
	}
	
}
