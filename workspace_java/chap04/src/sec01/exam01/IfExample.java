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
		
		
		
	} 

}
