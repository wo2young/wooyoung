window.addEventListener('load', bind)

let cnt = 0;  // 고유한 체크박스 id 생성을 위한 카운터

function bind(){

    ///////////////////////////////////////////////////////////////////////////////////////
    // 1번 이름 입력 기능
    const btn = document.querySelector('#btn');
    const nmInput = document.getElementById('nm');
    const msg = document.getElementById('msg');

    btn.onclick = function () {
        const nm = nmInput.value.trim();
        if(nm === ''){
            msg.innerText = "이름을 입력해주세요"
        } 
        else {
            msg.innerText += `${nm}\n`
        };
    }

    // Enter 입력시 버튼 클릭 효과 추가 (사용자 UX 개선용)
    nmInput.addEventListener('keyup', function (event) {
        if (event.keyCode === 13) {
            btn.click(); // 보충: Enter로 신청도 가능하게
        }
    });    

    ///////////////////////////////////////////////////////////////////////////////////////
    // 2번 주문정보 같음 체크박스
    const oneInput = document.querySelectorAll('#one input')       
    const twoInput = document.querySelectorAll('#two input')       
    const chk = document.querySelector('#chk')

    chk.addEventListener('change', function() {
        if (this.checked) {
            oneInput.forEach((input, index) => {
                twoInput[index].value = input.value
            })
        } else {
            twoInput.forEach(input => input.value = '');
        }
    })

    ///////////////////////////////////////////////////////////////////////////////////////
    // 3번 메뉴 클릭 시 강조 효과
    const menu = document.querySelectorAll('#menu div');
    menu.forEach((item) => {
        item.addEventListener('click', function () {
            menu.forEach(el => el.style.color = 'grey'); // 초기화
            this.style.color = 'black'; // 현재 클릭된 메뉴 강조
        });
    });

    ///////////////////////////////////////////////////////////////////////////////////////
    // 6번 미구현 영역 (하려다 실패)
    const plus = document.querySelector('#plus');
    plus.addEventListener('click', function(){
        // TODO: 이후 구현
    })

    ///////////////////////////////////////////////////////////////////////////////////////
    // 6번 할일(TODO) 추가
    document.querySelector('#add')
        .addEventListener('click', function(){
            const todo = document.querySelector('#todo').value.trim();
            const wrap = document.querySelector('.todo-wrap');

            if (todo === '') return; // 공백 입력 방지

            const div = document.createElement('div')
            div.classList.add('todo')
            div.innerHTML = `
                <input type="checkbox" class="chk" id="chk_${cnt++}">
                <span>${todo}</span>
                <input type="button" class="del" value="삭제">
            `

            // 삭제버튼이 포함된 줄을 삭제해야함
            // 1. DOM 출력
            // 2. 줄 삭제

            // 이벤트가 발생한 DOM
            // console.log('event.target')
            // 그부모
            // console.log('event.target.parentNode')

            // 삭제 버튼 클릭 시 해당 줄 삭제 (보충: 사용자 확인도 추가)
            div.querySelector('.del').addEventListener('click', function(event){
                const isDel = confirm('삭제하시겠습니까?')
                if(isDel){
                    event.target.parentNode.remove()
                }
            })

            wrap.append(div)
            document.querySelector('#todo').value = '' // 입력창 초기화
        })

    ///////////////////////////////////////////////////////////////////////////////////////
    // 선택삭제 기능
    document.querySelector('#del_chk')
        .addEventListener('click', function(){
            // 체크된거 가져오기
            const chks = document.querySelectorAll('.chk:checked');

            // 부모 지우기
            chks.forEach((c) => {
                const isDel = confirm('삭제하시겠습니까?')
                if(isDel){
                    c.parentNode.remove()
                }
            })
        })
}


/*
==================== 전체 정리 (실무 중요도 포함) ====================

1. btn.onclick  
→ 이름 입력 시 목록에 추가, 빈 값이면 안내 메시지  
→ Enter 키 입력으로도 신청 가능하게 처리  
→ ★★☆

2. 주문자 정보 복사 (#chk)
→ 체크 시 수령자 → 배송자 자동 복사  
→ 해제 시 배송자 입력란 초기화  
→ ★★★ (실무 form 자동 입력 패턴)

3. 메뉴 클릭 강조
→ 클릭한 메뉴만 색상 강조  
→ 다른 메뉴는 초기화 처리  
→ ★★☆

4. 할일 추가 (#add)
→ 입력값이 공백이 아니면 <div> 생성 후 .todo-wrap에 추가  
→ 삭제 버튼에 개별 이벤트 연결  
→ 입력창 비우기까지 처리  
→ ★★★ (동적 DOM 조작 기본기)

5. 선택 삭제 (#del_chk)
→ class="chk" 체크된 항목만 찾아서 확인창 후 삭제  
→ querySelectorAll('.chk:checked') 실무에서 매우 자주 사용됨  
→ ★★★ (이벤트, 선택자 실무 핵심)

6. 기타
→ 체크박스에 고유 id 부여 (chk_0, chk_1...) → `cnt++`  
→ 삭제 확인창 confirm() 사용  
→ 사용자 주석 유지 + 보충 설명으로 협업에도 용이한 코드 구성  
→ ★★★ (전체 구조 실무 흐름과 유사)

=======================================================================
*/
