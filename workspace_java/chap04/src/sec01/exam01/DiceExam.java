package sec01.exam01;

public class DiceExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 문제 1번 
				// 주사위 두개를 굴려서 나올수 있는 모든 조합을 출력
		for(int d1 = 1; d1 <=6;d1++) {
			System.out.println(d1 + "조합");
			for(int d2 = 1; d2 <= 6; d2++) {
				System.out.print("[" + d1 + "," + d2 + "]");
			}	System.out.println();
		}
		
		// 문제 2번
		// 주사위 2개의 합 별로 나올 수 있는 조합
		// 조건 d1 + d2 = 2이면 합이 2인 값이 한번에 
		for(int i = 2; i <= 12; i++) {
			System.out.print("합" + (i) + " : ");
		for(int d1 = 1; d1 <= 6; d1++) {
			for(int d2 = 1; d2 <= 6; d2++) {
				if(i == d1 + d2) {
					System.out.print("[" + d1 + "," + d2 + "]");
				} 
			} 
		} System.out.println();
	}
		// 문제 3번
//		합 별 조합의 수 출력
		for(int i = 2; i <= 12; i++) {
			System.out.print("합 " + (i) + " : ");
			int cnt = 0;
		for(int d1 = 1; d1 <= 6; d1++) {
			for(int d2 = 1; d2 <= 6; d2++) {
				
				if(i == d1 + d2) {
					System.out.print("[" + d1 + "," + d2 + "]");
					cnt++;
				} 
			}  
		}System.out.print(" 합계" + " : " + cnt + "개");  
		System.out.println();
	}
		// 문제 4번
//		순서에 관계 없이 중복 제거
//		합2 : [1,1]	합3 : [1,2]와 [2,1]는 같음
		for(int i = 2; i <= 12; i++) {
			System.out.print("합 " + (i) + " : ");
			int cnt = 0;
		for(int d1 = 1; d1 <= 6; d1++) {
			for(int d2 = d1; d2 <= 6; d2++) {
					if(i == d1 + d2) {
						System.out.print("[" + d1 + "," + d2 + "]");
						cnt++;
				} 
			}  
		} 
		System.out.print(" 합계" + " : " + cnt + "개");  
		System.out.println();
		}
		
		
		
	}

}
