package download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String filename = request.getParameter("filename");
		String path = "c:\\tmp\\upload";
		File file = new File(path +"\\"+ filename);
		
		// 브라우저 캐시(메모리)를 사용하지 않도록 설정
		response.setHeader("Cache-Control", "no-cache");
		// 지금 응답이 첨부파일이라는 것
		// 그리고 그 파일 명이 뭐라는 것
		response.addHeader("Content-disposition", "attachment; fileName="+ filename);
//		response.addHeader("Content-disposition", "attachment; fileName=a_txt");
		
		// 파일 읽기 시작
		InputStream is = new FileInputStream(file);
		// 메모리로 퍼 올릴 바가지 크기 설정
		// 버퍼 사이즈 설정
		byte[] BUF = new byte[1024 * 8]; // 보통 8kB
		
		// 브라우저로 내보낼 객체
		OutputStream os = response.getOutputStream();
		
		// 얼마나 읽었는가
		int count = -1;
		
		// 바가지 크기 만큼 읽음
		// 읽을 것이 없으면 -1
		while( (count = is.read(BUF)) != -1 ) {
			// 브라우저로 내보내기
			// 0 : offset 지금 읽은 것에서 얼마나 건너 뛸지
			// count : 보낼 Byte 수
			os.write(BUF, 0, count);
		}
		
		os.flush();
		os.close();
		is.close();
	}

}