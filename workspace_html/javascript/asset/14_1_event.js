window.addEventListener('load', bind)
function bind(){

    const btn = document.querySelector('#btn')
    const resultDiv = document.getElementById('result'); 

    btn.addEventListener('click',() => {
        const checked = document.querySelectorAll('.chk:checked')
        let total = 0;
        const selected = [...checked].map(el => {      
            const name = el.value;
            const price = parseInt(el.dataset.price); 
            total += price;
            return `${name} ${price}원`
            });

            if (selected.length === 0) {     
                resultDiv.innerText = '토핑 없음';
            } else{
                resultDiv.innerText = '토핑 : ' + selected.join(', ') + `\n total: ${total}원`;
            }
        })    
}


