package src.exam01;

import java.util.Random;

public class CopilotExam {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] alphabet = new int[26];
	
		// 배열에 'a'부터 'z'까지의 ASCII 값 저장
		for (int j = 0; j < alphabet.length; j++) {
		    alphabet[j] = 'a' + j;
		}
	
		System.out.print("비밀번호 : ");
	
		// 랜덤하게 인덱스를 선택해 비밀번호 생성
		for (int i = 1; i <= 8; i++) {
		    int randomIndex = (int) (Math.random() * 26);
		    System.out.print((char) alphabet[randomIndex]);
		}
		///// 코드 더 줄이기
		System.out.print("비밀번호 : ");
		for (int i = 1; i <= 8; i++) {
		    System.out.print((char) ((int) (Math.random() * 26) + 'a'));
		}
			
		System.out.println();
		// 로또 번호 중복값 없이
        int[] numbers = new int[45];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        Random rand = new Random();
        int remaining = numbers.length;

        for (int i = 0; i < 6; i++) {
            int index = rand.nextInt(remaining); // 0 ~ 남아 있는 개수 중에서 랜덤 선택
            System.out.print(numbers[index] + " | ");

            // 선택된 숫자를 배열에서 제거 (뒤쪽 값과 교체)
            numbers[index] = numbers[remaining - 1];
            remaining--;
        }
        System.out.println();
	        
	        
	        
	        
	}
	
	
}


