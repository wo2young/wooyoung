let view

window.onload = function () {
    view = document.querySelector("#view");

    const cursor = document.querySelector("#cursor");
    cursor.style.top = '-1000px';
    cursor.style.left = '-1000px';

    bind();
}

function bind() {
    view.innerHTML = '안녕?<br>';

    document.querySelector('#mouse')
        .addEventListener('mousedown', function (evt) {
            view.innerHTML = 'mousedown 발생<br>' + view.innerHTML;

            console.log(evt);
            // offset : DOM의 좌상단 기준
            // page : 스크롤에 관계없이 문서 좌상단 기준
            // client : 지금 보이는 화면 좌상단 기준

            // 보충: 위 세 좌표계는 마우스 좌표 계산 시 많이 사용됨
            // 예: 드래그, 커서 표시, 절대 위치 지정 등

            view.innerHTML = `
            event.offsetX : ${evt.offsetX}<br> event.offsetY : ${evt.offsetY}<br>
            event.pageX : ${evt.pageX}<br> event.pageY : ${evt.pageY}<br>
            event.clientX : ${evt.clientX}<br> event.clientY : ${evt.clientY}<br>
            <br>
            ${view.innerHTML}`;
        });

    document.querySelector('#mouse')
        .addEventListener('mouseup', function () {
            view.innerHTML = 'mouseup 발생<br>' + view.innerHTML;
        });

    document.querySelector('#mouse')
        .addEventListener('mousemove', function () {
            // view.innerHTML += 'mousemove 발생<br>'
            // 보충: 너무 많이 발생해서 로그를 남기면 브라우저가 버벅일 수 있음
        });

    // 마우스 들어옴
    document.querySelector('#mouse')
        .addEventListener('mouseover', function () {
            view.innerHTML = 'mouseover 발생<br> + view.innerHTML';
            document.querySelector('#mouse').style.backgroundColor = 'yellow';
        });

    // document.querySelector('#mouse')
    //     .addEventListener('mouseenter', function () {
    //         view.innerHTML = 'mouseenter 발생<br> + view.innerHTML';
    //     });

    // 마우스 나감
    document.querySelector('#mouse')
        .addEventListener('mouseout', function () {
            view.innerHTML = 'mouseout 발생<br> + view.innerHTML';
            document.querySelector('#mouse').style.backgroundColor = 'white';
        });

    // document.querySelector('#mouse')
    //     .addEventListener('mouseleave', function () {
    //         view.innerHTML = 'mouseleave 발생<br> + view.innerHTML';
    //     });

    // document.querySelector('body')
    //     .addEventListener('mousemove', function (evt) {
    //         const cursor = document.getElementById('cursor');

    //         cursor.style.top = evt.pageY + 30 + 'px';
    //         cursor.style.left = evt.pageX + 30 + 'px';
    //     });

    ///////////////////////////////////////////////////////////////////////////////////

    const game = document.getElementById('game');

    let isDragging = false;
    let offsetX = 0;
    let offsetY = 0;

    game.addEventListener('mousedown', function (evt) {
        isDragging = true;

        // 마우스가 이미지 안에서 눌린 상대 좌표
        // (즉, 피카츄 안에서의 클릭 위치)

        // 보충: 이 좌표를 저장해둬야 마우스 기준 위치 계산 가능
        offsetX = evt.offsetX;
        offsetY = evt.offsetY;

        game.style.cursor = 'grabbing'; // 드래그 중인 느낌을 주는 커서
    });

    document.addEventListener('mousemove', function (evt) {
        if (isDragging) {
            // [계산 원리]
            // 이미지의 새 위치 = 현재 마우스 위치 - 처음 클릭한 위치(mousedown에서 초기화)
            // → 그래야 마우스와 이미지가 자연스럽게 붙어 다님

            // 보충: 마우스 따라 이미지가 자연스럽게 이동하려면 클릭된 지점을 기준으로 좌표 계산 필요
            game.style.left = (evt.pageX - offsetX) + 'px';
            game.style.top = (evt.pageY - offsetY) + 'px';
        }
    });

    document.addEventListener('mouseup', function () {
        isDragging = false;
        game.style.cursor = 'grab'; // 클릭 놓으면 다시 원래 커서
    });
}

/*
전체 정리:
★★★ [실무 필수] 마우스 이벤트의 좌표계 차이 이해하기 
                  (offset, page, client)는 드래그, 절대 위치 계산 시 매우 중요함
★★★ [실무 필수] mousedown 이벤트에서 클릭 상대 좌표(offsetX/Y)를 저장해두는 이유: 
                            드래그 중 마우스 위치와 이미지 위치를 정확히 맞추기 위함
★★★ [실무 필수] mousemove에서 마우스 위치 기준으로 이미지 위치를 재계산하여 자연스러운 드래그 구현
★★ addEventListener는 같은 이벤트에 대해 여러 핸들러 등록 가능, 중복 등록 주의
★★ move 이벤트는 빈번해서 로그 시 성능 저하 주의
*/
