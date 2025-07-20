package sec01.exam01;

public class ExtendsExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Child son = new Child();
		System.out.println("--------------------------------------------");
		son.printName();
		
		System.out.println("son.name : " + son.name);
		
		System.out.println( son.getName());
		son.setName("바뀐 이름");
		System.out.println( son.getName());
		
		
	}

}
