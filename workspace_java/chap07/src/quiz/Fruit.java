package quiz;

public class Fruit {
	// 필드
	String [] name = {"1.바나나", "2.복숭아", "3.사과", "4.수박"}; // 과일 이름
	int [] price = {4000, 2000, 1500, 15000} ; // 과일 가격
	
	// 생성자

	// 메소드
		/** 과일명이 들어가면 가격을 알려줘
		 * 메소드명 : pay
		 * 전달인자 : String name
		 * @return int price
		 */
	int pay(String n) {	
		for(int i=0; i<name.length; i++) {
			if (name[i].equals(n)) { 
                return price[i];
			}
		} return 0; 	
	}
	
	void all() { // 주문판
		for(int i=0; i< name.length; i++) {
	            System.out.println((i + 1) + ". " + name[i] + " : " + price[i] + "원");
	        }
		} 
	}
	
	
	
