/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.HashMap;
import java.util.Vector;


/**
 * Your solution goes in this class.
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
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Matching problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching given_matching) {
    	Vector<Vector<Integer>> landlordPref = given_matching.getLandlordPref();
		Vector<Vector<Integer>> tenantPref = given_matching.getTenantPref();
		Vector<Integer> match = given_matching.getTenantMatching();
		Vector<Vector<Integer>> landlordOwn = given_matching. getLandlordOwners();
		
		
		
		if(landlordPref.size() != tenantPref.size()) {
			
			Vector<Vector<Integer>> landlordPref2 = new Vector<Vector<Integer>>();
			
			for(int i = 0; i < given_matching.getTenantCount(); i ++) {
				int y = 0;
				while(y < landlordPref.size()) {
					if(landlordOwn.get(y).contains(i)) {
						landlordPref2.add(landlordPref.get(y));
					}
					y++;
				}
			}
			
			landlordPref = landlordPref2;
			
		}
		
		for(int i = 0; i < given_matching.getTenantCount(); i++){
			int Jmat = match.get(i).intValue();
			int Wind = landlordPref.get(Jmat).indexOf(i);
			
			for(int y = 1; y <= Wind; y++){
				
				int Wpref = landlordPref.get(Jmat).get(Wind-y);
				if(tenantPref.get(Wpref).indexOf(Jmat) < 
					tenantPref.get(Wpref).indexOf(match.get(Wpref))){
					return false;
				}

			}
		}

		return true; 
    }
    
    
    HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
    
    
    public int setMatch(Matching given_matching, Vector<Integer> match, int i){
		
    	Vector<Vector<Integer>> landlordPref = given_matching.getLandlordPref();
		Vector<Vector<Integer>> tenantPref = given_matching.getTenantPref();
		Vector<Vector<Integer>> landlordOwn = given_matching. getLandlordOwners();
		
		if(landlordPref.size() != tenantPref.size()) {
			
			Vector<Vector<Integer>> landlordPref2 = new Vector<Vector<Integer>>();
			
			for(int i1 = 0; i1 < given_matching.getTenantCount(); i1 ++) {
				int y1 = 0;
				while(y1 < landlordPref.size()) {
					if(landlordOwn.get(y1).contains(i1)) {
						landlordPref2.add(landlordPref.get(y1));
					}
					y1++;
				}
			}
			
			landlordPref = landlordPref2;
			
		 }
		
		int rank = 1;
		boolean run = true;
		int appPoss = tenantPref.get(i).indexOf(rank);
		while(run) {
			
			if(appPoss == -1){
				rank++;
				appPoss = tenantPref.get(i).indexOf(rank);
			}
			else if(hm.containsKey(appPoss) == false) {
				hm.put(appPoss, i);
				run = false;
			}
			else if(hm.containsKey(appPoss) == true) {
				int tenantRev = (int) hm.get(appPoss);
				if(landlordPref.get(appPoss).get(tenantRev) > landlordPref.get(appPoss).get(i)) {
					hm.put(appPoss, i);
					return tenantRev;
				}
				else
					appPoss = tenantPref.get(i).indexOf(rank, appPoss+1);
			}
			else
				appPoss = tenantPref.get(i).indexOf(rank, appPoss+1);
			
		}
		
		
		
		
		
		return -1;
	}


    /**
     * Determines a solution to the Stable Matching problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMatchingGaleShapley(Matching given_matching) {
    	Vector<Integer> match = new Vector<Integer>(given_matching.getTenantCount());
    	for(int i = 0; i < given_matching.getTenantCount(); i++){
    		match.add(i, 0);
    	}
		
    	for(int i = 0;i < given_matching.getTenantCount(); i++){
			int v = setMatch(given_matching, match, i);
			while(v != -1){
				v=setMatch(given_matching,match,v);
			}
		}
		for(int i = 0; i < given_matching.getTenantCount(); i++){
			match.set(hm.get(i),i);
		}
		given_matching.setTenantMatching(match);

		return given_matching;
    }
}
