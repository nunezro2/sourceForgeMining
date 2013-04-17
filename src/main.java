import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.ext.VertexNameProvider;
import java.util.*;
import java.io.*;
import java.lang.Integer;

public class main {
	
	public static UndirectedGraph<vertex, DefaultEdge> graph;
	public static Map<String, vertex> project;
	public static Map<String, vertex> developer;
	public static Map<String, vertex> language;
	public static Map<String, vertex> topic;
	public static Map<String, vertex> license;
	public static Map<String, vertex> audience;
	public static Map<String, vertex> os;
	
	public static Map<String, vertex> author = new TreeMap<String, vertex>();
	public static Map<String, vertex> paper = new TreeMap<String, vertex>();
	public static Map<String, vertex> venue = new TreeMap<String, vertex>();
	
	public static final String A = "author";
	public static final String P = "paper";
	public static final String V = "venue";
	
	
	public static void main(String[] args) throws IOException {
		
		//Utilities
		Scanner stdin = new Scanner(System.in);
		
		// Creating the graph
		graph = new SimpleGraph<vertex, DefaultEdge>(DefaultEdge.class);
		
		// Creating the files handlers
		Scanner verticesFile = null;
		Scanner edgesFile = null;
		try {
		System.out.println(main.class.getClassLoader().getResource("example/vertices_example").getPath());
		verticesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/vertices_example").getPath()));
		edgesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/edges_example").getPath()));
		//PrintWriter outputFile = new PrintWriter(new File("example/NRS_example.txt"));
		} catch (NullPointerException  e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING");
			System.exit(-1);
		}
		//System.out.println("These are the vertices:");
		while (verticesFile.hasNext()) {
			String line = verticesFile.nextLine();
			String tokens[] = line.split("	");

			/*
			for (String x: tokens){
				System.out.print(x + " ");
			}
			System.out.println();
			*/
			
			Entities p = Entities.valueOf(tokens[0].toUpperCase());
			switch(p) {
				case AUTHOR: 
					System.out.println("this is author"); 
					author.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case PAPER: 
					System.out.println("this is paper"); 
					paper.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case VENUE: 
					System.out.println("this is venue"); venue.put(tokens[1], 
					new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				default:
					System.out.println("THERE IS A NOT EXPECTED NODE LABEL: " + tokens[0] +" ... EXITING!");
					System.exit(-1);
					break;
			}
			
			/*
			if (tokens[0].equalsIgnoreCase(Entities.AUTHOR.toString().toLowerCase())) {
				
			} else if (tokens[0].equalsIgnoreCase(Entities.PAPER.toString().toLowerCase())) {
				
			} else if (tokens[0].equalsIgnoreCase(Entities.VENUE.toString().toLowerCase())) {
				
			}
			*/
		}
		
		// TODO this can be done immediately during reading the file
		for (vertex node: author.values()) {
			graph.addVertex(node);
		}
		
		for (vertex node: paper.values()) {
			graph.addVertex(node);
		}
		
		for (vertex node: venue.values()) {
			graph.addVertex(node);
		}
		
		//System.out.println("These are the edges:");
		while (edgesFile.hasNext()) {
			String line = edgesFile.nextLine();
			
			String tokens[] = line.split("	");
			
			/*
			for (String x: tokens){
				System.out.print(x + " ");
			}
			System.out.println();
			*/
			
			if (tokens[0].equalsIgnoreCase("author")) {
				if (tokens[1].equalsIgnoreCase("paper")) {
					graph.addEdge(author.get(tokens[2]), paper.get(tokens[3]));
				} else if(tokens[1].equalsIgnoreCase("venue")) {
					graph.addEdge(author.get(tokens[2]), venue.get(tokens[3]));
				}
			} else if(tokens[0].equalsIgnoreCase("paper")) {
				if (tokens[1].equalsIgnoreCase("author")) {
					graph.addEdge(paper.get(tokens[2]), author.get(tokens[3]));
				} else if(tokens[1].equalsIgnoreCase("venue")) {
					System.out.println(" " + tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
					graph.addEdge(paper.get(tokens[2]), venue.get(tokens[3]));
				}
			} else if(tokens[0].equalsIgnoreCase("venue")) {
				if (tokens[1].equalsIgnoreCase("author")) {
					graph.addEdge(venue.get(tokens[2]), author.get(tokens[3]));
				} else if(tokens[1].equalsIgnoreCase("paper")) {
					graph.addEdge(venue.get(tokens[2]), paper.get(tokens[3]));
				}
			}

		}
		
		System.out.println("printing the graph");
		System.out.println(graph.toString());
		System.out.println("Degree of Jim " + graph.degreeOf(author.get("Jim")));
		System.out.println("Degree of Jim " + graph.degreeOf(author.get("Mike")));
		System.out.println("Degree of KDD " + graph.degreeOf(venue.get("KDD")));
		System.out.println("Degree of SIGMOD " + graph.degreeOf(venue.get("SIGMOD")));
		System.out.println("Degree of SDM " + graph.degreeOf(venue.get("SDM")));
		System.out.println("Degree of ICDM " + graph.degreeOf(venue.get("ICDM")));
		System.out.println("Degree of p1 " + graph.degreeOf(paper.get("p1")));
		System.out.println("Degree of p2 " + graph.degreeOf(paper.get("p2")));
		System.out.println("Degree of p7 " + graph.degreeOf(paper.get("p7")));
		System.out.println("Degree of p10 " + graph.degreeOf(paper.get("p10")));
		System.out.println("Degree of p4 " + graph.degreeOf(paper.get("p4")));
	
		// Creating the adjacency matrices
		int A_P[][] = adjacencyMatrix(author, paper);
		int P_V[][] = adjacencyMatrix(paper, venue);
		// Creating the inverse matrices
		int P_A[][] = invertMatrix(A_P);
		int V_P[][] = invertMatrix(P_V);
		//Printing the matrices
		/*
		System.out.println("Author Paper");
		printMatrix(A_P);
		System.out.println("Paper Author");
		printMatrix(P_A);
		System.out.println("Paper Venue");
		printMatrix(P_V);
		*/
		//Matrix multiplication
		int pc[][] = matmul(A_P, P_V, V_P, P_A);
		printMatrix(pc);
		//Computing NPC
		double npc[] = NPC(pc);
		for (double x: npc) {
			System.out.println(x);
		}
		
		stdin.close();
		verticesFile.close();
		edgesFile.close();
		//outputFile.close();
	}
	
	public static int[][] adjacencyMatrix( Map<String, vertex> a, Map<String, vertex> b ) {
		
		int mat[][] = new int[a.size()][b.size()];
		int r = 0, c = 0;
		for (vertex row: a.values() ) {
			//System.out.print(row.getName() + " ");
			for (vertex column: b.values()) {
				if (graph.containsEdge(row, column))
					mat[r][c] = 1;
				//System.out.print(column.getName() + " ");
				//System.out.print(mat[r][c] + " ");
				c++;	
			}
			r++;
			c = 0;
			//System.out.println();
		}
		return mat; 
		
	}
	
	public static int[][] invertMatrix(int[][] input){
		int mat[][] = new int[input[0].length][input.length];
		
		for (int i = 0; i < input[0].length; i++) {
			for (int j = 0; j < input.length; j++) {
				mat[i][j] = input[j][i];
			}
		}
		
		return mat;
	}
	
	public static void printMatrix(int mat[][]) {
		for (int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static int[][] matmul(Object... args) {
		System.out.println("Multiplying a " + ((int[][])(args[0])).length + "x" + ((int[][])(args[0]))[0].length + " and " + ((int[][])(args[1])).length + "x" + ((int[][])(args[1]))[0].length);
		int result[][] = matmul2((int[][])(args[0]), (int[][])(args[1]));
		int temp[][];
		for (int i = 2; i < args.length; i++ ) {
			System.out.println("Multiplying a " + result.length + "x" + result[0].length + " and " + ((int[][])(args[i])).length + "x" + ((int[][])(args[i]))[0].length);
			temp = matmul2(result, (int[][])(args[i]));
			result = temp;
		}
		
		return result;
	}
	
	public static int[][] matmul2(int[][] a, int[][] b) {
		int aqum = 0;
		int result[][] = new int[a.length][b[0].length];
		for(int i = 0; i < a.length; i++){
			for (int j = 0; j < b[0].length; j++){
				for (int k = 0; k < a[0].length; k++) {
					aqum += a[i][k] * b[k][j];
				}
				result[i][j] = aqum;
				aqum = 0;
			}
		}
		return result;
	}
	
	public static double[] NPC(int[][] A_A){
		double result[] = new double[A_A.length * A_A.length];
		int index = 0;
		for(int i = 0; i < A_A.length; i++) {
			int sumRow = sumRow(A_A, i);
			for (int j = 0; j < A_A.length; j++) {
				result[index++] = ((A_A[i][j] + A_A[j][i])*(1.0)) / (sumRow + sumColumn(A_A, j));
				//System.out.println( (index - 1) + " "+ result[index - 1] + " " + A_A[i][j] + " " + A_A[j][i] + " "+ sumRow + " " + sumColumn(A_A, j));
			}
		}
		return result;
	}
	
	public static int sumRow(int[][] mat, int r) {
		int aqum = 0;
		for (int i = 0; i < mat[0].length; i ++) {
			aqum += mat[r][i];
		}
		return aqum;
	}
	
	public static int sumColumn(int[][] mat, int c) {
		int aqum = 0;
		for (int i = 0; i < mat.length; i++ ) {
			aqum += mat[i][c];
		}
		return aqum;
	}
}