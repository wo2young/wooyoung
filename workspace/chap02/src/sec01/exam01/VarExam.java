package sec01.exam01;

public class VarExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 변수 선언
		int value = 5;
		// 변수 value는 5이므로 5 + 10 = 15
		int result = value + 10;

		System.out.println(result);

		int hour = 3;
		int minute = 5;
		System.out.println(hour + "시간" + minute + "분");
		System.out.println("value : " + value);

		value = value + 1;
		System.out.println("value : " + value);
//		한줄 이동: alt + 위아래 방향키
//		한줄 복사:ctrl + alt + 위아래 방향키
//		한줄 지우기: ctrl + d
//		주석 단축키: ctrl + /
//		자동정렬: ctrl + shift + f
		int x = 10;
		int y = x;
		System.out.println(x);
		{
			int z = 2;
			int t = 5;
			System.out.println("z : " + z);
		}
		int z = 23;
		System.out.println(z);

		int x1 = 26854;
		int x2 = 684684;
		x1 = x2;
		System.out.println("x1 :" + x1);

		x1 = 26854;
		x2 = 684684;
		x2 = x1;
		System.out.println("x2 :" + x2);
//////////////////////////////////////////////////////////
		x1 = 26854;
		x2 = 684684;
		int z1 = x1;
		int z2 = x2;
		x1 = z2;
		x2 = z1;
		System.out.println("x1 :" + x1);
		System.out.println("x2 :" + x2);
///////////////////////////////////////////////////////
		x1 = 26854;
		x2 = 684684;
		int z3 = x1;
		x1 = x2;
		x2 = z3;
		System.out.println("x1 :" + x1);
		System.out.println("x2 :" + x2);
		
		
		
		
	
	}

}
