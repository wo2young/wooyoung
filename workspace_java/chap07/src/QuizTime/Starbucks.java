package QuizTime;

public class Starbucks extends Cafe {
	
//	public Starbucks() {}
	public Starbucks(int price, String menu, String storeName, String supplies, String building, String staff) {
        super(price, menu, storeName, supplies, building, staff);
        price = 5000;
        menu = "아메리카노";
        storeName = "스타벅스점";
        supplies = "빨대";
        building = "킹빌딩";
        staff = "김현민";
    }
	
	public Starbucks() {};
//	@Override
////	public void 
	
	
}
