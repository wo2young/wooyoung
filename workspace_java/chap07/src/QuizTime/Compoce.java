package QuizTime;

public class Compoce extends Cafe {
	public Compoce(int price, String menu, String storeName, String supplies, String building, String staff) {
        super(price, menu, storeName, supplies, building, staff);
        price = 3000;
        menu = "아메리카노";
        storeName = "컴포즈커피";
        supplies = "빨대";
        building = "콩빌딩";
        staff = "김현민";
    }
	
	public Compoce() {};
}
