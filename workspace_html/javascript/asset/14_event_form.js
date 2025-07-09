
window.addEventListener('load', bind)
function bind(){
    document.querySelector('#keyword')
    .addEventListener('focus', ()=>{
    const keyword = document.querySelector('#keyword')
    keyword.style.backgroundColor = 'yellow';
    document.querySelector('#keyword')
    })
    document.querySelector('#keyword')
    .addEventListener('blur', ()=>{
    const keyword = document.querySelector('#keyword')
    keyword.style.backgroundColor = '';
    })
    document.querySelector('#keyword')
    .addEventListener('input', ()=>{
    const keyword = document.querySelector('#keyword')
    
    const r = parseInt(Math.random()*256)
    const g = parseInt(Math.random()*256)
    const b = parseInt(Math.random()*256)
    const a = Math.random()
    
    keyword.style.backgroundColor = `rgba(${r},${g},${b},${a})`;
    })

    document.querySelector('#site')
    .addEventListener('change', ()=>{
            const value = document.querySelector('#site').value;
            console.log('change 이벤트의 value', value);
            const form = document.querySelector('#form');
            const input = document.querySelector('#search');

            if(value === '1'){
            // 네이버
            form.setAttribute('action', 'https://search.naver.com/search.naver');
            input.setAttribute('name', 'query');
        } else if( value === '2'){
            // 구글
            form.setAttribute('action', 'https://www.google.com/search')
            input.setAttribute('name', 'q');     // ✅ 구글은 q
        }

    })

    document.querySelector('#form')
        .addEventListener('submit', (event)=>{
            event.preventDefault();

            const value = document.querySelector('#keyword').value
            if(value.trim().length < 2){
                alert('검색어는 두 글자 이상입니다')
            }else{
                // submit
                document.querySelector('#form').submit();
            }
        })

        addEventListener('copy', (event)=>{
            event.preventDefault();
            alert('복사금지')
        })
        addEventListener('selectstart', (event)=>{
            event.preventDefault();
        })
    document.getElementById('btn').addEventListener('click', () => {
        const checked = document.querySelectorAll('.chk:checked');
        let total = 0;
    const selected = [...checked].map(el =>{  // 이새끼가 map이 안돼서 개오래 걸림
            const name = el.value            // 째는 배열이아니라서 [...]을 사용해야 함.
            const price = parseInt(el.dataset.price);
            total += price;
            return `${name} ${price}원`;

    });

        const resultDiv = document.getElementById('result');

        if (selected.length === 0) {
            resultDiv.innerText = '토핑 없음';
        } else {
            resultDiv.innerText = '토핑 : ' + selected.join(', ')
            +`\n total: ${total}원`;
        }  
    });
     const all = document.getElementById('chk_All');
        all.addEventListener('change', () => {
        const topping = document.querySelectorAll('.chk');
        topping.forEach(el => {
            el.checked = all.checked;
        });
         topping.forEach(el => {
            el = all;
        });
    });
}   