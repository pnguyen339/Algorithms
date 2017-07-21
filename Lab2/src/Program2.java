/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.awt.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;


/* Your solution goes in this file.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */

public class Program2 extends VertexNetwork {
    /* DO NOT FORGET to add a graph representation and 
       any other fields and/or methods that you think 
       will be useful. 
       DO NOT FORGET to modify the constructors when you 
       add new fields to the Program2 class. */
    Vector<Vector<Edge>> graph = new Vector<Vector<Edge>>();
    
    
    private void createGraph() {
    	for(int i = 0; i < this.location.size(); i++){
    		graph.add(new Vector<Edge>());
    		Vertex currentV = this.location.get(i);
    		
    		for(int y = 0; i< this.location.size(); i++){
    			Vertex possNeigh = this.location.get(y);
    			double euclideanDis = currentV.distance(possNeigh);
    			
    			
    			if( euclideanDis == 0) {
    				graph.get(i).add(null);
    			}
    			else if( euclideanDis <= this.transmissionRange) {
    				for(int x = 0; x <= this.edges.size(); x++){
    					Edge possUV = this.edges.get(x);
    					if((possUV.getU() == i && possUV.getV() == y)  || (possUV.getU() == y && possUV.getV() == i))
    						graph.get(i).add(possUV);
    				}
    			}
    			else {
    				graph.get(i).add(null);
    			}
    				
    		}
    		
    		graph.get(i).removeAll(Collections.singleton(null));
    	}
    }
	
	
	
	
    Program2() {
        super();
        
    }
    
    Program2(String locationFile) {
        super(locationFile);
        
    }
    
    Program2(String locationFile, double transmissionRange) {
        super(locationFile, transmissionRange);
        
    }
    
    Program2(double transmissionRange, String locationFile) {
        super(transmissionRange, locationFile);
        
    }

    private boolean findGPSR(Vector<Vertex> path, int sourceIndex, int sinkIndex){
    	Vector<Double> 
    	
    	return false;
    }
    
    
    
    public Vector<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
        /* This method returns a path from a source at location sourceIndex 
           and a sink at location sinkIndex using the GPSR algorithm. An empty 
           path is returned if the GPSR algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements the GPSR algorithm. */
        return new Vector<Vertex>(0);
    }
    
    
    private int neigh(Edge e, int vertex) {
    	if(e.getU() == vertex)
    		return e.getV();
    	else
    		return e.getU();
    }
    
    private int smallestDist(Vector<Double> d, Vector<Integer> unvisited){
    	
    	int smallestIndex = 0;
    	for(int i = 0; i < d.size(); i ++){
    		if( (d.get(i) < d.get(smallestIndex)) &&
    				unvisited.contains(i) == true)
    			smallestIndex = i;
    	}
    	
    	return smallestIndex;
    }
    
    
    
    public Vector<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
    	
    	
        /* This method returns a path (shortest in terms of latency) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
    	Vector<Double> dist = new Vector<Double>(this.location.size()); 
    	int[] previous = new int[this.location.size()];
    	Vector<Integer> unvisited = new Vector<Integer>(this.location.size());
    	Vector<Integer> visitedPath = new Vector<Integer>();
    	for(int i = 0; i < this.location.size(); i ++){
    		dist.set(i,Double.POSITIVE_INFINITY);
    		previous[i] = -1;
    		unvisited.add(i);
    	}
    	
    	dist.set(sourceIndex, 0.0);
    	while(unvisited.size() != 0) {
    		int u = smallestDist(dist, unvisited);
    		
    		if(dist.get(u) == Double.POSITIVE_INFINITY)
    			break;
    		
    		unvisited.remove(u);
    		if(u == sinkIndex)
    			break;
    		
    		for(int i = 0; i < graph.get(u).size(); i++){
    			Edge someEdgeinU = graph.get(u).get(i);
    			int vertexNum = neigh(someEdgeinU, u);
    			double alt = dist.get(u) + someEdgeinU.getW();
    			if(alt < dist.get(vertexNum)){
    				dist.set(vertexNum, alt);
    				previous[vertexNum] = u;
    				
    			}
    		}
    		unvisited.remove(u);
    		
    		
    	}
    	
    	
    	int traceback = sinkIndex;
    	visitedPath.addElement(sinkIndex);
    	while(traceback != sourceIndex) {
    		visitedPath.add(previous[traceback]);
    		traceback = previous[traceback];
    	}
    	
    	Vector<Vertex> result = new Vector<Vertex>();
    	
    	for(int i = visitedPath.size()-1 ; i >=0; i++) {
    		result.add(this.location.get(visitedPath.get(i)));
    	}
    	
    	
    	return result;
    }
    
    public Vector<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of hops) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
    	Vector<Double> dist = new Vector<Double>(this.location.size()); 
    	int[] previous = new int[this.location.size()];
    	Vector<Integer> unvisited = new Vector<Integer>(this.location.size());
    	Vector<Integer> visitedPath = new Vector<Integer>();
    	for(int i = 0; i < this.location.size(); i ++){
    		dist.set(i,Double.POSITIVE_INFINITY);
    		previous[i] = -1;
    		unvisited.add(i);
    	}
    	
    	dist.set(sourceIndex, 0.0);
    	while(unvisited.size() != 0) {
    		int u = smallestDist(dist, unvisited);
    		
    		if(dist.get(u) == Double.POSITIVE_INFINITY)
    			break;
    		
    		unvisited.remove(u);
    		if(u == sinkIndex)
    			break;
    		
    		for(int i = 0; i < graph.get(u).size(); i++){
    			Edge someEdgeinU = graph.get(u).get(i);
    			int vertexNum = neigh(someEdgeinU, u);
    			double alt = dist.get(u) + 1;
    			if(alt < dist.get(vertexNum)){
    				dist.set(vertexNum, alt);
    				previous[vertexNum] = u;
    				
    			}
    		}
    		unvisited.remove(u);
    		
    		
    	}
    	
    	
    	int traceback = sinkIndex;
    	visitedPath.addElement(sinkIndex);
    	while(traceback != sourceIndex) {
    		visitedPath.add(previous[traceback]);
    		traceback = previous[traceback];
    	}
    	
    	Vector<Vertex> result = new Vector<Vertex>();
    	
    	for(int i = visitedPath.size()-1 ; i >=0; i++) {
    		result.add(this.location.get(visitedPath.get(i)));
    	}
    	
    	
    	return result;
    }
    
}

