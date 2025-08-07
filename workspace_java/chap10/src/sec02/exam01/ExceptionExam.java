package sec02.exam01;

public class ExceptionExam {

	public static void main(String[] args) {
		System.out.println("앙");

		// args 배열 출력 (명령행 인자)
		for(int i = 0; i < args.length; i++) {
			System.out.println(i + ": " + args[i]);
		}

		int a = -1;  // 나중에 Integer.parseInt 결과 저장할 변수

		try {
			String name = "우영";
			// 문자열 "우영"을 정수로 변환 시도 → 예외 발생 (NumberFormatException)
			a = Integer.parseInt(name);

			// 아래 코드는 실행되지 않음 (예외가 위에서 발생했기 때문)
			System.out.println(args[100]);  // ArrayIndexOutOfBoundsException 가능성
			System.out.println("1번자리");

		// 전체 예외를 하나로 묶은 방식 (현재 주석 처리되어 있음)
		// } catch(Exception e) {
		// 	System.out.println("4번자리");
		// 	e.printStackTrace();
		// }

		} catch(ArrayIndexOutOfBoundsException e) {
			// 배열 인덱스 범위를 벗어났을 때
			System.out.println("2번자리");
			e.printStackTrace();

		} catch(NumberFormatException e) {
			// 문자열을 숫자로 바꿀 수 없을 때
			System.out.println("3번자리");
			e.printStackTrace();

			System.out.println("------------");
			System.out.println(e);                // 예외 객체 출력
			System.out.println("------------");
			System.out.println(e.getMessage());   // 예외 메시지 출력
		} finally {
			// 예외 발생 여부와 관계없이 항상 실행됨
			System.out.println("finally 무조건 실행");
		}

		System.out.println("출력");  // 예외가 발생해도 여기는 실행됨
		System.out.println("a : " + a);  // a는 예외 전 초기값 -1 또는 정상 변환된 값
	}

	void test() {
		try {
			// 기타 처리 코드 작성 가능
		} catch (Exception e) {
			e.printStackTrace();  // 예외 전체 출력
		}
	}
}
