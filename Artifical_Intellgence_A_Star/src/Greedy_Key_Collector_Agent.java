import java.util.Iterator;
import java.util.LinkedList;

public class Greedy_Key_Collector_Agent extends Agent{
	
	private int next_key_node;
	private LinkedList<Graph_Node> updated_nodes;
	Greedy_Key_Collector_Agent(int current_Node, int goal_Node,int horizon) {
		super(current_Node, goal_Node,horizon);
		next_key_node=goal_Node;
		updated_nodes=null;
	}

	@Override
	void move(SingeltonSimulator simulator) {
		//temp matrix state for agent
		int[][] greedy_matrix =create_Agent_Matrix();
		Graph_Node[] nodes = simulator.getNodes();
		System.out.println("Done updating temp matrix, now searching for path---> Dijkstra running ");
		Dijkstra run_search_algorithm=new Dijkstra(greedy_matrix,current_Node,nodes,goal_Node);
		System.out.println("This is the temp matrix---> running Dijkstra  ");
		run_search_algorithm.print_matrix(greedy_matrix);
		
		
		updated_nodes = run_search_algorithm.run_Dijkstra(false);		 								//false says this is not a smart agent dijkstra will seach with edges as one
		find_Nearest_Key();//updates the field next_key_node
		run_search_algorithm.setGoal_Node(next_key_node); //after we found the nearest key we upadte dijkstra to find it
		int next_Step = run_search_algorithm.next_Step_Dijkstra();//returns the first step in path
		try_To_Move(next_Step, simulator.getWeight_Matrix(),simulator.getNodes());
		
		
	}

	
	
	
	private void find_Nearest_Key() {
				Iterator<Graph_Node> it_Updated_Nodes = updated_nodes.iterator(); 
				Graph_Node min_key_Node = null;
				Graph_Node cur_Node = null;
				int cur_Node_Distance;
				int cur_Node_Id;
				
				while(it_Updated_Nodes.hasNext()){
					cur_Node =it_Updated_Nodes.next();
					if(cur_Node.getNodeKeys().isEmpty()){
						continue;
					}
					cur_Node_Distance = cur_Node.getDistance();
					cur_Node_Id = cur_Node.getNode_Id();
					//check if the node has a key
					if(min_key_Node==null){
						min_key_Node=cur_Node;
					}
					else{ 		//if the cur node distance is smaller than the current minimum, or distance are equals 
								//and if the id of the current node is smaller, update the minimum node
						
						if(cur_Node_Distance<min_key_Node.getDistance()){
							min_key_Node =cur_Node;
						}
						else if(cur_Node_Distance == min_key_Node.getDistance() &&cur_Node_Id< min_key_Node.getNode_Id() ){
							min_key_Node =cur_Node;
						}
					
					
					}
				}
				if(min_key_Node==null){ 			//get the next node in path id 
					next_key_node = goal_Node;
				}
				else{
					next_key_node=min_key_Node.getNode_Id();
				}	
		
	}
	
	
	

}
