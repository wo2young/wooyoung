package sec01.exam01;

public class SwichExam {
	public static void main(String[] args) {
		
		int time = (int) (Math.random() * 4) + 8;
		System.out.println("[현재시간: " + time + " 시]");
		
		switch(time) {
		case 8:
			System.out.println("출근합니다.");
		case 9:
			System.out.println("회의를 합니다.");
		case 10:
			System.out.println("업무를 봅니다.");
		default:
			System.out.println("외근을 나갑니다.");
		}
		
		int num = (int) (Math.random() * 6);
		switch(num) {
			case 1:
			System.out.println(1 + " 입니다.");
			break;
			case 2:
			System.out.println(2 + " 입니다.");
			break;
			case 3:
			System.out.println(3 + " 입니다.");
			break;
			case 4:
			System.out.println(4 + " 입니다.");
			break;	
			case 5:
			System.out.println(5 + " 입니다.");
			break;	
			default:
			System.out.println(6 + " 입니다.");
			break;		
		}
		
		 // switch문에 사용가능 : char, byte, short, int, string
		 // switch문에 사용불가 : boolean, long, float, double
		
		// 문제1
		int ran = -1; //숫자는 임의로 변경 가능
		if(ran > 0) { 
			System.out.println("양수");
		} else if(ran == 0) {
			System.out.println("0");
		} else {
			System.out.println("음수");
		}////////////////////////////////////////////////
		//문제2
		int a = 1; //숫자는 임의로 변경 가능
		int b = 2;
		if (a > b) {
			System.out.println(a);
		} else if (a < b) {
			System.out.println(b);
		} else {
			System.out.println("a와 b는 값이 같다");
		}//////////////////////////////////////////////////
		//문제3
		int h = 22;//시간
		int m = 32;//분
		m = (m + 35); //67
		h = ((h * 60) + m) / 60; // ((3*60) + 67) / 60
		m = m % 60; //67분이라 7이된다
		System.out.println(h + "시간" + m + "분");
		/////////////////////////////////////////////////////
		//문제4
		int dom = (int) (Math.random() * 100); // 숫자변경 가눙
		if(dom >= 15 && dom <= 20) {
			System.out.println("15와 20사이에 있습니다.");
		} else {
			System.out.println("15와 20사이에 없습니다.");
		}/////////////////////////////////////////////////////
		//문제5
		int emn = 10000;// 통장잔액
		int mn = 12000;// 출금금액
		if(emn >= mn) {
			emn = emn - mn;
			System.out.println(mn + " 출금했고 " + emn + " 남았습니다.");
		} else {
			System.out.println("잔액이 부족합니다.");
		}////////////////////////////////////////////////////////
		//문제6
		int cc = -5;
		int cc1 = cc % 2;
		if (cc >=100) {
			System.out.printf("100보다 큰수이며,");
		} else { // printf라서 이문장과 다음 println까지 연결된다. 
			System.out.printf("100보다 작은수이며,");
		} if (cc >= 0) {
			System.out.printf("양수이고,");
		} else {
			System.out.printf("음수이고,");
		} if (cc1 > 0) {
			System.out.println("홀수입니다.");
		}	else { 
			System.out.println("짝수입니다.");
		}////////////////////////////////////////////////////////////
		//문제7
		int n1 = 2, //어제 온도
			n2 = 1; //오늘 온도
		if (n2 > 0) {
			System.out.printf("오늘 온도는 영상 " + n2 + "도 입니다.");
		} else {		// printf라서 이문장과 다음 println까지 연결된다. 
			System.out.printf("오늘 온도는 영하 " + -n2 + "도 입니다.");
		} if (n1 < n2) {
			int n3 = n2 - n1;
			System.out.println(" 어제보다 " + n3 + "도 높습니다");
		} else if (n1 > n2) {
			int n3 = n1 - n2;//변수이름 재활용도 가능
			System.out.println(" 어제보다 " + n3 + "도 낮습니다");
		}else {
			System.out.println(" 어제와 같습니다.");
		}////////////////////////////////////////////////////////////////
		//문제8
		int y = 13; // 두자리 숫자만 입력가능
		int y10 = y / 10;
		int y1 = y % 10;
		if (y10 == y1) {
			System.out.println("같은 수이다");
		} else {
			System.out.println("같은 수가 아니다");
		}
		//문제9	 모서리 좌표 x1:10 y1:20 / 대각선 위치 모서리 x2:90 y2:100 사각형인데 x:3 y:3이 포함되나
				//x3:10 y3:100  x4:90 y4:20
		
		int x = 10, z = 33;// 변경되는 값
		
		int x1 = 10, x2 = 90,        //사각형의 범위 
			z1 = 20, z2 = 100;
		
		if (x1 <= x && x2 >= x) {
			if(z1 <= z && z2 >= z) {
				System.out.println("포함합니다.");
			} else {
				System.out.println("포함되지 않습니다.");	
			}
	  } else {
			System.out.println("포함되지 않습니다.");
		}
		///////////////////////////////////////////////////////////////////
		
		
	  }
		
		
	}
	
	
