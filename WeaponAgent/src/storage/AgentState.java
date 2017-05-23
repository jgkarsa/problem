package storage;

public class AgentState {

	private String[] agentConnectionState;
	private String[] agentRunState;
	private int agentSize = 14;

	public AgentState() {

		agentConnectionState = new String[agentSize];
		agentRunState = new String[agentSize];
	}

	public void setAgentState(String[] line) {

		agentConnectionState[Integer.parseInt(line[2])] = "1";

	}

	public void setAgentRunState(String[] line) {

		agentRunState[Integer.parseInt(line[2])] = "1";
	}

	public void showAgentState() {

		for (int i = 0; i < agentSize; i++) {

			int num = i+1;
			if (agentConnectionState[i] == "1") {
				System.out.println("Agent " + num + " Ping is ok");

			}
			else{
				System.out.println("Agent " + num + " Ping error");
			}

		}
	}

	public void showAgentRunState() {

		for (int i = 0; i < agentSize; i++) {

			int num = i+1;
			if (agentRunState[i] == "1") {
				
				System.out.println("Agent " + num + " Run is ok");

			}
			else{
				System.out.println("Agent " + num + " Run error");
			}

		}
	}
	
	public void clearAgentState(){
		
		for(int i =0;i<agentSize;i++){
			
			agentConnectionState[i] = null;
		}
	}
	
	public void clearAgentRunState(){
		
		for(int i =0;i<agentSize;i++){
			agentRunState[i] = null;
		}
	}

}
