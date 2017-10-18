import java.util.Iterator;

public class Adversarial_Agent extends Agent implements Type_Of_Game_Score {
	
	int horizon;
	
	Adversarial_Agent(int starting_Node, int goal_Node,int horizon) {
		super(starting_Node, goal_Node,horizon);
		this.horizon=horizon;
	}

	@Override
	void move(SingeltonSimulator simulator) {
		if(getHorizon()==0){
			System.out.println("Agent "+ agentId + " Horizon has ended, he is been removed from the loop");
			System.out.println("Agent " + agentId + " score: ");
			printAgent();
			simulator.getAgents().remove(this);// have to come before print score
			update_Score();	
		}
		
		else{
			Graph_Node[] nodes = simulator.getNodes();
			Agent enemy_Agent = null;
			Iterator<Agent> ItAgent = simulator.getAgents().iterator();
			while(ItAgent.hasNext()){
				enemy_Agent = ItAgent.next();
				if(enemy_Agent.agentId!= this.agentId ){
					break;
				}
			}
			//Creating first state node 

			State_Node first_state = new State_Node(current_Node, enemy_Agent.current_Node, agent_Keys, enemy_Agent.agent_Keys, goal_Node, enemy_Agent.goal_Node, nodes);
			int next_Step = Mini_Max.alpha_Beta_Decision(first_state, this.goal_Node, simulator.getCutOff());
			System.out.println("ADAGENT __>> NEXT STEP = " + next_Step);
			try_To_Move(next_Step, simulator.getWeight_Matrix(),simulator.getNodes());
		
		}
	}

	@Override
	public void update_Score() {
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		int game_Type = simulator.getGame_Type();
		switch (game_Type){
		
		case 1://Zero-Sum game
				if(!simulator.getAgents().isEmpty()){
					if(current_Node==goal_Node){
						simulator.getAgents_Score()[agentId] =1;
					}//else remains 0
			}
			else{
				boolean enemy_Finished_Before = false;
				int[] agent_Score_Array = simulator.getAgents_Score();
				for (int i = 1; i < agent_Score_Array.length; i++) {
					if (agent_Score_Array[i] == 1 && i != agentId) {
						enemy_Finished_Before = true;
					}
				}
				if (enemy_Finished_Before) {
					simulator.getAgents_Score()[agentId] = -1;
				} else {
					if (current_Node == goal_Node) {
						simulator.getAgents_Score()[agentId] = 1;
						for (int i = 1; i < agent_Score_Array.length; i++) {
							if (agent_Score_Array[i] != agentId) {
								simulator.getAgents_Score()[i] = -1;
							}
						}
					} else {
						simulator.getAgents_Score()[agentId] = 0;
					}
				}
			}
			
			break;
		case 2://Semi-Co game
				if(current_Node == goal_Node){
					simulator.getAgents_Score()[agentId] = -num_Of_Actions;				
				}
				else{
					simulator.getAgents_Score()[agentId]= -10000;
				}
				
			break;
		case 3://Full-co game
				if(current_Node == goal_Node){
					simulator.getAgents_Score()[agentId] = -num_Of_Actions;				
				}
				else{
					simulator.getAgents_Score()[agentId]= -10000;
				}
		break;

	}
		

	}


}


