package sec01.exam01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionExam {

	public static void main(String[] args) {
		
		List list = new ArrayList();
		System.out.println(list.isEmpty());
		
		// 추가
		list.add(123);
		list.add("글씨");
		
		// 중간에 삽입
		list.add(1, "삽입");
		System.out.println(list);
		
		// 검색
		System.out.println(list.contains(123));
		
		System.out.println(list.get(1));
		String a = (String)list.get(1);
		String b = (String)list.get(1).toString();
		
		list.remove(1);
		System.out.println(list);
		
		list.clear();
		System.out.println(list);
		System.out.println(list.size());
		System.out.println(list.isEmpty());
		list = new ArrayList();
		
		System.out.println("=====================================");
		Map map = new HashMap();
		
		// 추가
		map.put("k1", "v1");
		map.put("list", list);
		map.put("k1", "v2"); // 키가 같으면 덮어쓴다
		
		// 가져오기
		String k1 = (String)map.get("k1");
		List list2 = (List)map.get("list");
		
		System.out.println(map);
		
		System.out.println("-------------------------------");
		list = new ArrayList();
		map = new HashMap();
		map.put("제목", "golden");
		map.put("가수", "헌트릭스");
		list.add(map);
		System.out.println(list);
		
		// 마지막에 값만 여러번 나온다면 new 해줘야 한다
		// 이게 얇은 복사 떄문이다 그래서 new해서 객체를 다르게 해서 깊은 복사를 한다면 해결
		map = new HashMap();
		map.put("제목", "나는 반딧불");
		map.put("가수", "황가람");
		list.add(map);
		System.out.println(list);
		
		// 제네릭 : <String, Integer>
		// 60점 짜리 설명 : 추가할 자료형을 제한한다. 
		List<String> list3 = new ArrayList<String>();
		list3.add("123");
		String s = list3.get(0);
		// 100점 짜리 설명 : 전달인자나 리턴타입에 자료형을 동적으로 변경한다.
		// GPT에 100점 문장: “컴파일 타임 타입 매개변수화로 타입 안전성과 재사용성을 제공, 런타임엔 타입 소거”
		// 제네릭은 코드 만들 땐 자료형을 비워두고, 쓸 땐 자료형을 정해서 잘못된 타입을 막아주는 기능이에요.
        // 실행할 땐 그 자료형 정보가 없어져요.
		
		// 제네릭에 원시타입은 사용할 수 없고, wrapper 클래스 사용
//		Map<String, int> map2 = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();
		map2.put("str", 123);
		int c = map2.get("str");
		
	}

}
