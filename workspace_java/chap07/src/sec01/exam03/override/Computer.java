package sec01.exam03.override;

public class Computer extends Calc {
	@Override // @붙은 친구들이 annotation(어노테이션)이라고 한다.
			  // 부모에 해당 메소드가 똑같이 선언돼 있는지 검사해줌
	double areaCircle(double r) {
		System.out.println("Computer 객체의 areaCircle() 실행");
		return Math.PI * r * r;
	}
	
	@Override
	public int plus(int x, int y) {
		int result = super.plus(x, y);
		
		System.out.println("Computer의 plus실행");
		System.out.println("정답은 바로 "+ result +"입니다");
		return result;
	}
	
	
}
