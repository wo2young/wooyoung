package sec02.exam01.drive;

public class Driver {
	void drivePorsche(Porsche porsche) {
		System.out.println("포르쉐 구른다");
		porsche.run();
	}
	
	void drive(Car car) {
		System.out.println("출발할게 손놈아");
		car.start();
		car.run();
		car.stop();
	}
}
