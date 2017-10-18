import java.util.LinkedList;
import java.util.Scanner;

public class SingeltonSimulator {
			private int num_Of_Nodes;
			private Graph_Node[] Nodes;
			private int[][] Weight_Matrix;
			private LinkedList<Agent> agents;
			private Scanner reader;
			private int game_Type;
			private int cutOff;
			private static SingeltonSimulator instance = null;
			private int[] agents_Score;
			
		   protected SingeltonSimulator( ) {
			   			setReader(new Scanner(System.in));
		   }
		   public static SingeltonSimulator getInstance() {
		      if(instance == null) {
		         instance = new SingeltonSimulator();
		      }
		      return instance;
		   }
			public int getNum_Of_Nodes() {
				return num_Of_Nodes;
			}
			public void setNum_Of_Nodes(int num_Of_Nodes) {
				this.num_Of_Nodes = num_Of_Nodes;
			}
			public Graph_Node[] getNodes() {
				return Nodes;
			}
			public void setNodes(Graph_Node[] nodes) {
				Nodes = nodes;
			}
			public int[][] getWeight_Matrix() {
				return Weight_Matrix;
			}
			public void setWeight_Matrix(int[][] weight_Matrix) {
				Weight_Matrix = weight_Matrix;
			}
			public LinkedList<Agent> getAgents() {
				return agents;
			}
			public void setAgents(LinkedList<Agent> agents) {
				this.agents = agents;
			}
			public Scanner getReader() {
				return reader;
			}
			public void setReader(Scanner reader) {
				this.reader = reader;
			}
			public int getGame_Type() {
				return game_Type;
			}
			public void setGame_Type(int game_Type) {
				this.game_Type = game_Type;
			}
			public int getCutOff() {
				return cutOff;
			}
			public void setCutOff(int cutOff) {
				this.cutOff = cutOff;
			}
			public int[] getAgents_Score() {
				return agents_Score;
			}
			public void setAgents_Score(int[] agents_Score) {
				this.agents_Score = agents_Score;
			}
			public void setAgentScoreArray() {
				agents_Score = new int[(agents.size()+1)];	
			}
			public void print_Agent_Score(){
				System.out.println("*****************Agent Scores************");
				for (int i = 1; i < agents_Score.length; i++) {
					System.out.print("Agent " + i + " Score is " + agents_Score[i]);
					
				}
				System.out.println("*****************************************");
				
			}
			
}
