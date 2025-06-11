package src.exam03;

public class CulcExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Calculator calc = new Calculator();
		
		calc.powerOn();
		
		calc.powerOff();
		
		int sum = calc.plus(5, 8);
		
		System.out.println(sum);
		
		double mod = calc.divide(1, 2);
		System.out.println(mod);
		
		int [] array = {1, 2, 3, 4, 5};
		int sum2 = calc.sum(array);
		System.out.println("sum : " + sum2);
		
	}

}
