package sec01.exam03.override;

public class ComputerExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Calc calc = new Calc();
		System.out.println(calc.areaCircle(3));
	
		Computer com = new Computer();
		System.out.println(com.areaCircle(3));
		
		System.out.println(com.plus(10, 20));
	}
	
	public int plus (int x, int y) {
		System.out.println("Calc의 plus 실행");
		return x+y;
	}
		
		
}
