package sec01.exam01;

import java.util.Scanner;

public class jemini {
		
		public static void main(String[] args) {
        
			char space = '.'; // 공백 문자
	        char mark = '*';  // 패턴 문자
	        int totalRows = 9; // 피라미드 높이 (줄 수)
	
	        // 각 줄 (0부터 totalRows-1까지)
	        for (int row = 0; row < totalRows; row++) {
	            // 1. 공백 출력: (전체 높이 - 현재 줄 번호 - 1) 만큼
	            // 예: row=0 -> 4칸, row=1 -> 3칸, ..., row=4 -> 0칸
	            for (int i = 0; i < (totalRows - row - 1); i++) {
	                System.out.print(space);
	            }
	
	            // 2. 마크 출력: (현재 줄 번호 * 2 + 1) 만큼
	            // 예: row=0 -> 1개, row=1 -> 3개, ..., row=4 -> 9개
	            for (int i = 0; i < (2 * row + 1); i++) {
	                System.out.print(mark);
	            }
	
	            // (옵션) 대칭을 위한 뒤쪽 공백. 보통 피라미드는 앞쪽 공백만 있어도 형태가 나옴.
	            // 필요하다면 위 공백 출력과 동일한 로직 사용 가능.
	             for (int i = 0; i < (totalRows - row - 1); i++) {
	                 System.out.print(space);
	             }
	
	            System.out.println(); // 줄 바꿈
	        }
//-------------------------------------------------------------------------------
//                        Scanner의 nextInt를 사용한 방법	               
	        Scanner scanner = new Scanner(System.in); // Scanner 객체 생성
	        int balance = 0; // 초기 잔고를 0으로 설정
	        boolean run = true; // while 루프를 계속 실행할지 결정하는 플래그
	
	        System.out.println("환영합니다! 스위프트 은행입니다.");
	
	        while (run) { // run 플래그가 true인 동안 루프 반복
	        	System.out.println("\n-------------------------------------");
	            System.out.println("1. 예금 | 2. 출금 | 3. 잔고확인 | 4. 종료");
	            System.out.println("-------------------------------------");
	            System.out.print("선택> ");
	
	            int selectNo = scanner.nextInt(); // 사용자로부터 메뉴 번호 입력받기
	
	            if (selectNo == 1) { // 1. 예금
	                System.out.print("예금액> ");
	                int depositAmount = scanner.nextInt();
	                if (depositAmount < 0) { // 음수 입력 방지
	                    System.out.println("예금액은 음수가 될 수 없습니다.");
	                } else {
	                    balance += depositAmount; // 잔고에 예금액 추가
	                    System.out.println(depositAmount + "원이 예금되었습니다.");
	                        }
	            } 	else if (selectNo == 2) { // 2. 출금
	            		System.out.print("출금액> ");
	            		int withdrawAmount = scanner.nextInt();
	            		if (withdrawAmount < 0) { // 음수 입력 방지
	            			System.out.println("출금액은 음수가 될 수 없습니다.");
	                  } else if (withdrawAmount > balance) { // 잔고보다 큰 금액 출금 방지
	                	  System.out.println("잔고가 부족합니다. 현재 잔고: " + balance + "원");
	                  } else {
	                	  balance -= withdrawAmount; // 잔고에서 출금액 차감
	                	  System.out.println(withdrawAmount + "원이 출금되었습니다.");
	                  }
	            } else if (selectNo == 3) { // 3. 잔고확인
	            	System.out.println("현재 잔고: " + balance + "원");
	            } else if (selectNo == 4) { // 4. 종료
	            	run = false; // run 플래그를 false로 변경하여 루프 종료
	            } else { // 잘못된 입력 처리
	            	System.out.println("잘못된 메뉴 번호입니다. 다시 입력해주세요.");
	            }
	        }
	
	                System.out.println("스위프트 은행을 이용해주셔서 감사합니다.");
	                scanner.close(); // Scanner 객체 닫기 (자원 누수 방지)
	                
//---------------------------------------------------------------------------------------------------
//                       Scanner의 nextLine을 사용한 방법
//                Scanner scanner = new Scanner(System.in);	             
//                int balance = 0;
//                boolean run = true;

                System.out.println("환영합니다! 스위프트 은행입니다.");

                while (run) {
                    System.out.println("\n-------------------------------------");
                    System.out.println("1. 예금 | 2. 출금 | 3. 잔고확인 | 4. 종료");
                    System.out.println("-------------------------------------");
                    System.out.print("선택> ");

                    String selectNoStr = scanner.nextLine(); // 사용자 선택을 문자열로 읽어옴
                    int selectNo = 0;

                    try {
                        selectNo = Integer.parseInt(selectNoStr); // 문자열을 정수로 변환
                    } catch (NumberFormatException e) {
                        // 숫자가 아닌 문자열을 입력했을 때 예외 처리
                        System.out.println("잘못된 입력입니다. 메뉴는 숫자로 입력해주세요.");
                        continue; // 다시 메뉴를 보여주기 위해 루프의 처음으로 돌아감
                    }

                    if (selectNo == 1) { // 1. 예금
                        System.out.print("예금액> ");
                        String depositAmountStr = scanner.nextLine(); // 예금액을 문자열로 읽어옴
                        try {
                            int depositAmount = Integer.parseInt(depositAmountStr);
                            if (depositAmount < 0) {
                                System.out.println("예금액은 음수가 될 수 없습니다.");
                            } else {
                                balance += depositAmount;
                                System.out.println(depositAmount + "원이 예금되었습니다.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("잘못된 입력입니다. 예금액은 숫자로 입력해주세요.");
                        }
                    } else if (selectNo == 2) { // 2. 출금
                        System.out.print("출금액> ");
                        String withdrawAmountStr = scanner.nextLine(); // 출금액을 문자열로 읽어옴
                        try {
                            int withdrawAmount = Integer.parseInt(withdrawAmountStr);
                            if (withdrawAmount < 0) {
                                System.out.println("출금액은 음수가 될 수 없습니다.");
                            } else if (withdrawAmount > balance) {
                                System.out.println("잔고가 부족합니다. 현재 잔고: " + balance + "원");
                            } else {
                                balance -= withdrawAmount;
                                System.out.println(withdrawAmount + "원이 출금되었습니다.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("잘못된 입력입니다. 출금액은 숫자로 입력해주세요.");
                        }
                    } else if (selectNo == 3) { // 3. 잔고확인
                        System.out.println("현재 잔고: " + balance + "원");
                    } else if (selectNo == 4) { // 4. 종료
                        run = false;
                    } else { // 잘못된 입력 처리
                        System.out.println("잘못된 메뉴 번호입니다. 다시 입력해주세요.");
                    }
                }

                System.out.println("스위프트 은행을 이용해주셔서 감사합니다.");
                scanner.close();
	
		
		
		
	}
}