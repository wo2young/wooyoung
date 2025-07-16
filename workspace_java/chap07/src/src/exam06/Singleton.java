package src.exam06;

public class Singleton {

	// (1) static 멤버로 클래스 내부에서 단 하나의 인스턴스를 생성함
	private static Singleton singleton = new Singleton();

	// (2) 생성자를 private으로 설정해서 외부에서 new Singleton() 못하게 막음
	private Singleton() {}

	// (3) 외부에서 이 메서드를 통해서만 Singleton 객체를 얻을 수 있음
	static Singleton getInstance() {
		return singleton;
	}

	/* 🔸 아래는 참고용: lazy loading 방식 (요청 시 생성)
	// private static Singleton singleton;
	// private Singleton() {}

	// static Singleton getInstance() {
	//     if (singleton == null) {
	//         singleton = new Singleton(); // 처음 요청될 때 생성됨
	//     }
	//     return singleton;
	// }
	*/
	
	/* ========================== Singleton 패턴 정리 ==========================
	[1] 싱글톤(Singleton)이란?
	- 애플리케이션에서 단 하나의 객체만 생성되도록 보장하는 패턴
	- 대표적으로 DB 연결, 설정 관리, 로그 시스템 등에 자주 사용됨

	[2] 이 코드의 핵심 구성
	1. `private static Singleton singleton`  
	   → 클래스 내부에 자기 자신의 인스턴스를 static으로 하나만 만들고 저장함

	2. `private Singleton()`  
	   → 외부에서 new Singleton()으로 객체 생성 못 하게 막음

	3. `static Singleton getInstance()`  
	   → 이 메서드로만 인스턴스를 반환받을 수 있음

	[3] 사용 예
	```java
	Singleton s1 = Singleton.getInstance();
	Singleton s2 = Singleton.getInstance();
	System.out.println(s1 == s2); // true → 동일한 인스턴스
	[4] 실무에서 자주 쓰이는 이유 ★★★

	리소스 낭비 방지 (필요 이상으로 객체 안 만들게 막음)

	설정 값이나 상태가 하나로 유지되어야 할 때 강력함

	Spring 등 프레임워크에서도 Bean 관리 시 유사하게 작동

	[5] 확장 학습 필요 포인트
	★ 필수 개념 → 아래도 꼭 정리하길 추천

	lazy loading vs eager loading (지금 코드 = eager)

	멀티스레드 환경에서의 동기화 (synchronized, double-checked locking)

	enum을 사용한 싱글톤 구현 (Java 1.5 이상에서 가장 안전함)

	========================================================================= */
}
