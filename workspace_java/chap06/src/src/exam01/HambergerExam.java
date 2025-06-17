package src.exam01;

import java.util.Scanner;

public class HambergerExam {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		        Scanner scanner = new Scanner(System.in);

		        while (true) {
		            System.out.println("메뉴를 선택하세요 (햄버거, 피자, 치킨) 또는 '종료'를 입력하세요:");
		            String menu = scanner.nextLine();

		            if (menu.equals("종료")) {
		                System.out.println("프로그램을 종료합니다.");
		                break; // 반복문 종료
		            }

		            int price = 0;
		            switch (menu) {
		                case "햄버거":
		                    price = 5000;
		                    break;
		                case "피자":
		                    price = 8000;
		                    break;
		                case "치킨":
		                    price = 10000;
		                    break;
		                default:
		                    System.out.println("잘못된 메뉴입니다. 다시 선택하세요.");
		                    continue; // 다음 반복으로 넘어감
		            }

		            // 완제품 최종 가격 출력
		            System.out.println(menu + "의 최종 가격은 " + price + "원입니다.");
		        }

		    }


	}

