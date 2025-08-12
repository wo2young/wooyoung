package sec01.exam08;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeExam {

	public static void main(String[] args) {
	
		long before = System.currentTimeMillis();
		
		for(int i=0; i<1000000; i++) {
//			System.out.println(i);
		}
		long after = System.currentTimeMillis();
		System.out.println("걸린시간 : " + (after - before) + "[ms]");
		System.out.println(after);
		System.out.println(Long.MAX_VALUE);
		
		Date date = new Date();
		System.out.println(date);
		
		String format = "yyyy년 MM월 dd일 HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String srtDate = sdf.format(date);
		System.out.println(srtDate);
		
	}

}
