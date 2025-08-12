package sec01.exam02;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputExam {

	public static void main(String[] args) {
		
		String path = "c:\\temp";
//		String path = "c:/temp";
		String fileName = "stream.txt";
		
		OutputStream os = null;
		try {
			// 스트림 열기
//			os = new FileOutputStream(path + "/" + fileName);
			os = new FileOutputStream(path + System.getProperty("file.separator") + fileName);
			
			String data = "abc\n한글\n1234";
			
			byte[] datas = data.getBytes();
			
			// 쓰기
			os.write(datas, 0, datas.length);
			// 버퍼에 남은 데이터 모두 출력
			os.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		}
	}
		static void saveTxt(String data){
			String path = "c:\\temp";
//			String path = "c:/temp";
			String fileName = "stream.txt";
			// try-with-resources
			// close()를 자동으로 해준다
			try(OutputStream os = new FileOutputStream(path + System.getProperty("file.separator") + fileName);)
			{
				byte[] datas = data.getBytes();
				
				// 쓰기
				os.write(datas, 0, datas.length);
				// 버퍼에 남은 데이터 모두 출력
				os.flush();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}

}
