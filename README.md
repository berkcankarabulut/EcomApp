<h1>🛒 Spring E-Commerce API</h1>
<p>A modern RESTful <b>E-Commerce API</b> built with <b>Java Spring Boot</b></p>

<p>
  <img src="https://img.shields.io/badge/Java-17-orange" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-green" />
  <img src="https://img.shields.io/badge/Database-MySQL-blue" />
  <img src="https://img.shields.io/badge/Build-Maven-red" />
</p>

<hr/>

<h2>📋 Features</h2>
<ul>
  <li>✅ Product management (CRUD)</li>
  <li>✅ Product search & filtering</li>
  <li>✅ Image upload & display</li>
  <li>✅ Order management</li>
  <li>✅ Stock tracking</li>
  <li>✅ Transaction management</li>
  <li>✅ Clean Architecture</li>
</ul>

<hr/>

<h2>🛠️ Tech Stack</h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot 3.x</li>
  <li>Spring Data JPA</li>
  <li>PostgreSQL</li>
  <li>Maven</li>
  <li>Lombok</li>
</ul>

<hr/>

<h2>📡 API Endpoints</h2>

<h3>📦 Product Endpoints</h3>
<table>
  <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
  <tr><td>GET</td><td><code>/api/products</code></td><td>Get all products</td></tr>
  <tr><td>GET</td><td><code>/api/product/{id}</code></td><td>Get product details</td></tr>
  <tr><td>GET</td><td><code>/api/product/{id}/image</code></td><td>Get product image</td></tr>
  <tr><td>GET</td><td><code>/api/products/search</code></td><td>Search products</td></tr>
  <tr><td>POST</td><td><code>/api/product</code></td><td>Add new product</td></tr>
  <tr><td>PUT</td><td><code>/api/product/{id}</code></td><td>Update product</td></tr>
  <tr><td>DELETE</td><td><code>/api/product/{id}</code></td><td>Delete product</td></tr>
</table>

<h3>🛒 Order Endpoints</h3>
<table>
  <tr><th>Method</th><th>Endpoint</th><th>Description</th></tr>
  <tr><td>POST</td><td><code>/api/orders/place</code></td><td>Place an order</td></tr>
  <tr><td>GET</td><td><code>/api/orders</code></td><td>Get all orders</td></tr>
</table>

<hr/>

<h2>⚡ Installation</h2>

<ol>
  <li><b>Clone the repository</b>
    <pre><code>git clone https://github.com/berkcankarabulut/SpringEcom.git
cd SpringEcom</code></pre>
  </li>
  <li><b>Configure database</b> (<code>application.properties</code>)
    <pre><code>spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update</code></pre>
  </li>
  <li><b>Run the application</b>
    <pre><code>mvn clean install
mvn spring-boot:run</code></pre>
  </li>
</ol>

<hr/>

<h2>💻 Usage Examples</h2>

<h3>Place Order</h3>
<pre><code>POST /api/orders/place
{
    "customerName": "Berkcan Karabulut",
    "email": "berkcan@example.com",
    "items": [
        {
            "productId": 1,
            "quantity": 2
        }
    ]
}
</code></pre>

<h3>Search Product</h3>
<pre><code>GET /api/products/search?keyword=laptop</code></pre>

<hr/>

<h2>📁 Project Structure</h2>

<pre><code>src/main/java/com/berkcankarabulut/SpringEcom/
├── controller/
│   ├── OrderController.java
│   └── ProductController.java
├── model/
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   └── dto/
├── service/
│   ├── OrderService.java
│   └── ProductService.java
└── repo/
    ├── OrderRepo.java
    └── ProductRepo.java
</code></pre>

<hr/>

<h2>📜 License</h2>
<p>This project is licensed under the MIT License.</p>
