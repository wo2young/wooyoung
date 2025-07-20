package sec02.exam01.drive;

import java.util.ArrayList;
import java.util.HashMap;

public class DriveExam {
	public static void main(String[] args) {
		Porsche porsche = new Porsche();
		
		Driver driver = new Driver();
		
		driver.drivePorsche(porsche);
		
		TeslaModel3 Tesla = new TeslaModel3();
		Car a1 = Tesla;
		driver.drive(a1);
		
		ArrayList list = new ArrayList();
		list.add(driver);
		Driver d = (Driver)list.get(0);
		
		HashMap map = new HashMap();
		map.put("key", "value");           // 넣는 법
		String k = (String)map.get("key"); // 꺼내는 법
		
	}
}
