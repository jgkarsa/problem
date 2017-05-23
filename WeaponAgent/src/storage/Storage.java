package storage;

import java.util.ArrayList;

import other.Instance;
import readFile.Attribute;

public class Storage {
	
	private Solution solution;
	private AgentState agentState;
	
	
	public Storage(Attribute AT) {
		
		solution = new Solution(AT);
		agentState = new AgentState();	
		
	}
	
	public void setLocalNum(int localNum){
		
		solution.setLocalNum(localNum);
	} 
	
	public void setLocalBestAns(Instance localBestAns){
		
		solution.setLocalBestAns(localBestAns);
		
	}
	
	public void addOtherBestAns(String ans){
		
		solution.addOtherBestAns(ans);
		
	}
	
	public boolean checkOtherBestAns(){
		
		return solution.checkOtherBestAns();
		                          
	}
	
	public int getLocalNum(){
		
		return solution.getLocalNum();
		
	}
	
	public String getLocalBestAns(){
		
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
	public void initialSolutionAtribute(){
		
	    solution.initialSolution();
	}
	public boolean checkLocalBestAns(){
		return solution.checkLocalBestAns();
	}

}
