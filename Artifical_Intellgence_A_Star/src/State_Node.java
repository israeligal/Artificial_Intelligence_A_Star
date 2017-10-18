import java.util.Iterator;
import java.util.LinkedList;

public class State_Node {
	private int my_Current_Node;
	private int enemy_Current_Node;
	private LinkedList<Graph_Key> my_Graph_Path_Keys;
	private LinkedList<Graph_Key> enemy_Graph_Path_Keys;
	private int coop_Heuristic_Value;
	private int my_Goal_Node;
	private int enemy_Goal_Node;
	//private int enemy_Heuristic_Value;
	private Graph_Node[] nodes;
	
	
	
	
	public State_Node(int my_Current_Node, int enemy_Current_Node, LinkedList<Graph_Key> my_Graph_Path_Keys,
			LinkedList<Graph_Key> enemy_Graph_Path_Keys, int my_Goal_Node, int enemy_Goal_Node,
			Graph_Node[] nodes) {
		this.my_Current_Node = my_Current_Node;
		this.enemy_Current_Node = enemy_Current_Node;
		this.my_Graph_Path_Keys = my_Graph_Path_Keys;
		this.enemy_Graph_Path_Keys = enemy_Graph_Path_Keys;
		this.my_Goal_Node = my_Goal_Node;
		this.enemy_Goal_Node = enemy_Goal_Node;
		nodes = nodes;
		System.out.println("State_Node --> State_Node_constructor---> printing after creating new node");
		print_State_Node();
	}


	
	public State_Node(State_Node other) {
		my_Current_Node=other.my_Current_Node;
		enemy_Current_Node = other.enemy_Current_Node;
		my_Goal_Node = other.my_Goal_Node;
		enemy_Goal_Node = other.enemy_Goal_Node;
		my_Graph_Path_Keys =other.my_Graph_Path_Keys;
		 enemy_Graph_Path_Keys= other.enemy_Graph_Path_Keys;
		// heuristic_Value=other.heuristic_Value;
		 nodes=other.nodes;

	}
	
	public void set_State_Node(int current_Node,LinkedList<Graph_Key> my_Keys_To_Add,boolean me){ //me indicates if its my turn or my enemy
		if(me){
			my_Current_Node = current_Node;
			my_Graph_Path_Keys.addAll(my_Keys_To_Add);
		}
		else{
			enemy_Current_Node = current_Node;
			enemy_Graph_Path_Keys.addAll(my_Keys_To_Add);
		}
		System.out.println("State_Node --> set_State_Node---> printing after set state node");
		print_State_Node();
	}
		
	

	//search if can move to the new node.Removes some locks and keys from graph_Path_Keys anyway
	public boolean search_if_Path_lock(Graph_Node graph_Node,boolean me) { 
		Graph_Lock curLock =null;
		Graph_Key curKey=null;
		LinkedList<Graph_Lock> node_Locks = new LinkedList<Graph_Lock>(graph_Node.getNodeLocks());
		LinkedList<Graph_Key> node_Keys;
		if(me){
			node_Keys = new LinkedList<Graph_Key>(my_Graph_Path_Keys);
		}
		else{
			node_Keys = new LinkedList<Graph_Key>(enemy_Graph_Path_Keys);
		}
		Iterator<Graph_Lock> itLocks = node_Locks.iterator();
		Iterator<Graph_Key>itKeys =node_Keys.iterator(); 
		
		//Loop to find which locks can't unlock by the agent--> we will use that to update the temp matrix
		while(itLocks.hasNext()){	
			itKeys =node_Keys.iterator(); 
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
		if (node_Locks.isEmpty()) { // meaning we can move to this node so no need to delete him from the matrix 
			if (me) {
				my_Graph_Path_Keys = node_Keys;
			} else {
				enemy_Graph_Path_Keys = node_Keys;
			}
			return false;
		} else {
			return true;
		}

	}

	public int getMy_Current_Node() {
		return my_Current_Node;
	}

	public void setMy_Current_Node(int my_Current_Node) {
		this.my_Current_Node = my_Current_Node;
	}

	public int getEnemy_Current_Node() {
		return enemy_Current_Node;
	}

	public void setEnemy_Current_Node(int enemy_Current_Node) {
		this.enemy_Current_Node = enemy_Current_Node;
	}

	public LinkedList<Graph_Key> getMy_Graph_Path_Keys() {
		return my_Graph_Path_Keys;
	}

	public void setMy_Graph_Path_Keys(LinkedList<Graph_Key> my_Graph_Path_Keys) {
		this.my_Graph_Path_Keys = my_Graph_Path_Keys;
	}

	public LinkedList<Graph_Key> getEnemy_Graph_Path_Keys() {
		return enemy_Graph_Path_Keys;
	}

	public void setEnemy_Graph_Path_Keys(LinkedList<Graph_Key> enemy_Graph_Path_Keys) {
		this.enemy_Graph_Path_Keys = enemy_Graph_Path_Keys;
	}

	

	public Graph_Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Graph_Node[] nodes) {
		nodes = nodes;
	}
	
