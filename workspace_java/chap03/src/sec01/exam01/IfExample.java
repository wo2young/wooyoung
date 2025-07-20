package sec01.exam01;

public class IfExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int score = 93;
		if (score >= 90) {
			System.out.println("점수가 90보다 큽니다.");
			System.out.println("등급은 A입니다.");
		}

		if (score < 90) {
			System.out.println("점수가 90보다 작습니다.");
			System.out.println("등급은 B입니다.");
		}
		score = 85;
		if (score >= 90) {
			System.out.println("점수가 90보다 큽니다.");
			System.out.println("등급은 A입니다.");
		} else {
			System.out.println("점수가 90보다 작습니다.");
			System.out.println("등급은 B입니다.");
		}
		
		score = 5;
		
		if(score >= 90) {
			System.out.println("A등급");
		} else if (score >= 80) {
			System.out.println("B등급");
		} else if (score >= 70) {
			System.out.println("C등급");
		} else {
			System.out.println("D등급");
		} 
		
		int x = 2;
		if(x % 2 == 0){
			System.out.println("짝수입니다.");
		} else {
			System.out.println("홀수입니다.");
		}
		
		double random1 = Math.random();
		System.out.println(random1);
		
		
	 int dice = (int)(Math.random() * 46);
			 System.out.println(dice);
	 
	 //  사용가능 : char, byte, short, int, string
	 // 사용불가 : boolean, long, float, double
	 
	 int month = 1;
	 if (month == 1 || month == 2 || month == 12) {
		 System.out.println("겨울");
	 } else if (month == 3 || month == 4 || month == 5) {
		 System.out.println("봄");
	 } else if (month == 6 || month == 7 || month == 8) {
		 System.out.println("여름");
	 } else {
		 System.out.println("가을");
	 }
	 
	 
	 
	 
	} 

}
