package sec02.exam01;

public class Cat extends Animal{
	
	int age = 3;
	 
	@Override
	void eat() {
		System.out.println("냥냥");
	}

	@Override
	void think() {
		System.out.println("아몰랑");
	}
	
	void clean() {
		System.out.println("클린");
	}
	
	void grooming() {
		System.out.println("냥이 세수한다");
	}
	
}
