# Jobify: Plataforma de Gestión de Empleo

## Descripción del Proyecto
Jobify es una plataforma desarrollada en **Java Spring Boot** que permite a las empresas publicar ofertas de empleo y a los usuarios aplicar a dichas ofertas de manera eficiente. Este sistema está diseñado para proporcionar una experiencia intuitiva tanto para las compañías como para los aspirantes, integrando funcionalidades robustas para gestionar el ciclo completo de publicación y aplicación.

---

## Características

### Para Compañías
- Crear, editar y eliminar ofertas de empleo.
- Gestionar postulaciones recibidas por oferta.
- Filtrar aplicaciones según el perfil del candidato.

### Para Usuarios
- Crear un perfil con datos personales y habilidades.
- Explorar ofertas de empleo disponibles.
- Aplicar a las ofertas de interés.
- Consultar el estado de las aplicaciones.

### Otras Funcionalidades
- Gestión de roles y permisos (administrador, compañía, usuario).
- Uso de tokens JWT para autenticación y autorización seguras.
- Validación de datos de usuarios y empresas.
- Integración con una base de datos PostgreSQL para almacenar información.

---

## Tecnologías Utilizadas
- **Backend**:
  - Java Spring Boot
  - Spring Security (autenticación y autorización)
  - JPA/Hibernate (gestión de datos)
- Base de Datos: PostgreSQL
- Testing: JUnit, Mockito
- Herramientas:
  - Maven (gestión de dependencias)
  - IntelliJ IDEA (IDE)
  - Postman (pruebas de API)

---

## **Estructura del Proyecto**
El proyecto sigue la arquitectura estándar de Spring Boot basada en capas:
1. **Controller**: Maneja las solicitudes HTTP y responde a los clientes.
2. **Service**: Contiene la lógica de negocio.
3. **Repository**: Interactúa con la base de datos.
4. **Model**: Define las entidades del sistema.

### Entidades Principales
- Company: Representa una empresa.
  - Campos: `id`, `name`, `email`, `description`, `location`.
- Offer: Representa una oferta de empleo.
  - Campos: `id`, `title`, `description`, `requirements`, `salary`, `companyId`.
- MyUser: Representa un usuario que puede aplicar a ofertas.
  - Campos: `id`, `name`, `email`, `skills`.
- ApplyOffer: Representa la aplicación de un usuario a una oferta de empleo.
  - Campos: `id`, `userId`, `jobOfferId`, `status`.

---

## **Requisitos Previos**
- JDK 17+
- PostgreSQL instalado y configurado.
- Maven** instalado para gestionar dependencias.



## Contribución
Si deseas contribuir:
1. Haz un fork del repositorio.
2. Crea una rama para tus cambios:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Envía un Pull Request.
