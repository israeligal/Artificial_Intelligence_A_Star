import java.util.Iterator;

public class Real_Time_A_Star_Agent extends Smart_Agent{
	int number_Of_Expansions;
	Real_Time_A_Star_Agent(int starting_Node, int goal_Node,int horizon) {
		super(starting_Node, goal_Node,horizon);
		number_Of_Expansions=3;
	}

	@Override
	void move(SingeltonSimulator simulator) { 						//*************************************************************************************************
			int next_Step=0;
			next_Step=smart_Agent_Search(number_Of_Expansions);
			if(next_Step<0){
				try_To_Move(-1, Weight_Matrix, Nodes);
			}
			else{
				try_To_Move(next_Step, Weight_Matrix, Nodes);		
			}
	}

	@Override
	Fringe_Node get_Fringe_Min() {
		
		Fringe_Node  temp_Cur_Path = null;
		Fringe_Node  next_Graph_Path = null;
		Iterator<Fringe_Node> it_Fringe = fringe.iterator();
		int min_Value = 10000;
		
		while (it_Fringe.hasNext()) {
			temp_Cur_Path = it_Fringe.next();
			if (min_Value > (temp_Cur_Path.getLast_Heuristic_Value()+temp_Cur_Path.getTotal_Weight())) {
				next_Graph_Path = temp_Cur_Path;
				min_Value = (temp_Cur_Path.getLast_Heuristic_Value()+temp_Cur_Path.getTotal_Weight());
			}
		}
		return next_Graph_Path;
	}

}
