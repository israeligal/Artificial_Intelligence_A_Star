import java.util.HashSet;
import java.util.Iterator;

public class Mini_Max {
	
	static int alpha=-10000;
	static int beta=10000;
	static int goal_Node=-1;
	static HashSet<State_Node> my_Visited_Fringe_Nodes  = new HashSet<State_Node>();	
	static HashSet<State_Node> enemy_Visited_Fringe_Nodes = new HashSet<State_Node>();	
	static int coop_Val=10000;//this var is being used to choose the minimum steps routh in cases of equal heurstics
	
	
	
	static int alpha_Beta_Decision(State_Node state,int goal, int cutOff){	
		cutOff--;
		System.out.println("------------Mini_Max-->alpha_Beta_Decision -->starting search for next step----------------- ");
		my_Visited_Fringe_Nodes.add(state);	///CHECK IF THIS IS GOODD@@@@@@@@@@@@@@@@@@@@@@@
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		goal_Node=goal;
		int[][] Weight_Matrix = simulator.getWeight_Matrix();		//only to check if there is an edge between our node to others
		int edge;
		int next_Step=-1;
		int maxValue=-10000;
		int minCoop=10000;
		int curValue;
		int coopCurValue;
		
		for (int i = 1; i < Weight_Matrix.length; i++) {
			System.out.println("Mini_Max-->alpha_Beta_Decision --> checking i= "+i);
			edge = Weight_Matrix[state.getMy_Current_Node()][i];
			if (edge > 0) { 										//check if smart Agent visited contains the new fringe suppose to use to equals we defined 
				State_Node new_State_Node = new State_Node(state);
				if (!new_State_Node.search_if_Path_lock(state.getNodes()[i], true)) { 					//true mean myself not enemy
					new_State_Node.set_State_Node(i, state.getNodes()[i].getNodeKeys(), true);
					if (!search_if_Visited(new_State_Node, true)) {
						System.out.println("Mini_Max-->alpha_Beta_Decision --> checking i= "+i + " -->sending state to min_Value");
						curValue= min_Value(new_State_Node, alpha, beta, cutOff);
						coopCurValue=new_State_Node.getCoop_Heuristic_Value();
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^^^^^^^^^^ HEURISTIC VALUE OF STEP  " + i+ " : " + maxValue);
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^^^^^^^^^^ HEURISTIC VALUE OF STEP  " + i+ " : " + maxValue);

						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^^^^^^^^^^ HEURISTIC VALUE OF STEP  " + i+ " : " + maxValue);
						
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^^^^^^^^^^ HEURISTIC VALUE OF STEP  " + i+ " : " + maxValue);
						if(curValue>maxValue){
							maxValue=curValue;
							minCoop=coopCurValue;
							
							next_Step = i;
						}
						else if(curValue==maxValue){
							if(minCoop>coopCurValue){
								maxValue=curValue;
								minCoop=coopCurValue;
								next_Step = i;
							}
						}
					}
				}
			}
		}
		clear_Mini_Max();
		return next_Step;
		 			
		
	}
	
