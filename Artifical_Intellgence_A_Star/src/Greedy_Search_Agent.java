import java.util.Iterator;

public class Greedy_Search_Agent extends Smart_Agent {
	private boolean init_Stack_Path;
	int next_Step;
	
	Greedy_Search_Agent(int starting_Node, int goal_Node,int horizon) {
		super(starting_Node, goal_Node,horizon);
		init_Stack_Path=false;										//this will be used to do smart agent search only once
		next_Step=0;
	}
	@Override
	void move(SingeltonSimulator simulator) {
		if(!init_Stack_Path){
			next_Step=smart_Agent_Search(-1);		//**********we will call it with -1 so real time can reduce number_Of_Expansions to 0 and stop, and this will never reach 0********************
			init_Stack_Path=true;
		}	
		if(reconstructed_Path.isEmpty()){
			try_To_Move(-1, Weight_Matrix, Nodes);
		}
		else{
			next_Step =reconstructed_Path.pop().intValue();
			try_To_Move(next_Step, Weight_Matrix, Nodes);		
		}
		
	}

	protected Fringe_Node get_Fringe_Min() {
		Fringe_Node  temp_Cur_Path = null;
		Fringe_Node  next_Graph_Path = null;
		Iterator<Fringe_Node> it_Fringe = fringe.iterator();
		int min_Heuristic = 10000;
		int min_weight=10000;
		
		while (it_Fringe.hasNext()) {
			temp_Cur_Path = it_Fringe.next();
			if (min_Heuristic > temp_Cur_Path.getLast_Heuristic_Value()) {
				next_Graph_Path = temp_Cur_Path;
				min_weight=temp_Cur_Path.getTotal_Weight();
				min_Heuristic = temp_Cur_Path.getLast_Heuristic_Value();
			}
			else if(min_Heuristic == temp_Cur_Path.getLast_Heuristic_Value() && min_weight> temp_Cur_Path.getTotal_Weight()) {
					next_Graph_Path = temp_Cur_Path;
					min_weight=temp_Cur_Path.getTotal_Weight();
					min_Heuristic = temp_Cur_Path.getLast_Heuristic_Value();
				
			}
		}
		return next_Graph_Path;
	}
	

}
