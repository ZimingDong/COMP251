import java.io.*;
import java.util.*;

public class Universal_Hashing extends Open_Addressing{
	int a;
	int b;
	int p;

	protected Universal_Hashing(int w, int seed) {
		super(w, seed, -1);
		int temp = this.m+1; // m is even, so temp is odd here
		while(!isPrime(temp)) {
			temp += 2;
		}
		this.p = temp;
		a = generateRandom(0, p, seed);
		b = generateRandom(-1, p, seed);
	}
	
	/**
	 * Checks if the input int is prime
	 */
	public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i*i <= n; i++) {
        	if (n % i == 0) return false;
        }
        return true;
    }
	
	/**
     * Implements universal hashing
     */
	@Override
    public int probe(int key, int i) {
    	//TODO: implement this function and change the return statement
		int hashvalue = a*key+b;
		hashvalue = hashvalue%p;
		hashvalue = hashvalue%m;
		hashvalue = (hashvalue+i)%this.power2(this.r);
		return hashvalue;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions encountered,
     * and resizes the hash table if needed
     */
	@Override
    public int insertKeyResize(int key) {
		double ratio = this.size+1;
		ratio = ratio/m;
		if(ratio>0.75) {
			Universal_Hashing temp = new Universal_Hashing(w+2,seed);
			for(int j=0;j<m;j++) {
				if(this.Table[j]==-1) {
					continue;
				}
				temp.insertKey(super.Table[j]);
			}
			super.A=temp.A;
			super.m=temp.m;
			super.r=temp.r;
			super.w=temp.w;
			super.Table=temp.Table;
			return this.insertKey(key);
		}
		else {
			return this.insertKey(key);
		}
    	//TODO: implement this function and change the return statement    	
    }
	@Override
	public int insertKey(int key) {
		//TODO: implement this function and change the return statement.
		int count = 0;
		int hashvalue = this.probe(key, 0);
		while(this.Table[hashvalue]!=-1) {
			count++;
			if(count==super.m) {
				return super.m;
			}
			hashvalue = this.probe(key, count);
		}
		super.Table[hashvalue]=key;
		super.size+=1;
		return count;
	}
}
