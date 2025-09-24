<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>emp2 회원 목록</title>
<style>
    body {
        font-family: "Segoe UI", Arial, sans-serif;
        background: #f4f6f8;   /* 부드러운 회색 배경 */
        margin: 20px;
        color: #222;           /* 글자는 거의 검정 */
    }
    form {
        margin-bottom: 20px;
    }
    select, input[type="text"], input[type="submit"], button {
        padding: 6px 10px;
        margin: 5px 0;
        border: 1px solid #bbb;
        border-radius: 4px;
        font-size: 14px;
        background: #fff;
    }
    input[type="submit"], button {
        background: #444;      /* 어두운 회색 */
        color: white;
        border: none;
        cursor: pointer;
        transition: 0.2s;
    }
    input[type="submit"]:hover, button:hover {
        background: #222;      /* 더 진한 회색 */
    }
    table {
        width: 100%;
        border-collapse: collapse;
        background: white;
        margin-top: 10px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.08);
    }
    table th, table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
    }
    table th {
        background: #666;      /* 진회색 헤더 */
        color: white;
    }
    table tr:nth-child(even) {
        background: #f9f9f9;
    }
    table tr:hover {
        background: #efefef;
    }
    hr {
        margin: 20px 0;
        border: none;
        border-top: 1px solid #ccc;
    }
    a button {
        background: #555;      /* 회색 계열 버튼 */
    }
    a button:hover {
        background: #333;
    }
</style>
</head>
<body>
<!-- 검색 폼 -->
<div>
    <form method="get" action="search">
        <select name="type">
            <option value="1" <c:if test="${empDTO.type eq 1}">selected</c:if>>ename</option>
            <option value="2" <c:if test="${empDTO.type eq 2}">selected</c:if>>job</option>
            <option value="3" <c:if test="${empDTO.type eq 3}">selected</c:if>>ename and job</option>
            <option value="4" <c:if test="${empDTO.type eq 4}">selected</c:if>>sal이하</option>
        </select>
        <input type="text" name="keyword" value="${empDTO.keyword }">
        <input type="submit" value="검색">
    </form>
</div>
<hr>

<!-- 회원가입 버튼 -->
<div>
    <a href="join"><button type="button">회원 가입</button></a>
</div>
<hr>

<!-- 회원 목록 테이블 -->
<form method="get" action="choice">
    <table>
        <tr>
            <th>선택</th>
            <th>empno</th>
            <th>ename</th>
            <th>sal</th>
            <th>job</th>
        </tr>
    <c:if test="${empty list}">
        <tr>
            <td colspan="5">조회 내역이 없습니다</td>
        </tr>
    </c:if>
    <c:if test="${not empty list}">
        <c:forEach var="empDTO" items="${list }">
        <tr>
            <td><input type="checkbox" name="empnos" value="${empDTO.empno }"></td>
            <td>${empDTO.empno }</td>
            <td><a href="empDetail?empno=${empDTO.empno }">${empDTO.ename }</a></td>
            <td>${empDTO.sal }</td>
            <td>${empDTO.job }</td>
        </tr>
        </c:forEach>
    </c:if>
    </table>
    <input type="submit" value="선택 조회">
</form>
</body>
</html>
