package quiz;

public class Drink {
	
	// 필드
	String drink; // 음료 이름
	int price; // 가격
	int stock; // 재고

	@Override
	public String toString() {
	    return drink + " (" + price + "원)";
	}
	
	
	
////	boolean run;
//	
//	
//	// 생성자 초기화 값 넣기
//	Vending(){
//		penny = 5000;
//		money = 0;
//		drink = null;
////		boolean run = false;
//	};
//	
//	
//	// 매서드
//	/**
//	 * 돈 넣기
//	 * 메서드명 : setMoney
//	 * 전달인자 : int money
//	 * 리턴타입 : void
//	 */
//	void setMoney(int m) {
//		if(m >= 100) {
//			money = money + m;
//			System.out.println(money + "원이 들어왔습니다.");
//		} else {
//			System.out.println("금액이 너무 적습니다.");
//		}
//	}
//	/**
//	 * 음료 선택
//	 * 메서드명 : selectDrink
//	 * 전달인자 : String drink
//	 * 리턴타입 : void
//	 */
//	void selectDrink(String d) {
//		if()
//		drink = drink + d;
//		System.out.println(drink);
//		System.out.println("음료가 나왔습니다. 가져가주세요");
//	}
//
//	/**
//	 * 잔돈 반환
//	 * 메서드명 : getPenny
//	 * 전달인자 : int penny
//	 * 리턴타입 : int
//	 */
//	int getPenny(int p) {
//		System.out.println("잔돈 : " + (penny - money));
//		System.out.print("잔돈을 가져가 주세요" + "\n");
//		return penny;
//	}
//	
////	void Vending() {
////		if(money > 0) {
////			run = true;
////			while(run) {
////			System.out.println();
////			}
////		}
//		
//	}
	
	
}
