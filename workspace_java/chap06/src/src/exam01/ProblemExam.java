package src.exam01;

import java.util.Random;
import java.util.Scanner;

public class ProblemExam {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 문제1
		int[] a = { 1, 2, 3 };
		for (int i = 0; i < a.length; i++) {
			a[i] = a.length - i;
//			System.out.println("a[" + i + "] : " + (a.length - i));
			System.out.println("a[" + i + "] : " + a[i]);
		}
		// 문제2
		int cnt = 0;
		int[] b = { 3, 4, 7, 5, 1, 9, 4 };
		for (int i = 0; i < b.length; i++) {
			if (b[i] % 2 == 1) {
//				System.out.println("b[" + i +"] : " + b[i]);
				cnt++;
			} else {
//				System.out.println();
			}
		}
		System.out.println(cnt + "개");

		// 문제3
		cnt = 0;
//		int [] b = {3,4,7,5,1,9,4};
		for (int i = 0; i < b.length; i++) {
			if (b[i] > 4) {
//				System.out.println("b[" + i +"] : " + b[i]);
				cnt++;
			} else {
//				System.out.println();
			}
		}
		System.out.println(cnt + "개");

		// 문제4
		// 여기서 최대값을 출력하시오
//		int [] b = {3,4,7,5,1,9,4}; 여기서
//		max = Integer.MIN_VALUE; int에 들어갈 수 있는 가장 작은 수 
//		max = Integer.MAX_VALUE; int에 들어갈 수 있는 가장 큰 수
		int max = b[0];
		for (int i = 0; i < b.length; i++) {
			if (b[i] > max) {
				max = b[i];
			}
		}
		System.out.println("최대값 : " + max);
		// 문제5
		// 여기서 두번째 큰 수 구하기
//		int max = b[0];
//		for(int i=0; i<b.length; i++) {
//			if(b[i] > max) {
//				max = b[i];
//			}
//		}    문제4에서 이미 max에 최대값을 구했음
		int second = b[0];
		for (int i = 0; i < b.length; i++) {
			if (max > b[i]) {
				if (b[i] > second) {
					second = b[i];
				}
			}
		}
		System.out.println("두번째 큰 수 : " + second);

		// 문제6
		// 오른쪽으로 한칸 밀기(왼쪽은 0으로 채우기)
		// 예시 : {3,4,7,5,1,9,4} -> {0,3,4,7,5,1,9,4}
//		int [] b = {3,4,7,5,1,9,4};
		int[] b0 = new int[b.length + 1];
		System.out.println(b0.length);
		for (int i = 0; i < b.length; i++) {
			b0[i + 1] = b[i];
			System.out.println("b0[" + i + "] : " + b0[i]);
		}
		System.out.println("b0[" + b.length + "] : " + b0[b.length]);

		// 문제7
//		오른쪽이 이동 하는데 맨 끝에 값을 맨 처음으로 보내기
//		{3,4,7,5,1,9,4} -> {4,3,4,7,5,1,9}
		int[] b1 = new int[b.length + 1];
		for (int i = 0; i < b.length; i++) {
			b1[i + 1] = b[i];
//			System.out.println("b1[" + i + "] : " + b1[i]);
			b[i] = b1[i];
//			System.out.println("b[" + i + "] : " + b[i]);
		}
		b[0] = b1[b.length];
		for (int i = 0; i < b.length; i++) {
			System.out.println("b["+ i + "] : " + b[i]);
			
		}
		// 문제8
//		임시비밀번호 8자리 생성
//	    + 8-1 : 숫자만
//	    + 8-2 : 소문자만
//	    + 8-3 : 숫자2개 이상, 대/소문자 각 1개 이상
		// 8-1
		int[] pass = new int[10];
		for (int i = 0; i < pass.length; i++) {
			pass[i] = i;

		}
		System.out.print("비밀번호 : ");
		for (int i = 1; i <= 8; i++) {
			System.out.print(pass[(int) (Math.random() * 10)]);
		}
		System.out.println();
		// 8-2
//		소문자만
		int[] al = new int[26];
		for (int j = 0; j < al.length; j++) {
			al[j] = 'a' + j; // 'a'는 아스키코드에서 a이며 10진수로 97이다.
//			System.out.println(alphabet[j]);
		}

