package src.exam02.constructor;

public class Car {
	
	
	String model = "아반떼";
	String color;
	int maxSpeed;
	
	// 생성자는 class이름과 동일한 이름을 사용하며 return이 없다.
	Car() {
		System.out.println("Car");
	}
	
//	String model = "아반떼";
//	
//	String color;
//	
	Car(String c){
//		color = c;
//		model = "그랜저";
//		maxSpeed = 250;
		this("그랜저", c, 250); // this는 무조선 첫줄에 들어가야 한다.
	}
	Car(String m, String c, int ms) {
		model = m;
		color = c;
		maxSpeed = ms;
	}
	
	void test() {
		
	}
	
	void setColor() { //color을 필드에서 생성했지만 지역변수로 생성 가능
		String color; //좀 더 구체적인걸 사용
		color = "빨강";
//		this.color = color; //필드에서 color를 가져온다.
	}
}
	
