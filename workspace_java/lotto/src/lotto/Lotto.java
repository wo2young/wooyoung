package lotto;

import java.util.Arrays;
import java.util.Random;

public class Lotto {
    public static void main(String[] args) {
        int[] lo = new int[45];
        for (int i = 0; i < lo.length; i++) {
            lo[i] = i + 1;
        }

        Random rand = new Random();
        int number = lo.length;

        int[] result = new int[6]; // 뽑은 숫자 저장용

        for (int i = 0; i < 6; i++) { // 6번 반복
            int lotto = rand.nextInt(number);
            result[i] = lo[lotto]; // 뽑은 숫자를 result에 저장

            lo[lotto] = lo[number - 1];
            number--;
        }

        Arrays.sort(result); // 정렬하기

        // 출력
        for (int n : result) {
            System.out.print(n + " | ");
        }
    }
}