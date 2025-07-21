package Student;

public class Wooyoung extends Student {
	
	Wooyoung(){
		super("우영");
	}
	
	WooyoungService woo = new WooyoungService(); 
	
	@Override
	String answer(String question) {
		String result = woo.getInfo(question);
		return result;
	}
}
