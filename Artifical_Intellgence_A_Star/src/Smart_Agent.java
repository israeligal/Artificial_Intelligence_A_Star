import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public abstract class Smart_Agent extends Agent {
	
	protected int num_Of_Nodes;
	protected Graph_Node[] Nodes;
	protected int[][] Weight_Matrix;
	protected HashSet<Fringe_Node> fringe;
	protected HashSet<Fringe_Node> smart_Agent_Visited_Fringe_Nodes;
	protected Stack<Integer> reconstructed_Path;
	protected int num_Of_Expansions_Performed;
	protected int  f;
			
	public Stack<Integer> getReconstructed_Path() {
		return reconstructed_Path;
	}

	public void setReconstructed_Path(Stack<Integer> reconstructed_Path) {
		this.reconstructed_Path = reconstructed_Path;
	}

	Smart_Agent(int starting_Node, int goal_Node,int horizon) {
		super(starting_Node, goal_Node, horizon);
		init_Smart_Agent();
		smart_Agent_Visited_Fringe_Nodes = new HashSet<Fringe_Node>();
		fringe = new HashSet<Fringe_Node>();
		reconstructed_Path=new Stack<Integer>();
		num_Of_Expansions_Performed = 0;
		f=1;
	}
	
	abstract Fringe_Node get_Fringe_Min(); //to change ************
	
	private void init_Smart_Agent() {
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();		
		num_Of_Nodes = simulator.getNum_Of_Nodes();
		
		Weight_Matrix = Simulator.copy_Matrix(simulator.getWeight_Matrix());
		
		Graph_Node[] Simulator_Nodes = simulator.getNodes();
		Nodes = new Graph_Node[num_Of_Nodes+1];
		for (int i = 1; i < Nodes.length; i++) {
			Nodes[i] =  Simulator_Nodes[i];
		}
	}

	protected void clear_States() { 								//clear fringe and Nodes pai no need to reset visited nodes because need to use until we've reached the goal
			fringe.clear();
			smart_Agent_Visited_Fringe_Nodes.clear();
	}


	protected int smart_Agent_Search(int number_Of_Expansions) {	//search for the neighbor with the lowest heuristic value 				//*****************************************************
		clear_States();
		int edge = 0;
		int heuristic_Value=-1;
		Fringe_Node temp_Fringe_Node = new Fringe_Node(num_Of_Nodes);
		heuristic_Value=find_Heuristic_Value(current_Node);
		int temp_Goal_Node = goal_Node;
		temp_Fringe_Node.set_Fringe_Node(heuristic_Value,current_Node, 0,agent_Keys,null);								//needs to reset nodes at end*******************************
		fringe.add(temp_Fringe_Node);
		while(true){			
			number_Of_Expansions--;	
			System.out.println();
			System.out.println();
			num_Of_Expansions_Performed++;
			temp_Fringe_Node = get_Fringe_Min();											//Expansion
			if(temp_Fringe_Node!=null && number_Of_Expansions==0){ 			//This is only for real time agent if num of expansions=0 than take current minimum and go next step
				temp_Goal_Node = temp_Fringe_Node.getCurrent_Node();
			}
			if(temp_Fringe_Node==null){
				System.out.println("Could not find goal node path -------->Smart_Agent-->heurisitic_Search");
				return -1;
			}
			else if(temp_Fringe_Node.getCurrent_Node()==temp_Goal_Node){//to check for real star stop if
					fringe.remove(temp_Fringe_Node);
					smart_Agent_Visited_Fringe_Nodes.add(temp_Fringe_Node);
					int next_Step  = reconstruct_Next_Step(temp_Fringe_Node);
					return next_Step;
			}
			else{
					fringe.remove(temp_Fringe_Node);
					smart_Agent_Visited_Fringe_Nodes.add(temp_Fringe_Node);
					//add valid Fringe_Nodes to the fringe
					int temp_Cur_Node_Id =temp_Fringe_Node.getCurrent_Node(); 					//get the last node in path
					for (int i = 1; i < Weight_Matrix.length; i++) {
							edge= Weight_Matrix[temp_Cur_Node_Id][i];
							if(edge>0){ 																	//check if smart Agent visited contains the new fringe suppose to use to equals we defined 
									Fringe_Node new_Fringe_Node = new Fringe_Node(temp_Fringe_Node);  					//add to every neighbour the temp current node as father
									heuristic_Value=find_Heuristic_Value(i);
									if(!new_Fringe_Node.search_if_Path_lock(Nodes[i])){ 		//search if can move to the new node and remove some locks and keys from graph_Path_Keys anyway
										new_Fringe_Node.set_Fringe_Node(heuristic_Value,i, edge,Nodes[i].getNodeKeys(),temp_Fringe_Node);
										if (!search_if_Visited(new_Fringe_Node)){ 					//check if the temp_Fringe_Node already visited the node
											fringe.add(new_Fringe_Node);						
											}
									}
							}
					}					
			}	
			
		}
																														
	}	
	
	
	private boolean search_if_Visited(Fringe_Node new_Fringe_Node) {
		Iterator<Fringe_Node> itFringe_Visited= smart_Agent_Visited_Fringe_Nodes.iterator();
		Fringe_Node temp_Node;
		while(itFringe_Visited.hasNext()){
			temp_Node = itFringe_Visited.next();
			if(temp_Node.equals(new_Fringe_Node)){
				return true;
			}
		}
		
		return false;
	}

	protected int reconstruct_Next_Step(Fringe_Node reconstruct_My_Path) {
		System.out.println(reconstruct_My_Path);
		int next_Step=-1;
		Fringe_Node current_Fringe_Node=null;
		while(reconstruct_My_Path.getParent_Fringe_Node()!=null){
			reconstructed_Path.push(reconstruct_My_Path.getCurrent_Node());
			current_Fringe_Node=reconstruct_My_Path;
			reconstruct_My_Path=reconstruct_My_Path.getParent_Fringe_Node();
			next_Step=current_Fringe_Node.getCurrent_Node();
		}
		
		System.out.println("Smart Agent>reconstruct_Next_Step     next_Step: " + next_Step);
		return next_Step;
	}
	
	private int find_Heuristic_Value(int current_Node) { 			//BEfore change starting from goal node
		Dijkstra heuristic_Dijkstra=new Dijkstra(Weight_Matrix,goal_Node,Nodes,current_Node);
		LinkedList<Graph_Node> S_group=heuristic_Dijkstra.run_Dijkstra(true);												//true says this is smart agent and will search with edges weight
		Iterator<Graph_Node> find_current_distance= S_group.iterator();
		Graph_Node temp_Node=null;
		int distance=10000;
		while (find_current_distance.hasNext()){
			temp_Node=find_current_distance.next();
			if(temp_Node.getNode_Id()==current_Node){
				distance=temp_Node.getDistance();
				break;
			}
		}
		
		return distance;
	}
	public void print_Preformance (){
		System.out.println("Agent performance is:  "+ " total weight= "+ total_Weight +" number of expansions= "+ num_Of_Expansions_Performed );
		System.out.println("Total preformance:   "+((f*total_Weight)+num_Of_Expansions_Performed));
	}
}
