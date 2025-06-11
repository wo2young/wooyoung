package src.exam04;

public class Calc {

	// 이름이 같지만 전달인자에 타입이 다르기 때문에 오버로딩 된다.
	// 리턴타입은 상관없고 메소드 이름이 같을때 전달인자가 다르면 오버로딩.
	int plus(int x, int y) {
		int result = x + y;
		System.out.println("int 실행");
		return result;
	}
	double plus(double x, double y) {
		double result = x + y;
		System.out.println("double 실행");
		return result;
	}
}
