import java.io.*;
import java.util.*;




public class main {     


	public static void main(String[] args) {
		//TODO:build the hash table and insert keys using the insertKeyArray function.
		System.out.println("1");
		Open_Addressing test1 = new Open_Addressing(10,0,-1);
		Open_Addressing test2 = new Open_Addressing(7,0,-1);
		//System.out.println(Arrays.toString(test1.Table));
		test1.insertKey(0);
		test1.insertKey(20);
		
		test1.insertKey(40);
		
		//int[] hao= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
		//test1.insertKeyArray(hao);
		//test1.insertKeyResize(0);
		System.out.println(test1.A);
		System.out.println(Arrays.toString(test1.Table));
		test1.removeKey(20);
		System.out.println(Arrays.toString(test1.Table));
		System.out.println(Arrays.toString(test1.searchKeyOptimized(40)));
		System.out.println(Arrays.toString(test1.Table));
		//System.out.println(Arrays.toString(test1.searchKey(40)));
		
		//System.out.println(Arrays.toString(test1.collidingKeys(0, 10, 10)));
		//System.out.println(Arrays.toString(test1.searchKeyOptimized(20)));
		//System.out.println(Arrays.toString(test1.Table));
	}
}

