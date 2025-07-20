package sec01.exam01;

public class VarExam {

	public static void main(String[] args) {
		
		// 변수 선언 + 초기화
		int value = 5;
		int result = value + 10; // (5 + 10 = 15)
		System.out.println(result); // 출력: 15

		int hour = 3;
		int minute = 5;
		System.out.println(hour + "시간" + minute + "분"); // 문자열 + 숫자 → 문자열

		System.out.println("value : " + value); // 출력: 5

		value = value + 1; // 기존 value 값에 1 더함 (→ 6)
		System.out.println("value : " + value); // 출력: 6

		// [단축키 정리] (실무에서 코드 작성 속도 높이기 유용함)
		// 한줄 이동: alt + ↑↓
		// 한줄 복사: ctrl + alt + ↑↓
		// 한줄 삭제: ctrl + d
		// 주석 토글: ctrl + /
		// 자동 정렬: ctrl + shift + f

		// 값 복사 예시
		int x = 10;
		int y = x; // y에 x의 값을 복사함 (값 복사, 주소 아님)
		System.out.println(x); // 출력: 10

		{
			int z = 2; // (z는 이 블록 안에서만 유효)
			int t = 5;
			System.out.println("z : " + z); // 출력: 2
		}

		int z = 23; // 위 블록과 다른 z (이건 main 메서드 블록 안)
		System.out.println(z); // 출력: 23

		// 값 복사: x1이 x2의 값을 받음
		int x1 = 26854;
		int x2 = 684684;
		x1 = x2;
		System.out.println("x1 :" + x1); // 출력: 684684

		// 반대 방향 값 복사
		x1 = 26854;
		x2 = 684684;
		x2 = x1;
		System.out.println("x2 :" + x2); // 출력: 26854

//////////////////////////////////////////////////////////

		// 변수 값 바꾸기 (swap 방법1: 임시 변수 2개)
		x1 = 26854;
		x2 = 684684;
		int z1 = x1;
		int z2 = x2;
		x1 = z2;
		x2 = z1;
		System.out.println("x1 :" + x1); // 출력: 684684
		System.out.println("x2 :" + x2); // 출력: 26854

///////////////////////////////////////////////////////

		// 변수 값 바꾸기 (swap 방법2: 임시 변수 1개)
		x1 = 26854;
		x2 = 684684;
		int z3 = x1;
		x1 = x2;
		x2 = z3;
		System.out.println("x1 :" + x1); // 출력: 684684
		System.out.println("x2 :" + x2); // 출력: 26854
	}
}
