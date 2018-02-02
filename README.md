Spring Project for practice.

* Spring Framework
1. Java Configuration
2. Spring MVC 
3. Bean validation & Custom validation
4. Async method
5. Task Execution and Scheduling
6. RESTful Web Services
7. Spring Security
* Java Persistence API (JPA):
1. Object-Relational Mapping (ORM)
2. QueryDSL
3. NamedQuery
4. Inheritance - SINGLE_TABLE, JOINED
* Gradle
* JBoss (wildfly-10.1.0.Final)
* Slf4j + Log4j
* JUnit & Mockito 

Приложение - Блог:
------------------
- Создать категорию
- Изменить категорию
- Удалить категорию
- Блог может иметь иметь несколько категорий или не иметь вообще
- Пользователь может оставлять комментарии
- У комментариев есть иерархия(многоуровневая)
- Нотификация автору блога о комментарии
- Письмо автору блога о комментарии

Тех задание
------------------
- Сервер Tomcat
- База MySQL
- BackendJob, которая чистит нотификации старше 2 недель 
- Отправка письма должна быть асинхронной 
- Отдавать блоги по заданным датам/категориям
- Создать, редактировать, удалять, читать блоги
- JaxRS 
- Использовать QueryDSL
- Use gradle
- Use logging SLF4J with log4j
- Spring Web MVC
- Добавить Валидацию