<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>10 sản phẩm mới</title>
</head>
<body>
<h2>10 sản phẩm được tạo trong 7 ngày gần đây</h2>

<c:if test="${empty products}">
    <p>Không có sản phẩm mới trong 7 ngày gần đây.</p>
</c:if>

<c:if test="${not empty products}">
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Danh mục</th>
            <th>Giá</th>
            <th>Số lượng bán</th>
            <th>Ngày tạo</th>
        </tr>
        <c:forEach var="p" items="${products}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>
                    <c:if test="${p.category != null}">
                        ${p.category.name}
                    </c:if>
                </td>
                <td>${p.price}</td>
                <td>${p.quantitySold}</td>
                <td>${p.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p><a href="/">Về trang chủ</a></p>
</body>
</html>
