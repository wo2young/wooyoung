package src.exam05;

public class CalcExam {
	int a = 10;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println(a);
		
		System.out.println("Calc.pi : " + Calc.pi);
		
		Calc c1 = new Calc();
		c1.color = "블루";
		c1.pi = 3.141592; // c1.pi를 바꿨는데 c2.pi도 바뀜
		// static이기 떄문에 heap영역이 아닌 method영역에 저장됨
		// 그래서 어디서든 pi를 바꾸면 다 바뀐다.
		
		System.out.println("c1.pi : " + c1.pi);
		System.out.println("c1.color : " + c1.color);
		
		Calc c2 = new Calc();
		c2.color = "그린";
		System.out.println("c2.pi : " + c2.pi);
		System.out.println("c2.color : " + c2.color);
	}

}
