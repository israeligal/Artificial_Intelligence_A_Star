import java.util.Iterator;
import java.util.LinkedList;

public abstract class Agent {
		protected int current_Node;
		protected int goal_Node;
		protected int total_Weight;
		protected int num_Of_Actions;
		protected LinkedList<Graph_Key> agent_Keys;
		protected int agentId;
		protected int horizon;
		public static int id=1;
		
		Agent(int starting_Node ,int goal_Node,int horizon){
			setAgent_Keys(new LinkedList<Graph_Key>());
			setCurrent_Node(starting_Node);
			setGoal_Node(goal_Node);
			total_Weight = 0;
			num_Of_Actions= 0;
			agentId=id;
			id++;
			setHorizon(horizon);
		}
		
		abstract void move(SingeltonSimulator simulator );
		
		
		//--------------------Check if possible for agent to move-----------------------
		protected boolean try_To_Move(int nextNode,int[][] Weight_Matrix,Graph_Node[] Nodes){
			SingeltonSimulator simulator = SingeltonSimulator.getInstance();
			boolean canMove = false;
			if(nextNode>0){
					
				
				int edgeWeight = Weight_Matrix[getCurrent_Node()][nextNode]; 								//get edge weight between current agent node and node to advance
					
				if(edgeWeight>0){ 	//for every node check if agent have all keys for the locks	
						// Check if edge exist between current node and the node the user want to advance to
						LinkedList<Graph_Lock> locks = Nodes[nextNode].getNodeLocks();						//get the next node locks
						LinkedList<Graph_Key> agentKeys = getAgent_Keys();
						Iterator<Graph_Lock> itLocks = locks.iterator();
						Iterator<Graph_Key> itAgentKeys = agentKeys.iterator();
						
						while(itLocks.hasNext()){
							int curLock = itLocks.next().get_Id();
									while(itAgentKeys.hasNext()){
												int curKey =  itAgentKeys.next().get_Id();
												if(curLock == curKey){
														itLocks.remove();
														itAgentKeys.remove();
														break;
													
												}
									}
						}
						//Update next node Locks --> also for case that just some locks were opened
						//simulator.getNodes()[nextNode].setNodeLocks(locks);
						
						if(!locks.isEmpty()){
							System.out.println("You dont have all the keys for the locks");
							addWeightAndActions(0);
						}
						else{
							//Update the changes in the Node Lock list and the Agent Keys list
							canMove = true;
							
							 // Update Agent location 
							setCurrent_Node(nextNode);
							
							 // Update Agent Keys 
							setAgent_Keys(agentKeys);	
							this.add_Keys(Nodes[this.current_Node].getNodeKeys());
							Nodes[this.current_Node].getNodeKeys().clear();
							addWeightAndActions(edgeWeight); 
							
							if(nextNode==getGoal_Node()){
								System.out.println("*********************");
								System.out.println("Agent"  + (simulator.getAgents().indexOf(this)+1) +" reached goal node!");
								this.printAgent();
								System.out.println("*********************");
								if(this  instanceof Smart_Agent) {
									Smart_Agent agent_For_Print = (Smart_Agent) this;
									agent_For_Print.print_Preformance();
								}
						
								simulator.getAgents().remove(this);
							}	
							
						}
						printAgent();
		
							
				}
				//in case the agent stay in place --->no op
				else{
					addWeightAndActions(0);
					System.out.println("Stay in the same location--->NO-OP");
				}
			}
			else {
				addWeightAndActions(0);
				System.out.println("***********************************");
				System.out.println("could not find a path---> Agent  -->  try_To_Move");
				System.out.println("***********************************");
				if(this  instanceof Smart_Agent) {
					Smart_Agent agent_For_Print = (Smart_Agent) this;
					agent_For_Print.print_Preformance();
				}
				simulator.getAgents().remove(this);
				
				if(this instanceof Type_Of_Game_Score) {
					Adversarial_Agent agent_For_Print = (Adversarial_Agent) this;
					agent_For_Print.update_Score();
				}
			}
					
			
				return canMove;
		}
		
		
		public void addWeightAndActions(int edgeWeight){
			total_Weight = total_Weight+ edgeWeight;
			num_Of_Actions++;
		}
		
