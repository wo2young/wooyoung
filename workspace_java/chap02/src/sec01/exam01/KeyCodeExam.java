package sec01.exam01;

import java.util.Scanner;

public class KeyCodeExam {

	public static void main(String[] args) {
		// 순차적으로 숫자 출력
		System.out.println(1);
		System.out.println(2);
		
		System.out.println(3);
		System.out.println(4);
		
		System.out.println(5);
		System.out.println(6);

		// printf 사용법: %s는 문자열, %d는 정수
		System.out.printf("이름: %s", "김자바"); // 문자열 포맷 출력
		System.out.println();
		System.out.printf("이름: %d", 25);       // 정수 포맷 출력
		System.out.println();
		System.out.printf("이름: %s,나이: %d", "김자바", 25); // 여러 개 출력
		System.out.println();
		System.out.printf("이름: %6s,나이: %04d", "김자바", 25); 
		// %6s : 문자열 6자리 폭 확보 (오른쪽 정렬)
		// %04d : 정수를 4자리로 출력, 빈칸은 0으로 채움

		/*
		 * System.in.read()는 문자 하나를 byte 단위로 읽음
		 * IOException 처리가 반드시 필요함 (try-catch)
		 * 여러 글자 입력받으려면 반복해서 호출해야 함
		 
		int keyCode;
		try {
			keyCode = System.in.read(); // (예: 'A' 입력 시 65가 나옴)
			System.out.println("keyCode: " + keyCode);
			
			keyCode = System.in.read();
			System.out.println("keyCode: " + keyCode);
			
			keyCode = System.in.read();
			System.out.println("keyCode: " + keyCode);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		*/

		// Scanner 객체 생성 (입력용 도구)
		Scanner scan = new Scanner(System.in);

		// nextLine(): 엔터 치기 전까지 한 줄 전체 읽음 (공백 포함)
		String inputData = scan.nextLine();
		System.out.println("inputData : " + inputData);

		// next(): 엔터 치기 전까지, 공백 전까지만 읽음
		String inputData2 = scan.next();
		System.out.println("inputData2 : " + inputData2);

		// nextInt(): 정수 하나 입력 (엔터 입력까지 기다림)
		int input = scan.nextInt();
		System.out.println("input : " + (input * 10)); // 입력한 값의 10배 출력
	}
}
