package Student;

import java.util.HashMap;

public class WooyoungDAO {
	
	HashMap map = new HashMap();
	
	WooyoungDAO(){
		map.put("사탕", "청포도");
		map.put("점심", "달식당");
		map.put("회식", "어디로가요?");
		map.put("휴식", "네!!!");
	}
	
	String selectAnswer(String question) {
		return (String)map.get(question);
	}
	
}
