package Student;

public class Study {
	
	String getAnswer(Student student, String question){
		System.out.println(question +"에 대하여");
		System.out.println(student.name+"님에게 물어봅니다");
		System.out.println(question +"의 대답은 : "+ student.answer(question));
		return student.answer(question);
	}
	
}
