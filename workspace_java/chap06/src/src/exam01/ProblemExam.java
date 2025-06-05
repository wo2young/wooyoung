package src.exam01;

public class ProblemExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//문제1
		int [] a = {1, 2, 3};
		for(int i=0; i < a.length; i++) {
			a[i] = a.length - i;
			System.out.println("a[" + i + "] : " + (a.length - i));
		}
		//문제2
		int cnt = 0;
		int [] b = {3,4,7,5,1,9,4};
		for(int i=0; i<b.length; i++) {
			if(b[i] % 2 == 1) {
//				System.out.println("b[" + i +"] : " + b[i]);
				cnt++;
			}else {
//				System.out.println();
			} 
		}System.out.println(cnt + "개");
		
		//문제3
		cnt = 0;
//		int [] b = {3,4,7,5,1,9,4};
		for(int i=0; i<b.length; i++) {
			if(b[i] > 4) {
//				System.out.println("b[" + i +"] : " + b[i]);
				cnt++;
			}else {
//				System.out.println();
			} 
		}System.out.println(cnt + "개");
		
		//문제4
//		int [] b = {3,4,7,5,1,9,4};
		System.out.println(b.length);
		for(int i=0; i<b.length; i++) {
			if(b.length < b[i]) {
				System.out.println("최대값 : " + b[i]);
				}
			
			
		}
		
	}

}
