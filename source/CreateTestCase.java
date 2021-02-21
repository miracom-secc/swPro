package swPro.source;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

/*
 * Test Case를 만드는 용도
 * random.nextInt(10) + 1 : 1 ~ 10까지의 난수를 생성한다. 이를 활용하여 다양하게 바꿀 수 있다.
 */

public class CreateTestCase {
	
	public static void main(String[] args) throws FileNotFoundException {
		// sample_input.txt로 데이터 생성
		File file = new File("src/swPro/sample_input.txt");
		PrintStream printStream = new PrintStream(new FileOutputStream(file));
		
		System.setOut(printStream);
		Random random = new Random();
		
		// [1,10] 사이의 난수 생성
		int T = random.nextInt(10) + 1;
		//System.out.println(T);
		
		for (int test_case = 0; test_case < T; test_case++) {
			// [1, 100] 사이의 난수 생성
			int N = random.nextInt(100) + 1;
			//System.out.println(N);
			for (int i=0; i<N; i++) {
				// [0, 100] 사이의 난수를 공백을 두고 출력
				System.out.print(random.nextInt(101)+" ");
			}
			
			System.out.println();
		}
	}

}
