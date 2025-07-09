// console.log('hello world');

window.addEventListener('load',init); // 이건 add로 추가할 수 있어 여러개 실행 가능
// window.onload = init; // 이건 inload 변수라서 한개만 실행 

function init(){
    const c = document.querySelector('#console')
    console.log('console :', c);

     const game = document.querySelector('#game')
        // console.log(game.computedStyleMap.top) 
        game.style.top = '10px';
        game.style.left = '10px';
    
    bind();
}
function off(){
    const btn4 = document.querySelector('#btn4');
    btn4.onclick = function(){
        window.close(); 
    }

}

function bind(){

    const msg = document.querySelector('#console')
    const btn1 = document.querySelector('#btn1');
    btn1.onclick = function(){
        msg.innerHTML += '<br>버튼1 클릭'
    }
    const btn2 = document.querySelector('#btn2');
    btn2.addEventListener('click', function(){
        msg.innerHTML += '<br>버튼2 클릭'
    })
    btn2.addEventListener('click', function(){
        msg.innerHTML += '<br>버튼2 클릭'
    })
    
    // 로그인 버튼을 누르면 
    // 아이디와 비밀번호가 비어있지 않다면
    //  아이디, 비밀번호 출력
    // 하나라도 안썼다면 
    // 아이디는 필수입니다 또는 비밀번호는 필수입니다 출력
//     const loCl = document.querySelector('#login');
//     loCl.onclick = 
//     function (아이디, 비밀번호){
//         const 아이디 = document.querySelector('#id')
//         if(아이디 != null){
//         console.log(id);
//         } else {
//             console.log('아이디는 필수입니다')
//         }
//         const 비밀번호 = document.querySelector('#pw') 
//         if(비밀번호 != null){
        
//         console.log(pw);
//         }
//         else{
//             console.log('비밀번호는 필수입니다')
//         }
// }

document.querySelector('#pw').addEventListener('click', btnClick);
document.querySelector('#pw').removeEventListener('click', btnClick);

    function btnClick() {
        const msg = document.querySelector('#console');
        msg.innerHTML += '<br>버튼3 클릭';
    }

    const loginBtn = document.getElementById('login');
    const idInput = document.getElementById('id');
    const pwInput = document.getElementById('pw');

    loginBtn.onclick = function () {
      const id = idInput.value.trim();  // trim이 뛰어쓰기 제거
      const pw = pwInput.value.trim();
      msg.innerHTML = ''; // 메시지 초기화

      if (id === '' && pw === '') {
        msg.innerHTML = '아이디와 비밀번호는 필수입니다.';
      } else if (id === '') {
        msg.innerHTML = '아이디는 필수입니다.';
      } else if (pw === '') {
        msg.innerHTML = '비밀번호는 필수입니다.';
      } else {
        msg.innerHTML = `아이디: ${id}<br>비밀번호: ${pw}`;
      }
    };

    // const kd = document.querySelector('#id').addEventListener( 'keydown', function(){
    //     console.log(kd);
    // });
    document.querySelector('#id').addEventListener( 'keyup', function(event){
        console.log(event); // 하나만 돌려줌
        console.log(event.KeyCode)
        if(event.KeyCode == 13){
            console.log('엔터');
            document.querySelector('#pw').focus()
        }
        // console.log(b);
        // console.log(c);
    });

    document.querySelector('#pw')
    .addEventListener('keyup', function(event){
        if(event.KeyCode == 13){
            document.querySelector('#login').click();
        }
    });
    // document.body
    // document.querySelector('body').addEventListener('keydown', function(){
    //     console.log(event.keyCode)
    //     const game = document.querySelector('#game')
    //     // console.log(game.computedStyleMap.prototype) 
    //     // 왼쪽
    //     if(event.keyCode == 37){
    //         game.style.left = (parseInt(game.style.left) - 10) + 'px'
    //     }else if(event.keyCode == 39){
    //         game.style.left = (parseInt(game.style.left) + 10) + 'px'
    //     }
    // })
   // ========================
  // 피카츄 이동 및 점프 로직
  // ========================

  let isJumping = false;

  document.body.addEventListener('keydown', function (event) {
    const game = document.querySelector('#game');
    const step = 10;

    let left = parseInt(game.style.left || '0');
    let top = parseInt(game.style.top || '300'); // 초기 top값

    // ← 왼쪽
    if (event.keyCode === 37) {
      game.style.left = (left - step) + 'px';

    // → 오른쪽
    } else if (event.keyCode === 39) {
      game.style.left = (left + step) + 'px';

    // ↑ 점프
    } else if (event.keyCode === 38 && !isJumping) {
      isJumping = true;

      let jumpHeight = 80;
      let jumpUpSpeed = 10;
      let jumpDownSpeed = 10;

      let upInterval = setInterval(() => {
        if (jumpHeight <= 0) {
          clearInterval(upInterval);

          let downInterval = setInterval(() => {
            if (jumpHeight >= 80) {
              clearInterval(downInterval);
              isJumping = false;
            } else {
              jumpHeight += jumpDownSpeed;
              game.style.top = (top + (80 - jumpHeight)) + 'px';
            }
          }, 20);

        } else {
          jumpHeight -= jumpUpSpeed;
          game.style.top = (top - (80 - jumpHeight)) + 'px';
        }
      }, 20);
    }
  });
}
















