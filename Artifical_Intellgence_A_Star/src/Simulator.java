import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Simulator {
	
	public static void main(String[] args) throws IOException {
		// ---------------read from file---------------------------------//
		 SingeltonSimulator simulator = SingeltonSimulator.getInstance();
		 simulator.setCutOff(30);
		 readFile(simulator);

		 //--------------get type of game-------------------------------//
		get_game_type(simulator); 
		// --------------Init Agents by user preferences----------------//
		initAgents(simulator);
		Iterator<Agent> itAgents;
		simulator.setAgentScoreArray();
		// -------------------main_loop--------------------------------//
		
		printWorld(simulator);
		while (!simulator.getAgents().isEmpty()) {
			itAgents = simulator.getAgents().iterator();
			while (itAgents.hasNext()){
				Agent s = itAgents.next();
					s.move(simulator);				
			}
			System.out.println("------------------------------------------------------------------------");
			printWorld(simulator);
			System.out.println("------------------------------------------------------------------------");
			
	
		}
		simulator.print_Agent_Score();
		simulator.getReader().close();
	}

//*********************************END OF MAIN**********************************************************//	
	
	
//********************************set game type********************************************************//
	private static void get_game_type(SingeltonSimulator simulator) {
		System.out.println("Please enter game type");
		System.out.println("1 = zero-sum game \n2 = non zero-sum game \n3 = fully cooperative game");
		int game_Type;
		try{
			game_Type = simulator.getReader().nextInt();
		}
		catch(Exception e){
			System.out.println("WRONG INPUT declaring game type as one");
			game_Type=1;
		}
		simulator.setGame_Type(game_Type);
	}
//************************************Print World state  function******************************************//
	
	

	static void printWorld(SingeltonSimulator simulator) {
		printMatrix(simulator);
		printNodes(simulator);
		printAgents(simulator);

	}

//************************************Print Agents state  function******************************************//	
	
	private static void printAgents(SingeltonSimulator simulator) {
		Iterator<Agent> itAgent = simulator.getAgents().iterator();
		int agentNum = 1;
		while (itAgent.hasNext()) {
			System.out.println("Printing agent " + agentNum + " data");
			itAgent.next().printAgent();
			agentNum++;
			System.out.println();
		}

	}

//************************************Print Nodes state  function******************************************//
	
	private static void printNodes(SingeltonSimulator simulator) {
		for (int i = 1; i < simulator.getNodes().length; i++) {
			System.out.println("Node Number " + i + " printing data: ");
			simulator.getNodes()[i].printKeysAndLocks();
			System.out.println();
		}
	}

//************************************Print matrix state  function******************************************//
	
	private static void printMatrix(SingeltonSimulator simulator) {
		System.out.println("Weight Matrix: ");
		System.out.print("     ");
		for (int i = 1; i < simulator.getNodes().length; i++) {
			System.out.print(i + " ");
		}
		System.out.println("\n     --------");
		for (int i = 1; i < simulator.getWeight_Matrix().length; i++) {
			System.out.print(i+ " |   ");
			for (int j = 1; j < simulator.getWeight_Matrix().length; j++) {
				System.out.print(simulator.getWeight_Matrix()[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

//************************************Initialize agents  function******************************************//
	
	private static void initAgents(SingeltonSimulator simulator) {
		int number_Of_Agents;
		System.out.print("Enter number of agents: ");
		try {
			number_Of_Agents = simulator.getReader().nextInt();
		} catch (Exception e) {
			System.out.println("wrong input, 3 agents will be created");
			number_Of_Agents = 3;
		}
		simulator.setAgents(new LinkedList<Agent>());

		int agent_Type, current_Node, goal_Node;

		for (int i = 0; i < number_Of_Agents; i++) {
			// --------------Read User Input for Agents INITALIZE!
			// -------------------------
			System.out.println("\nChoose agent " + (i + 1) + " type: ");
			System.out.println("1. Human Agent \n2. Greedy Agent \n3.Greedy Key Collector Agent "
					+ "\n4.Greedy Search Agent \n5.A star agent \n6.real time A star Agent\n7.Adversarial Agent");
			System.out.print("agent type: ");
			agent_Type = simulator.getReader().nextInt();
			System.out.print("\nEnter starting node: ");			
			current_Node = simulator.getReader().nextInt();
			System.out.print("\nEnter goal node: ");
			goal_Node = simulator.getReader().nextInt();
			Agent new_Agent;
			int horizon=15;
							/*											System.out.println("Please enter horizon for agent");
																		try{
			 ************************									horizon = simulator.getReader().nextInt();
																		}
																		catch(Exception e){
																			System.out.println("WRONG INPUT declaring horizon as 10");
																			horizon=10;
																		}*/
			// -----------Get Agent Type----------------
			switch (agent_Type) {
			case 2: {
				new_Agent = new Greedy_Agent(current_Node, goal_Node, horizon);
				break;
			}
			case 3: {
				new_Agent = new Greedy_Key_Collector_Agent(current_Node, goal_Node, horizon);
				break;
			}
			case 4:{
				new_Agent = new Greedy_Search_Agent(current_Node, goal_Node, horizon);
				break;
			}
			case 5:{
				new_Agent = new A_Star_Agent(current_Node, goal_Node, horizon);
				break;
			}
			case 6:{
				new_Agent = new Real_Time_A_Star_Agent(current_Node, goal_Node, horizon);
				break;
			}
			case 7:{
				new_Agent = new Adversarial_Agent(current_Node, goal_Node, horizon);              
				break;
			}
			
			default: {
				new_Agent = new Human_Agent(current_Node, goal_Node, horizon);
				break;
			}
			}
			new_Agent.add_Keys(simulator.getNodes()[current_Node].getNodeKeys());
			simulator.getNodes()[current_Node].getNodeKeys().clear();
			simulator.getAgents().add(new_Agent);

		}

	}
	public static int[][] copy_Matrix(int[][] Weight_Matrix){  															
		int num_Of_Nodes = Weight_Matrix.length;
		int[][] new_Weight_Matrix = new int[num_Of_Nodes][num_Of_Nodes];
		
		for (int i = 1; i < num_Of_Nodes; i++) {
			for (int j = 1; j < num_Of_Nodes; j++) {
				new_Weight_Matrix[i][j] = Weight_Matrix[i][j];
			}
			
		}
		return new_Weight_Matrix;
	}
	
	
//************************************read from file function******************************************//
	
	private static void readFile(SingeltonSimulator simulator) throws IOException {

		Scanner fileIn = new Scanner(new File("C:\\File.txt"));

		fileIn.next(); // Get number of Nodes
		simulator.setNum_Of_Nodes(fileIn.nextInt());
		simulator.setWeight_Matrix(new int[simulator.getNum_Of_Nodes()+1][simulator.getNum_Of_Nodes()+1]);

		simulator.setNodes(new Graph_Node[simulator.getNum_Of_Nodes()+1]); // init
																			// node
																			// list
		for (int i = 1; i < simulator.getNodes().length; i++) {
			simulator.getNodes()[i] = new Graph_Node(i);
		}
		int currentNode; // the number of node currently working on

		while (fileIn.hasNext()) { // check for next word
			String next_Word = fileIn.next();

			// ------------------VERTEX--------------------------------
			if (next_Word.equals("#V")) {
				currentNode = fileIn.nextInt();
				String type = fileIn.next();
				int object_Id = fileIn.nextInt();
				String object_Name = fileIn.next();
				switch (type) {
				case "K": {
					Graph_Key new_Key = new Graph_Key(object_Id, object_Name);
					simulator.getNodes()[currentNode].getNodeKeys().add(new_Key);
					break;
				}
				case "L": {
					Graph_Lock new_Lock = new Graph_Lock(object_Id, object_Name);
					simulator.getNodes()[currentNode].getNodeLocks().add(new_Lock);
					break;
				}
				}

			}

			// -------------------------EDGE--------------------------------------------
			else if (next_Word.equals("#E")) {
				int first_Node = fileIn.nextInt();
				int second_Node = fileIn.nextInt();
				String get_Weight = fileIn.next();
				int weight = Integer.parseInt(get_Weight.substring(1));
				simulator.getWeight_Matrix()[first_Node][second_Node] = weight;
				simulator.getWeight_Matrix()[second_Node][first_Node] = weight;
			}
		}
		fileIn.close();
	}

}
