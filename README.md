# 🛒 Sistema de Gestión de Tienda - Spring Boot

## 📋 Descripción del Proyecto

Sistema web de gestión de tienda desarrollado con **Spring Boot** que permite la administración completa de pedidos y clientes. El proyecto implementa un sistema de roles diferenciados donde los clientes pueden gestionar sus propios pedidos y los administradores tienen control total sobre la gestión de clientes.

### Temática
Aplicación de e-commerce enfocada en la gestión de pedidos con dos niveles de usuario:
- **Clientes**: Pueden crear, editar y eliminar sus propios pedidos
- **Administradores**: Gestionan el sistema completo de clientes y tienen visibilidad total

---

## 🗂️ Diagrama Entidad-Relación

<img width="1707" height="922" alt="image" src="https://github.com/user-attachments/assets/a2167973-f422-44bc-8e7c-60cfdd893661" />



## 🚀 Instrucciones de Instalación y Ejecución

### Requisitos Previos
- **Java 17** o superior
- **Maven 3.6+**
- **MySQL 8.0+**  (para desarrollo)
- **Git**

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/tienda-springboot.git
cd tienda-springboot
```

2. **Configurar la base de datos**

Edita el archivo `src/main/resources/application.properties`:

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/tienda
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

# Puerto del servidor
server.port=8080
```

3. **Crear la base de datos**
```sql
CREATE DATABASE tienda_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

4. **Compilar el proyecto**
```bash
mvn clean install
```

5. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

O bien, ejecutar el JAR generado:
```bash
java -jar target/tienda-0.0.1-SNAPSHOT.jar
```

6. **Acceder a la aplicación**
```
http://localhost:8080
```

### Usuarios de Prueba

**Cliente:**
- Usuario: `cliente@tienda.com`
- Contraseña: `cliente123`

---

## ✨ Funcionalidades Implementadas

### 🔐 Autenticación y Autorización
- [x] Sistema de login con Spring Security
- [x] Registro de nuevos clientes
- [x] Roles diferenciados (CLIENTE, ADMIN)
- [x] Gestión de sesiones
- [x] Logout seguro

### 👤 Gestión de Clientes (Admin)
- [x] Listar todos los clientes
- [x] Ver detalles de cliente
- [x] Crear nuevo cliente
- [x] Editar información de cliente
- [x] Eliminar cliente
- [x] Buscar clientes por nombre


### 📦 Gestión de Pedidos (Cliente)
- [x] Ver mis pedidos
- [x] Crear nuevo pedido
- [x] Editar pedido 
- [x] Eliminar pedido
- [x] Ver detalles completos del pedido
- [x] Agregar productos al pedido
- [x] Modificar cantidades


### 📦 Gestión de Pedidos (Admin)
- [x] Ver todos los pedidos por cliente
- [x] Cambiar estado de pedidos


### 📊 Dashboard y Reportes
- [x] Panel de control para administradores
- [x] Estadísticas de ventas


### 🎨 Interfaz de Usuario
- [x] Diseño responsive con Bootstrap 5
- [x] Navegación intuitiva
- [x] Mensajes de confirmación y error
- [x] Validación de formularios
- [x] Paginación de listados

---

## 📸 Capturas de Pantalla

<img width="1919" height="1079" alt="Captura de pantalla 2025-12-04 190333" src="https://github.com/user-attachments/assets/e2efad0b-bc54-4d6d-8bba-6023ed7ef445" />

<img width="1918" height="1078" alt="image" src="https://github.com/user-attachments/assets/57e73584-e1c8-4d08-b19e-3acbeeaeed15" />

<img width="1919" height="1079" alt="Captura de pantalla 2025-12-04 190457" src="https://github.com/user-attachments/assets/4a450fd2-343a-431b-b27d-558bd3827ad6" />

<img width="1919" height="1079" alt="Captura de pantalla 2025-12-04 190522" src="https://github.com/user-attachments/assets/9f530580-b106-4af8-983f-a13bfda06e48" />

---

## 🛠️ Tecnologías Utilizadas

- **Backend:**
  - Spring Boot 3.x
  - Spring Data JPA
  - Spring Security
  - MySQL 

- **Frontend:**
  - Thymeleaf
  - Bootstrap 5
  - JavaScript

- **Herramientas:**
  - Maven
  - Git

---

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/tienda/
│   │   ├── config/          # Configuración de seguridad
│   │   ├── controller/      # Controladores REST y Web
│   │   ├── model/           # Entidades JPA
│   │   ├── repository/      # Repositorios Spring Data
│   │   ├── service/         # Lógica de negocio
│   │   ├── dto/             # Objetos de transferencia de datos
│   │                        # Manejo de excepciones
│   └── resources/
│       ├── templates/       # Plantillas Thymeleaf       
│       └── application.properties
└── test/                    
```

---

