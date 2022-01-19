import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */
    	
    	int V = g.getNbNodes();
    	int max = Integer.MAX_VALUE;
    	this.distances = new int[V];
    	this.predecessors = new int[V];
    	ArrayList<Edge> edges = g.getEdges();
    	for(int i=0;i<V;i++) {
    		this.distances[i]= max;
    		this.predecessors[i] = -1;
    	}
    	this.distances[source] = 0;
    	for(int i=0;i<V-1;i++) {
    		for(int j=0;j<edges.size();j++) {
    			int u = edges.get(j).nodes[0];
    			int v = edges.get(j).nodes[1];
    			int w = edges.get(j).weight;
    			if(this.distances[v]>this.distances[u]+w) {
    				this.distances[v] = this.distances[u] + w;
    				this.predecessors[v] = u;
    			}
    			
    		}
    	}
    	for(int k=0;k<edges.size();k++) {
    		int u = edges.get(k).nodes[0];
			int v = edges.get(k).nodes[1];
			int w = edges.get(k).weight;
			if(this.distances[v]>this.distances[u]+w) {
				throw new NegativeWeightException("Contain negative cycle.");
			}
    	}
    	

    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException{
        /* Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
    	ArrayList<Integer> path = new ArrayList<Integer>();
    	path.add(destination);
    	int p = this.predecessors[destination];
    	while(p!=this.source) {
    		path.add(p);
    		p = this.predecessors[p];
    		if(p==-1) {
    			throw new PathDoesNotExistException("Patj does not exist.");
    			
    		}
    	}
    	path.add(p);
    	int size = path.size();
    	int [] spath = new int[size];
    	int k=0;
    	for(int i=size-1;i>=0;i--) {
    		spath[k] = path.get(i);
    		k++;
    	}
        return spath;
    }

    public void printPath(int destination){
        /* Print the path in the format s->n1->n2->destination
         * if the path exists, else catch the Error and 
         * prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

