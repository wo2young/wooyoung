package src.exam01.field;

public class CarExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 객체(class) 생성
		Car mycar = new Car();
		
		// 필드값 읽기
		System.out.println("제작회사 : " + mycar.company);
		mycar.company = "BMW";
		System.out.println("제작회사 : " + mycar.company);
		System.out.println("모델명 : " + mycar.model);
		System.out.println("색깔 : " + mycar.color);
		System.out.println("현재속도 : " + mycar.speed);
		
		mycar.speed = 50;
		System.out.println("현재속도 : " + mycar.speed);
		
		Car mycar2 = new Car();
		System.out.println("제작회사 : " + mycar2.company);
		
		
	}

}
