window.addEventListener('load', bind)

function bind() {
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

    // btn 클릭 이벤트
    const keyword = document.querySelector('#keyword');
    const form = document.querySelector('#form');
    const input = document.querySelector('#search');
    const site = document.querySelector('#site');
    const resultDiv = document.getElementById('result');
    const all = document.getElementById('chk_All');  // 모두 선택 체크박스
    const toppings = document.querySelectorAll('.chk');  // 개별 체크박스들
    const btn = document.getElementById('btn');
    
    btn.addEventListener('click', () => {
        const checked = document.querySelectorAll('.chk:checked'); // query는 이게
        let total = 0;
        const selected = [...checked].map(el => {  // 이새끼가 map이 안돼서 개오래 걸림
            const name = el.value            // 째는 배열이아니라서 [...]을 사용해야 함.
            const price = parseInt(el.dataset.price); // 문자로 넣어놔서 parseInt로 바꿈
            total += price; // 이것도 아무생각앖이 쓰다가 위에다가 초기화식 만듬
            return `${name} ${price}원`; // 배열에 한번에 넣기
        });

        if (selected.length === 0) {
            resultDiv.innerText = '토핑 없음';
        } else {
            resultDiv.innerText = '토핑 : ' + selected.join(', ') + `\n total: ${total}원`;
        }

        all.checked = (checked.length === toppings.length); // 모두 체크되었을 경우 "모두 선택"도 체크
        // 보충: 체크된 개수가 전체 개수와 같을 때만 전체선택 체크박스를 체크함
    });

    const topping = document.querySelectorAll('.chk');

    all.addEventListener('change', () => { // 갓갓 에로우 형님
        topping.forEach(el => { // forEach는 아직 이해 안감
            el.checked = all.checked;
        });
        // 보충: all.checked 값(true/false)을 그대로 개별 체크박스에 반영하는 구조
    });

    // 개별 체크박스를 클릭할 때마다 "모두 선택" 체크박스 상태 갱신
    toppings.forEach(el => {
        el.addEventListener('change', () => {
            const checkedCount = document.querySelectorAll('.chk:checked').length;
            all.checked = (checkedCount === toppings.length);
            // 보충: 개별 체크 하나라도 해제되면 자동으로 "모두 선택"도 해제됨
        });
    });
}



// ------------------------------
// [실무 기준 중요 포인트 ★★★]
// ------------------------------

// ★★★ NodeList는 배열이 아님
// → map, forEach 등을 쓰려면 [...NodeList]로 전개해서 배열로 만들어야 한다

// ★★★ dataset에서 가져온 값은 항상 문자열(string)
// → parseInt(el.dataset.price) 또는 Number(el.dataset.price)로 숫자 변환 필수

// ★★★ 이벤트 리스너는 중복해서 등록하면 안 됨
// → 같은 이벤트(copy, selectstart 등)는 한 번만 addEventListener 해야 한다

// ★★★ forEach에서 el => {...} 구조는 콜백함수임
// → el은 해당 배열의 현재 요소이며, 외부 변수와 충돌 안 나게 잘 써야 함

// ★★★ const 변수 선언은 한 번만 해야 함
// → 같은 변수(all, resultDiv 등) 여러 번 const로 선언하면 에러 발생
