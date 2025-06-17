package quiz;

public class VendingMachine {
	
	// 필드
	String design; // 디자인
	int money; // 돈
	int penny; // 잔돈
	
	Drink [][] vend = new Drink[3][3];
	
	// 생성자
	// 생성자 
	VendingMachine(){
		penny = 5000;
		money = 0;
		
	}
	
	// 매서드	
	void init() {
		vend[0][0] = new Drink();
		vend[0][0].drink = "포카리";
		vend[0][0].price = 1500;
		vend[0][0].stock = 8;
		
		vend[0][1] = new Drink();
		vend[0][1].drink = "몬스터";
		vend[0][1].price = 2200;
		vend[0][1].stock = 5;
		
		vend[0][2] = new Drink();
		vend[0][2].drink = "코카콜라";
		vend[0][2].price = 1500;
		vend[0][2].stock = 6;
		
		vend[1][0] = new Drink();
		vend[1][0].drink = "비락식혜";
		vend[1][0].price = 1200;
		vend[1][0].stock = 9;
		
		vend[1][1] = new Drink();
		vend[1][1].drink = "게토레이";
		vend[1][1].price = 2000;
		vend[1][1].stock = 8;
		
		vend[1][2] = new Drink();
		vend[1][2].drink = "2%";
		vend[1][2].price = 1600;
		vend[1][2].stock = 2;
		
		vend[2][0] = new Drink();
		vend[2][0].drink = "스프라이트";
		vend[2][0].price = 1400;
		vend[2][0].stock = 3;
		
		vend[2][1] = new Drink();
		vend[2][1].drink = "환타";
		vend[2][1].price = 1500;
		vend[2][1].stock = 6;
		
		vend[2][2] = new Drink();
		vend[2][2].drink = "레쓰비";
		vend[2][2].price = 800;
		vend[2][2].stock = 11;
		
	}
	
	// 돈 넣기
	void setMoney(int m) {
		if(m >= 100) {
			money = money + m;
			penny = penny + money;
			System.out.println(money + "원이 들어왔습니다.");
		} 
	}
	
	// 음료 선택
	void selectDrink(String drinkName) {
	    boolean found = false;

	    for (int i = 0; i < vend.length; i++) {
	        for (int j = 0; j < vend[i].length; j++) {
	            if (vend[i][j].drink.equals(drinkName)) {
	                found = true;
	                if (money >= vend[i][j].price) {
	                    System.out.println(vend[i][j]);
	                    System.out.println("음료가 나왔습니다. 가져가주세요.");
	                    money -= vend[i][j].price;

	                    // 잔돈 자동 반환
	                    if (money > 0) {
	                        System.out.println("잔돈 : " + money + "원");
	                        System.out.println("잔돈을 가져가 주세요.");
	                        penny -= money;
	                        money = 0;
	                    }
	                } else {
	                    System.out.println("투입하신 금액이 부족합니다.");
	                }
	                return; // 음료 찾았으면 종료
	            }
	        }
	    }

	    if (!found) {
	        System.out.println("해당 음료가 없습니다. 다시 입력해주세요.");
	    }
	}


	
	// 잔돈 반환
//	int getPenny() {
//		if(penny > money) {
//			System.out.println("잔돈 : " + (penny - money));
//			System.out.print("잔돈을 가져가 주세요" + "\n");
//			money =0;
//			return penny;
//		} 
//	return 0;
//	}
			
//	 품목보기
	void print() {
		for(int i=0; i<vend.length; i++) {
			for(int j=0; j<vend[0].length; j++) {
				System.out.print(vend[i][j] + " ");
			}
		}
	}
	
	
			
		
		
		
	

}
