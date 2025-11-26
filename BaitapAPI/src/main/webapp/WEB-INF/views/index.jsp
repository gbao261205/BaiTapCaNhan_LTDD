<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product API Demo</title>
</head>
<body>
<h2>Spring Boot + JSP + REST API</h2>

<h3>View hiển thị trực quan</h3>
<ul>
    <li><a href="/categories">Xem tất cả danh mục</a></li>
    <li><a href="/products/top-sold">Top 10 sản phẩm bán nhiều nhất</a></li>
    <li><a href="/products/recent">10 sản phẩm mới (<= 7 ngày)</a></li>
</ul>

<h3>Các API (JSON) – nếu cần test bằng Postman:</h3>
<ul>
    <li>GET <code>/api/categories</code></li>
    <li>GET <code>/api/categories/{id}/products</code></li>
    <li>GET <code>/api/products/top-sold</code></li>
    <li>GET <code>/api/products/recent</code></li>
</ul>

</body>
</html>
