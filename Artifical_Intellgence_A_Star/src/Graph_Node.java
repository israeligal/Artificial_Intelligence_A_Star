import java.util.Iterator;
import java.util.LinkedList;



public class Graph_Node implements Comparable<Object> { 
	
	
	private LinkedList<Graph_Key> NodeKeys;
	private LinkedList<Graph_Lock> NodeLocks;
	private int distance;
	private Graph_Node pai;
	private int node_Id;
	private int heuristic_Value;
	


	public Graph_Node(int id) {
		setNodeKeys(new LinkedList<Graph_Key>());
		setNodeLocks(new LinkedList<Graph_Lock>());
		heuristic_Value = 1000; //Will be override in every node anyway
		node_Id=id;
	}

	public Graph_Node(Graph_Node temp_node) {
		this.node_Id=temp_node.node_Id;
		this.distance=temp_node.distance;
		this.pai=temp_node.pai;
		this.NodeKeys=new LinkedList<Graph_Key>(temp_node.NodeKeys) ;
		this.NodeLocks=new LinkedList<Graph_Lock>(temp_node.NodeLocks) ;
		this.heuristic_Value=temp_node.heuristic_Value;
	}

	public LinkedList<Graph_Key> getNodeKeys() {
		return NodeKeys;
	}

	public void setNodeKeys(LinkedList<Graph_Key> nodeKeys) {
		NodeKeys = nodeKeys;
	}

	public LinkedList<Graph_Lock> getNodeLocks() {
		return NodeLocks;
	}

	public void setNodeLocks(LinkedList<Graph_Lock> nodeLocks) {
		NodeLocks = nodeLocks;
	}
	public void printKeysAndLocks(){
			Iterator<Graph_Key>itKeys =  NodeKeys.iterator();
			System.out.print("     Node keys: ");
			while(itKeys.hasNext()){
				System.out.print("  "+ itKeys.next().get_Id()+"");
			}
			if(NodeKeys.isEmpty()){
				System.out.print(" No keys");
			}
			System.out.println();
			Iterator<Graph_Lock>itLocks =  NodeLocks.iterator();
			System.out.print("     Node locks: ");
			while(itLocks.hasNext()){
				System.out.print("  " + itLocks.next().get_Id());
			}
			if(NodeLocks.isEmpty()){
				System.out.print(" No locks");
			}
			System.out.println();
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Graph_Node getPai() {
		return pai;
	}

	public void setPai(Graph_Node u) {
		this.pai = u;
	}

	
	public int compareTo(Object vs) {
			if( vs instanceof Graph_Node){
			if (this.getDistance()>((Graph_Node) vs).getDistance())
				return 1;
			}
		return -1;
	}
	

	 @Override
	  public boolean equals(Object o){
	    if(o instanceof Graph_Node){
	    	Graph_Node c = (Graph_Node)o;
	      return (c.node_Id == this.node_Id);
	    }
	    return false;
	  }
	 
	 

	public int getNode_Id() {
		return node_Id;
	}

	public void setNode_Id(int node_Id) {
		this.node_Id = node_Id;
	}
	public int getHeuristic_Value() {
		return heuristic_Value;
	}

	public void setHeuristic_Value(int heuristic_Value) {
		this.heuristic_Value = heuristic_Value;
	}
	public void print_Graph_Node(){
		if(this.getPai()==null){
			System.out.println("Graph Node id: "+node_Id+ "  pai: "+pai+ "  distance: "+ distance + " Heuristic value: " + heuristic_Value);
		}
		else{
			
			System.out.println("Graph Node id: "+node_Id+ "  pai: "+pai.getNode_Id()+ "  distance: "+ distance + " Heuristic value: " + heuristic_Value);
		}
	}
}
