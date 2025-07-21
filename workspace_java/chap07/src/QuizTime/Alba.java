package QuizTime;

public class Alba {
	
	void CompoceHi(Compoce compoce) {
		System.out.println("어서오세요 손님 컴포즈입니다");
	}

	void StarbucksHi(Starbucks starbucks) {
		System.out.println("어서오세요 손님 스타벅스입니다");
	}
	
	
	
	void macro(Cafe cafe) {
		
		cafe.order();
		cafe.pay();
		cafe.make();
		cafe.serving();
		System.out.println("안녕히가세요");
		cafe.wash();
	}
}
