window.addEventListener('load', bind)
function bind(){
    
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
    nmInput.addEventListener('keyup', function (event) {
        if (event.keyCode === 13) {
            btn.click(); // 보충: Enter로 로그인도 가능하게
        }
    });    
///////////////////////////////////////////////////////////////////////////////////////
    const oneInput = document.querySelectorAll('#one input')       
    const twoInput = document.querySelectorAll('#two input')       
    const chk = document.querySelector('#chk')

    chk.addEventListener('change', function() {
        if( this.checked ){
            oneInput.forEach((input, index) => {
                twoInput[index].value = input.value
            })
        }
        else {
            twoInput.forEach(input => input.value = '');
        }
    })
///////////////////////////////////////////////////////////////////////////////////////  
    const menu = document.querySelectorAll('#menu div');
    menu.forEach((item) => {
        item.addEventListener('click', function () {
            menu.forEach((el) => {
                el.style.color = 'grey';
                this.style.color = 'black';
            });
        });
    });
}
///////////////////////////////////////////////////////////////////////////////////////

