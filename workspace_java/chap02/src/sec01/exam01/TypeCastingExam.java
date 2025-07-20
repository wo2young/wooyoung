package sec01.exam01;

public class TypeCastingExam {

	public static void main(String[] args) {
		
		int intValue = 10;
		byte byteValue = (byte) intValue; // (int → byte) 강제 형변환. 값 손실 없음

		intValue = 200;
		byteValue = (byte) intValue; // (int → byte) 200은 byte 범위 초과 → 오버플로우 발생
		System.out.println(byteValue); // 출력: -56

		double d = 3.14;
		int i = (int) d; // (double → int) 소수점 버림 (truncate)
		System.out.println(i); // 출력: 3

		int i2 = 100;
		long L1 = i2; // (int → long) 자동 형변환
		System.out.println(L1); // 출력: 100

		int i3 = 3;
		long l3 = 4L;
		long i4 = i3 + l3; // (int + long → long) 자동 승격
//		long i4 = (long)i3 + l3; // 명시적 형변환도 가능
		System.out.println(i4); // 출력: 7

		int i5 = 10;
		double d2 = 5.5;
		double d3 = i5 + d2; // (int + double → double) 자동 승격
		System.out.println(d3); // 출력: 15.5

		double d4 = 10 / 4; // int/int = int → 결과는 2 → double에 저장돼서 2.0 출력
		System.out.println(d4); // 출력: 2.0

		double d5 = (double)10 / 4; // (double / int → double) 실수 나눗셈
		System.out.println(d5); // 출력: 2.5

		String s1 = "123";
//		int i8 = s1; // 오류: 문자열은 직접 int로 못 넣음
		int i8 = Integer.parseInt(s1); // 문자열 → 정수로 변환
		System.out.println(i8); // 출력: 123

		// 숫자 → 문자열: 제일 쉬운 방법은 "" + 숫자
		String s2 = "" + i8;
		System.out.println(s2); // 출력: "123"

		// 더치페이 계산 예제
		double z = 5.6; 
		double z1 = 4;
		double z2 = z / z1; // 1인당 금액 (소수 있음)
		System.out.println("인당" + (z2 * 10000) + "원"); // 10750원

		int z3 = (int) z2; // 소수점 버림 (truncate)
		System.out.println("인당" + (z2 * 10000) + "원"); // 여전히 10750원

		double z4 = (double) z3; // 다시 double로 형변환 (1.0)
		System.out.println("참석인원은" + (z4 * 10000) + "원" ); // 10000원 (1명분 기준)

		double z5 = z1 - 1; // 남은 인원 수 (3명)
		double z6 = (z - (z5 * z4)) * 10000; // 본인이 부담할 잔액 계산
		long rounded_z6 = Math.round(z6); // 반올림
		System.out.println("나는" + rounded_z6 + "원" ); // 출력: 13000원
	}
}
