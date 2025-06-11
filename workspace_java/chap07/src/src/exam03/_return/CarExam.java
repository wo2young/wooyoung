package src.exam03._return;

public class CarExam {
	
	public static void main(String[] args) {
		
		Car mycar = new Car();
		Car2 c2 = new Car2();
		
		c2.setGas(3);
		c2.run();
		mycar.setGas(5);
		
		boolean gasState = mycar.isLeftGas();
		if(gasState) {
			System.out.println("출발");
			mycar.run();
		}
		
		if(mycar.isLeftGas()) {
			System.out.println("gas를 주입할 필요가 없습니다.");
		}
		else {
			System.out.println("gas를 주입하십시오.");
		}
		
	}

}
