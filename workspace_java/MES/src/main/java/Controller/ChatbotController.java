package Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/chatbot")   // http://localhost:8080/프로젝트명/chatbot
public class ChatbotController extends HttpServlet {

    // ✅ 램 프님이 새로 발급받은 Gemini API Key
    private static final String API_KEY = "AIzaSyBblRzs198MWHpZkt3ehGS4HCeGcE_t6X0";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        // 👉 JSP에서 전달된 메시지
        String userMessage = request.getParameter("message");
        if (userMessage == null) userMessage = "";

        // 👉 JSON 깨짐 방지
        String safeMessage = userMessage.replace("\"", "\\\"").replace("\n", "\\n");

        // ✅ Gemini API 요청 JSON (항상 한국어로 답변하도록 지시 추가)
        String requestJson =
            "{ \"contents\": [" +
            "  { \"role\": \"user\", \"parts\": [ { \"text\": \"당신은 반드시 한국어로만 답변하는 챗봇입니다.\" } ] }," +
            "  { \"role\": \"user\", \"parts\": [ { \"text\": \"" + safeMessage + "\" } ] }" +
            "]}";

        System.out.println("👉 보낼 JSON: " + requestJson);

        // 👉 Gemini API URL
        String apiUrl =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        // 👉 HTTP 연결 설정
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("X-goog-api-key", API_KEY);   // ✅ 헤더 방식
        conn.setDoOutput(true);

        // 👉 요청 전송
        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestJson.getBytes("UTF-8"));
        }

        int status = conn.getResponseCode();
        System.out.println("👉 Gemini 응답 코드: " + status);

        // 👉 응답 읽기
        InputStream inputStream = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) result.append(line);
        }

        System.out.println("👉 Gemini 응답: " + result);

        // 👉 클라이언트(JSP JS)로 전달
        response.getWriter().write(result.toString());
    }
}
