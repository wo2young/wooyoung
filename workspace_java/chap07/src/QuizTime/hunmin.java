package QuizTime;

public class hunmin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int floor = 11; // 층 정하기
		for(int i = 0; i < floor; i++) {
			System.out.println();
			for(int j = 1; j < floor-i +4; j++) {
			System.out.print(" ");	
			}
			for(int s = 1; s <= (2 * i) + 1; s++) {
			System.out.print("*");	
			}
		}
	}

}
