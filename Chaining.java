import java.io.*;
import java.util.*;

public class Chaining {

	public int m; // number of slots
	public int A; // the default random number
	int w;
	int r;
	public ArrayList<ArrayList<Integer>>  Table;

	// if A==-1, then a random A is generated. else, input A is used.
	protected Chaining(int w, int seed, int A){
		this.w = w;
		this.r = (int) (w-1)/2 +1;
		this.m = power2(r);
		this.Table = new ArrayList<ArrayList<Integer>>(m);
		for (int i=0; i<m; i++) {
			Table.add(new ArrayList<Integer>());
		}
		if (A==-1){
			this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
		}
		else{
			this.A = A;
		}

	}
	/** Calculate 2^w*/
	public static int power2(int w) {
		return (int) Math.pow(2, w);
	}
	//generate a random number in a range (for A)
	public static int generateRandom(int min, int max, int seed) {     
		Random generator = new Random(); 
		if(seed>=0){
			generator.setSeed(seed);
		}
		int i = generator.nextInt(max-min-1);
		return i+min+1;     
	}




	/**Implements the hash function h(k)*/
	public int chain (int key) {
		int temp = this.A*key%this.power2(this.w);
		int div = this.w-this.r;
		temp = temp/this.power2(div);
		// TODO: implement this and change the return statement
		return temp;
	}


	/**Inserts key k into hash table. Returns the number of collisions encountered*/
	public int insertKey(int key){
		//TODO: implement this and change the return statement
		int count = 0;
		int hashvalue = this.chain(key);
		if(this.Table.get(hashvalue)!= null) {
			count = this.Table.get(hashvalue).size();
		}
		this.Table.get(hashvalue).add(key);
		
		return count;

	}



	/**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
	public int insertKeyArray (int[] keyArray){
		int collision = 0;
		for (int key: keyArray) {
			collision += insertKey(key);
		}
		return collision;
	}


}

