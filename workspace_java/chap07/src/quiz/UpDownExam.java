package quiz;

import java.util.Scanner;

public class UpDownExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UpDown game = new UpDown();
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		
		System.out.println("UpDown게임을 실행합니다.");
		System.out.println("정답은 1~9까지의 숫자중 랜덤"  );
		
		while(run) {
			System.out.print("선택 >> ");
			int menu = sc.nextInt();
			System.out.println();
			if(game.answer == menu) {
				game.correct(menu);
				run = false;
			}
			else if(game.answer < menu) {
				game.up(menu);
			}
			else if(game.answer > menu) {
				game.down(menu);
			}
				
		}
		
		

		}

}
