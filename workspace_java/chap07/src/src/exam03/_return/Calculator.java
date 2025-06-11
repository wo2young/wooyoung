package src.exam03._return;

public class Calculator {
	
	// 메소드명 powerOn
	void powerOn() {
		System.out.println("전원을 켭니다.");	
	}
	
	// 메소드명 powerOff
	void powerOff() {
		System.out.println("전원을 끕니다.");
//		return;
		
	}
	
	/**
	 * JAVADOC 주석
	 * 더하기 기능 추가
	 * 두 수를 입력 받아서 더한 결과를 돌려준다.
	 * 
	 * 메소드명 : plus
	 * 전달인자 : int, int
	 * 리턴타입 : int (두 수의 합)
	 * 
	 * @param int x, int y
	 * @return int
	 * @author "회사메일"
	 */
	
	int plus(int x, int y) {
		int result = x + y;
		return result;
	}
	
	/**
	 * 두 정수를 입력 받아서
	 * 나누기 한 결과를
	 * double로 돌려준다.
	 * 단! y가 0일때는 "안된다"고 출력하고 0을 돌려 줌
	 * 
	 * 메소드명 : divide
	 * 전달인자 : int x, int y
	 * 리턴타입 : double
	 * @param x
	 * @param y
	 * @return double
	 */
	double divide(int x, int y) {
		if(y == 0 || x == 0) {
			System.out.println("안된다");
			return 0;
		} 
		else {
		double result = (double)x / y;
		return result; 
		}
	}	
	int sum(int [] array) {
		int sum = 0;
		if(array != null) {
			for(int i=0; i<array.length; i++) {
				sum += array[i];
				
				
			}
		}
		return sum;			
	}
	
	/**
	 * 메소드명 : avg
	 * 전달인자 : int
	 * 리턴타입 : double
	 */
	
	double avg(int x, int y) {
		int sum = plus(x, y);
		double result = (double)sum / 2;
		return result;
	}
	
	void execute(int a1, int a2) {
		double result = avg(a1, a2);
		System.out.println(a1 + "과 " + a2 + "의 평균은 " + result);
	}
		
	
}
