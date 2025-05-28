package sec01.exam01;

public class VarTypeExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte b1;
		b1 = 127;
		System.out.println("b1 : " + b1);
//		b1 = 128;



		char c2 = 'F';
		System.out.println("c2 : " + c2);

		String s1 = "김우영";
		System.out.println("s1 : " + s1);
		
		String s2 = "김\"우영";
		System.out.println("s2 : " + s2);
		
		String s3 = "김\t우영";
		System.out.println("s3 : " + s3);
		
		String s4 = "김\n우영";
		System.out.println("s4 : " + s4);
		
//		s2 = "김우영" + 3;
//		System.out.println(s2);
		
		s1 = s1 + 3;
		System.out.println(s1);
		
		s1 = 3 + s1;
		System.out.println("s1 : " + s1);
		
		int i = 2100000000;
//		int i2 = 2200000000; int의 범위를 넘는 값이라 안된다.
		
//		long l1 = 2200000000;  뒤에 대 / 소문자 ㅣ을 안붙이면 int로 생각해 안된다.
		long l2 = 2200000000l; 
		System.out.println(l2);
		l2 = 2200000000L; 
		System.out.println(l2);
		
		float f1 = 3.14f;
		System.out.println("f1 : " + f1);
		double d1 = 3.14;
		System.out.println("d1 : " + d1);
		
		float f2 = 0.123456789f; //float는 소수점 7자리까지
		System.out.println(f2);
		double d2 = 0.12345678901234567890;//double는 소수점 16자리까지
		System.out.println(d2);
		
		boolean stop = true;
		boolean state = false;
		if(stop) {
			System.out.println("중지합니다");
		} else {
			System.out.println("시작합니다");
		}
		
		int age;
		age = 24;
		
		int phone = 3;
		
		boolean car = true;
		if(car) {
			System.out.println("차를 소유하고 있습니다");
		} else {
			System.out.println("차를 소유하고 있지 않습니다");
		}
		
		String name = "김우영";
		
		double area = 3.3 * 5;
		
//		Q0 두 변수 a, b에 각각 3,4를 넣고 출력 ㅣ
		
		int a = 3;
		int b = 4;
		System.out.println(a + "과 " + b);
		System.out.println(b);
//		Q1-1
		System.out.println(a + " > " + b + "결과는" + (a > b) + "입니다");
//		Q1-2
		a = 6;
		b = 5;
		System.out.println(a + " > " + b + "결과는" + (a > b) + "입니다");
//		Q2
		int c = 537;
		int c100 = c / 100;// 1
		System.out.println(c100);
		int c10 = ((c - c100 * 100) / 10);// 3
		System.out.println(c10);
		int c1 = ((c - c100 * 100) - c10 * 10);// 2
		System.out.println(c1);
		
		System.out.println("백의자리 : " + c100);
		System.out.println("십의자리 : " + c10);
		System.out.println("일의자리 : " + c1);
		
		
				
		 
		
		
		
	}

}
