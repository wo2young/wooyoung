using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Calkulator
{
    public partial class Form1 : Form
    {
        // 첫 번째 피연산자 저장용.
        // 사용 흐름: [숫자 입력] → [연산자 클릭] 시점의 화면 숫자를 firstNum에 저장한다.
        int firstNum = 0;

        // 현재 선택된 연산자. "+", "-", "*", "/" 중 하나가 들어간다.
        // "=" 클릭 시, op와 firstNum, 그리고 현재 화면 숫자(label1.Text)를 이용해 계산한다.
        string op = null;

        // 다음 숫자 입력 시 화면 초기화 여부 플래그.
        // 연산자 버튼 클릭 직후 true가 되고, 그 다음 숫자 버튼을 누르는 순간 화면을 지우고 false로 되돌린다.
        bool isNext = false;

        public Form1()
        {
            InitializeComponent();
        }

        // [공통 패턴 안내]
        // 모든 숫자 버튼 핸들러는 다음 로직을 따른다:
        // 1) isNext가 true면 직전 연산 이후의 첫 입력이므로 label1을 비우고 isNext=false로 복원한다.
        // 2) label1.Text에 해당 숫자 문자열을 이어붙인다.
        // 3) Int32.Parse로 정수 변환 후 다시 문자열로 돌려놓아 선행 0("07" 등)을 제거한다.
        //    주의: 매우 긴 숫자를 누르면 Int32 범위를 넘을 수 있으므로 실무에선 길이 제한 또는 long/decimal 고려.

        private void btn9_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "9";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn8_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "8";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn7_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "7";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn6_Click(object sender, EventArgs e)
        {
            // 다른 숫자들과 동일하게 isNext 처리 포함.
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "6";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn5_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "5";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn4_Click(object sender, EventArgs e)
        {
            // 다른 숫자들과 동일하게 isNext 처리 포함.
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "4";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn3_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "3";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn2_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "2";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn1_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "1";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        private void btn0_Click(object sender, EventArgs e)
        {
            if (isNext)
            {
                label1.Text = "";
                isNext = false;
            }

            string str = label1.Text += "0";
            int i = Int32.Parse(str);
            label1.Text = "" + i;
        }

        // 연산자 버튼들은 공통적으로 아래 3가지를 수행:
        // (a) 현재 화면 숫자(label1.Text)를 firstNum에 보관
        // (b) 선택된 연산자를 op에 기록
        // (c) isNext = true로 만들어 다음 숫자 입력 시 화면을 비워 새 피연산자 입력으로 전환
        // 주의: 연산자 버튼을 연달아 누르는 패턴(예: 5 + - 3)은 현재 로직에선 "마지막으로 누른 연산자"로 덮어쓴다.
        //       즉시 연산(연속 계산)을 원하면 연산자 클릭 시 직전 op를 처리하도록 상태머신을 확장해야 한다.

        private void btn_dv_Click(object sender, EventArgs e)
        {
            firstNum = int.Parse(label1.Text);
            op = "/";
            isNext = true;
        }

        private void btn_mul_Click(object sender, EventArgs e)
        {
            firstNum = int.Parse(label1.Text);
            op = "*";
            isNext = true;
        }

        private void btn_mi_Click(object sender, EventArgs e)
        {
            firstNum = int.Parse(label1.Text);
            op = "-";
            isNext = true;
        }

        private void btn_pl_Click(object sender, EventArgs e)
        {
            firstNum = int.Parse(label1.Text); // 현재 화면 숫자를 저장
            op = "+";                          // 연산자 저장
            isNext = true;                     // 다음 숫자 입력 시 화면 초기화
        }

        // "=" 버튼: 저장된 firstNum, op, 그리고 현재 화면 숫자(두 번째 피연산자)로 결과를 계산한다.
        // 현재는 int 기반 정수 연산이다. "/"는 정수 나눗셈이므로 소수점은 버려진다.
        // 주의: 0으로 나누기 시 예외가 발생할 수 있으므로, 실무에선 가드가 필요하다.
        private void btn_eq_Click(object sender, EventArgs e)
        {
            int result = 0;

            // 현재 화면 숫자 파싱. 빈 문자열 등 비정상 입력은 본 예제 구조에선 발생하지 않지만
            // 실무에선 TryParse 사용 및 입력 길이/형식 제어를 권장한다.
            int right = Int32.Parse(label1.Text);

            if (op == "+")
            {
                result = firstNum + right;
            }
            else if (op == "-")
            {
                result = firstNum - right;
            }
            else if (op == "*")
            {
                result = firstNum * right;
            }
            else if (op == "/")
            {
                // 0으로 나누기 보호는 현재 미구현. 아래 주석 참고.
                // if (right == 0) { MessageBox.Show("0으로 나눌 수 없습니다."); return; }
                result = firstNum / right;
            }

            // 결과를 화면에 표시. isNext를 true로 두면, 다음 숫자 입력 시 화면이 비워지며 새 입력이 시작된다.
            label1.Text = "" + result;
            isNext = true;

            /*
            (참고) 이 구역의 간략 정리는 파일 맨 아래 "전체 정리" 섹션을 참조.
            */
        }

        // "C" (Clear) 버튼: 현재 화면과 상태를 초기화한다.
        private void btn_cl_Click(object sender, EventArgs e)
        {
            label1.Text = "0"; // 화면 초기화

            op = null;         // 대기 상태로 전환
            isNext = false;    // 다음 입력에서 화면을 비우지 않도록

            // 권장: firstNum도 함께 초기화해 과거 값 잔존 가능성을 원천 제거.
            // firstNum = 0;
        }

        /*
        ================================
        전체 정리 (핵심 개념 및 개선 포인트)
        ================================

        [현재 구조 요약]
        - 숫자 버튼: label1.Text에 숫자 추가 → Int32.Parse → 문자열로 재표시(선행 0 제거 목적).
        - 연산 버튼(+,-,*,/): firstNum에 현재 화면 값 저장, op에 연산자 저장, isNext=true로 전환.
        - "=" 버튼: firstNum (좌측 피연산자), op, 현재 화면 값(우측 피연산자)으로 연산 수행 후 결과 표시.
        - 자료형: int(정수). "/"는 정수 나눗셈이므로 소수점 이하가 버려진다.

        [중요/실무 빈도 높음] 입력 처리 일관성
        - 모든 숫자 버튼에서 isNext 처리(연산 직후 첫 입력 시 화면 비움)를 동일하게 유지해야 한다.
        - 본 주석에서 btn4/btn6에도 isNext 처리를 추가해 일관성을 맞춤.

        [중요] 0으로 나누기 가드
        - "/" 연산 시 우변(right)이 0이면 예외가 발생한다.
        - "=" 처리에서 if (right == 0) { MessageBox.Show(...); return; } 가드를 권장.

        [중요] 정수 vs 실수
        - 현재 int로 구현되어 소수점 결과를 표현하지 못한다.
        - 실무에서 계산기처럼 동작하려면 double/decimal로 전환하고, 표시 포맷(자릿수, 반올림) 정책을 정해야 한다.

        [실무 빈도 높음] 입력 길이/범위 제한
        - Int32.Parse는 범위를 넘어가면 예외가 난다.
        - label1.Text 길이를 제한하거나 long/decimal 사용, TryParse로 방어하는 설계가 필요.

        [개선 권장] 중복 제거(유지보수성)
        - 숫자 버튼 핸들러가 거의 동일하다. AppendDigit(string d) 같은 공통 함수로 묶어 중복을 제거하면 유지보수에 유리.

        [선택] 연속 계산(즉시 연산) 지원
        - 5 + 2 - 3 같은 흐름을 원하면, 연산자 버튼 클릭 시점에 직전 op를 즉시 계산하고 결과를 firstNum로 누적하는 방식으로 확장한다.

        [선택] Clear 범위
        - C 버튼에서 firstNum까지 0으로 초기화하면 과거 상태 잔존 가능성을 더 줄일 수 있다.
        */
    }
}
