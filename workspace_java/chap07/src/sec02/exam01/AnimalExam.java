package sec02.exam01;

public class AnimalExam {
	
	public static void main(String[] args) {
		
		Animal a1 = new Animal();
		a1.think();
		a1.eat();
		
		Cat cat = new Cat();
		cat.eat();
		cat.grooming();
		
		Animal a2 = (Animal)cat;  // 자식이 부모가 될때
		Animal a3 = cat;          // 형 변환 연산자 생략 가능
		
		a2.eat();
//		a2.clean();
		
		Dog dog = new Dog();
		Animal a5 = dog;
		// 위와 밑이 결국 같은 거임
		Animal a4 = new Dog();
		
		catMom(cat);
		animalCare(a4);
		animalCare(new Cat());
	}
	
	static void catMom(Cat cat) {
		cat.eat();
		cat.grooming();
	}
	static void animalCare(Animal animal) {
		System.out.println("나이는 : " + animal.age);
		animal.eat();
		animal.think();
	}
	
}
		//자바 변수 메모리 구조 정리 (★★★ 실무 필수)
		//1. static 변수 → Method Area에 저장, 자동 초기화됨
		//2. 인스턴스 변수 → Heap에 저장, 자동 초기화됨 (int는 0)
		//3. 지역 변수 → Stack에 저장, 반드시 직접 초기화 안 하면 오류
		//
		//※ 지역 변수는 자바에서 자동 초기화를 하지 않기 때문에,
		// 사용 전에 반드시 명시적으로 값을 줘야 한다.
		// 예: int a = 0; 이렇게 해야 함.