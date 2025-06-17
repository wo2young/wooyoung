package quiz;

import java.util.Scanner;

public class VendExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VendingMachine vend = new VendingMachine();
		vend.init();
		Scanner sc = new Scanner(System.in);
		boolean run = true;
<<<<<<< HEAD
		System.out.println("돈을 넣어주세요");
		int i = 0;
=======
//		System.out.println("돈을 넣어주세요");
>>>>>>> origin/main
		
		
		while(run) {
			System.out.println("돈을 넣어주세요");
			int money = sc.nextInt();
		    vend.setMoney(money);
			if(money == 0) {
				System.out.println("돈을 넣어주세요");
			}
		    
		    System.out.println("1.음료 선택, 2.잔돈 반환");

		    int select = sc.nextInt();
	    	if(select == 1) {
	    		sc.nextLine(); // 먼저 개행 문자 제거
    	    	System.out.println("음료 이름을 한글로 적어주세요.");
    	    	vend.print();
	    	    System.out.print("\n선택 >> ");
	    	    String name = sc.nextLine();  // 여기서 진짜 사용자 입력 받음
	    	    vend.selectDrink(name);       // 음료 선택
<<<<<<< HEAD
	    	    i++;
	    	} 
//	    	else if(select == 2) {
//		        vend.getPenny();
//		    } 
    	    else if(select == 2) {
		        System.out.println("종료");
=======
//	    	    System.out.println("종료");
	    	    
		    } 
//	    	else if(select == 2) {
//		        vend.getPenny();
//		    } 
	    	    else if(select == 2) {
>>>>>>> origin/main
		        System.out.println("잔돈 : " + money);
		        System.out.println("잔돈을 가져가 주세요");
		        System.out.println("종료");
//		        vend.getPenny();
		        run = false; // 종료
    	     } 
    	    else {
	        	System.out.println("잘못된 번호 입니다.");
	    	}
		}
	}
}

