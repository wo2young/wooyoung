package sec01.exam01;

public class Exam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int i =0;
		int [] s = new int [6];
		// 10~5까지 출력
		//		1        2 5 8   4 7 10
		for(int x = 10; x > 4; x--) {
			//    3 6 9 
			for(int j=0; j<1;j++) {
				s[i] = x;
				
				System.out.println("s[" + i + "] : " + s[i]);
				i++;
			}
				
		}
		
//		for while 반복문
//		if switch 조건문
		 i = 1;
		if( i == 1 ) {
			System.out.println("팀장 김동현");
		}
		
		int a = 0;
		int b = 1;
		
		
		boolean menu = true;
		
		while(menu) {
			if(a > 0 && b < 10) {
					System.out.println(a+b);
					break;
			} 
			System.out.println("조건에 맞지 않습니다.");
			break;
		} 

	}

}
