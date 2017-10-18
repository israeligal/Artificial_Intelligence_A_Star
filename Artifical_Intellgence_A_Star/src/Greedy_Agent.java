public class Greedy_Agent extends Agent {
	
	Greedy_Agent(int current_Node, int goal_Node,int horizon) {
		super(current_Node, goal_Node, horizon);
	}

	@Override
	void move(SingeltonSimulator simulator) {
		
			//temp matrix state for agent
			int[][] greedy_matrix =create_Agent_Matrix();
			System.out.println("Searching for unavilable locks");
			
			Graph_Node[] nodes = simulator.getNodes();
			int next_node_move=current_Node;
			System.out.println("Done updating temp matrix, now searching for path---> Dijkstra running ");
			Dijkstra run_search_algorithm=new Dijkstra(greedy_matrix,current_Node,nodes,goal_Node);
			System.out.println("This is the temp matrix---> running Dijkstra  ");
			run_search_algorithm.print_matrix(greedy_matrix);
			run_search_algorithm.run_Dijkstra(false);												//false says this is not a smart agent dijkstra will seach with edges as one
			next_node_move=run_search_algorithm.next_Step_Dijkstra();
			try_To_Move(next_node_move, simulator.getWeight_Matrix(),simulator.getNodes());
		
	
	}
	
	
	
	
	
	
}
