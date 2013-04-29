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
	public static UndirectedGraph<vertex, DefaultEdge> graph2;
	public static Map<String, vertex> project = new TreeMap<String, vertex>();
	public static Map<String, vertex> developer = new TreeMap<String, vertex>();
	public static Map<String, vertex> language = new TreeMap<String, vertex>();
	public static Map<String, vertex> topic = new TreeMap<String, vertex>();
	public static Map<String, vertex> license = new TreeMap<String, vertex>();
	public static Map<String, vertex> audience = new TreeMap<String, vertex>();
	public static Map<String, vertex> os = new TreeMap<String, vertex>();
	public static Map<String, vertex> status = new TreeMap<String, vertex>();
	
	public static Map<String, vertex> author = new TreeMap<String, vertex>();
	public static Map<String, vertex> paper = new TreeMap<String, vertex>();
	public static Map<String, vertex> venue = new TreeMap<String, vertex>();
	
	public static int D_P_D[][] = null;
	
	/*
	public static final String A = "author";
	public static final String P = "paper";
	public static final String V = "venue";
	*/
	
	public static void main(String[] args) throws IOException {
		
		//Utilities
		Scanner stdin = new Scanner(System.in);
		
		// Creating the graph
		graph = new SimpleGraph<vertex, DefaultEdge>(DefaultEdge.class);
		
		// Creating the files handlers
		Scanner verticesFile = null;
		Scanner edgesFile = null;
		PrintWriter fileOut = null;
		try {
			//System.out.println(main.class.getClassLoader().getResource("example/vertices_example").getPath());
			verticesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/vertices_example").getPath()));
			edgesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/edges_example").getPath()));
			fileOut = new PrintWriter ( new FileWriter ("NRC_output_example.txt"));
		} catch (NullPointerException  e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING");
			System.exit(-1);
		}
			catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING");	
		}
		catch (IOException e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING");	
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
					//System.out.println("this is author"); 
					author.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case PAPER: 
					//System.out.println("this is paper"); 
					paper.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case VENUE: 
					//System.out.println("this is venue"); 
					venue.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
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
					//System.out.println(" " + tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3]);
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
		int A_P[][] = adjacencyMatrix(graph, author, paper);
		int P_V[][] = adjacencyMatrix(graph, paper, venue);
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
		int pc_2[][] = matmul(A_P, P_V, V_P, P_A);
		int pc_3[][] = matmul(P_A, A_P);
		
		//Computing NPC
		//double npc[] = NPC(pc);
		//double npc2[] = NPC(pc_2);
		//double npc3[] = NPC(pc_3);
		
		/*
		for (double x: npc) {
			System.out.println(x);
		}
		*/
		
		// Saving to file
		//printToFile(graph, author, author, fileOut, npc, npc2, npc3);
		
		verticesFile.close();
		edgesFile.close();
		fileOut.close();

		
		// Creating the graph of SourceForge
		graph2 = new SimpleGraph<vertex, DefaultEdge>(DefaultEdge.class);
		
		// Creating the files handlers
		Scanner verticesFile2 = null;
		Scanner edgesFile2 = null;
		PrintWriter fileOut2 = null;
		PrintWriter fileOut3 = null;
		try {
			//System.out.println(main.class.getClassLoader().getResource("example/").getPath());
			verticesFile2 = new Scanner(new File(main.class.getClassLoader().getResource("example/Nodes_50_50.txt").getPath()));
			edgesFile2 = new Scanner(new File(main.class.getClassLoader().getResource("example/Edges_50_50.txt").getPath()));
			fileOut2 = new PrintWriter ( new FileWriter("NRC_output_sourceForge_50_50.txt"));
			fileOut3 = new PrintWriter ( new FileWriter("NRC_output_sourceForge_50_50_SS.txt"));
		} catch (NullPointerException  e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING!");
			System.exit(-1);
		}
		catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND: " + e.getMessage() + "... EXITING");	
			System.exit(-1);
		}
		catch (IOException  e) {
			System.out.println("ERROR: " + e.getMessage() + "... EXITING");	
			System.exit(-1);
		}

		// Loading the nodes to the graph
		while (verticesFile2.hasNext()) {
			String line = verticesFile2.nextLine();
			String tokens[] = line.split("	");

			/*
			for (String x: tokens){
				System.out.print(x + " ");
			}
			System.out.println();
			*/
			
			Entities p = Entities.valueOf(tokens[0].toUpperCase());
			switch(p) {
				case PROJECT: 
					//System.out.println("this is project"); 
					project.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case DEVELOPER: 
					//System.out.println("this is developer"); 
					developer.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case AUDIENCE: 
					//System.out.println("this is audience"); 
					audience.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case LANGUAGE: 
					//System.out.println("this is LANGUAJE"); 
					language.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case LICENSE: 
				//	System.out.println("this is LICENSE"); 
					license.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case SO: 
					//System.out.println("this is SYSTEM"); 
					os.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case TOPIC: 
				//	System.out.println("this is TOPIC"); 
					topic.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				case STATUS: 
				//	System.out.println("this is TOPIC"); 
					status.put(tokens[1], new vertex(tokens[0].toLowerCase(), tokens[1].toLowerCase())); 
					break;
				default:
					System.out.println("THERE IS A NOT UNEXPECTED NODE LABEL: " + tokens[0] + " ... EXITING!");
					System.exit(-1);
					break;
			}
			
		}
		
		// TODO this can be done immediately during reading the file
		for (vertex node: project.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: developer.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: audience.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: language.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: license.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: os.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: topic.values()) {
			graph2.addVertex(node);
		}
		
		for (vertex node: status.values()) {
			graph2.addVertex(node);
		}
		
		//Loading the edges to the graph
			while (edgesFile2.hasNext()) {
				String line = edgesFile2.nextLine();
				
				String tokens[] = line.split("	");
				
				/*
				for (String x: tokens){
					System.out.print(x + " ");
				}
				System.out.println();
				*/

				Entities p = Entities.valueOf(tokens[0].toUpperCase());
				switch(p) {
					case PROJECT: 
						Entities q = Entities.valueOf(tokens[1].toUpperCase());
						switch(q) {
							case DEVELOPER: 
									//System.out.println("link " + " " + tokens[2] + " " + tokens[3]);
									graph2.addEdge(project.get(tokens[2]), developer.get(tokens[3]));
								break;
							case AUDIENCE: 
									System.out.println("link " + " " + tokens[2] + " " + tokens[3]);
									graph2.addEdge(project.get(tokens[2]), audience.get(tokens[3]));
								break;
							case LANGUAGE: 
									graph2.addEdge(project.get(tokens[2]), language.get(tokens[3]));
								break;
							case LICENSE: 
									graph2.addEdge(project.get(tokens[2]), license.get(tokens[3]));
								break;
							case SO: 
									System.out.println(tokens[2] + " " + tokens[3]);
									graph2.addEdge(project.get(tokens[2]), os.get(tokens[3]));
								break;
							case TOPIC: 
									System.out.println(tokens[2] + " " + tokens[3]);
									graph2.addEdge(project.get(tokens[2]), topic.get(tokens[3]));
								break;
							case STATUS: 
								System.out.println(tokens[2] + " " + tokens[3]);
								graph2.addEdge(project.get(tokens[2]), status.get(tokens[3]));
							break;
							default:
								System.out.println("THERE IS A UNEXPECTED NODE LABEL: " + tokens[1] + " ... EXITING!");
								System.exit(-1);
								break;
						}
						break;
					case DEVELOPER: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph2.addEdge(developer.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("DEVELOPER: SOMETHING WRONG HAPPENED " + tokens[0] + " " + tokens[1] + "... exiting");
							System.exit(-1);
						} 
						break;
					case AUDIENCE: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph2.addEdge(audience.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("AUDIENCE: SOMETHING WRONG HAPPENED " + tokens[1] + " ... exiting");
							System.exit(-1);
						} 
						break;
					case LANGUAGE: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph2.addEdge(language.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("LANGUAGE: SOMETHING WRONG HAPPENED... exiting");
							System.exit(-1);
						} 
						break;
					case LICENSE: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph2.addEdge(license.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("LICENSE: SOMETHING WRONG HAPPENED... exiting");
							System.exit(-1);
						} 
						break;
					case SO: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph2.addEdge(os.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("SYSTEM: SOMETHING WRONG HAPPENED... exiting");
							System.exit(-1);
						} 
						break;
					case TOPIC: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph.addEdge(topic.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("TOPIC: SOMETHING WRONG HAPPENED... exiting");
							System.exit(-1);
						} 
						break;
					case STATUS: 
						if (tokens[1].equalsIgnoreCase("project")) {
							graph.addEdge(status.get(tokens[2]), project.get(tokens[3]));
						} else  {
							System.out.println("TOPIC: SOMETHING WRONG HAPPENED... exiting");
							System.exit(-1);
						} 
						break;
					default:
						System.out.println("THERE IS A UNEXPECTED NODE LABEL: " + tokens[0] +" ... EXITING!");
						System.exit(-1);
						break;
				}
			}
	
			//System.out.println(graph2.containsEdge(developer.get("2708097"), project.get("372429")));
			
			
		//System.out.println("printing the graph");
		System.out.println(graph2.toString());
		/*
		
		System.out.println("Degree of Developers " + graph.degreeOf(audience.get("Developers")));
		System.out.println("Degree of End Users/Desktop " + graph.degreeOf(audience.get("End Users/Desktop")));
		*/
		// Creating the adjacency matrices
		System.out.println("Creating the Adjacency Matrices");
		int D_P[][] = adjacencyMatrix(graph2, developer, project);
		int P_PL[][] = adjacencyMatrix(graph2, project, language);
		//int P_T[][] = adjacencyMatrix(graph2, project, topic);
		int P_S[][] = adjacencyMatrix(graph2, project, status);
		int P_A2[][] = adjacencyMatrix(graph2, project, audience);
		int P_OS[][] = adjacencyMatrix(graph2, project, os);
		int P_L[][] = adjacencyMatrix(graph2, project, license);
		
		
		// Creating the inverse matrices
		System.out.println("Creating the Inverse Matrices");
		int P_D[][] = invertMatrix(D_P);
		int PL_P[][] = invertMatrix(P_PL);
		//int T_P[][]	= invertMatrix(P_T);
		int S_P[][]	= invertMatrix(P_S);
		int A2_P[][] = invertMatrix(P_A2);
		int OS_P[][] = invertMatrix(P_OS);
		int L_P[][] = invertMatrix(P_L);
		
		//Matrix multiplication or Path Count (PC)
		System.out.println("Multiplying...");
		int pc2[][] = matmul(D_P, P_PL, PL_P);
		int pc3[][] = matmul(D_P, P_S, S_P);
		int pc4[][] = matmul(D_P, P_A2, A2_P);
		int pc5[][] = matmul(D_P, P_OS, OS_P);
		int pc6[][] = matmul(D_P, P_L, L_P);
		int pc7[][] = matmul(D_P, P_D, D_P);
		//D_P_D = matmul(D_P, P_D);
		//System.out.println(D_P_D.length);
		//Computing NPC
		System.out.println("Creating the NPC for D-P-Languages-P");
		double np2c[] = NPC(pc2, developer, project, "developer", "project", "D-P-Languages-P");
		System.out.println("Creating the NPC for D-P-Status-P");
		double np3c[] = NPC(pc3, developer, project, "developer", "project", "D-P-Status-P");
		System.out.println("Creating the NPC for D-P-Audience-P");
		double np4c[] = NPC(pc4, developer, project, "developer", "project", "D-P-Audience-P");
		System.out.println("Creating the NPC for D-P-Operating sys-P");
		double np5c[] = NPC(pc5, developer, project, "developer", "project", "D-P-Operating sys-P");
		System.out.println("Creating the NPC for D-P-License-P");
		double np6c[] = NPC(pc6, developer, project, "developer", "project", "D-P-License-P");
		System.out.println("Creating the NPC for D-P-Developer-P");
		double np7c[] = NPC(pc7, developer, project, "developer", "project", "D-P-Developer-P");
		
		//Printing to file
		System.out.println("Printing to File!");
		printToFileSS(graph2, developer, project, fileOut3, np2c, np3c, np4c, np5c, np6c, np7c);
		printToFile(graph2, developer, project, fileOut2, np2c, np3c, np4c, np5c, np6c, np7c);

		
		System.out.println("DONE!");
		verticesFile2.close();
		edgesFile2.close();
		fileOut2.close();
		fileOut3.close();
		stdin.close();
	}
	
	public static int[][] adjacencyMatrix( UndirectedGraph<vertex, DefaultEdge> g, Map<String, vertex> a, Map<String, vertex> b ) {
		
		int mat[][] = new int[a.size()][b.size()];
		int r = 0, c = 0;
		for (vertex row: a.values() ) {
			//System.out.print(row.getName() + " ");
			for (vertex column: b.values()) {
				if (g.containsEdge(row, column))
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
	
	public static void printToFile( UndirectedGraph<vertex, DefaultEdge> g, Map<String, vertex> a, Map<String, vertex> b, PrintWriter fileOut, Object... v) throws IOException{
		
		for (int i = 0; i < a.size() ; i++) {
			for (int j = 0; j < b.size(); j++) {
				fileOut.print( ((vertex)(a.values()).toArray()[i]).getName() + "-" + ((vertex)(b.values()).toArray()[j]).getName() + "," );
				for (Object meta_path: v) {
					fileOut.print( ((double[])(meta_path))[i*b.size() + j] + ",");
				}
				//System.out.println(a.size() + " " + b.size());
				//fileOut.print((D_P_D[i][j] != 0)? "1":"0");

				fileOut.print(g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), b.get(((vertex)(b.values()).toArray()[j]).getName()))? "1":"0");
				if (g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), b.get(((vertex)(b.values()).toArray()[j]).getName()))) {
					//System.out.println("HERE!");
				}
				
				fileOut.println();
			}	
			
		}
		fileOut.flush();
		fileOut.close();
	}
	
