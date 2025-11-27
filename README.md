ARTE DIGITAL – Aplicación Móvil & Microservicios
📌 1. Nombre del Proyecto

ARTE DIGITAL

👥 2. Integrantes

Johan González
Francisco Guerra

📱 3. Funcionalidades de la Aplicación

🔐 Autenticación

Registro de usuarios.

Inicio de sesión (login con token JWT).

👤 Rol Usuario

Agregar servicios al carrito.

Vibración al agregar un producto (Haptic Feedback).

Comprar servicios (funcionalidad en desarrollo final).

Ver historial de compras (Mis Compras).

Visualización de servicios activos.

Carrusel dinámico mostrando servicios con precio menor a $10.000.

Conversión automática de precio USD → CLP utilizando una API externa de tipo de cambio.

🛠 Rol Administrador

Crear servicios.

Editar servicios.

Activar / Desactivar servicios.

Eliminar servicios.

Listar todos los usuarios registrados en la plataforma.

🌐 4. Endpoints Utilizados
☁ Microservicio Backend (Render)

Todos los endpoints comienzan con:
https://<tu-render-backend-url>/api

🔐 AUTH
Método	Endpoint	Descripción
POST	/auth/login	Iniciar sesión
POST	/auth/register	Registrar nuevo usuario

🛒 SERVICIOS
Método	Endpoint	Descripción
GET	/servicios/listar	Listar todos los servicios
GET	/servicios/{id}	Obtener un servicio por ID
POST	/servicios/crear	Crear nuevo servicio (admin)
PUT	/servicios/{id}	Editar servicio por ID
DELETE	/servicios/{id}	Eliminar servicio (admin)
PATCH	/servicios/{id}/activar	Activar
PATCH	/servicios/{id}/desactivar	Desactivar

👥 USUARIOS (ADMIN)
Método	Endpoint	Descripción
GET	/usuarios/listar	Listar todos los usuarios
GET	/usuarios/{id}	Obtener usuario por ID
PUT	/usuarios/{id}	Editar usuario

💳 COMPRAS
Método	Endpoint	Descripción
GET	/compras/miscompras	Ver compras del usuario
POST	/compras/registrar	Registrar compra

🌎 API Externa – Conversión USD → CLP

Se consume un endpoint externo de tipo de cambio para convertir precios a pesos chilenos en tiempo real.


▶️ 5. Pasos para Ejecutar el Proyecto

🟦 Backend (Microservicio Spring Boot – Render)

Clonar el repositorio:

git clone <URL_BACKEND>


Abrir en Spring Tools Suite / IntelliJ.

Configurar base de datos (PostgreSQL).

Ejecutar:

mvn spring-boot:run


Verificar disponibilidad:

https://<backend-render>/api/servicios/listar

📱 Aplicación Android (Kotlin + Jetpack Compose)

Clonar el repositorio:

git clone <URL_FRONTEND>


Abrir en Android Studio Ladybug / Koala.

Configurar:

Reemplazar URL base en el archivo RetrofitClient.kt si es necesario.

Conectar dispositivo o utilizar emulador.

Ejecutar:

Run > Run app


🎉 7. Conclusión

ARE DIGITAL es una plataforma completa que permite gestionar servicios artísticos digitales mediante un sistema seguro basado en microservicios, roles, y compras integradas. Desarrollado con Jetpack Compose, Spring Boot, PostgreSQL, Render, y API externa, ofrece una experiencia robusta tanto para usuarios como administradores.
