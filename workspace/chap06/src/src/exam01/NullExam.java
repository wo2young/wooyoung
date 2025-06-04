package src.exam01;

public class NullExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = "dd";
		System.out.println("총 문자수: " + str.length()); // null이라서 오류
		
		int a = 10;
		int b = a;
		System.out.println(a +", " + b);
		
		b = 12;
		System.out.println(a +", " + b);
		
		String c = "김우영";
		// =을 기준으로 오른쪽이 먼저 실행된다.
		// "김우영"을 힙heap 영역의 비어있는 번지에 할당
		// 스택stack 영역의 변수 c에 방금 그 번지를 저장
		System.out.println("c: " + c);
		System.out.println(a == b);
		// == 또는 !=는 무조건 stack의 값을 비교한다.
		
		String d = new String("김우영");
		System.out.println(c == d);
		System.out.println(c.equals(d));
		// 참조 변수(String 같이 대문자로 표현)를 비교할때는 equals를 사용해야 정확하다.
		
		String e = d;
		System.out.println(e == d); // 같음
		// =은 d에 stack값을 e에 대입해라
		
		String f0 = "김우영";
		System.out.println("c == f0 : " + (c == f0));
		String f = "김" + "우영";
		System.out.println("c == f : " + (c == f));
		
		String f1 = new String("김우영");
		String f2= new String("김" + "우영");
		// 자바는 문자열 리터럴이 동일하다면 String객체를 공유한다.
		// 그래서 직접 String객체를 생성할 수 있는데 new룰 사용하면 된다.
		System.out.println("c == f1 : " + (c == f1));
		System.out.println("c == f2 : " + (c == f2));
		
//		a = null; 기본 타입에는 null이 들어갈 수 없다.
		// null : 참조하는 주소가 없는 상태
		String g = "휴먼";
		System.out.println("g == null : " + (g == null));
		System.out.println("g == null : " + (g != null));
		
		String h ="";
		System.out.println("h != null : " + (h != null));
		
		g = null;
		System.out.println("g == null : " + (g == null));
		System.out.println("g + \"abc\": "+ (g+"abc"));
		
//		System.out.println(h.equals(g)); // false
//		if(g != null) { // 방어 코딩 error를 막아준다.
//		System.out.println(g.equals(h)); // g가 null이 아니라면 값을 내준다.
//		}
		
		
		
		
		
		
		
		
		
		
	}

}