		public int getGoal_Node() {
			return goal_Node;
		}
		
		public void setGoal_Node(int goal_Node) {
			this.goal_Node = goal_Node;
		}
		
		public int getCurrent_Node() {
			return current_Node;
		}
		
		public void setCurrent_Node(int starting_Node) {
			this.current_Node = starting_Node;
		}
		
		public void printAgent(){
			System.out.println("AFTER STEP------>>>>>CurrentNode: "+ current_Node + ",  Goal node: " + goal_Node + ",  Total Weight: " + total_Weight + ",  Number of Actions: "
								+ num_Of_Actions+",  agentId: " + agentId);
			print_Agent_Keys();	
		}
		
		public void add_Keys(LinkedList<Graph_Key >new_Key){
			agent_Keys.addAll(new_Key);
		}
		
		
		public void remove_key(Graph_Key remove_Key){
			agent_Keys.remove(remove_Key);
		}
		
		public LinkedList<Graph_Key> getAgent_Keys() {
			return agent_Keys;
		}

		public void setAgent_Keys(LinkedList<Graph_Key> agent_Keys) {
			this.agent_Keys = agent_Keys;
		}
		
		public void print_Agent_Keys(){
			Iterator<Graph_Key> itAgentKeys = agent_Keys.iterator();
			System.out.print("Agent keys: ");
			while(itAgentKeys.hasNext()){
				Graph_Key temp= itAgentKeys.next();
				System.out.print("Key " +temp.get_Name()+ "  id " + temp.get_Id()+" , " );
			}
			System.out.print("\n");
		}
		protected int[][] create_Agent_Matrix(){
			SingeltonSimulator simulator =SingeltonSimulator.getInstance();
			int[][] greedy_matrix = new int[simulator.getWeight_Matrix().length][simulator.getWeight_Matrix().length];
			
			//copy original matrix
			for (int i = 0; i < greedy_matrix.length; i++) {
				for (int j = 0; j < greedy_matrix.length; j++) {
					greedy_matrix[i][j] = simulator.getWeight_Matrix()[i][j];
				}
			}
			
			Graph_Node[] nodes = simulator.getNodes();
			boolean delete_me;
			
			// Update the temp matrix for the agent
			for (int i = 1; i < nodes.length; i++) {
					delete_me=false;
					System.out.println("Searching for locks at node    "+ i);
					delete_me=search_if_lock(nodes[i]);
					if (delete_me){ // then delete the node from the matrix
						System.out.println("Node "+ i + "  can't be unlock ----> no path to this node!");
						for (int k = 1; k < greedy_matrix.length; k++) {
							greedy_matrix[k][i]=0;
						}	
					}
			}
			return greedy_matrix;
		}
		
		protected boolean search_if_lock(Graph_Node curr_node){
			
			Graph_Lock curLock =null;
			Graph_Key curKey=null;
			LinkedList<Graph_Lock> node_Locks = new LinkedList<Graph_Lock>(curr_node.getNodeLocks());
			Iterator<Graph_Key>itKeys = this.getAgent_Keys().iterator();
			Iterator<Graph_Lock> itLocks = node_Locks.iterator();
			
			//Loop to find which locks can't unlock by the agent--> we will use that to update the temp matrix
			while(itLocks.hasNext()){	
				itKeys = this.getAgent_Keys().iterator();
				curLock = itLocks.next();
					while(itKeys.hasNext()){
							curKey=itKeys.next();
							//System.out.println("check  if key "+curKey.get_Id() + "  can  unlock lock number  " +curLock.get_Id() ); &***************************************************************
							if(curKey.get_Id()==curLock.get_Id()){
								itLocks.remove();
								//System.out.println("found key "+curKey.get_Id() + "  for lock " +curLock.get_Id() );	         &***************************************************************
								break;
							}		
					}
				
			}
			if (node_Locks.isEmpty()){
				return false; // meaning we can move to this node so no need to delete him from the matrix
			}
			else return true;
				
		}
		public int getAgentId() {
			return agentId;
		}

		public void setAgentId(int agentId) {
			this.agentId = agentId;
		}

		public int getHorizon() {
			return horizon;
		}

		public void setHorizon(int horizon) {
			this.horizon = horizon;
		}


}
