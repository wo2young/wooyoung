package sec01.exam01;

import java.util.Scanner;

public class BreakExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int count = 1;
		while(true) {
			int num = (int) (Math.random()*6) +1;
			System.out.println("주사위 : " + num);
			if(num == 6) {
				break;
			}
				count++;
		}	
		System.out.println("게임 종료 : 총 : " + count);
		
		for(int i = 1 ; i < 10; i++) {
			System.out.println(i);
			if(i >= 5) { // 게임을 만들 때 핑이 튀거나 하면 넘어갈 수 있으니 ==보다는 >=, <=를 사용하는데 좋다.
				break;
			}
		
		}
		
		for(int i = 0; i<5;i++) {
			System.out.println("i: " + i);
			for(int j=0;j<20; j++) {
				System.out.println("j: " + j);
				if(j >=2) {
					System.out.println("break");
							break;
				}
			}
		}
		
		// 한명이라도 6이 나오면 전체 게임 종료
		boolean isStop = false;
		// 총 5명이 순차적으로 게임 시작
		for(int i =1; i<=5;i++) {
			//한명 당 주사위 3번씩 던지기
			for(int j =1; j<=3; j++) {
				int num2 = (int) (Math.random()*6) +1;
				System.out.println("주사위 : " + num2);
				
				// 만약 6이 나오면 전체 게임 종료
				if(num2 == 6) {
					isStop = true;
					break;
				}
				
			}	
			if(isStop) {
				break;
			} System.out.println(i + "번째");
		} 
		
		for(int i=1; i<=10; i++) {
			if(i % 2 == 0) { // 짝수만 출력해라
				System.out.println(i);
			}
		}
		// 위에 코드를 continue를 사용한 코드
		for(int i=1; i<=10; i++) {
			if(i % 2 != 0) {
				continue;	// 홀수일때는 건너뛰어라 
			}
			System.out.println(i);
		}
		
		//문제 1
		// 특정 수가 오기 전까지 반복 수를 입력하세요
		int m = (int) (Math.random()* 10);
		while(m <= 10) {
			m--;
			if(m < 0) {
				System.out.println("음수");
				break;
			}else {
				System.out.println("양수");
			}	
		}	
		//문제 2
		//월을 입력하면 계절이 나오고 0을 입력하면 종료
		int i = 12; // 월
		for(; i <= 12; i--) {
			if (i == 0) {
				break;
			}
			else if(i <= 3) {
				System.out.println("겨울");
//				break;
		} 	else if(i <= 5) {
				System.out.println("봄");
//				break;
		}
			else if(i <= 8) {
				System.out.println("여름");
//				break;
		}
			else if(i <= 11) {
				System.out.println("가을");
//				break;
		} 	else {
				System.out.println("겨울");
//				break;
		} 
	} 
		System.out.println("종료");
		//////////////////////////////////while//////////////////////////////////////
		int i1 = 1; //월
		while(i1 <= 12) {
			if (i1 == 0) {
				break;
				
			}
			else if(i1 <= 3) {
				System.out.println("겨울");
				i1--;
//				break;
			} 
			else if(i1 <= 5) {
				System.out.println("봄");
				i1--;
//				break;
				
			}
			else if(i1 <= 8) {
				System.out.println("여름");
				i1--;
//				break;
			}
			else if(i1 <= 11) {
				System.out.println("가을");
				i1--;
//				break;
			} 
			else {
				System.out.println("겨울");
				i1--;
//				break;
		} 	
			
		}System.out.println("종료");
		// 문제 3
		// 가위바위보게임 0이 나올 때 까지 반복
		
//		System.out.println("메뉴를 고르세요");
//		System.out.println("1:커피, 2:차, 0:종료");
		System.out.println("어떻걸 선택하시겠어요");
		System.out.println("1:가위, 2:바위, 3:보, 0:종료");
		
//		String scissors = "1";
//		String rock = "2";
//		String paper = "3";

		Scanner scan = new Scanner(System.in);
		int op1 = (int) (Math.random() * 3 + 1); // 상대
		int op2 = scan.nextInt(); // 나
			if(op2 == 1) {
				System.out.println("가위");
			} else if(op2 == 2) {
				System.out.println("바위");
			} else if(op2 == 3) {
				System.out.println("보");
			} else if(op2 == 0) {
				System.out.println("종료");
			} else {
				System.out.println("다시 선택해주세요");
			} System.out.println(op1); // 상대 고른 거 
			System.out.println("1:가위, 2:바위, 3:보");
			System.out.println("-------------------------------------------------------");
			if(op1 == op2) {
				System.out.println("비겼습니다.");
			}
			if(op1 == 1 && op2 == 2) { // 1:가위, 2:바위, 3:보, 0:종료
				System.out.println("이겼습니다.");
			} 
			else if(op1 == 1 && op2 == 3) {
				  System.out.println("졌습니다.");
			}
			if (op1 == 2 && op2 == 3){
				System.out.println("이겼습니다.");
			}
			else if(op1 == 2 && op2 == 1) {
				System.out.println("졌습니다.");
			  } 
			if(op1 == 3 && op2 == 1) {
				  System.out.println("이겼습니다."); // 1:가위, 2:바위, 3:보, 0:종료
			  } 
			else if (op1 == 3 && op2 == 2) {
				System.out.println("졌습니다.");
			} scan.close();
	
		// 문제 4 
//			아래와 같이 나오며 예금할떄 음수 불가, 출금할 때 음수 불가, 잔고보다 큰 금액 불가 
			// 결과는 1.예금 | 2.출금 | 3.잔고 | 4.종료
			//		----------------------------
			//      선택> 1      선택> 2      선택>3     선택>4
			//      예금액>10000  출금액>2000  출금액>8000 프로그램 종료
		boolean run = true; //run이라는 변수는 항상 true이다.
		int balance = 0; // 잔고를 0으로 초기화
		Scanner scanner = new Scanner(System.in);
		
		
		while (run) {
		System.out.println("-------------------------------------");
		System.out.println("1.예금 | 2.출금 | 3.잔고 | 4.종료");
		System.out.println("-------------------------------------");
		System.out.print("선택> "); // 반복할 문장들
		
			int bank = scanner.nextInt();
			if(bank == 1) {
				System.out.print("예금액> ");
				int plus = scanner.nextInt(); // 
				if(plus < 0) {
					System.out.println("음수는 들어갈 수 없습니다. ");
				}
				else {
					System.out.println("예금액> " + plus);
					balance += plus;
				}
			} else if(bank == 2) {
				System.out.print("출금액> ");
				int minus = scanner.nextInt();
				if(minus < 1) {
					System.out.println("0과 음수는 들어갈 수 없습니다. ");
				}
				else {
					if(balance < minus) {
					System.out.print("잔고가 부족합니다. ");
					System.out.println("현재 잔고: " + balance);
					} else {
						System.out.println("출금액> " + minus );
						balance -= minus;
						System.out.println("남은 잔고: " + balance);
					}
				}
			} else if(bank == 3) {
			System.out.println("잔고:" + balance);
			} else if(bank == 4) {
				break;
			} else {
				System.out.println("메뉴 확인 후 다시 선택해주세요");
			}
		
		}
		
		System.out.println("프로그램 종료");
		scanner.close();
		
		
	}

}
