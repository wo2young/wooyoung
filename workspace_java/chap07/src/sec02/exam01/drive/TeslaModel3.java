package sec02.exam01.drive;

public class TeslaModel3 extends Car{
	@Override
	void run() {
		System.out.println("무소음");
	}
	void autoRun() {
		System.out.println("자율 주행");
	}
}
