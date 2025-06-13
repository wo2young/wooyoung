package quiz;

public class UpDown {
	// 필드
	
	int answer = (int)(Math.random()* 9 + 1); // 답
	int number; // 내가 고른숫자
	int cnt;
	
	// 생성자
	UpDown() {
		cnt = 0;	
	}
	
	
	// 메서드
	/**
	 * 기능 : random이 정해지면 number와 다를때 
	 * 메서드 : up
	 * 전달인자 : int number
	 * 리턴타입 : void 
	 * @return
	 */
	void count() {
		 cnt++;
		 System.out.print(" - 시도 " + cnt + "번");
		 System.out.println();
	}
	
	 void up(int num) {
		 if(answer < num) {
			 System.out.print("Down");
			 count();
			 }
	 }
	 void down(int num) {
		 if(answer > num) {
			 System.out.print("Up");
			 count();
		 }
	 }
	 void correct(int num) {
		 if(answer == num) {
			 System.out.print("정답입니다.");
			 count();
		 }
	 }
	 
}