		System.out.print("비밀번호 : ");
		for (int i = 1; i <= 8; i++) {
			int randomIndex = (int) (Math.random() * 26);
			System.out.print((char) al[randomIndex]);
		}
		System.out.println();
//		// 8-3
//		//숫자2개 이상, 대/소문자 각 1개 이상
//		//숫자
//		int[] pass = new int[10];
//		for (int i = 0; i < pass.length; i++) {
//			pass[i] = i;
//		//소문자
//		int[] al = new int[26];
//		for (int j = 0; j < al.length; j++) {
//			al[j] = 'a' + j;
//		// 대문자
		int[] A = new int[26];
		for (int i = 0; i < A.length; i++) {
			A[i] = 'A' + i;
		}
		System.out.print("비밀번호 : ");
		int k = 0;
		int h = 0;
		int t = 0;
		for (int i = 1; i <= 8; i++) {
			int random = (int) (Math.random() * 3);
			int randomIndex = (int) (Math.random() * 26);
			if (i <= 6) {
				if (k < 2) {
					System.out.print(pass[(int) (Math.random() * 10)]);
					k++;
					i++;
				} else if (h < 1) {
					System.out.print((char) al[randomIndex]);
					h++;
					i++;
				} else if (t < 1) {
					System.out.print((char) A[randomIndex]);
					t++;
					i++;
				}
			}
			if (random == 0) {
				System.out.print(pass[i]);
				k++;
			} else if (random == 1) {
				System.out.print((char) al[randomIndex]);
				h++;
			} else if (random == 2) {
				System.out.print((char) A[randomIndex]);
				t++;
			}

		}
		
//		문제9
//		자리가 10개 있는 소극장의 예약 시스템
//		자리 번호는 1~10번까지 번호의 자리가 있습니다.
//		메뉴 : "1.예약 2.모든 좌석 현황 3.잔여좌석 0.종료"
//		만약1 : 예약이 가능하다면 "n번 자리 예약 했습니다"
//		만약2 : 예약이 불가능하다면 "이미 예약 되어 있습니다"
		
		boolean run = true;
		int [] num = new int [10];
		while (run) { // run 플래그가 true인 동안 루프 반복
        	System.out.println("\n\n-------------------------------------");
        	System.out.println("메뉴를 고르세요");
            System.out.println("1.예약, 2.모든 좌석 현황, 3.잔여좌석, 0.종료");
            System.out.println("-------------------------------------");
			Scanner scan  = new Scanner(System.in);
			int menu = scan.nextInt();
			if(menu == 1) {
				System.out.println("몇번 자리 예약하시겠습니까?");
				int seat = scan.nextInt();
				if(num[seat-1] == 0) {
					System.out.println(seat + "번 자리 예약했습니다.");
					num[seat-1] = 1;
				}	
				else {
					System.out.println("이미 예약 되어 있습니다.");
				}
			}
			if(menu == 2) {
				for(int i = 1; i <= 10; i++) {
					System.out.print(" | " + i);
					
				} System.out.print(" | ");
			}
			int count = 0;
			if(menu == 3) {
				for(int i=1; i<=10; i++) {
					
					if(num[i-1] == 0) {
						System.out.print(i + "좌석");
						System.out.print(" ");
						count++;
					}
				}
				System.out.println();
				System.out.println("남은좌석 : " + count + "좌석");
			}
			if(menu == 0) {
				System.out.println("종료");
				break;
			} scan.close();
		}	
		
		System.out.println();
		
//		boolean run = true;
//		int [] num = new int [10];
//		while(run) { // run 플래그가 true인 동안 루프 반복
//        	System.out.println("\n\n-------------------------------------");
//        	System.out.println("메뉴를 고르세요");
//            System.out.println("1.예약, 2.모든 좌석 현황, 3.잔여좌석, 0.종료");
//            System.out.println("-------------------------------------");
//			Scanner scan  = new Scanner(System.in);
//			int menu = scan.nextInt();
//			if(menu == 1) {
//				System.out.println("몇번 자리 예약하시겠습니까?");
//				int seat = scan.nextInt();
//				if(num[seat-1] == 0) {
//					System.out.println(seat + "번 자리 예약했습니다.");
//					num[seat-1] = 1;
//				}	
//				else {
//					System.out.println("이미 예약 되어 있습니다.");
//				}
//			}
//			if(menu == 2) {
//				for(int i = 1; i <= 10; i++) {
//					System.out.print(" | " + i);
//					
//				} System.out.print(" | ");
//			}
//			int count = 0;
//			if(menu == 3) {
//				for(int i=1; i<=10; i++) {
//					
//					if(num[i-1] == 0) {
//						System.out.print(i + "좌석");
//						System.out.print(" ");
//						count++;
//					}
//				}
//				System.out.println();
//				System.out.println("남은좌석 : " + count + "좌석");
//			}
//			if(menu == 0) {
//				System.out.println("종료");
//				break;
//			} 
//		} 	
		System.out.println();
		System.out.print("| ");

		//문제10
//		로또 6개 생성. 단, 중복 없이 
		int [] lo = new int [45];
		for(int i = 0; i < lo.length; i++) {
			lo[i] = i + 1;
		} 
		
		Random rand = new Random();//랜덤으로 숫자만들기
		int number = lo.length;
		
		for(int i = 1; i <= 6; i++) { // 6번 반복
			int lotto = rand.nextInt(number); //lotto는 랜덤으로 lo의 길이만큼에서 숫자 만들기
			System.out.print(lo[lotto] + " | ");
			

			lo[lotto] = lo[number - 1];// 만약 44가 나왔다면 lo[44]에 lo[45-1]을 넣음 
			number--; //위에서 44를 넣었으니 number--로 lo[44]를 없앤다.
		}

		
	}

}
