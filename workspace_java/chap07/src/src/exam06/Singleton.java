package src.exam06;

public class Singleton {
	
//	private static Singleton singleton = new Singleton();
		
//	private Singleton() {}
//	
//	
//		//Singleton getInstance() {
//		static Singleton getInstance() {
//			if(singleton == null) {
//				Singleton singleton = new Singleton();
//				
//			} return singleton;
//		}
		

		
		private static Singleton singleton = new Singleton();
		private Singleton() {}
		static Singleton getInstance() {
			return singleton;
		}
}