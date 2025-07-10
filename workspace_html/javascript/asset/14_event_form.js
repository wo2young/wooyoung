window.addEventListener('load', bind)

function bind() {
    const keyword = document.querySelector('#keyword');
    const form = document.querySelector('#form');
    const input = document.querySelector('#search');
    const site = document.querySelector('#site');
    const resultDiv = document.getElementById('result');
    const all = document.getElementById('chk_All');  // 모두 선택 체크박스
    const toppings = document.querySelectorAll('.chk');  // 개별 체크박스들
    const btn = document.getElementById('btn');

    // focus 이벤트
    keyword.addEventListener('focus', () => {
        keyword.style.backgroundColor = 'yellow';
    });

    // 포커스 해제
    keyword.addEventListener('blur', () => {
        keyword.style.backgroundColor = '';
    });

    // 배경을 랜덤색으로 바꾸는 input 이벤트
    keyword.addEventListener('input', () => {
        const r = parseInt(Math.random() * 256);
        const g = parseInt(Math.random() * 256);
        const b = parseInt(Math.random() * 256);
        const a = Math.random();
        keyword.style.backgroundColor = `rgba(${r},${g},${b},${a})`;
    });

    // select 이벤트
    site.addEventListener('change', () => {
        const value = site.value;
        console.log('change 이벤트의 value', value);

        if (value === '1') {
            // 네이버
            form.setAttribute('action', 'https://search.naver.com/search.naver');
            input.setAttribute('name', 'query');
        } else if (value === '2') {
            // 구글
            form.setAttribute('action', 'https://www.google.com/search');
            input.setAttribute('name', 'q');     // 구글은 q
        }
    });

    // submit 이벤트
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const value = keyword.value.trim();
        if (value.length < 2) {
            alert('검색어는 두 글자 이상입니다')
        } else {
            form.submit(); // 검색어 유효성 검사 후 제출
        }
    });

    // 복사 방지
    document.addEventListener('copy', (event) => {
        event.preventDefault();
        alert('복사금지');
    });

    // 선택 방지
    document.addEventListener('selectstart', (event) => {
        event.preventDefault();
    });

<<<<<<< HEAD
        addEventListener('copy', (event)=>{
            event.preventDefault();
            alert('복사금지')
        })
        addEventListener('selectstart', (event)=>{
            event.preventDefault();
        })
    document.getElementById('btn').addEventListener('click', () => {
        const checked = document.querySelectorAll('.chk:checked'); // query는 이게
        let total = 0;
    const selected = [...checked].map(el =>{  // 이새끼가 map이 안돼서 개오래 걸림
            const name = el.value            // 째는 배열이아니라서 [...]을 사용해야 함.
            const price = parseInt(el.dataset.price); // 문자로 넣어놔서 parseInt로 바꿈
            total += price; // 이것도 아무생각앖이 쓰다가 위에다가 초기화식 만듬
            return `${name} ${price}원`; // 배열에 한번에 넣기

    });

        const resultDiv = document.getElementById('result');
=======
    // btn 클릭 이벤트
    btn.addEventListener('click', () => {
        const checked = document.querySelectorAll('.chk:checked');
        let total = 0;

        const selected = [...checked].map(el => {
            // 이새끼가 map이 안돼서 개오래 걸림
            // 째는 배열이 아니라서 [...]을 사용해야 함.

            // 보충: NodeList는 배열 아님 → [...NodeList]로 배열로 바꿔줘야 map/filter 가능
            const name = el.value;
            const price = parseInt(el.dataset.price);
            total += price;
            return `${name} ${price}원`;
        });
>>>>>>> origin/main

        if (selected.length === 0) {
            resultDiv.innerText = '토핑 없음';
        } else {
            resultDiv.innerText = '토핑 : ' + selected.join(', ') + `\n total: ${total}원`;
        }

        // 모두 체크되었을 경우 "모두 선택" 체크박스도 체크되게
        all.checked = (checked.length === toppings.length);
    });
<<<<<<< HEAD
     const all = document.getElementById('chk_All'); // 역시 이게 더 편한다
        all.addEventListener('change', () => { // 갓갓 에로우 형님
        const topping = document.querySelectorAll('.chk');
        topping.forEach(el => { // forEach는 아직 이해 안감
            el.checked = all.checked; 
        });
         topping.forEach(el => { // 역시 해제는 만들기 쉽네
            el = all;
=======

    // "모두 선택" 체크박스를 클릭했을 때 개별 체크박스들 상태 변경
    all.addEventListener('change', () => {
        toppings.forEach(el => {
            el.checked = all.checked;
        });
    });

    // 개별 체크박스를 클릭할 때마다 "모두 선택" 체크박스 상태 갱신
    toppings.forEach(el => {
        el.addEventListener('change', () => {
            const checkedCount = document.querySelectorAll('.chk:checked').length;
            all.checked = (checkedCount === toppings.length);

            // 보충: 개별 체크 하나라도 해제되면 자동으로 "모두 선택"도 해제됨
>>>>>>> origin/main
        });
    });
}

/*
전체 정리:
★★★ [실무 필수] NodeList는 배열이 아니므로, map/filter 사용하려면 [...NodeList]로 배열 변환 필수
★★★ [실무 필수] "모두 선택" 체크박스와 개별 체크박스 상태 동기화 구현 중요 (UX 측면)
★★★ [실무 필수] form submit 이벤트에서 preventDefault 후 유효성 검사 후 submit 호출 권장
★★ addEventListener로 여러 이벤트 핸들러 등록 가능, 중복 등록 주의
★★ 복사 및 선택 방지 이벤트는 문서 단위로 걸어 사용자 행위 제한 가능
*/
