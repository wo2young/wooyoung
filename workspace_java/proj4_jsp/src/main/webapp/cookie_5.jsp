<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.popup {
	border: 1px solid black;
	width: 200px;
	height: 200px;
}

.hide {
	display: none;
}
</style>
<script type="text/javascript">
	
	window.addEventListener('load', ()=>{
		
		showPopup()
		
		document.querySelector('#noShow')
		.addEventListener('click', function(event){
			
			// 쿠키 먹이는 것
			document.cookie = 'a=a1'
// 			document.cookie = 'abcd=1234;expires='+ new Date().toGMTString()
			document.cookie = 'abcd=1234'
	
			// 쿠키 가져오기
			console.log(document.cookie)

			const isCheck = event.target.checked;
			if( isCheck ){
				// 10초 뒤	
				const now = new Date()
				const after_10s = now.getSeconds() + 10
				now.setSeconds(after_10s) // 초 단위 재설정
				
				document.cookie = 'noShow=true;expires='+now.toGMTString()
			}
		})	
	})
	
	function showPopup(){
		console.log(document.cookie)
		
		const cookies = document.cookie.split('; ')
		for(let i=0; i<cookies.length; i++){
			
			const cookie = cookies[i].split('=')
			const name = cookie[0]
			const value = cookie[1]
		
			if(name == 'noShow' && value == 'true'){
				document.querySelector('.popup').classList.add('hide')
			}
		}
		
	}
</script>
</head>
<body>
	<div class="popup">
		공지입니다. <br>꼭 보세요<br>
		<br> <input type="checkbox" id="noShow">10초 동안 보지 않기
	</div>
</body>
</html>