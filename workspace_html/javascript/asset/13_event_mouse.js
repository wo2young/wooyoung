let view
window.onload = function(){
    view = document.querySelector("#view");

    const cursor = document.querySelector("#cursor");
    cursor.style.top = '-1000px'
    cursor.style.left = '-1000px'

    bind();
}
function bind(){

    view.innerHTML = '안녕?<br>'

    document.querySelector('#mouse')
    .addEventListener('mousedown', function(evt){
        view.innerHTML = 'mousedown 발생<br>' + view.innerHTML

        console.log(evt)
        // offset : DOM의 좌상단 기준
        // page : 스크롤에 관계없이 문서 좌상단 기준
        // client : 지금 보이는 화면 좌상단 기준
        view.innerHTML = `
        event.offsetX : ${evt.offsetX}<br> event.offsetY : ${evt.offsetY}<br>
        event.pageX : ${evt.pageX}<br> event.pageY : ${evt.pageY}<br>
        event.clientX : ${evt.clientX}<br> event.clientY : ${evt.clientY}<br>
        <br>
        ${view.innerHTML}`
    })

    document.querySelector('#mouse')
    .addEventListener('mouseup', function(){
        view.innerHTML = 'mouseup 발생<br>' + view.innerHTML
    })
    document.querySelector('#mouse')
    .addEventListener('mousemove', function(){
        // view.innerHTML += 'mousemove 발생<br>'
    })
    // 마우스 들어옴
    document.querySelector('#mouse')
    .addEventListener('mouseover', function(){
        view.innerHTML = 'mouseover 발생<br> + view.innerHTML'
         document.querySelector('#mouse').style.backgroundColor = 'yellow'
    })
    // document.querySelector('#mouse')
    // .addEventListener('mouseenter', function(){
    //     view.innerHTML = 'mouseenter 발생<br> + view.innerHTML'
   
    // })
    // 마우스 나감
    document.querySelector('#mouse')
    .addEventListener('mouseout', function(){
        view.innerHTML = 'mouseout 발생<br> + view.innerHTML'

        document.querySelector('#mouse').style.backgroundColor = 'white'
    })    
    // document.querySelector('#mouse')
    // .addEventListener('mouseleave', function(){
    //     view.innerHTML = 'mouseleave 발생<br> + view.innerHTML'
    // })

    // document.querySelector('body')
    // .addEventListener('mousemove', function(evt){
    //     const cursor = document.getElementById('cursor')

    //     cursor.style.top = evt.pageY+30 + 'px';
    //     cursor.style.left = evt.pageX+30 + 'px';
    // })
    ///////////////////////////////////////////////////////////////////////////////////
    const game = document.getElementById('game');

    let isDragging = false;
    let offsetX = 0;
    let offsetY = 0;

    game.addEventListener('mousedown', function (evt) {
        isDragging = true;
        // 마우스가 이미지 안에서 눌린 상대 좌표
        // (즉, 피카츄 안에서의 클릭 위치)
        offsetX = evt.offsetX;
        offsetY = evt.offsetY;
        game.style.cursor = 'grabbing';
    });

    document.addEventListener('mousemove', function (evt) {
        if (isDragging) {
        // [계산 원리]
        // 이미지의 새 위치 = 현재 마우스 위치 - 처음 클릭한 위치(mousedown에서 초기화)
        // → 그래야 마우스와 이미지가 자연스럽게 붙어 다님
            game.style.left = (evt.pageX - offsetX) + 'px';
            game.style.top = (evt.pageY - offsetY) + 'px';
        }
    });

    document.addEventListener('mouseup', function () {
        isDragging = false;
        game.style.cursor = 'grab';
    });
}
  