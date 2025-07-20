package sec01.exam01;

public class TypeCastingExam {

	public static void main(String[] args) {
		// 정수 int -> byte 강제 형변환
		int intValue = 10;
		byte byteValue = (byte) intValue;

		// int 값이 byte 범위를 초과할 경우 오버플로우 발생
		intValue = 200;
		byteValue = (byte) intValue;
		System.out.println(byteValue); // -56 출력 (byte 범위는 -128 ~ 127)

		// 실수 -> 정수 (소수점 버림: truncate)
		double d = 3.14;
		int i = (int) d;
		System.out.println(i); // 3

		// int -> long (자동 형변환)
		int i2 = 100;
		long L1 = i2;
		System.out.println(L1); // 100

		// int + long = long (자동 승격)
		int i3 = 3;
		long l3 = 4L;
		long i4 = i3 + l3; // 자동 형변환됨
//		long i4 = (long)i3 + l3; // 명시적 형변환도 가능
		System.out.println(i4); // 7

		// int + double = double
		int i5 = 10;
		double d2 = 5.5;
		double d3 = i5 + d2;
		System.out.println(d3); // 15.5

		// int 나눗셈 결과를 double에 저장 → 결과는 여전히 정수 나눗셈
		double d4 = 10 / 4; 
		System.out.println(d4); // 2.0

		// 피연산자 하나를 double로 형변환 → 실수 나눗셈 유도
		double d5 = (double)10 / 4;
		System.out.println(d5); // 2.5

		// 문자열 -> 숫자 (Integer.parseInt)
		String s1 = "123";
//		int i8 = s1; // 오류
		int i8 = Integer.parseInt(s1); // 문자열을 숫자로 변환

		// 숫자 -> 문자열 (가장 쉬운 방법: "" + 숫자)
		String s2 = "" + i8;
		System.out.println(s2); // "123"

		// 아래는 실전 예제: 인당 금액 계산
		double z = 5.6; 
		double z1 = 4;
		double z2 = z / z1;
		System.out.println("인당" + (z2 * 10000) + "원"); // 1인당 금액: 10750원

		int z3 = (int) z2; // 소수점 제거
		System.out.println("인당" + (z2 * 10000) + "원"); // 여전히 10750원

		double z4 = (double) z3; // 다시 double로 변환
		System.out.println("참석인원은" + (z4 * 10000) + "원"); // 10000원: 1인당 기준액

		double z5 = z1 - 1; // 3명
		double z6 = (z - (z5 * z4)) * 10000;
		long rounded_z6 = Math.round(z6); // 나머지 금액: 반올림
		System.out.println("나는" + rounded_z6 + "원"); // 본인이 낼 금액: 13000원
	}
		/*
		[형변환 (Type Casting)과 연산 결과 승격]
	
		1. (강제 형변환) int -> byte
		   - byte 범위 초과시 데이터 손실 발생 (예: 200 → -56)
	
		2. (실수 → 정수) double → int
		   - 소수점 이하 버림 (truncate), 반올림 아님
	
		3. (자동 형변환)
		   - int → long, int → double은 자동으로 가능
		   - int + long → 결과는 long
		   - int + double → 결과는 double
	
		4. (정수 나눗셈 주의)
		   - 10 / 4 → 2 (정수 나눗셈이므로 실수 저장해도 2.0)
		   - (double)10 / 4 → 2.5
	
		5. (문자열 ↔ 숫자 변환)
		   - 문자열 → 숫자: Integer.parseInt("123")
		   - 숫자 → 문자열: "" + 숫자
	
		6. (실무 예제: 더치페이 계산)
		   - 전체 인원 중 일부는 10000원 기준 지불
		   - 나머지 금액(소숫점 차이)은 한 명이 정리 (round 사용)
	
		[★★★ 실무에서 자주 쓰이는 것]
		- 문자열 ↔ 숫자 변환 (`parseInt`, `"" + 숫자`)
		- 나눗셈에서 double 강제 캐스팅하여 소수 계산
		- 나머지 금액 계산 후 `Math.round`로 반올림 처리
		*/

}
