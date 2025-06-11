package sec.exam1002;

import java.util.Scanner;

public class GptExam {

    /**
     * 두 원의 중심 좌표 (x1, y1), (x2, y2)와 반지름 r1, r2가 주어졌을 때
     * 두 원이 만나는 점의 개수를 반환하는 함수.
     *
     * @param x1 첫 번째 원의 x좌표
     * @param y1 첫 번째 원의 y좌표
     * @param r1 첫 번째 원의 반지름
     * @param x2 두 번째 원의 x좌표
     * @param y2 두 번째 원의 y좌표
     * @param r2 두 번째 원의 반지름
     * @return 교점 개수 (0, 1, 2, 또는 -1)
     */
    public static int countIntersectionPoints(int x1, int y1, int r1, int x2, int y2, int r2) {
        // 두 중심 사이의 거리 d 계산 (피타고라스 정리)
        double d = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

        // 두 반지름의 합과 차를 미리 계산
        double sum = r1 + r2;
        double diff = Math.abs(r1 - r2);

        // 부동소수점 비교를 위한 허용 오차
        double EPSILON = 1e-6;

        // 1. 중심 좌표가 같은 경우
        if (x1 == x2 && y1 == y2) {
            if (r1 == r2) {
                // 중심과 반지름이 같으면 두 원이 완전히 겹침 → 무한한 교점
                return -1;
            } else {
                // 중심은 같지만 반지름이 다르면 절대 만날 수 없음
                return 0;
            }
        } else {
            // 2. 중심이 다를 경우

            // 2-1. 두 원이 외접하거나 내접할 경우 → 교점 1개
            if (Math.abs(d - sum) < EPSILON || Math.abs(d - diff) < EPSILON) {
                return 1;
            }
            // 2-2. 두 원이 서로 다른 두 점에서 만남 → 교점 2개
            else if (diff < d && d < sum) {
                return 2;
            }
            // 2-3. 두 원이 아예 만나지 않음 → 교점 0개
            else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 테스트 케이스 수 입력 받기
        int T = sc.nextInt();

        // 테스트 케이스 수만큼 반복
        for (int i = 0; i < T; i++) {
            // 두 원의 좌표와 반지름 입력 받기
            int x1 = sc.nextInt(); // 첫 번째 원 중심 x좌표
            int y1 = sc.nextInt(); // 첫 번째 원 중심 y좌표
            int r1 = sc.nextInt(); // 첫 번째 원 반지름

            int x2 = sc.nextInt(); // 두 번째 원 중심 x좌표
            int y2 = sc.nextInt(); // 두 번째 원 중심 y좌표
            int r2 = sc.nextInt(); // 두 번째 원 반지름

            // 교점 개수를 출력
            System.out.println(countIntersectionPoints(x1, y1, r1, x2, y2, r2));
        }

        // 스캐너 닫기 (자원 해제)
        sc.close();
    }
}

