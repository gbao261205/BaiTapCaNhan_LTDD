<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Danh mục</title>
</head>
<body>
<h2>Danh sách danh mục</h2>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Tên danh mục</th>
        <th>Xem sản phẩm</th>
    </tr>
    <c:forEach var="c" items="${categories}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>
                <a href="/categories/${c.id}/products">Xem sản phẩm</a>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="/">Về trang chủ</a></p>
</body>
</html>
