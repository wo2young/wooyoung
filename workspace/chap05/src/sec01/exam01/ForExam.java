package sec01.exam01;

public class ForExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int sum = 0;
		for (int i = 1; i <= 100; i++) {
			sum = sum + i;
		}
		System.out.println("1~100의 합:" + sum);
		////////////////////////////////////////////////////////////////
		int sum1 = 0;
		for (int i = 1; i <= 10; i++) {
			sum1 = sum1 + i;
		}
		System.out.println(sum1);

//		int i = 1;
//		sum = 0;
//		
//		sum = sum + i; //1
//		i++;
//		sum = sum + i; //3
//		i++;
//		sum = sum + i; //5
//		i++;
//		sum = sum + i; //9
//		i++;
//		sum = sum + i; //14
//		System.out.println(sum);
//		/////////////////////////////////////////////////////////////////
		int x = 2; // 9X9단 2단 출력
		int x1;
		for (int i = 1; i <= 9; i++) {
			x1 = x * i;
			System.out.println(x + " x " + i + " = " + x1);
		}
		/////////////////////////////////////////////////////////////////
		for (int i = 0; i <= 10; i = i + 2) { // 10까지의 숫자중 짝수만 출력
			System.out.println(i);
		} ///////////////////////////////////////////////////////////////
		for (int i = 10; i >= 0; i--) { // 10부터 1씩 줄어드는 결과
			System.out.println(i);
		} ////////////////////////////////////////////////////////////////
		for (int i = 1; i <= 10; i += 3) { // 1~10까지 3개씩 옆으로 출력
			if (i <= 9) {
				System.out.print(i);
				System.out.print(i + 1);
				System.out.print(i + 2);
				System.out.println();
			} else {
				System.out.println(i);
			}
		}
		/////////////////////////////////////////////////////////////////
		int i1 = 1;
		for (int i = 1; i <= 100; i += 2, i1++) { // 1부터 100까지 홀수의 개수
			// System.out.println(i); //홀수만 출력
		}
		System.out.println(i1 - 1 + "개");// 홀수 개수 출력
		/////////////////////////////////////////////////////////////////
		for (int m = 2; m <= 9; m++) { // 9X9단 전부 출력
			System.out.println("*** " + m + "단 ***"); // 이중for문이 되어야 2단 3단 등 분리가 된다.
			for (int n = 1; n <= 9; n++) {
				System.out.println(m + " X " + n + " = " + (m * n));
			}
		} /////////////////////////////////////////////////////////////////
		for (int m = 2; m <= 9; m++) {
			System.out.println(); // 1단씩 출력
			System.out.println("*** " + m + "단 ***");
			for (int n = 1; n <= 9; n++) {
				if (n <= 8) {
					System.out.printf(m + " X " + n + " = " + (m * n) + ", ");
				} else {
					System.out.printf(m + " X " + n + " = " + (m * n));
				}
			}
		}
		System.out.println();
		for (int m = 2; m <= 9; m += 2) {
			System.out.println(); // 1단씩 출력
			for (int n = 1; n <= 9; n++) {
				if (n <= 9) {
					System.out.printf(m + " X " + n + " = " + (m * n) + ", ");
					System.out.println((m + 1) + " X " + n + " = " + ((m + 1) * n) + " ");
				} else {
					System.out.printf(m + " X " + n + " = " + (m * n));
				}
			}
		}
		System.out.println();
		// 피라미드 찍기
		String mark = "+";
		String space = ".";
//		1단계
		for (int j = 1; j <= 5; j++) {
			System.out.print(mark);
		}
		System.out.println();
		System.out.println();
//		2단계
		for (int j = 1; j <= 5; j++) {
			System.out.print(mark);
			System.out.print(space);
		}
		System.out.println();
		System.out.println();
//		3단계
		for (int j = 1; j <= 3; j++) {
			System.out.println();
			for (int p = 1; p <= 5; p++) {
				System.out.print(mark);
			}
		}
		System.out.println();

//		4단계
		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int p = 1; p <= 5; p++) {
				System.out.print(j);
			}
		}
		System.out.println();

//		5단계

		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int p = 1; p <= j; p++) {
				System.out.print(j);
			}
		}
		System.out.println();
//		6단계
		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int p = 1; p <= j; p++) {
				System.out.print(mark);
			}
		}
		System.out.println();
//		7단계
		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int p = 5; p >= j; p--) {
				System.out.print(j);
			}
		}
		System.out.println();
//		8단계
		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int p = 1; p <= j; p++) {
				System.out.print(mark);
			}
			for (int n = 1; n <= 5 - j; n++) {
				System.out.print(space);
			}
		}
		System.out.println();
//		9단계
		for (int j = 1; j <= 5; j++) {
			System.out.println();
			for (int n = 4; n >= j; n--) {
				System.out.print(space);
			}
			for (int p = 5; p >= 6 - j; p--) {
				System.out.print(mark);
			}
		}
		System.out.println();

//		10단계     
		for (int j = 1; j <= 9; j += 2) {
			System.out.println();
			for (int n = 8; n >= j; n -= 2) {
				System.out.print(space);
			}
			for (int p = 1; p <= j; p++) {
				System.out.print(mark);
			}
		}

//		11단계
		for (int j = 1; j <= 9; j += 2) {
			System.out.println();
			for (int n = 8; n >= j; n -= 2) {
				System.out.print(space);
			}
			for (int p = 1; p <= j; p++) {
				System.out.print(mark);
			}
			for (int n = 8; n >= j; n -= 2) {
				System.out.print(space);
			} 
		}
		System.out.println();
//		12단계
		int row = 22;// 줄높이
//		String mark = "+";	
//		String space = ".";
		for (int j = 0; j <= row - 1; j++) {
			System.out.println();
			for (int i = 0; i + j  < row - 1; i++) {
				System.out.print(space);
			}
			for (int i = 0; i < (2 * j) + 1 ; i++) {
				System.out.print(mark);
			}
			for (int i = 0; i + j  < row - 1; i++) {
				System.out.print(space);
			}
			System.out.println();
		}
		
		
		
		
	
		} 
	}
