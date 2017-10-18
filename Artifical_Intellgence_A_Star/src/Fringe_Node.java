import java.util.Iterator;
import java.util.LinkedList;

public class Fringe_Node {
		private LinkedList<Graph_Key> graph_Path_Keys;
		private int total_Weight;
		private int last_Heuristic_Value;
		private Fringe_Node parent_Fringe_Node;
		private int Utility;
		
		public LinkedList<Graph_Key> getGraph_Path_Keys() {
			return graph_Path_Keys;
		}

		public void setGraph_Path_Keys(LinkedList<Graph_Key> graph_Path_Keys) {
			this.graph_Path_Keys = graph_Path_Keys;
		}


		public Fringe_Node getParent_Fringe_Node() {
			return parent_Fringe_Node;
		}

		public void setParent_Fringe_Node(Fringe_Node parent_Fringe_Node) {
			this.parent_Fringe_Node = parent_Fringe_Node;
		}

		private int current_Node;
	
		public int getCurrent_Node() {
			return current_Node;
		}

		public void setCurrent_Node(int current_Node) {
			this.current_Node = current_Node;
		}

		Fringe_Node(int num_Of_Nodes){
			this.graph_Path_Keys = new LinkedList<Graph_Key>();
			parent_Fringe_Node=null;
			total_Weight = 0;
			last_Heuristic_Value=0;
		}
		
		public Fringe_Node(Fringe_Node other) {

			this.graph_Path_Keys = new LinkedList<Graph_Key>(other.graph_Path_Keys);
			total_Weight = other.total_Weight;
			last_Heuristic_Value = other.last_Heuristic_Value;
			this.parent_Fringe_Node = other.parent_Fringe_Node;
			this.current_Node=other.current_Node;
			this.Utility = other.Utility;
		}
		
		public void set_Fringe_Node(int heuristic_Value,int next_Node, int edge_Weight, LinkedList <Graph_Key> keys_to_add,Fringe_Node parent_Fringe_Node){
			last_Heuristic_Value =heuristic_Value; //call Dijkstra with next_node id
			this.parent_Fringe_Node =parent_Fringe_Node;
			this.current_Node=next_Node;
			total_Weight = total_Weight +edge_Weight;
			graph_Path_Keys.addAll(keys_to_add);
			
		}

		//search if can move to the new node and remove some locks and keys from graph_Path_Keys anyway
		public boolean search_if_Path_lock(Graph_Node graph_Node) { 
			Graph_Lock curLock =null;
			Graph_Key curKey=null;
			LinkedList<Graph_Lock> node_Locks = new LinkedList<Graph_Lock>(graph_Node.getNodeLocks());
			LinkedList<Graph_Key> node_Key = new LinkedList<Graph_Key>(graph_Path_Keys);
			Iterator<Graph_Lock> itLocks = node_Locks.iterator();
			Iterator<Graph_Key>itKeys =node_Key.iterator(); 
			
			//Loop to find which locks can't unlock by the agent--> we will use that to update the temp matrix
			while(itLocks.hasNext()){	
				itKeys =node_Key.iterator(); 
				curLock = itLocks.next();
					while(itKeys.hasNext()){
							curKey=itKeys.next();
							//System.out.println("check  if key "+curKey.get_Id() + "  can  unlock lock number  " +curLock.get_Id() ); &***************************************************************
							if(curKey.get_Id()==curLock.get_Id()){
								itLocks.remove();
								itKeys.remove();
								//System.out.println("found key "+curKey.get_Id() + "  for lock " +curLock.get_Id() );	         &***************************************************************
								break;
							}		
					}
				
			}
			if (node_Locks.isEmpty()){ 				// meaning we can move to this node so no need to delete him from the matrix
				graph_Path_Keys=node_Key;
				return false; 
			}
			else {
				return true;
			}
				
		}
			
			
		

		public LinkedList<Graph_Key> get_Fringe_Node_Keys() {
			return graph_Path_Keys;
		}

		public void set_Fringe_Node_Keys(LinkedList<Graph_Key> graph_Path_Keys) {
			this.graph_Path_Keys = graph_Path_Keys;
		}

		public int getTotal_Weight() {
			return total_Weight;
		}

		public void setTotal_Weight(int total_Weight) {
			this.total_Weight = total_Weight;
		}

		public int getLast_Heuristic_Value() {
			return last_Heuristic_Value;
		}

		public void setLast_Heuristic_Value(int last_Heuristic_Value) {
			this.last_Heuristic_Value = last_Heuristic_Value;
		}	
		
		@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fringe_Node) {
			Fringe_Node new_Fringe_Node = (Fringe_Node) obj;
			if (this.current_Node == new_Fringe_Node.current_Node
					&& compare_Fringe_Keys(graph_Path_Keys, new_Fringe_Node.graph_Path_Keys)) {
				if (total_Weight <= new_Fringe_Node.total_Weight) {

					return true;
				}

			}

		}		
			    return false;
		}
		
		public boolean compare_Fringe_Keys(LinkedList<Graph_Key> my_Keys,LinkedList<Graph_Key> his_keys){
				if(my_Keys.containsAll(his_keys) && his_keys.containsAll(my_Keys)){
					return true;
				}
				return false;
		
				
		}

		public int getUtility() {
			return Utility;
		}

		public void setUtility(int utility) {
			Utility = utility;
		}
}
/*System.out.println("this.current_Node" + this.current_Node + " total_Weight " +  total_Weight + " last_Heuristic_Value " +  last_Heuristic_Value);
						System.out.println("parent_Fringe_Node" + parent_Fringe_Node.getCurrent_Node() + "graph_Path_Keys" + graph_Path_Keys.size());
						System.out.println("temp.current_Node" + new_Fringe_Node.current_Node + " temp.total_Weight " +  new_Fringe_Node.total_Weight + " temp.last_Heuristic_Value " +  new_Fringe_Node.last_Heuristic_Value);
						System.out.println("temp.parent_Fringe_Node" + new_Fringe_Node.parent_Fringe_Node.getCurrent_Node() + "temp.graph_Path_Keys"+ new_Fringe_Node.graph_Path_Keys.size() );
						;*/