<h1>Market System API</h1>
<p>This project was built to facilitate the management of market products and users. It provides functionalities for querying market products, managing users, and monitoring system health and metrics.</p>

<h2>Features</h2>
<ul>
    <li>User registration and login with Spring Security</li>
    <li>Password encryption using BCrypt</li>
    <li>Role-based authorization with Spring Security</li>
    <li>Exception handling for cleaner error responses</li>
    <li>API documentation with Swagger</li>
    <li>Dashboards with Grafana and Prometheus for monitoring</li>
    <li>Actuator endpoints for info, metrics, health, and Prometheus integration</li>
</ul>

<h2>Technologies</h2>
<ul>
    <li>Spring Boot 3.3</li>
    <li>Spring Security 6.3</li>
    <li>Spring Data</li>
    <li>Maven</li>
    <li>Swagger</li>
    <li>Prometheus</li>
    <li>Grafana</li>
</ul>

<h2>Requirements</h2>
<ul>
    <li>Java 17</li>
    <li>Docker (recommended) or MySQL</li>
</ul>

<h2>Getting Started</h2>
<p>You can run the application in two ways:</p>
<ol>
    <li><strong>Using Docker Compose:</strong> Run <code>docker-compose up</code> in the terminal.</li>
    <li><strong>Manual MySQL Connection:</strong> Set up a local MySQL database and configure the connection details.</li>
</ol>

<h3>Initial Setup</h3>
<p>After running the API, you'll need to add users and roles to the database for the integration tests to work. Below is a suggested setup:</p>

<p><strong>Roles Table:</strong></p>
<ul>
    <li>ID: 1, name: ROLE_ADMIN</li>
    <li>ID: 2, name: ROLE_USER</li>
</ul>

<p><strong>Users Table:</strong></p>
<ul>
    <li>ID: 1, name: Murillo, username: Murillo, password: marques</li>
    <li>ID: 2, name: Fabiano, username: Fabiano, password: Santos</li>
</ul>

<p><strong>User_Role Table:</strong></p>
<ul>
    <li>role_id: 1, user_id: 1 (Assigns Murillo the ADMIN role)</li>
    <li>role_id: 2, user_id: 2 (Assigns Fabiano the USER role)</li>
</ul>

<p>With this configuration, the integration tests should pass successfully.</p>

<h3>Running Tests</h3>
<ul>
    <li>To run <strong>unit tests</strong>: <code>mvn test</code></li>
    <li>To run <strong>integration tests</strong>: <code>mvn test -Pintegration-tests</code></li>
    <li>To run <strong>all tests</strong>: <code>mvn test -Pall-tests</code></li>
</ul>

<h2>Usage</h2>
<h3>Swagger API Documentation</h3>
<p>To view the API documentation, run the application and navigate to: <br><code>http://localhost:8080/swagger-ui.html</code><br>All endpoints include detailed summaries and descriptions.</p>

<h3>Prometheus Monitoring</h3>
<p>Prometheus can be accessed at: <br><code>http://localhost:9090/</code></p>

<h3>Grafana Dashboards</h3>
<p>To view metrics in Grafana:</p>
<ol>
    <li>Run the application.</li>
    <li>Navigate to <code>http://localhost:3000</code> (default Grafana URL).</li>
    <li>Create a new data source using Prometheus with this URL: <code>http://prometheus:9090</code>.</li>
    <li>A recommended dashboard is <a href="https://grafana.com/grafana/dashboards/4701-jvm-micrometer/" target="_blank">JVM (Micrometer)</a>, which provides detailed JVM and application metrics.</li>
</ol>

<h2>Actuator Endpoints</h2>
<ul>
    <li><code>/actuator/info</code> – Basic application information</li>
    <li><code>/actuator/metrics</code> – Application metrics</li>
    <li><code>/actuator/health</code> – Health status</li>
    <li><code>/actuator/prometheus</code> – Prometheus metrics endpoint</li>
</ul>
