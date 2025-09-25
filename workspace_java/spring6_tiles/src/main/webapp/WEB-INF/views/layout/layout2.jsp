<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><tiles:insertAttribute name="title"/></title>
<style>
	body{
		margin: 20px 30px;
	}
	div{
		border: 1px solid red;
	}
	.main_wrapper{
		display: flex;
	}
	.main_wrapper > .side {
		flex-grow: 2
	}
	.main_wrapper > .content {
		flex-grow: 8
	}
</style>
<script>
	window.onload = function(){
		alert(1)
	})

	window.addEventListnerner("load",function(){
		alert(22)
	})
</script>
</head>
<body>
	<!-- header -->
	<header>
		<tiles:insertAttribute name="header"/>
	</header>
	<!-- main -->
	<section class="main_wrapper">
		<!-- side -->
		<aside class="side">
			<tiles:insertAttribute name="side"/>
		</aside>
		<!-- content -->
		<article class="content">
			<tiles:insertAttribute name="content"/>
		</article>
	</section>
	<!-- footer -->
	<footer>
		<tiles:insertAttribute name="footer"/>
	</footer>
</body>
</html>