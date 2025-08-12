package sec01.exam08;

public class StringExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.gc();
		
		String s1 = "영일이삼사오육칠팔구삼사";
		
//		char c = s1.charAt(100); //범위 예외
		char c = s1.charAt(0);
		System.out.println("char c : "+ c);
		
		int i1 = s1.indexOf("삼사");
		System.out.println("i1 : " + i1);
		
		int i2 = s1.lastIndexOf("삼사");
		System.out.println("i2 : " + i2);
		
		int i3 = s1.indexOf("a");
		System.out.println("i3 : " + i3);
		
		// 이메일 양식 점검
		// @와 .이 하나 이상 있는가?
		String email = "todair@naver.com";
		int a1 = email.indexOf("@");
		int a2 = email.indexOf(".");
		if(a1 > 0) {
			if(a2 > a1) {
				System.out.println("이메일 맞음");
			} else {
			System.out.println("이메일에 .이 없어요"); 
			}; 
		} else {
			System.out.println("이메일에 @가 없어요");
		}
		
//		indexOf를 구현하는데 for문과 if문을 가지고 결과를 낸다
		// email에 @가 몇번째에 있는지 for, if
		int idx = -1;
		for(int i=0; i < email.length(); i++) {
			if( email.charAt(i) == '@') {
				idx = i;
				System.out.println("@에 위치는 : " + idx  + "+1 번쨰");
			}
		}
		String s2 = s1.replace("삼사", "34");
		System.out.println("s1: " + s1);
		System.out.println("s2: " + s2);
		// replace는 모두 바꿔준다
			
		String s3 = s1.substring(5, 8);
		System.out.println("s3: " + s3);
		
		// 주민번호로 남자인지 여자인지 출력
		String ssn = "123456-1234567";
		
		int start = ssn.indexOf("-") + 1;
		int end = start + 1;
		String s4 = ssn.substring(start, end);
		
		if( s4.equals("1") ||  s4.equals("3")) {
			System.out.println("남자입니다");
		} else {
			System.out.println("여자입니다");
		}
		
		// 문제1
		// blog.naver.co.kr에서 naver만 추출하기
		// 첫번쨰 .에 위치를 찾고 거기서 5글자 가져오기
		
		String blog = "blog.naver.co.kr";
		int start2 = blog.indexOf(".") + 1;
		int end2 = start2 + 5;
		System.out.println(blog.indexOf("."));
		blog.substring(start2, end2);
		System.out.println(blog.substring(start2, end2));
		
		// 문제2
		// 흘러가는 효과 전광판처럼
		// "Hello world "
		// "ello world H"
		// 음 이거는 배열로 만들어서 배열 2개로 해서 값을 밀어내는 방식으로 for문 돌려서
		
		String h1 = "Hello world";
		String h2 = "Hello world";
		for(int i=0; i<h1.length();i++) {
			
		}
		
		// 문제3 마스킹
		// humanec@naver.com 이걸
		// hu*****@naver.com 이케
		// love@naver.com
		// lo**@naver.com
		// 이것도 @에 위치를 찾고 3번째부터 @전까지에 글자 변경 for문으로
		
		String email2 = "humanec@naver.com";
		System.out.println(email2.indexOf("@")); 
		
		String em = email2.substring(2,email2.indexOf("@"));
		System.out.println(em);
		String star = "*";
		for(int i=0; i<em.length(); i++) {
			star +="*";
		}
		String ema = email2.replace(em, star);
		System.out.println(ema);

		String email3 = "love@naver.com";
		System.out.println(email3.indexOf("@")); 
		
		String em2 = email3.substring(2,email3.indexOf("@"));
		System.out.println(em2);
		String star2 = "*";
		for(int i=0; i<em2.length()-1; i++) {
			star2 +="*";
		}
		String ema2 = email3.replace(em2, star2);
		System.out.println(ema2);
		
		// 문제4 검색어 찾기
        // https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=1234&ackey=1oiioasi
        // 키 query의 값이 검색어
        // 검색어만 출력
		
		String s5 = " 글씨 중간공백 ";
		System.out.println("[" + s5 + "]" );
		System.out.println("[" + s5.trim() + "]" );
		
		String menu = "깁밥,라면,돈까스,제육덮밥";
		String[] menus = menu.split(",");
		System.out.println(menus.length);
		for(String m : menus) {
			System.out.println(m);
		}
		
		String url = "blog.naver.com";
		// split 정규 표현시을 사용(문자 이님에 주의)
//		String[] urls = url.split("."); 이건 안됨
//		String[] urls = url.split("\\."); // 방법1
		String[] urls = url.split("[.]"); // 방법2
		System.out.println(urls.length);
		
		String a = "a";
		a += "b";
		a = a + "c";
		
		// StringBuffer
		// 메오리를 효율적으로 사용한다
		// StringBuilder보다 쪼금 느림
		// 대신 스레드에 안전하다)Thread-safe)
		StringBuffer sb = new StringBuffer("a");
		sb.append("b");
		sb.append("c");
		String d = sb.toString();
		
		StringBuilder sbb = new StringBuilder("a");
		sbb.append("b");
		String d2 = sbb.toString();
	}	

}