	static int max_Value(State_Node state,int alpha,int beta,int cutOff){
		System.out.println(
				"------------------Mini_Max--------->>>>>>>>>>>>>>max_Value  >>>>>>>>>>>>%%%%%%%%%******&&&&&&&&");
		my_Visited_Fringe_Nodes.add(state);
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		int[][] Weight_Matrix = simulator.getWeight_Matrix(); // only to check
																// if there is
																// an edge
																// between our
																// node to
																// others
		int edge;
		if (state.getMy_Current_Node() == state.getMy_Goal_Node() || cutOff == 0) { // NEED
																					// TO
																					// ADD
																					// IF
																					// WE
																					// GOT
																					// STUCK
																					// =========
																					// NO
																					// MORE
																					// SONS
			return state_Heuristic_Value(state);
		}
		int v = -10000;
		cutOff--;
		for (int i = 1; i < Weight_Matrix.length; i++) {
			edge = Weight_Matrix[state.getMy_Current_Node()][i];
			if (edge > 0) { // check if smart Agent visited contains the new
							// fringe suppose to use to equals we defined
				State_Node new_State_Node = new State_Node(state);
				if (!new_State_Node.search_if_Path_lock(state.getNodes()[i], true)) { // true
																						// mean
																						// myself
																						// not
																						// enemy
					new_State_Node.set_State_Node(i, state.getNodes()[i].getNodeKeys(), true);
					if (!search_if_Visited(new_State_Node, true)) {
						v = Math.max(v, min_Value(new_State_Node, alpha, beta, cutOff));
						if (v > beta) {
							return v;
						}
						if (v == beta && coop_Val < new_State_Node.getCoop_Heuristic_Value()) {
							return v; // if the branches are equal we want to
										// take the one with minimum steps
						}
						if (alpha < v) { // if the branches are equal we want to
											// take the one with minimum steps
							coop_Val = new_State_Node.getCoop_Heuristic_Value();
							alpha = v;
							state.setCoop_Heuristic_Value(coop_Val);
						}

						else if (alpha == v) {
							if (coop_Val > new_State_Node.getCoop_Heuristic_Value()) {
								coop_Val = new_State_Node.getCoop_Heuristic_Value();
								alpha = v;
								state.setCoop_Heuristic_Value(coop_Val);
							}
						}
					}

				}

			}
		}
		return v;
	}
		

	
	static int  min_Value(State_Node state,int beta,int alpha,int cutOff){	// we send alpha and put as beta CHANGED BETA WITH ALPHA
		System.out.println("------------------Mini_Max--------->>>>>>>>>>>>>>min_Value  >>>>>>>>>>>>%%%%%%%%%******&&&&&&&&");
		enemy_Visited_Fringe_Nodes.add(state);
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		int[][] Weight_Matrix = simulator.getWeight_Matrix();		//only to check if there is an edge between our node to others
		int edge;
		if(state.getEnemy_Current_Node()==state.getEnemy_Goal_Node() || cutOff==0 ){			//NEED TO ADD IF WE GOT STUCK ========= NO MORE SONS
			return state_Heuristic_Value(state);
		}
		int v = -10000;
		cutOff--;
		for (int i = 1; i < Weight_Matrix.length; i++) {
			edge = Weight_Matrix[state.getEnemy_Current_Node()][i];
			if (edge > 0) { 																			//check if smart Agent visited contains the new fringe suppose to use to equals we defined 
				State_Node new_State_Node = new State_Node(state);
				if (!new_State_Node.search_if_Path_lock(state.getNodes()[i], false)) { 					//true mean myself not enemy
					new_State_Node.set_State_Node(i, state.getNodes()[i].getNodeKeys(), false);
					if (!search_if_Visited(new_State_Node, false)) {
						v = Math.max(v, max_Value(new_State_Node, alpha, beta, cutOff));
						if (v > beta) {
							return v;
						}
						if (v == beta && coop_Val <new_State_Node.getCoop_Heuristic_Value()) {
							return v;	// if the branches are equal we want to take the one with minimum steps
						}
						if (alpha < v) { // if the branches are equal we want to
							// take the one with minimum steps
							coop_Val = new_State_Node.getCoop_Heuristic_Value();
							alpha = v;
							state.setCoop_Heuristic_Value(coop_Val);
						}

						else if (alpha == v) { //if the branches the same v check for the coop heurstic value
							if (coop_Val > new_State_Node.getCoop_Heuristic_Value()) {
								coop_Val = new_State_Node.getCoop_Heuristic_Value();
								alpha = v;
								state.setCoop_Heuristic_Value(coop_Val);
							}
						}
					}

				}

			}
		}
		return v;
	}
	static private boolean search_if_Visited(State_Node new_State_Node,boolean me) {
		Iterator<State_Node> itFringe_Visited;
		if(me){
			itFringe_Visited= my_Visited_Fringe_Nodes.iterator();			
		}
		else{
			itFringe_Visited= enemy_Visited_Fringe_Nodes.iterator();		
		}
		State_Node temp_Node;
		while(itFringe_Visited.hasNext()){
			temp_Node = itFringe_Visited.next();
			if(temp_Node.checkEquals(new_State_Node,me)){
				return true;
			}
		}
		
		return false;
	}
	
	private static int state_Heuristic_Value(State_Node state){
		SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		int game_Type = simulator.getGame_Type();
		
		Dijkstra my_dijkstra = new Dijkstra(simulator.getWeight_Matrix(), state.getMy_Goal_Node(), state.getNodes(), state.getMy_Current_Node());
		my_dijkstra.run_Dijkstra(false);
		int my_Heuristic = state.getNodes()[state.getMy_Current_Node()].getDistance();
		
		Dijkstra enemy_dijkstra = new Dijkstra(simulator.getWeight_Matrix(), state.getMy_Goal_Node(), state.getNodes(), state.getMy_Current_Node());
		enemy_dijkstra.run_Dijkstra(false);
		int enemy_Heuristic = state.getNodes()[state.getEnemy_Current_Node()].getDistance();
		int heuristic=-99999;
		state.setCoop_Heuristic_Value((enemy_Heuristic+my_Heuristic));
		switch (game_Type){
			
			case 1://Zero Sum game
				heuristic= (enemy_Heuristic-my_Heuristic);
				break;
			case 2://Semi-Co game
				heuristic= (enemy_Heuristic-my_Heuristic);
				break;
			case 3://Full co game
				heuristic = (-1)*(enemy_Heuristic+my_Heuristic);
				break;
	
		}
		return heuristic;
	}
	
	private static void clear_Mini_Max(){
		alpha=-10000;
		beta=10000;
		goal_Node=-1;
		my_Visited_Fringe_Nodes  = new HashSet<State_Node>();	
		enemy_Visited_Fringe_Nodes = new HashSet<State_Node>();	
	}
}
