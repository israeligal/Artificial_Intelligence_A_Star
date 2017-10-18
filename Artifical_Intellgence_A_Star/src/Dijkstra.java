import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra {

	private int[][] matrix_edges;
	private int current;
	private PriorityQueue<Graph_Node> priority_queue;
	private LinkedList<Graph_Node> S_group;
	private int goal_Node_int;

	Dijkstra(int[][] matrix, int start_node, Graph_Node[] Node_queue,int goal_Node_int) {
		matrix_edges = matrix;
		current = start_node;
		priority_queue= new PriorityQueue<Graph_Node>();
		initialize_nodes(Node_queue, current);
		S_group = new LinkedList<Graph_Node>();
		this.setGoal_Node(goal_Node_int);
	}
	
	
	//*******************************Run Dijkstra  function ->the main function here*************************************
	// Loop runs over all the nodes and updating the distance and pai and 
	//return updated nodes group==S_group--> after updated pai and distance 
	public LinkedList<Graph_Node> run_Dijkstra(boolean smart_Agent) {
		
		// Loop runs over all the nodes and updating the distance and pai 
		while (!priority_queue.isEmpty()) {
			Graph_Node min_Node = priority_queue.remove();
			S_group.add(min_Node);
			LinkedList<Graph_Node> removedNodes = new LinkedList<>();// temp nodes list for taking out nodes and restore them
			//Search for neighbors and update them
			while (!priority_queue.isEmpty()) {
				Graph_Node curNode = priority_queue.remove();
				if (matrix_edges[min_Node.getNode_Id()][curNode.getNode_Id()] > 0
						&& curNode.getNode_Id() != min_Node.getNode_Id()) {
					if(smart_Agent){
						relax(min_Node, curNode, matrix_edges[min_Node.getNode_Id()][curNode.getNode_Id()] );						
					}
					else{
						relax(min_Node, curNode, 1);						
					}
				}
				removedNodes.add(curNode);
			}
			priority_queue.addAll(removedNodes);
		}
		return S_group;
	}
//******************************After creating distance and pai --> now we will find the next step************************************
	public int next_Step_Dijkstra() {  //returns the next node in path id
		
		int next_Step = get_Next_step(); 					
		// iteration on nodes for printing data
			Iterator <Graph_Node> it_for_print_nodes = S_group.iterator();
				while (it_for_print_nodes.hasNext()){
					it_for_print_nodes.next().print_Graph_Node();
				}
				
		System.out.println("Next step is founded ---> moving towards node  "+ next_Step);
		return next_Step;
	
	}	
	
	//*******************************Initialize Nodes function*************************************

	private void initialize_nodes(Graph_Node[] Node_queue, int start_node) {
		Graph_Node temp_node=null;
		for (int i = 1; i < Node_queue.length; i++) {
			temp_node = new Graph_Node(Node_queue[i]);
			if (i == start_node)
				temp_node.setDistance(0);
			else
				temp_node.setDistance(10000);
			
			temp_node.setPai(null); // -1==NUll
			priority_queue.add(temp_node);
			
		}
	}
	
	
	//*******************************Find next step on board function*************************************
	
	private int  get_Next_step() {
		Iterator<Graph_Node> it_S_group = S_group.iterator(); //S_group= the updated nodes group
		Graph_Node goal_Node =null;
		Graph_Node cur_Node=null;

		while(it_S_group.hasNext()){	
			cur_Node =it_S_group.next();
			if(cur_Node.getNode_Id() == goal_Node_int){
				goal_Node= cur_Node;
				System.out.println("Found goal node "+goal_Node.getNode_Id()+"  computing the path");
				break;
			}
		}
		if(goal_Node != null){ // reconstruct the path and return the first step 
			while(goal_Node.getPai()!=null && current != goal_Node.getPai().getNode_Id()){
				goal_Node = goal_Node.getPai();
			}
			return goal_Node.getNode_Id();
		}
		else{
			return -1;
		}
		
		
	}

	//*******************************Relax function*************************************
	
	private void relax(Graph_Node u, Graph_Node v, int w) {
		if (v.getDistance() > (u.getDistance() + w)) {
			v.setDistance((u.getDistance() + w));
			v.setPai(u);
		}
	}
	
//*******************************print matrix function*************************************
	
	public void print_matrix(int[][] matrix_to_print){
		System.out.print("     ");
		for (int i = 1; i < matrix_to_print.length; i++) {
			System.out.print(i + " ");
		}
		System.out.println("\n     --------");
		for (int i = 1; i < matrix_to_print.length; i++) {
			System.out.print(i+ " |   ");
			for (int j = 1; j < matrix_to_print.length; j++) {
				System.out.print(matrix_to_print[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		
	}
	//*******************************Getters*************************************
		public int getGoal_Node() {
			return goal_Node_int;
		}

		public void setGoal_Node(int goal_Node) {
			this.goal_Node_int = goal_Node;
		}
	

}
