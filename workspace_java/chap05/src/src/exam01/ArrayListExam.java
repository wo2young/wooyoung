package src.exam01;

import java.util.ArrayList;

public class ArrayListExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String [] menu = new String [2];
		menu [0] = "말차";
		menu [1] = "핫초코";
		
		String [] menu2 = menu;
		menu2 = new String[3];
		menu2 [0] = menu [0];
		menu2 [1] = menu [1];
		menu2 [2] = "레몬에이드";
		
		// ArrayList 사용법  
		// import 만들어야함
		ArrayList list = new ArrayList();
		list.add(1);// 0번지
		list.add("문자");// 1번지
		list.add(false);// 2번지
		
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		
		System.out.println(list.size());
		System.out.println(list);
		
		for(int i=0; i<list.size();i++) {
			System.out.println(list.get(i));
		}
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("첫번째");
		list2.add("두번째");
		
		for(String data : list2) {
			System.out.println(data);
		}
		
		//배열 쉽게하기
//		Arrays.sort(null); order by와 같이 정렬해준다.
		
		
		
		
		
		
	}

}
