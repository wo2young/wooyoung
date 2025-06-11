package src.exam01.field;

public class Car {
	// class 아래에 필드
	
	// 필드 선언과 동시에 초기화
	String company = "KB모빌리티";
	String model = "티볼리";
	String color = "흰색";
	int maxspeed = 220;
	
	// 필드 선언
	// 초기화 하지 않은 경우
	// 생성할 때 0, false, null로 초기화 된다. 마치 배열처럼
	int speed;
	// 필드 영역에는 실행할 수 없다. 
//	speed = 30; 동시에 초기화 하는게 아니면 안들어간다.
//public static void main (String [] args) {
//	메소드 영역
//}
}
