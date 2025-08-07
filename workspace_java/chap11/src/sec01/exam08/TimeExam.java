package sec01.exam08;

public class TimeExam {

	public static void main(String[] args) {
	
		long before = System.currentTimeMillis();
		
		for(int i=0; i<1000000; i++) {
//			System.out.println(i);
		}
		long after = System.currentTimeMillis();
		System.out.println("걸린시간 : " + (after - before) + "[ms]");
		System.out.println(after);
		System.out.println(Long.MAX_VALUE);
	}

}
