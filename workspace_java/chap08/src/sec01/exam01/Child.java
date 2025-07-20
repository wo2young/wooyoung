package sec01.exam01;

public class Child extends Parent {
	void printName() {
		System.out.println("Child의 printName 실행");
		System.out.println("name : " + name);
		System.out.println("this.name: " + this.name);
		
		String pName = getName();
		System.out.println("pName : " + pName);
		
		System.out.println("super.name: " + super.name);
	}
	
	// 전달인자가 필드를 가리는 현상
	// shadow
	void setName(String name) {
		this.name = name;
	}
	// 부모의 필드를 가리는 현상
	// overshadow
	String name = "Child의 name";
	
	Child(){
		 super(1); // 부모의 생성자 호출 가능(생략 가능)
//		 this(1)   // 첫줄에 적어야 한다
		 		   // 전달인자 맞춰줘야 한다
		 		   // super과  this 모두
		 		   // 첫줄에 적야해서 같이 못씀
		 System.out.println("Child 생성자");
	}
	Child(int a){
		super(1);
		
	}
	
	String getName(){
		System.out.println("Child의 getName실행");
		return this.name;
		
	}
}
