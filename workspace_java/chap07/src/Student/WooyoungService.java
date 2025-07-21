package Student;

public class WooyoungService {
	
	WooyoungDAO wooDAO = new WooyoungDAO(); 
	
	String getInfo(String question) {
		String result = wooDAO.selectAnswer(question);
		if(result == null) {
			result = "(신중히 생각 중)";
		} else {
			result = "음.. 저는 " + result + "요";
		}
		return result;
	}
}
