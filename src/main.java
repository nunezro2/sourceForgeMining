import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.ext.VertexNameProvider;

import java.util.*;
import java.io.*;

public class main {

	public static UndirectedGraph<vertex, DefaultEdge> graph;
	public static Map<String, vertex> project;
	public static Map<String, vertex> developer;
	public static Map<String, vertex> language;
	public static Map<String, vertex> topic;
	public static Map<String, vertex> license;
	public static Map<String, vertex> audience;
	public static Map<String, vertex> operating;
	
	public static Map<String, vertex> author = new HashMap<String, vertex>();
	public static Map<String, vertex> paper = new HashMap<String, vertex>();
	public static Map<String, vertex> venue = new HashMap<String, vertex>();
	
	
	public static void main(String[] args) throws IOException {
		
		//Utilities
		Scanner stdin = new Scanner(System.in);
		
		// Creating the graph
		graph = new SimpleGraph<vertex, DefaultEdge>(DefaultEdge.class);
		
		// Creating the files handlers
		//System.out.println(main.class.getClassLoader().getResource("example/vertices_example").getPath());
		Scanner verticesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/vertices_example").getPath()));
		Scanner edgesFile = new Scanner(new File(main.class.getClassLoader().getResource("example/edges_example").getPath()));
		//PrintWriter outputFile = new PrintWriter(new File("example/NRS_example.txt"));

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
			
			if (tokens[0].equalsIgnoreCase("author")) {
				author.put(tokens[1], new vertex(tokens[0], tokens[1]));
			} else if (tokens[0].equalsIgnoreCase("paper")) {
				paper.put(tokens[1], new vertex(tokens[0], tokens[1]));
			} else if (tokens[0].equalsIgnoreCase("venue")) {
				venue.put(tokens[1], new vertex(tokens[0], tokens[1]));
			}
		}
		
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
		
	
		stdin.close();
		verticesFile.close();
		edgesFile.close();
		//outputFile.close();
	}

}
