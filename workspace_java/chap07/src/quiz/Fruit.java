package quiz;

import java.util.Scanner;

public class Fruit {
	// 필드
	String [] name = {"바나나", "복숭아", "사과", "수박"}; // 과일 이름
	int [] price = {4000, 2000, 1500, 15000} ; // 과일 가격
	int cnt;
	
	Scanner scan = new Scanner(System.in);

	
	// 생성자
	Fruit(){
		this.scan = scan;
		cnt = 0;
	}
	// 메서드
		/** 과일명이 들어가면 가격을 알려줘
		 * 메소드명 : pay
		 * 전달인자 : String name
		 * @return int price
		 */
	int pay(String n) {	
		for(int i=0; i<name.length; i++) {
			if (name[i].equals(n)) { 
				return price[i] * cnt;
			}
		} return 0; 	
	}
	
	
	
	
	
	void all() { // 메뉴판
		for(int i=0; i< name.length; i++) {
	            System.out.println((i + 1) + ". " + name[i] + " : " + price[i] + "원");
	        }
		}

//	void menu1() { // 주문 받는 메서드
//					
//		int menu = scan.nextInt();
//		if (menu >= 1 && menu <= name.length) {
//			String a = name[menu-1]; // 지금까지 고른 물건 저장
//			System.out.println(a);
//			System.out.print("몇 개를 주문하시겠습니까? ");
//	        int c = scan.nextInt();
//	        count(c); // 개수 저장
//		}
//	}
	void menu1() { // 주문 받는 메서드
	    System.out.print("어떤 과일을 주문하시겠습니까? (1~" + name.length + "): ");
	    int menu = scan.nextInt();
	    
	    if (menu >= 1 && menu <= name.length) {
	        String select = name[menu - 1];  // 과일 이름
	        int fruitPrice = price[menu - 1];       // 과일 가격
	        System.out.println("선택한 과일은 " + select + "입니다. 가격은 " + fruitPrice + "원입니다.");
	        
	        System.out.print("몇 개를 주문하시겠습니까? ");
	        int count = scan.nextInt();
	        
	        count(count);  // 개수 저장
	        System.out.println("주문한 개수: " + count + "개");
	    } else {
	        System.out.println("잘못된 번호입니다. 다시 시도하세요.");
	    }
	}
	
	void count(int c) { // 개수 메소드
		cnt = c;
	}
	
	void menu2() { // 결제완료 메서드
		System.out.println("결제 완료 되었습니다.");
	}
	void menu3() { // 종료 메서드
		System.out.println("종료합니다.");
	}
	
}
	
	
	
