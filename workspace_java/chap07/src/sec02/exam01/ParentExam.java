package sec02.exam01;

public class ParentExam {

	public static void main(String[] args) {
		
		Parent1_1_1 p1_1_1 = new Parent1_1_1();
		
		Parent1_1 p1_1 = (Parent1_1)p1_1_1;
		p1_1 = p1_1_1; // 형 변환 생략 가능
		
		Parent1 p1 = p1_1;
		p1 = p1_1_1;
		
		Parent1 pp1 = new Parent1_1_1();
		/////////////////////////////////////
		Parent1_2 p1_2 = new Parent1_2();
		Parent1 ppp1 = p1_2;
		
		// 부모가 자식이 될 때는
		// 형변환 연산자 생략 불가능
		Parent1_2 pp1_2 = (Parent1_2)ppp1;
		
		// 런타임 runtime오류 : 실행해봐야 안다.
//		Parent1_2 ppp1_2 = (Parent1_2)pp1; 부모가 자식으로 가능건 가능하지만 
//		pp1이 p1_1_1이기 떄문에 오류가남 만약 앞에 (Parent1_1)이라면 가능함
		Parent1_1 ppp1_1 = (Parent1_1)pp1; // 이건 가능하다
		
//		Parent1 : {
//			Parent1_1 : {
//				Parent1_1_1
//			}
//			Parent1_2 : {
//				
//			}   이런 구조로 되어있음
//		}
	}
}
