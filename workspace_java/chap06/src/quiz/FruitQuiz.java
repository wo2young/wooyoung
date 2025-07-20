package quiz;

import java.util.Scanner;

public class FruitQuiz {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		Fruit f = new Fruit();
		 
		 
		boolean run = true;
		System.out.println("휴먼과일가게입니다.");
		
		while(run) {
			System.out.println("\n-----------------------------------------");
			System.out.println("어떤걸 도와드릴까요?");
			System.out.println("1.주문,  2.결제,  3.종료");
			String input = scan.next();
			int menu = Integer.parseInt(input);
			if (menu == 1) {
                f.all();
                f.menu1();
            } else if (menu == 2) {
                    f.menu2();
                run = false;
            } else if (menu == 3) {
                f.menu3();
                run = false;
            } else {
                System.out.println("1~" + f.name.length + "사이의 숫자를 입력하세요!");
            }
		} 
		

	}
}