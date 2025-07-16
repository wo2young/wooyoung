package sec01.exam01;

public class Child extends parent {
	void printName() {
		System.out.println("Child의 printName 실행");
		System.out.println("name : " + name);
		System.out.println("this.name: " + this.name);
		
		String pName = getName();
		System.out.println();
	}
	
	// 전달인자가 필드를 가리는 현상
	// shadow
	void setName(String name) {
		this.name = name;
	}
	// 부모의 필드를 가리는 현상
	// overshadow
	String name = "Child의 name";
}
