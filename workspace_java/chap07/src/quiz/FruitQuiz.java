package quiz;

import java.util.InputMismatchException; // 숫자가 아닌 입력 예외 처리용
import java.util.Scanner;

public class FruitQuiz {

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Fruit f = new Fruit();
		String n[] = new String[f.name.length];
		for(int i=0; i<f.name.length; i++) {
			n[i] = f.name[i];
		}
		 
		 
		boolean run = true;
		System.out.println("휴먼과일가게입니다.");
		
		while(run) {
			System.out.println("\n-----------------------------------------");
			System.out.println("어떤걸 도와드릴까요?");
			System.out.println("1.주문,  2.결제,  3.종료");
			Scanner scan = new Scanner(System.in);
			int menu = scan.nextInt();
			System.out.println("종류와 개수를 적어주세요");
			if(menu == 1) {
				f.all();
				int choice = scan.nextInt();
					if(n[choice].equals(f.name)) {
						System.out.println(f.name);
					}
				}
			else if(menu == 2) {
				System.out.println("결제 완료 되었습니다.");
				break;
			}
			else if(menu == 3) {
				System.out.println("종료합니다.");
				break;
			}
		} 
		

	}
}

