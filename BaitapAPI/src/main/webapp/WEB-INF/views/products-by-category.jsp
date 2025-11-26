<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sản phẩm theo danh mục</title>
</head>
<body>
<h2>Sản phẩm thuộc danh mục:
    <c:choose>
        <c:when test="${category != null}">
            ${category.name}
        </c:when>
        <c:otherwise>
            (Không tìm thấy danh mục)
        </c:otherwise>
    </c:choose>
</h2>

<c:if test="${empty products}">
    <p>Không có sản phẩm nào.</p>
</c:if>

<c:if test="${not empty products}">
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng bán</th>
            <th>Ngày tạo</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.quantitySold}</td>
                <td>${p.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p><a href="/categories">Quay lại danh sách danh mục</a></p>
<p><a href="/">Về trang chủ</a></p>
</body>
</html>
