public class Human_Agent extends Agent implements Type_Of_Game_Score {
	
				Human_Agent(int cur_Node, int goal_Node,int horizon){
					super(cur_Node,goal_Node,horizon);
				}

				@Override
				void move(SingeltonSimulator simulator) {
					if(getHorizon()==0){
						System.out.println("Agent "+ agentId + " Horizon has ended, he is been removed from the loop");
						System.out.println("Agent " + agentId + " score: ");
						printAgent();
						simulator.getAgents().remove(this);
						update_Score();
					}
					
					else{
					System.out.println("Agent " + (simulator.getAgents().indexOf(this)+1) +  "  enter node number to advance: ");
					int nextNode; 
					if(simulator.getReader().hasNext()){
						
							try{
								nextNode = simulator.getReader().nextInt();
							}
							catch(Exception e){
								System.out.println(e);
								System.out.println("wrong input, agents will do no-op");
								nextNode = this.getCurrent_Node();
							}
					}
					
					else{
							System.out.println("no input");
							nextNode = this.getCurrent_Node();
					}
										
					try_To_Move(nextNode,simulator.getWeight_Matrix(),simulator.getNodes());
				
					
					
					
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
							int[] agent_Score_Array =  simulator.getAgents_Score();
							for (int i = 1; i <agent_Score_Array.length; i++) {
								if(agent_Score_Array[i] ==1 && i != agentId){
									enemy_Finished_Before = true;
								}
							}
							if(enemy_Finished_Before){
								simulator.getAgents_Score()[agentId] = -1;
							}
							else{
								if(current_Node == goal_Node){
									simulator.getAgents_Score()[agentId] = 1;
									for (int i = 1; i < agent_Score_Array.length; i++) {
											if(agent_Score_Array[i]!=agentId){
												simulator.getAgents_Score()[i] = -1;
											}
									}
								}
								else{
									simulator.getAgents_Score()[agentId] = 0;
								}
							}
						}
						
						
						
						break;
					case 2://Semi-Co game
						
						break;
					case 3://Full-co game
						
						break;
			
				}
					

				}
	
	
}