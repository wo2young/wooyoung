package src.exam01;

public class StudentExam {
	
	public static void main(String [] args) {
		
		Student s1 = new Student();
		// =을 기준으로 오른쪽이 먼저 실행되기에 Student가 힙영역에 먼저 생성된다.
		Student s2 = new Student();
		System.out.println(s1 == s2); // false
		
		Student s3 = null;
		s3 = s1;
		System.out.println(s3 == s1); // true
		
		s1 = null;
		s3 = null;
		System.out.println(s1 == s3); // true
		
		System.out.println(s2); // 스택영역에 주소같은 거 내가원하는게 저 안에 있다.
	}
}
