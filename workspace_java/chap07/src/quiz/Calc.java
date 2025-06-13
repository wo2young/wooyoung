package quiz;

public class Calc {
	
	// 필드
	int [] num = new int[100];
	int a;
	
	// 생성자
	Calc() {
	a = 0;	
	}
	
	
	// 메서드
	int sum(int n, int m) { // 더하기
		this.a =  num[n] + num[m];
		return this.a;
	}
	
	int subtraction(int n, int m) { // 빼기
		this.a = num[n] - num[m];
		return this.a;
	}
	
	int product(int n, int m) { // 곱하기
		this.a = num[n] * num[m];
		return this.a;
	}
	
	int divide(int n, int m) { // 나누기
		this.a = num[n] / num[m];
		return this.a;
	}
	
	
}
