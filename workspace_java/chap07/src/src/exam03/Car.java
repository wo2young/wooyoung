package src.exam03;

public class Car {
	
	// 필드
	int gas;
	
	// 생성자
	
	//메소드
	void setGas(int g) {
		gas = g;
		
	}
	/** 가스가 0이면 false를 가스가 0이 아니면 true 
	 * 
	 *  메소드명 : isLeftGas
	 *  전달인자 : gas
	 * 	리턴타입 : boolean
	 * @return boolean
	 */
	boolean isLeftGas() {
		if(gas == 0) {
			System.out.println("gas가 없습니다.");
			return false;
		}
		System.out.println("gas가 있습니다.");
		return true;
	}
	
	void run() {
		while(true) {
			if(gas > 0) {
				System.out.println("달려.(gas 잔량 : " + gas + ")");
				gas -= 1;
			} else {
				System.out.println("멈춰.(gas 잔량 : " +  gas + ")");
				return;
			}
		}
	}
}
