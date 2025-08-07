package sec02.exam01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ThrowsExam {
	
	public static void main(String[] args) {
		
		try {
			test();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		try {
			test2(-50);
		} catch (HumanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			String errorCode = e.getMessage();
			if(errorCode.equals("EV10")) {
				System.out.println("볼륨은 10 이하만");
			} else if(errorCode.equals("EV0")) {
				System.out.println("볼륨은 0 이상만");
			}	
		} 
		FileInputStream fis = null;
		try {
			 fis = new FileInputStream("c:\\temp\\tect.txt");
			// 뭔가 함
			// 예외가 발생할 수 있다
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// try-with-resources
		// close() 자동 실행
		// Closeable 인터페이스가 있는 클래스만 ()에 넣을 수 있다
		try(
			FileInputStream fis2 = new FileInputStream("c:\\temp\\tect.txt");
				){
			System.out.println("뭔가 함");
			System.out.println(fis2);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	static void test()  throws ClassNotFoundException, IOException {
		Class.forName("abc");
	}
	
	static void test2(int vol) throws HumanException {
		if(vol > 10) {
			throw new HumanException("EV10");
		} else if(vol < 0) {
			throw new HumanException("EV0");
		}
		System.out.println("vol : " + vol);
	}
	static void test3() {
		try {
			// 코딩
			"ㅁ".equals(null);	
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}
}
