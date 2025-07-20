package sec02.exam01.drive;

public class Car extends Object {
	
	Car(){
		super();
	}
	
	void start() {
		System.out.println("시동 켜기");
	}
	void stop() {
		System.out.println("시동 끄기");
	}
	void run() {
		System.out.println("주행중...");
	}
}
