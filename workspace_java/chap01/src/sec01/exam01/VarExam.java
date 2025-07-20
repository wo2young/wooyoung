package sec01.exam01;

public class VarExam {

	public static void main(String[] args) {
		// 변수 선언
		int value = 5;
		// 변수 value는 5이므로 5 + 10 = 15
		int result = value + 10;

		System.out.println(result); // 15 출력

		int hour = 3;
		int minute = 5;
		System.out.println(hour + "시간" + minute + "분"); // 문자열 + 숫자는 문자열로 자동 변환됨
		System.out.println("value : " + value); // 5

		value = value + 1; // 변수 갱신 (기존 값 + 1)
		System.out.println("value : " + value); // 6

// 단축키 모음 (이건 실제 코딩 속도 올리는 데 유용하니까 외워두면 좋음)
// 한줄 이동: alt + ↑↓
// 한줄 복사: ctrl + alt + ↑↓
// 한줄 지우기: ctrl + d
// 주석 토글: ctrl + /
// 자동 정렬: ctrl + shift + f

		int x = 10;
		int y = x; // x의 값을 y에 복사 (변수 복사는 값만 복사됨, 주소 아님)
		System.out.println(x); // 10

		{
			int z = 2; // (블록 스코프 안에서 선언된 변수는 밖에서 사용 불가)
			int t = 5;
			System.out.println("z : " + z); // 2
		}

		int z = 23; // 위 블록의 z와는 별개 변수 (지역변수는 스코프마다 다르게 선언 가능)
		System.out.println(z); // 23

		int x1 = 26854;
		int x2 = 684684;
		x1 = x2; // (x1이 x2의 값을 가지게 됨)
		System.out.println("x1 :" + x1); // 684684

		x1 = 26854;
		x2 = 684684;
		x2 = x1; // (x2가 x1의 값으로 바뀜)
		System.out.println("x2 :" + x2); // 26854

//////////////////////////////////////////////////////////

		// 두 변수 값 교환 (swap 방법1: 임시 변수 2개 사용)
		x1 = 26854;
		x2 = 684684;
		int z1 = x1;
		int z2 = x2;
		x1 = z2;
		x2 = z1;
		System.out.println("x1 :" + x1); // 684684
		System.out.println("x2 :" + x2); // 26854

///////////////////////////////////////////////////////

		// swap 방법2: 임시 변수 1개만 사용 (더 실용적이고 간단함)
		x1 = 26854;
		x2 = 684684;
		int z3 = x1;
		x1 = x2;
		x2 = z3;
		System.out.println("x1 :" + x1); // 684684
		System.out.println("x2 :" + x2); // 26854
	}
			/*
			[변수와 값 복사 개념 정리]
		
			1. 변수에 값 할당:
			   - 기본형(int, double 등)은 값만 복사됨 (참조X)
		
			2. 출력에서 문자열 + 숫자 = 문자열 연결됨 (자동 형변환)
		
			3. 블록 내에서 선언된 변수는 블록 밖에서 사용 불가 (스코프 주의)
		
			4. ★ 두 변수의 값 바꾸기 (swap)
			   - 방법1: 임시 변수 2개 사용 (z1, z2)
			   - 방법2: 임시 변수 1개로도 가능 (z3) → 실무에서 더 자주 사용
		
			5. 단축키 정리 (실무 속도 향상에 도움됨)
			   - 이동: alt + ↑↓ / 복사: ctrl + alt + ↑↓ / 주석: ctrl + /
		
			[★★★ 실무에서 중요한 개념]
			- 변수는 "값 복사"임을 정확히 이해할 것 (참조형은 다름)
			- swap은 실무 코딩 테스트 단골 (특히 알고리즘 풀 때)
			- 블록 변수 범위(scope) 이해는 오류 방지에 매우 중요
			*/
}
