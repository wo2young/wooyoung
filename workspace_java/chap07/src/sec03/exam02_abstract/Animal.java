package sec03.exam02_abstract;

public abstract class Animal {
	
	String kind;
	
	void breathe() {
		System.out.println("숨쉬어");
	}
	
	// 추상 메소드
	// abstract 키워드기 븥은 메소드
	// 실행 블럭{}이 있으면 안됨
	// 나는 구현하지 않고 자식에게 구현을 떠넘김
	abstract void sound();
	
	 void love() {
		 
	 }
}