	//@Override
	/*public boolean equals(Object obj) {
		if (obj instanceof State_Node) {
			State_Node new_State_Node = (State_Node) obj;
			if (this.my_Current_Node == new_State_Node.my_Current_Node 
					&& compare_State_Keys(this.getMy_Graph_Path_Keys(), new_State_Node.getMy_Graph_Path_Keys()) && compare_State_Keys(this.getEnemy_Graph_Path_Keys(),new_State_Node.getEnemy_Graph_Path_Keys())) {
					return true;
			}

		}		
			    return false;
		}*/
	public boolean compare_State_Keys(LinkedList<Graph_Key> my_Keys,LinkedList<Graph_Key> his_keys){
		if(my_Keys.containsAll(his_keys) && his_keys.containsAll(my_Keys)){
			return true;
		}
		return false;

		
}

	public int getMy_Goal_Node() {
		return my_Goal_Node;
	}

	public void setMy_Goal_Node(int my_Goal_Node) {
		this.my_Goal_Node = my_Goal_Node;
	}

	public int getEnemy_Goal_Node() {
		return enemy_Goal_Node;
	}

	public void setEnemy_Goal_Node(int enemy_Goal_Node) {
		this.enemy_Goal_Node = enemy_Goal_Node;
	}
	public void print_State_Node(){
		System.out.println("----printing State_Node data------   -->State_Node class ");
		System.out.println("my_Current_Node   -->  "+my_Current_Node );
		System.out.println("enemy_Current_Node   -->  "+enemy_Current_Node );
		//System.out.println("heuristic_Value   -->  "+heuristic_Value );
		System.out.println("enemy_Goal_Node   -->  "+enemy_Goal_Node );
		System.out.println("my_Goal_Node   -->  "+my_Goal_Node );
	}



	public boolean checkEquals(State_Node new_State_Node,boolean me) {
			if(me){
				if (this.my_Current_Node == new_State_Node.my_Current_Node 
						&& compare_State_Keys(this.getMy_Graph_Path_Keys(), new_State_Node.getMy_Graph_Path_Keys()) && compare_State_Keys(this.getEnemy_Graph_Path_Keys(),new_State_Node.getEnemy_Graph_Path_Keys())) {
					return true;
				}
			}
			else{
					if (this.enemy_Current_Node == new_State_Node.enemy_Current_Node 
							&& compare_State_Keys(this.getMy_Graph_Path_Keys(), new_State_Node.getMy_Graph_Path_Keys()) && compare_State_Keys(this.getEnemy_Graph_Path_Keys(),new_State_Node.getEnemy_Graph_Path_Keys())) {
							return true;
					}

				}			
			    return false;

	}



	public int getCoop_Heuristic_Value() {
		return coop_Heuristic_Value;
	}



	public void setCoop_Heuristic_Value(int coop_Heuristic_Value) {
		this.coop_Heuristic_Value = coop_Heuristic_Value;
	}
	public void set_Updated_Nodes(Graph_Node[] update_Nodes){
		for (int i = 1; i < update_Nodes.length; i++) {
			nodes[i]=new Graph_Node(update_Nodes[i]);
		}
	}
}
