package sec02.exam01;

public class KeyboardImpl implements Keyboard {

	@Override
	public String press(int KeyCode) {
		// TODO Auto-generated method stub
		if(KeyCode == 13) {
			return "Enter";
		}
		return "뭔가 눌림";
	}
	
	
}
