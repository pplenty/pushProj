package pushMan;

import java.util.ArrayList;

public class test {


	public static void main(String[] args) {
//		System.out.println(divisibilityOfFactorials(10));
//		System.out.println(divisibilityOfFactorials(25));
		System.out.println(divisibilityOfFactorials(2000));
	}

	
	static int divisibilityOfFactorials(int N) {
		
		if (N == 2) return N;
		boolean isPrime = true;


		for (int i = 2; i < N; i++) {
			if (N % i == 0) {
				isPrime = false;
				break;
			}
			continue;
		}

		if (isPrime == true)
			return N;
		
		
		
		
		
		return 0;
	}
	
	
	
}
