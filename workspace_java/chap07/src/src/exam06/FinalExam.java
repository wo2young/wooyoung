package src.exam06;

public class FinalExam {
	final String nation = "Korea"; // final이므로 변경 불가, 선언과 동시에 초기화됨
	final String ssn;              // 생성자에서 1번만 초기화 가능
	String name;                   // 일반 변수, 자유롭게 변경 가능

	public FinalExam(String ssn, String name) {
		this.ssn = ssn; // final의 값이 없는 경우 생성자에서 1번만 바꿀 수 있다.
		this.name = name;
//		this.nation = "France"; final이라서 값을 바꿀 수 없다.
//		this.ssn = ssn; 얘도 final이라서 못바꾼다.
		this.name = "name2"; // 얘는 그냥 바뀜.
		// → name은 final이 아니기 때문에 나중에 다시 재할당 가능함
	}

	void test() {
//		this.ssn = "아뭇거나"; // final이라서 못바꿈 그냥 null임
		// → 이미 생성자에서 한 번 초기화했기 때문에 이후에는 변경 불가
	}

	// 내부 클래스 → static이 아니면 static context(main, loginCheck)에서 접근 불가함
	public static class Constant {

		static final int LOGIN_SUCCESS = 1;   // 로그인 성공
		static final int LOGIN_FAIL = 2;      // 로그인 실패
		static final int LOGIN_PW_90 = 3;     // 비밀번호 90일 경과
		static final int LOGIN_PW_TEMP = 4;   // 임시비밀번호
		// → 모두 static final로 선언: 상수 정의 (변하지 않는 값)
	}

	// main은 static이어야 하며, 메서드 안에 메서드는 정의 불가능하므로 loginCheck는 바깥에 둬야 함
	public static void main(String[] args) {

		int result = loginCheck("admin", "1234"); // 로그인 확인 로직 호출

		System.out.println("로그인 결과 코드: " + result); // 결과 출력용 추가
		// → 콘솔에 결과 코드 출력 (1: 성공, 2: 실패 등)
	}

	// static 메서드: main에서 사용하기 위해 static으로 선언
	public static int loginCheck(String id, String pw) {
		if ("admin".equals(id) && "1234".equals(pw)) {
			return Constant.LOGIN_SUCCESS; // 아이디 비번 맞으면 1 반환
		} else {
			return Constant.LOGIN_FAIL;    // 틀리면 2 반환
		}
	}
}

/* ============================= 전체 정리 =============================
[1] final 필드 관련
- final로 선언된 변수는 한 번만 값 설정 가능 (변경 불가)
  - 선언과 동시에 초기화하거나, 생성자에서 1회만 초기화 가능
- nation → 선언 시 초기화됨 → 이후 절대 변경 불가
- ssn → 생성자에서만 초기화 가능 → 이후 변경 불가
- name → 일반 변수이므로 자유롭게 변경 가능

[2] 내부 클래스 Constant
- static final로 정의된 값은 "상수"
- 실무에선 상수를 한곳에 모아두기 위해 별도 클래스로 관리함
- static 내부 클래스(Constant)는 외부 클래스(FinalExam) 안에 있어도,
  정적 방식으로 접근 가능 (예: Constant.LOGIN_SUCCESS)

[3] main 메서드와 loginCheck
- main은 자바 프로그램의 시작점 → 반드시 static 필요
- Java에서는 메서드 안에 다른 메서드 정의 불가 → loginCheck는 밖으로 빼야 함
- loginCheck도 static으로 선언해야 main에서 호출 가능

[4] 실무에서 자주 쓰이는 개념 ★★★
- final 키워드: 불변성 유지에 중요 (공통 설정, 민감 정보 등)
- static final 상수: 코드의 가독성과 유지보수성을 위해 자주 사용
- 로그인 처리 시, 단순 문자열 비교 대신 DB 및 암호화된 비교로 구현함

====================================================================== */
