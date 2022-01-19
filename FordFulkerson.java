import java.util.*;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE */
		int V = graph.getNbNodes();
		int rGraph[][] = new int[V][V];
		ArrayList<Edge> edges=graph.getEdges();
		for(Edge e:edges) {
			rGraph[e.nodes[0]][e.nodes[1]]=e.weight;
		}
		int parent[] = new int[V];
		ArrayList<Integer> result = new ArrayList<Integer>();
		int d = destination;
		if(Dfs(rGraph,source,destination,V,parent)) {
			for(int i=0;i<V;i++) {
				result.add(d);
				d=parent[d];
				if(d==-1) {
					break;
				}
			}
			int size = result.size();
			for(int i=size-1;i>=0;i--) {
				path.add(result.get(i));
				
			}
		}
		return path;
	}

	private static boolean Dfs(int rGraph[][],int s,int d,int V,int parent[]) {
		boolean visited[] = new boolean[V];
		for(int i=0;i<V;i++) {
			visited[i]=false;
		}
		visited[s]=true;
		parent[s]=-1;
		return dfs(rGraph,s, d, V,parent, visited);
	}
	private static boolean dfs(int rGraph[][],int s,int d,int V,int parent[],boolean visited[]) {
		
		for(int i=0;i<V;i++) {
			
			if(rGraph[s][i] > 0 && visited[i] == false) {
				
				if(i==d) {
					parent[i]=s;
					return true;
				}
				visited[i]=true;
				parent[i]=s;
				if(dfs(rGraph,i, d, V,parent, visited)==true) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String fordfulkerson(WGraph graph){
		String answer = "";
		int maxFlow = 0;
		int V = graph.getNbNodes();
		int s = graph.getSource();
		int d = graph.getDestination();
		int rGraph[][] = new int[V][V];
		int v;
		int u;
		ArrayList<Edge> edges=graph.getEdges();
		for(Edge e:edges) {
			rGraph[e.nodes[0]][e.nodes[1]]=e.weight;
		}
		int parent[] = new int[V];
		while(Dfs(rGraph,s,d,V,parent)) {
			int path_flow = Integer.MAX_VALUE;
			for (v = d; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                    = Math.min(path_flow, rGraph[u][v]);
            }
			for (v = d; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
                
            }
			maxFlow += path_flow;
		}
		for(Edge e:edges) {
			e.weight=rGraph[e.nodes[1]][e.nodes[0]];
		}
		/* YOUR CODE GOES HERE */
		
		answer += maxFlow + "\n" + graph.toString();	
		
		return answer;
	}
	

	 public static void main(String[] args){
		 String file = args[0];
		 WGraph g = new WGraph(file);
		 System.out.println(fordfulkerson(g));
		 
	 }
}