public static void printToFileSS( UndirectedGraph<vertex, DefaultEdge> g, Map<String, vertex> a, Map<String, vertex> b, PrintWriter fileOut3, Object... v) throws IOException{
		Random generator = new Random(System.currentTimeMillis());
		Set<vertex>  x = g.vertexSet();
		Object[] gVertices = (x.toArray());
		for (int i = 0; i < a.size() ; i++) {
			for (int j = 0; j < b.size(); j++) {
				fileOut3.flush();
				boolean link = g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), b.get(((vertex)(b.values()).toArray()[j]).getName()));
				if (link) 
				{
					fileOut3.print( ((vertex)(a.values()).toArray()[i]).getName() + "-" + ((vertex)(b.values()).toArray()[j]).getName() + "," );
					fileOut3.flush();
					for (Object meta_path: v) {
						fileOut3.print( ((double[])(meta_path))[i*b.size() + j] + ",");
						fileOut3.flush();
					}
					fileOut3.print(g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), b.get(((vertex)(b.values()).toArray()[j]).getName()))? "1":"0");
					fileOut3.print("," + (((vertex)(a.values()).toArray()[i]).toString() + " " + (((vertex)(b.values()).toArray()[j]).toString() )) + " " + i + "[" + getPos(a, ((vertex)(a.values()).toArray()[i])) + "]" + " " + j + "[" + getPos(b, ((vertex)(b.values()).toArray()[j])) + "]");
					fileOut3.println();
					vertex t;
					do {
						int p = generator.nextInt(gVertices.length);
						t = (vertex) gVertices[p];
					} while(  (!t.getType().equalsIgnoreCase("project")) ||  (g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), t)   && (t.getType().equalsIgnoreCase("project"))));					
					
					fileOut3.print( ((vertex)(a.values()).toArray()[i]).getName() + "-" + (t.getName() + "," ));
					int pos = getPos(b, t);
					//System.out.println("Posicion:" + pos);
					//System.out.println(t.toString());
					for (Object meta_path: v) {
						fileOut3.print( ((double[])(meta_path))[i*b.size() + pos] + ",");
						fileOut3.flush();
					}
					fileOut3.print(g.containsEdge(a.get(((vertex)(a.values()).toArray()[i]).getName()), t)? "1":"0");
					fileOut3.print("," + (((vertex)(a.values()).toArray()[i]).toString() + " " + t.toString()) + " " + i + "[" + getPos(a, ((vertex)(a.values()).toArray()[i])) + "]" + " " + pos + "[" + getPos(b, t) + "]");
					fileOut3.println();
					fileOut3.flush();
				}
			}		
		}
		fileOut3.flush();
		fileOut3.close();
	}

	public static int getPos(Map<String, vertex> m, vertex v ) {
	
		Object[] vertices = m.values().toArray();
		for (int i = 0; i < vertices.length; i++) {
			//System.out.println(((vertex)(vertices[i])).toString());
			if (v.getName().equalsIgnoreCase(((vertex)(vertices[i])).getName())) {
				return i;
			}
		}
		
		return -1;
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
	
	public static double[] NPC(int[][] A_A, Map<String, vertex> a, Map<String, vertex> b, String nameA, String nameB, String matName) throws IOException {
		PrintWriter fileOut = new PrintWriter ( new FileWriter ("NPC_" + nameA + "_" + nameB + "_" + matName +".csv"));
		fileOut.print(nameA + " size " + a.size()+ ". " + nameB + " size " + b.size()  );
		fileOut.println();
		fileOut.print("NPC matrix size " + A_A.length + " " + A_A[0].length );
		fileOut.println();
		Object[] aArray = a.values().toArray();
		Object[] bArray = b.values().toArray();
		fileOut.print(",");
		for (Object colVertex:  bArray) {
			fileOut.print("," + ((vertex)(colVertex)).getName());
		}
		fileOut.println();
		fileOut.print(",");
		for (int i = 0; i < b.size(); i++) {
			fileOut.print("," + i);
		}
		fileOut.println();
		double result[] = new double[A_A.length * A_A[0].length];
		int index = 0;
		for(int i = 0; i < A_A.length; i++) {
			fileOut.print( ((vertex)(aArray[i])).getName() + "," + i + ",");
			int sumRow = sumRow(A_A, i);
			for (int j = 0; j < A_A[0].length; j++) {
				int total = (sumRow + sumColumn(A_A, j));
				if (total != 0)
					result[index] = ((A_A[i][j]*2)*(1.0)) / total;
				index++;
				fileOut.print( result[index -1] + ",");
				/*
				System.out.println( ((vertex)(developer.values()).toArray()[i]).getName() + " " + 
						((vertex)(project.values()).toArray()[j]).getName() + " " +
				(index - 1) + " " + 
						result[index - 1] + " " + 
				A_A[i][j]*2 + " " + 
						sumRow + " " + 
				sumColumn(A_A, j));
				*/
			}
			fileOut.println();
		}
		fileOut.flush();
		fileOut.close();
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