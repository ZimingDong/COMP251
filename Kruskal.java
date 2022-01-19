//No collaborators
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
    	ArrayList<Edge> edges = g.listOfEdgesSorted();
        WGraph output = new WGraph();
        DisjointSets p = new DisjointSets(g.getNbNodes());
        for(int i=0;i<edges.size();i++) {
        	Edge e= edges.get(i);
        	if(IsSafe(p,e)) {
        		int first = p.find(e.nodes[0]);
        	    int second = p.find(e.nodes[1]);
        		output.addEdge(e);
        		p.union(first, second);
        	}
        	if(output.getNbNodes()==g.getNbNodes()) {
        		break;
        	}
        }
        return output;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
        int first = p.find(e.nodes[0]);
        int second = p.find(e.nodes[1]);
        
    	return (first!=second);
    
    }
    
    public static void main(String[] args){
        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
    
}


