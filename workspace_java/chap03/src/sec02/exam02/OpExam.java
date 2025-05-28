package sec02.exam02;

public class OpExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x = 10;
		int y = 10;
		int z;
		
		System.out.println("----------------------");
		x++;
		++x;
		System.out.println("x = " + x);
		
		System.out.println("----------------------");
		y--;
		--y;
		System.out.println("y = " + y);
		
		x = 10;
		
		z = ++x;
		System.out.println("z = " + z);
		System.out.println("x = " + x);
		System.out.println("----------------------");
		
		x = 10;
		y = 10;
		z = ++x + y++;
		System.out.println("z = " + z);
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("----------------------");
		
		x = 10;
		y = 10;
		z = x++ + y++;
		System.out.println("z = " + z); // ++가 먼저 나오면 먼저 피연산자에 1을 더하고 계산하고
		System.out.println("x = " + x); // ++가 뒤에 나오면 계산을 먼저하고 피연산자에 1을 더한다 
		System.out.println("y = " + y); // 그래서 x와 y가 11이지만 z는 20이다. 그냥 그런거다
		                                // ++가 뒤에 나오면 연산자를 넘어갈때 더해진다. 
		x = 1;
		z = x++ - --x * x++ - x--;
		//         
		System.out.printf("x: %d, z: %d", x, z);
		System.out.println();
		
		boolean a = false;
		a = !a;
		System.out.println(a);
		
		System.out.println(0.1 == 0.1f);
		//소수점을 비교할때는 같은 타입으로 변환해서 비교하자.

		int mn = 10000;
		int cff = 4500;
		
		System.out.println(mn / cff + "잔");
		System.out.println(mn % cff + "원");
		
		int hn = 8000;
		double sl = 0.15;
		System.out.println(hn - hn * sl);
		
		int score = 97;
		System.out.println((score >= 80 && score <= 90));
		
		double v1 = 1000;
		double v2 = 794;
		double v3 = (v1 / v2);
		v3 = (int)(v3 * 1000) / 1000.0; 
		System.out.println(v3);
		
		int s = 95;
		String grade = (s > 90) ? "A" : "B";
		System.out.println(grade);
		// String일때 ""를 사용하고, char일때는 ''를 사용한다.
		int s1 = 70;
		char grade1 = (s1 > 90) ? 'A' : 'B';
		System.out.println(grade1);
		
		
		
	}

}
