package swPro.source;

public class AlgorithmExecutionRate {
		
	public static void main(String[] args) {
		AlgorithmExecutionRate t = new AlgorithmExecutionRate();
			long startTime = System.currentTimeMillis();
			/* ========================================= */
			// 알고리즘을 해당 쿼리 안에 넣어 작성한 알고리즘의 성능을 계산할 수 있다.
			t.testMethod();	
			/* ========================================= */
			long endTime = System.currentTimeMillis();
			System.out.println("수행속도 : "+(endTime - startTime));
		}
		
		public void testMethod() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
