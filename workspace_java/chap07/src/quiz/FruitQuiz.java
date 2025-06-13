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
//			Scanner scan = new Scanner(System.in);
//			int menu = f.menu;
//			숫자만 나오게 하는 방법
//          System.out.println("숫자만 입력 가능합니다!");

			int menu = Integer.parseInt(input);
			if (menu == 1) {
                f.all();
                f.menu1();
            } else if (menu == 2) {
            	System.out.print("결제할 과일 이름을 입력하세요: ");
                String fruitName = scan.next();
                int total = f.pay(fruitName);
                if (total > 0) {
                    System.out.println("총 결제 금액은 " + total + "원입니다.");
                    f.menu2();
                } else {
                    System.out.println("잘못된 과일 이름입니다.");
                }
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

//package quiz;
//
//import java.util.Scanner;
//
//public class FruitQuiz {
//
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        Fruit f = new Fruit();
//        boolean run = true;
//
//        System.out.println("휴먼과일가게입니다.");
//
//        while (run) {
//            printMainMenu();
//            String input = scan.next();
//
//            try {
//                int menu = Integer.parseInt(input);
//
//                switch (menu) {
//                    case 1:
//                        handleOrder(f);
//                        break;
//                    case 2:
//                        handlePayment(f, scan);
//                        run = false;
//                        break;
//                    case 3:
//                        f.menu3();
//                        run = false;
//                        break;
//                    default:
//                        System.out.println("1~3 사이의 숫자를 입력하세요!");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("숫자만 입력 가능합니다!");
//            }
//        }
//    }
//
//    // 메서드 분리: 메뉴 출력
//    public static void printMainMenu() {
//        System.out.println("\n-----------------------------------------");
//        System.out.println("어떤걸 도와드릴까요?");
//        System.out.println("1.주문,  2.결제,  3.종료");
//        System.out.print("선택 > ");
//    }
//
//    // 메서드 분리: 주문
//    public static void handleOrder(Fruit f) {
//        f.all();     // 메뉴판 출력
//        f.menu1();   // 주문 처리
//    }
//
//    // 메서드 분리: 결제
//    public static void handlePayment(Fruit f, Scanner scan) {
//        System.out.print("결제할 과일 이름을 입력하세요: ");
//        String fruitName = scan.next();
//        int total = f.pay(fruitName);
//        if (total > 0) {
//            System.out.println("총 결제 금액은 " + total + "원입니다.");
//            f.menu2();
//        } else {
//            System.out.println("잘못된 과일 이름입니다.");
//        }
//    }
//}
