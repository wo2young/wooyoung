package maple;

import java.util.*;

// 게임 상태를 관리하는 클래스
class Game {
    private List<List<Integer>> valueBoard;
    private List<List<Integer>> ownerBoard;
    private boolean first;
    private boolean passed;

    public Game(List<List<Integer>> board, boolean first) {
        this.valueBoard = board;
        this.first = first;
        this.passed = false;

        ownerBoard = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < board.get(i).size(); j++) {
                row.add(-1);
            }
            ownerBoard.add(row);
        }
    }

    public int getMyTotalCells() {
        int myId = first ? 0 : 1;
        int cnt = 0;
        for (List<Integer> row : ownerBoard) {
            for (int owner : row) {
                if (owner == myId) cnt++;
            }
        }
        return cnt;
    }

    private boolean isValid(int r1, int c1, int r2, int c2) {
        int sum = 0;
        boolean top = false, bottom = false, left = false, right = false;

        for (int r = r1; r <= r2; r++) {
            for (int c = c1; c <= c2; c++) {
                int val = valueBoard.get(r).get(c);
                sum += val;

                if (val != 0) {
                    if (r == r1) top = true;
                    if (r == r2) bottom = true;
                    if (c == c1) left = true;
                    if (c == c2) right = true;
                }
            }
        }

        return (sum == 10) && top && bottom && left && right;
    }

    public int[] calculateMove(int myTime, int oppTime) {
        int n = valueBoard.size();
        int m = (n > 0 ? valueBoard.get(0).size() : 0);

        for (int r1 = 0; r1 < n; r1++) {
            for (int c1 = 0; c1 < m; c1++) {
                for (int r2 = r1; r2 < n; r2++) {
                    for (int c2 = c1; c2 < m; c2++) {
                        if (isValid(r1, c1, r2, c2)) {
                            return new int[]{r1, c1, r2, c2};
                        }
                    }
                }
            }
        }
        return null;
    }

    public int updateMove(int[] action, boolean isMyMove) {
        if (action == null) {
            passed = true;
            return 0;
        }

        int r1 = action[0], c1 = action[1], r2 = action[2], c2 = action[3];
        int id = (isMyMove ? (first ? 0 : 1) : (first ? 1 : 0));
        int count = 0;

        for (int r = r1; r <= r2; r++) {
            for (int c = c1; c <= c2; c++) {
                valueBoard.get(r).set(c, 0);
                if (ownerBoard.get(r).get(c) != id)
                    count++;
                ownerBoard.get(r).set(c, id);
            }
        }

        passed = false;
        return count;
    }

    public void updateOpponentAction(int[] action, int time) {
        updateMove(action, false);
    }

    // 🔓 valueBoard 접근용 getter 추가
    public List<List<Integer>> getValueBoard() {
        return valueBoard;
    }
}

// 메인 실행 코드
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = null;
        boolean first = false;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) continue;

            String[] parts = line.trim().split("\\s+");
            String command = parts[0];

            switch (command) {
                case "READY":
                    first = parts[1].equals("FIRST");
                    System.out.println("OK");
                    System.out.flush();
                    break;

                case "INIT":
                    List<List<Integer>> board = new ArrayList<>();
                    int expectedLength = -1;
                    for (int i = 1; i < parts.length; i++) {
                        List<Integer> row = new ArrayList<>();
                        for (char c : parts[i].toCharArray()) {
                            int val = Character.getNumericValue(c);
                            if (val >= 0 && val <= 9) {
                                row.add(val);
                            } else {
                                System.err.println("잘못된 문자 '" + c + "' 발견 → 0으로 대체됨");
                                row.add(0);
                            }
                        }
                        if (expectedLength == -1) {
                            expectedLength = row.size();
                        } else if (row.size() != expectedLength) {
                            System.err.println("경고: 행의 길이가 일치하지 않음 (모든 행이 같은 길이여야 함)");
                        }
                        board.add(row);
                    }
                    game = new Game(board, first);
                    break;

                case "TIME":
                    int myTime = Integer.parseInt(parts[1]);
                    int oppTime = Integer.parseInt(parts[2]);
                    int[] move = game.calculateMove(myTime, oppTime);
                    int count = game.updateMove(move, true);
                    String who = first ? "FIRST" : "SECOND";
                    if (move != null) {
                        System.out.printf("%s %d %d %d %d %d %d 내가 점령한 개수: %d\n",
                                who, move[0], move[1], move[2], move[3],
                                myTime, count, game.getMyTotalCells());
                    } else {
                        System.out.printf("%s -1 -1 -1 -1 %d 0 내가 점령한 개수: %d\n",
                                who, myTime, game.getMyTotalCells());
                    }
                    System.out.flush();
                    break;

                case "OPP":
                    int r1 = Integer.parseInt(parts[1]);
                    int c1 = Integer.parseInt(parts[2]);
                    int r2 = Integer.parseInt(parts[3]);
                    int c2 = Integer.parseInt(parts[4]);
                    int time = Integer.parseInt(parts[5]);

                    // 🔧 valueBoard에 안전하게 접근
                    List<List<Integer>> boardRef = game.getValueBoard();
                    int boardHeight = boardRef.size();
                    int boardWidth = (boardHeight > 0 ? boardRef.get(0).size() : 0);
                    if (r1 >= boardHeight || r2 >= boardHeight || c1 >= boardWidth || c2 >= boardWidth) {
                        System.err.println("오류: OPP 좌표가 보드 범위를 벗어났습니다.");
                        break;
                    }

                    int[] oppMove = (r1 == -1 ? null : new int[]{r1, c1, r2, c2});
                    game.updateOpponentAction(oppMove, time);
                    break;

                case "FINISH":
                    return;
            }
        }
    }
}
