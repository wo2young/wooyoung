package QuizTime;

import java.util.Arrays;

public class Cafe {
	
	// 필드
	 int price;
	 String menu;
	 String storeName;
	 String supplies;
	 String building;
	 String staff;
	 
	// 생성자
	 public Cafe(int price, String menu, String storeName, String supplies, String building, String staff) {
        this.price = price;
        this.menu = menu;
        this.storeName = storeName;
        this.supplies = supplies;
        this.building = building;
        this.staff = staff;
	    }
	 
	 public Cafe() {}
	 
	// 메서드
	public void open() {
		System.out.println("가게 문여는 중");
	}
	public void order() {
		System.out.println( menu + "주문");
	}
	public void pay() {
		System.out.println( price + "원 결제 완료");
	}
//	public void make() {
//		System.out.println( Arrays.toString(menu) + " 만들기");
//	}
	public void make() {
		System.out.println( menu + " 만들기");
	}
	public void serving() {
		System.out.println("주문하신" + menu + "나왔습니다");
	}
	public void wash() {
		System.out.println("설거지 좋아~");
	}
	public void close() {
		System.out.println("다 나가주세요");
	}
	
	
	
	
}
