import java.io.*;
import java.util.*;
    

public class Open_Addressing {
	public static final double MAX_LOAD_FACTOR = 0.75;
	
	public int m; // number of slots
	public int A; // the default random number
	int w;
	int r;
	int seed;
	public int[] Table;
	int size; // number of elements stored in the hash table

	protected Open_Addressing(int w, int seed, int A) {
		this.seed = seed;
		this.w = w;
		this.r = (int) (w - 1) / 2 + 1;
		this.m = power2(r);
		if (A == -1) {
			this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
		} else {
			this.A = A;
		}
		this.Table = new int[m];
		for (int i = 0; i < m; i++) {
			Table[i] = -1;
		}
		this.size = 0;
	}

	/**
	 * Calculate 2^w
	 */
	public static int power2(int w) {
		return (int) Math.pow(2, w);
	}

	public static int generateRandom(int min, int max, int seed) {
		Random generator = new Random();
		if (seed >= 0) {
			generator.setSeed(seed);
		}
		int i = generator.nextInt(max - min - 1);
		return i + min + 1;
	}

	/**
	 * Implements the hash function g(k)
	 */
	public int probe(int key, int i) {
		//TODO: implement this function and change the return statement.
		int temp = this.A*key%this.power2(this.w);
		int div = this.w-this.r;
		temp = temp/this.power2(div);
		temp = (temp+i)%this.power2(this.r);
		return temp;
	}

	/**
	 * Inserts key k into hash table. Returns the number of collisions encountered
	 */
	public int insertKey(int key) {
		//TODO: implement this function and change the return statement.
		int count = 0;
		int hashvalue = this.probe(key, 0);
		while(this.Table[hashvalue]!=-1) {
			count++;
			if(count==m) {
				return m;
			}
			hashvalue = this.probe(key, count);
		}
		this.Table[hashvalue]=key;
		this.size+=1;
		return count;
	}


	/**
	 * Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions
	 */
	public int insertKeyArray(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKey(key);
		}
		return collision;
	}

	/**
	 * @param the key k to be searched
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table if the key is present, = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKey(int k) {
		//TODO: implement this function and change the return statement
		
		int count = 0;
		int hashvalue = this.probe(k, 0);
		while(this.Table[hashvalue]!=k) {
			count+=1;
			if(count==m) {
				break;
			}

			hashvalue = this.probe(k, count);
		}
		if(count!=m) {
			int[] output = {hashvalue, count};
			return output;
		}
		else {
			int[] output = {-1, count};
			return output;
		}
		
	}

	/**
	 * Removes key k from hash table. Returns the number of collisions encountered
	 */
	public int removeKey(int k){
		//TODO: implement this function and change the return statement.
		int[] output = this.searchKey(k);
		if(output[0]!=-1) {
			this.Table[output[0]]=-1;
			return output[1];
		}
		int count = output[1];
		this.size-=1;
		return count;
	}

	/**
	 * Inserts key k into hash table. Returns the number of collisions encountered,
	 * and resizes the hash table if needed
	 */
	public int insertKeyResize(int key) {
		//TODO: implement this function and change the return statement
		double ratio = this.size+1;
		ratio = ratio/m;
		if(ratio>0.75) {
			Open_Addressing temp = new Open_Addressing(w+2,seed,A);
			for(int j=0;j<m;j++) {
				if(this.Table[j]==-1) {
					continue;
				}
				temp.insertKey(this.Table[j]);
			}
			this.A=temp.A;
			this.m=temp.m;
			this.r=temp.r;
			this.w=temp.w;
			this.Table=temp.Table;
			return this.insertKey(key);
		}
		else {
			return this.insertKey(key);
		}
		
		
	}

	/**
	 * Sequentially inserts a list of keys into the HashTable, and resize the hash table
	 * if needed. Outputs total number of collisions
	 */
	public int insertKeyArrayResize(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKeyResize(key);
		}
		return collision;
	}

	/**
	 * @param the key k to be searched (and relocated if needed)
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table (after the relocation) if the key is present, 
	 * 				 = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKeyOptimized(int k) {
		//TODO: implement this function and change the return statement
		int count = 0;
		int hashvalue = this.probe(k, 0);
		while(this.Table[hashvalue]!=-1) {
			count++;
			if(count==m) {
				break;
			}
			hashvalue = this.probe(k, count);
		}
		int number = this.searchKey(k)[1];
		int value = this.searchKey(k)[0];
		if(number>count) {
			this.Table[value]=-1;
			this.Table[hashvalue]=k;
			int[] output = {hashvalue,count};
			return output;
		}
		return this.searchKey(k);
		
	}

	/**
	 * @return an int array of n keys that would collide with key k
	 */
	public int[] collidingKeys(int k, int n, int w) {
		//TODO: implement this function and change the return statement
		int value = (this.A*k)%this.power2(w);
		int[] output = new int[n];
		int num =-1;
		for(int i=0;i<n;i++) {
			num+=1;
			int key = this.power2(w)*num;
			key = key+value;
			if(key%A!=0) {
				i-=1;
				num+=1;
				continue;
			}
			key = key/A;
			output[i]=key;
		}
		return output;
	}
}


