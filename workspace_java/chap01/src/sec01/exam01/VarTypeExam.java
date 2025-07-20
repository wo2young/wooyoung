package sec01.exam01;

public class VarTypeExam {

	public static void main(String[] args) {
		// byte는 -128~127까지만 저장 가능 (1바이트 정수형)
		byte b1;
		b1 = 127;
		System.out.println("b1 : " + b1);
//		b1 = 128; // 오류 (byte 범위 초과)

		// 문자 하나 저장 (작은따옴표 사용)
		char c2 = 'F';
		System.out.println("c2 : " + c2);

		// 문자열은 큰따옴표 사용
		String s1 = "김우영";
		System.out.println("s1 : " + s1);

		// \"는 큰따옴표 출력용 이스케이프 문자
		String s2 = "김\"우영";
		System.out.println("s2 : " + s2);

		// \t는 탭 간격, \n은 줄바꿈
		String s3 = "김\t우영";
		System.out.println("s3 : " + s3);

		String s4 = "김\n우영";
		System.out.println("s4 : " + s4);

		// 문자열 + 숫자 → 문자열로 자동 변환
		s1 = s1 + 3; // "김우영3"
		System.out.println(s1);

		s1 = 3 + s1; // "3김우영3"
		System.out.println("s1 : " + s1);

		// int 최대값은 약 21억 (2,147,483,647)
		int i = 2100000000;

		// long은 뒤에 L/l 붙여야 컴파일 오류 안남
		long l2 = 2200000000l; // 소문자 l도 되지만 대문자 L 추천
		System.out.println(l2);
		l2 = 2200000000L;
		System.out.println(l2);

		// float 리터럴도 뒤에 f 붙여야 함
		float f1 = 3.14f;
		System.out.println("f1 : " + f1);

		double d1 = 3.14; // double은 기본형이라 f 안 붙여도 됨
		System.out.println("d1 : " + d1);

		float f2 = 0.123456789f; // float은 소수점 약 7자리까지 정밀도 있음
		System.out.println(f2); // → 0.12345679 (뒤는 잘림)

		double d2 = 0.12345678901234567890; // double은 약 16자리 정밀도
		System.out.println(d2);

		// boolean은 true/false만 저장 가능
		boolean stop = true;
		boolean state = false;
		if (stop) {
			System.out.println("중지합니다");
		} else {
			System.out.println("시작합니다");
		}

		// 나이, 전화번호 예시 (사용 안 하지만 문법 확인용)
		int age;
		age = 24;

		int phone = 3;

		boolean car = true;
		if (car) {
			System.out.println("차를 소유하고 있습니다");
		} else {
			System.out.println("차를 소유하고 있지 않습니다");
		}

		// 문자열 변수 선언
		String name = "김우영";

		// 실수 계산 예시
		double area = 3.3 * 5;

		// Q0: 두 변수 출력
		int a = 3;
		int b = 4;
		System.out.println(a + "과 " + b);
		System.out.println(b);

		// Q1-1: 비교 연산 결과 출력
		System.out.println(a + " > " + b + "결과는" + (a > b) + "입니다");

		// Q1-2: a값을 바꿔서 다시 비교
		a = 6;
		b = 5;
		System.out.println(a + " > " + b + "결과는" + (a > b) + "입니다");

		// Q2: 자리수 추출 (백, 십, 일)
		int c = 537;
		int c100 = c / 100; // 백의 자리: 5
		System.out.println(c100);

		int c10 = ((c - c100 * 100) / 10); // 십의 자리: 3
		System.out.println(c10);

		int c1 = ((c - c100 * 100) - c10 * 10); // 일의 자리: 7
		System.out.println(c1);

		System.out.println("백의자리 : " + c100);
		System.out.println("십의자리 : " + c10);
		System.out.println("일의자리 : " + c1);
	}
}
