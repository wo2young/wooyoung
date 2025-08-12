package sec01.exam02;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; // (현재 예제에서는 Files.newBufferedReader를 사용하므로 선택)
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputExam {

    public static void main(String[] args) {

        String fullPath = "c:\\temp\\stream.txt";
        
        try(
            InputStream is = new FileInputStream(fullPath);
            ){
            
            // 퍼담을 바가지 크기 설정
            int BUFFER_SIZE = 1024 * 8;
//          int BUFFER_SIZE = 10;
//          int BUFFER_SIZE = 1;
            byte[] datas = new byte[BUFFER_SIZE];
            
            String data = "";
            int result = 0;
            
//          result = is.read(datas);
//          if(result != -1) {
//              data += new String(datas, 0, result);
//          }
//          while(result != -1) {
//              result = is.read(datas);
//              if(result != -1) {
//                  data += new String(datas, 0, result);
//              }
//          
//          }
            // 위에거 줄인게 밑에임
            while( (result = is.read(datas) ) != -1) {
                data += new String(datas, 0, result);
                System.out.println("-----------------");
                System.out.println(data);
            }
            is.close(); // try-with-resources로 자동 close되지만, 원본 유지

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}

/* ============================================================
   아래는 '개선/대안' 코드들을 같은 파일에 따로 추가한 것
   - 원본 코드는 그대로 두고, 보조 유틸/예제 클래스로 분리
   - 인코딩 명시, 문자열 누적 최적화, 대용량/소용량 대응 패턴 제공
   ============================================================ */

/** 읽기 패턴 모음 유틸 */
class ReadUtils {
    // 인코딩은 환경 의존성 제거를 위해 명시 (필요 시 교체)
    static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * 방식 1) BufferedReader로 문자 스트리밍
     * - 멀티바이트 안전, 대용량 파일에 유리
     * - StringBuilder로 누적 비용 최적화(O(n^2) 방지)
     */
    static String readByBufferedReader(String fullPath) throws Exception {
        final int BUFFER_SIZE = 8 * 1024; // 8KB 권장 기본값
        StringBuilder sb = new StringBuilder(BUFFER_SIZE);
        try (BufferedReader br = Files.newBufferedReader(Path.of(fullPath), CHARSET)) {
            char[] buf = new char[BUFFER_SIZE];
            int n;
            while ((n = br.read(buf)) != -1) {
                sb.append(buf, 0, n);
                // 청크 디버깅 필요 시:
                // System.out.println("----- chunk -----\n" + new String(buf, 0, n));
            }
        }
        return sb.toString();
    }

    /**
     * 방식 2) Files.readString으로 한 번에 읽기
     * - 작은~중간 크기 파일에 가장 간단한 코드
     * - 매우 큰 파일에는 비권장(메모리 사용량 큼)
     */
    static String readByNioAll(String fullPath) throws Exception {
        return Files.readString(Path.of(fullPath), CHARSET);
    }

    /**
     * 방식 3) 바이트 스트림 유지 + 개선
     * - 바이트를 전부 모은 뒤 마지막에 한 번만 디코딩
     * - 원본 로직 흐름 유지하되 성능/정확성 개선
     * - 대용량에는 메모리 사용량 증가 가능(스트리밍 권장)
     */
    static String readByInputStream_CollectBytesThenDecode(String fullPath) throws Exception {
        final int BUFFER_SIZE = 8 * 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        try (InputStream is = new FileInputStream(fullPath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE)) {
            int read;
            while ((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
                // 청크 디버깅 필요 시:
                // System.out.println("----- chunk(bytes) -----\n" + new String(buffer, 0, read, CHARSET));
            }
            return baos.toString(CHARSET);
        }
    }
}

/**
 * 예제 실행용 보조 클래스
 * - 원본 InputExam은 그대로 두고,
 *   이 클래스를 실행해서 개선 패턴을 개별 테스트 가능
 * - public이 아니므로 같은 파일 안에 함께 둘 수 있음
 */
class InputExam_Examples {
    public static void main(String[] args) {
        String fullPath = "c:\\temp\\stream.txt";
        try {
            String s1 = ReadUtils.readByBufferedReader(fullPath);
            System.out.println("=== BufferedReader ===\n" + s1);

            String s2 = ReadUtils.readByNioAll(fullPath);
            System.out.println("=== Files.readString ===\n" + s2);

            String s3 = ReadUtils.readByInputStream_CollectBytesThenDecode(fullPath);
            System.out.println("=== CollectBytesThenDecode ===\n" + s3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
전체 정리
- 에러 원인: 클래스 뒤에 import를 두면 문법 오류가 발생. 모든 import는 파일 상단에 배치.
- 원본 코드는 유지.
- 추가로 세 가지 개선 패턴을 같은 파일 하단에 별도 클래스로 제공.
- 선택 가이드:
  1) 대용량/멀티바이트 안전 → ReadUtils.readByBufferedReader
  2) 작은~중간 파일/간결함 → ReadUtils.readByNioAll
  3) 바이트 스트림 흐름 유지 개선 → ReadUtils.readByInputStream_CollectBytesThenDecode
*/
