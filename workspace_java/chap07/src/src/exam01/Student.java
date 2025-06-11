package src.exam01;

public class Student {

	//학생 클래스를 만들고
	//이름과 나이 필드를 만든다
	//학생Exam 클래스를 만들고
	//학생 2명을 각각 등록하고
	//두 학생의 정보를 각각 출력해보세요
	String name;
	
	int age;
	
	//이름과 나이를 전달해서 저장하기
	//이름과 나이를 돌려받아서 출력하기
	
	/**
	 * set에는 저장만 한다, 저장만 하니 return이 없어서 void를 사용
	 * 위에 name과 age를 필드로 만들어 뒀고 거기에 n과 a를 대입
	 * @param n
	 */
	void setName(String n) {
		name = n;	
	}
	
	void setAge(int a) {
		age = a;
	}
	/**
	 * get에는 돌려줘서 출력하니 return이 있어서 String과 int 사용
	 * @return
	 */
	String getName(){
		return name;
	}
	
	
	int	getAge() {
		return age;
	}
	
	
}





