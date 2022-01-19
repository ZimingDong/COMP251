//No collaborators
import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		if(a1.weight==a2.weight&&a1.deadline==a2.deadline&&a1.completiontime==a2.completiontime) {
			 return 0;
		}
		
		
		if(a1.weight<a2.weight&&a1.deadline==a2.deadline&&a1.completiontime==a2.completiontime) {
			 return 1;
		}
		if(a1.weight>a2.weight&&a1.deadline==a2.deadline&&a1.completiontime==a2.completiontime) {
			 return -1;
		}
		
		
		if(a1.weight==a2.weight&&a1.deadline>a2.deadline&&a1.completiontime==a2.completiontime) {
			 return 1;
		} 
		if(a1.weight==a2.weight&&a1.deadline<a2.deadline&&a1.completiontime==a2.completiontime) {
			 return -1;
		}
		
		
		if(a1.weight==a2.weight&&a1.deadline==a2.deadline&&a1.completiontime>a2.completiontime) {
			 return 1;
		} 
		if(a1.weight==a2.weight&&a1.deadline==a2.deadline&&a1.completiontime<a2.completiontime) {
			 return -1;
		} 
		
		
		double ratio1 = a1.weight/a1.completiontime;
		double ratio2 = a2.weight/a1.completiontime;
		if(a1.deadline<=a1.completiontime) {
			int t1 = a1.completiontime-a1.deadline+1;
			double a1W = a1.weight*(1-t1*0.1);
			ratio1 = a1W/a1.completiontime;
		}
		if(a2.deadline<=a2.completiontime) {
			int t2 = a2.completiontime-a2.deadline+1;
			double a2W = a2.weight*(1-t2*0.1);
			ratio2 = a2W/a2.completiontime;
		}
		if(ratio1>ratio2) {
			return -1;
		}
		if(ratio1<ratio2) {
			return 1;
		}
		if(ratio1==ratio2) {
			if(a1.deadline<a2.deadline) {
				return -1;
			}
			if(a1.deadline>a2.deadline) {
				return 1;
			}
			if(a1.deadline==a2.deadline) {
				if(a1.weight>a2.weight) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
		return 0;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
    int lastDeadline = 0;
    double grade = 0.0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
        if(size==0){
            throw new Exception("There is no assignment.");
        }
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
        
        //TODO Implement this

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i=0; i < lastDeadline; ++i) {
			homeworkPlan.add(-1);
		}
		int begin = 0;
		for(Assignment assg: Assignments) {
			int end = begin+assg.completiontime-1;
			if(end<assg.deadline) {
				this.grade=this.grade+assg.weight;
			}
			else {
				int late = end-assg.deadline+1;
				if(late>=10) {
					continue;
				}
				else {
					this.grade=this.grade+assg.weight*(1-0.1*late);
				}
			}
			for(int i=begin;i<=end;i++) {
				if(i==lastDeadline) {
					return homeworkPlan;
				}
				homeworkPlan.set(i, assg.number);
			}
			begin = end+1;
		}
		
		
	
		
		return homeworkPlan;
	}
	public static void main(String[] args) throws Exception{
		 int[] weights = {20,50};
		 int[] ddl = {2,5};
		 int[] time = {4,5};
		 int size = 2;
		 HW_Sched test = new HW_Sched(weights,ddl,time,size);
		 System.out.println(test.SelectAssignments().toString());
		 System.out.println(test.grade);
	}
}

	



