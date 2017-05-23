package storage;

import java.util.ArrayList;

import other.Instance;
import readFile.Attribute;

public class Storage {
	
	private Solution solution;
	private AgentState agentState;
	private ComposePos composePos;
	private Attribute AT;
	
	public Storage(Attribute AT) {
		
		this.AT = AT;
		solution = new Solution();
		agentState = new AgentState();	
		composePos = new ComposePos(AT);
		
	}
	
	public void setLocalNum(int localNum){
		
		solution.setLocalNum(localNum);
	} 
	
	public void setLocalBestAns(Instance localBestAns){
		
		solution.setLocalBestAns(localBestAns);
		
	}
	
	public void addOtherBestAns(Instance instance){
		
		solution.addOtherBestAns(instance);
		
	}
	
	public boolean checkOtherBestAns(){
		
		return solution.checkOtherBestAns();
		                          
	}
	
	public int getLocalNum(){
		
		return solution.getLocalNum();
		
	}
	
	public Instance getLocalBestAns(){
		
		return solution.getLocalBestAns();
		
	}
	public ArrayList<Instance> getOtherBestAns(){
		
		return solution.getOtherBestAns();
	}
	
	public void clearOtherBestAns(){
		
		solution.clearOtherBestAns();
	}
	public void setAgentState(String[] line) {
		
		agentState.setAgentState(line);
	}
	public void setAgentRunState(String[] line) {

		agentState.setAgentRunState(line);
	}
	public void showAgentState() {
		agentState.showAgentState();
		
	}
	public void showAgentRunState() {
		
		agentState.showAgentRunState();
	}
	public void clearAgentState(){
		
		agentState.clearAgentState();
	}
	public void clearAgentRunState(){
		agentState.clearAgentRunState();
	}
	public void composeAns(String weaponNum,String solution){
		
		composePos.composeAns(weaponNum, solution);
	}
	public void printComposAns(){
		composePos.printComposAns();
	}
	public void printAnsEnd(){
		composePos.printEnd();
	}
	public void initialComposValue(){
		composePos.initialCompos();
	}
	
	
	

}
