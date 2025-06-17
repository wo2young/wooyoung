package src.exam06;

public class FinalExam {
	final String nation = "Korea";
	final String ssn;
	String name;
	
	public FinalExam(String ssn, String name) {
		this.ssn = ssn; // final의 값이 없는 경우 생성자에서 1번만 바꿀 수 있다.
		this.name = name;
//		this.nation = "France"; final이라서 값을 바꿀 수 없다.
		
//		this.ssn = ssn; 얘도 final이라서 못바꾼다.
		this.name = "name2"; // 얘는 그냥 바뀜.
	}
	void test() {
//		this.ssn = "아뭇거나"; // final이라서 못바꿈 그냥 null임
	}
	public class Constant{
		
		static final int LOGIN_SUCCESS = 1;
		static final int LOGIN_FAIL = 2;
		static final int LOGIN_PW_90 = 3;
		static final int LOGIN_PW_TEMP = 4; // 임시비밀번호
		
	}
	public void main(String [] args) {
		
		int result = loginCheck("admin", "1234");
		static int loginCheck(String id, String pw) {
		if("admin".equals(id) && "1234".equals(pw)) {
			return Constant.LOGIN_SUCCESS;
		}
		else {
			return Constant.LOGIN_FAIL;
		}
	}

}
