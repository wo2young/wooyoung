package sec01.exam01;

import java.util.Scanner;

public class KeyCodeExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(1);
		System.out.println(2);
		
		System.out.println(3);
		System.out.println(4);
		
		System.out.println(5);
		System.out.println(6);
		
		System.out.printf("이름: %s", "김자바");
		System.out.println();
		System.out.printf("이름: %d", 25);
		System.out.println();
		System.out.printf("이름: %s,나이: %d", "김자바", 25);
		System.out.println();
		System.out.printf("이름: %6s,나이: %04d", "김자바", 25);
		
		/*
		 * scanner 연습하려고 처리
		int keyCode;
		try {
			keyCode = System.in.read();
			
			System.out.println("keyCode: " + keyCode);
			
			keyCode = System.in.read();
			System.out.println("keyCode: " + keyCode);
			
			keyCode = System.in.read();
			System.out.println("keyCode: " + keyCode);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */ 
		
		Scanner scan = new Scanner(System.in);
		//엔터까지의 모든 글자
		String inputData = scan.nextLine();
		System.out.println("inputData : " + inputData);
		// 엔터첬을 때 띄어쓰기까지의 모든 글자 
		String inputData2 = scan.next();
		System.out.println("inputData2 : " + inputData2);
		//엔터쳤을 때의 숫자
		int input = scan.nextInt();
		System.out.println("input : " + (input*10));
		
		
		
		
		
		
		
		
		

		
		}

}
