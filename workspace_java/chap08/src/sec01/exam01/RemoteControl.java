package sec01.exam01;

public interface RemoteControl {
// new 못함	
	public static final int MAX_VOLUME = 10;
	// 모든 필드는 static final(상수) 이다
	// 그래서 생략 가능
	public int MIN_VOLUME = 0;
	
	public abstract void turnOn();
	// 기본적으로 메소드는 abstract(추상) 메소드다
	// 그래서 생략 가능
	public void turnOff();
	public void setVOlume(int vol);
	
	default void mic(String text) {
	    System.out.println("기본 음성 명령 처리: " + text);
	}

}
