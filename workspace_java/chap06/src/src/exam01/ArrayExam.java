package src.exam01;

public class ArrayExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * 배열 array
		 * 한번에 여러 변수를 만드는 방법
		 * 같은 타입만 선언할 수 있음
		 * 생성할 때 전체 크기를 지정해줘야 한다
		 * 셍성된 여러 변수들은 index로 관리한다.(index는 0부터)
		 */
		
//		// 선언 방법
//		int[] a1; // java 스타일
//		int[] a;
//		int a2[]; // c 스타일
		
//		int[] score = new int [3];
//		score[0] = 90;
//		score[1] = 80;
//		score[2] = 75;
//		System.out.println("score[1] : " + score[1]);
		/*
		 * 첫번쨰 [] : int[]
		 * 			int로 이루어진 배열 변수 타입이다.
		 * 두번쨰 [] : new int[3]
		 * 			배열의 크기. 즉 한번에 만들 변수의 개수
		 * 세번째 [] : score[0] =90;
		 * 			만들어진 변수 중에서 몇번째인가?
		 * 				index는 0부터 시작한다.
		 */
		int[] score = new int[30];
		score[1] = 90;
		score[2] = 80;
		score[3] = 75;
		System.out.println("score[1] : " + score[1]);
		
		String[] str = new String[3];
		System.out.println("str[0] : " + str[0]);
		// 배열 생성 후 기본값은
		// 0, false, null로 초기화 된다.
		
		//배열을 선언하는 두번째 방법
		// 넣을 값을 모두 정확히 알고 있는 경우
		int[] i1 = new int[] {90, 80, 75};
		int[] i2 = null;
		i2 = new int[] {90, 80, 75};

		// 배열을 선언하는 세번째 방법
		// 선언과 동시에 초기화 하는 경우에만
		// new int[]를 생략할 수 있다.
		int[] i3 = {90, 80, 75};
		int[] i4 = null;
//		i4 = {90, 80, 75};
		
		// 1차원 배열
		int b0 = 5;
		int b1 = 15;
		int b2 = 55;
		// 2차원 배열 x축 y축으로 생각하는데 편하다.
		int[] bs0 = new int[3];
		bs0[0]= b0; 
		bs0[1]= b1;
		bs0[2]= b2;
		// 3차원 배열 여기까진 내머리에서 상상이 간다.
		int[] bs1 = new int[3];
		bs1[0] = b0 + 1;
		bs1[1] = b1 + 1;
		bs1[2] = b2 + 1;
		
		int [] [] bs = new int[2][3];
		System.out.println("bs[1] [1] : " + bs[1] [1]);
		System.out.println(bs1[2]);
		System.out.println(bs1[1]);
		System.out.println("bs.length : " + bs.length);
		System.out.println("bs[0].length : " + bs[0].length);
		
		int[] f = new int [10];
		for(int i = 0; i < 10; i++) {
			f[i] = i+1;
			System.out.println("f[" + i + "] : " + f[i]);
		}
		
		int[] d1 = new int[] {1,2,3,4,5};
		int[] d2 = d1;
		
		d2[1] = 10;
		System.out.println("d1[1] : " + d1[1]);
		// d2가 d1과 같은 주소를 가지고 있기때문에 d2를 바꿔면 d1의 값도 바뀐다.
		
		int[] f1 = {2, 4, 11, 24, 34, 22}; // 그냥 아무 숫자
		int[] f2 = new int [f1.length];// f1의 길이가 바뀌면 같이 바뀐다.
		for (int i = 0; i < f1.length; i++) {
			System.out.println(f1[i]);
		} // i=0은 배열로 만든 변수는 0부터 시작이기 때문이다.
		  // i<f2.length는 f2의 길이가 길어지면 알아서 수정되는 좋은 방법이다.
		for (int i = 0; i < f2.length; i++) {
			f2[i] = f1[i]; // f2 = f1을 해야 같은 값을 대입한다.
			System.out.println(f2[i]); // 대입했으니 맞는지 확인
		}
		
		for(int i=0; i<f1.length;i++) {
			int data = f1[i];
			System.out.println("data : "+ data);
		}//향상된 for문
		for(int data : f1) {
		System.out.println("data : "+ data);
		}
		//로또 자동추천 아직 중복된 숫자가 나올수 있음
		int[] lotto = new int[45];	
			for(int i=0; i < 6; i++) {
				for(int j = 1; j < lotto.length; j++) {
					lotto[j] = j+1;
				}
				System.out.print(lotto[(int)(Math.random()* lotto.length + 1)]);
				System.out.print(" ");
		}
		
			
		
		
		
		
		
	}
}
