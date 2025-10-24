<!--
**MorozovaAnastasiaa/MorozovaAnastasiaa** is a ✨ _special_ ✨ repository because its `README.md` (this file) appears on your GitHub profile.

Here are some ideas to get you started:

- 🔭 I’m currently working on ...
- 🌱 I’m currently learning ...
- 👯 I’m looking to collaborate on ...
- 🤔 I’m looking for help with ...
- 💬 Ask me about ...
- 📫 How to reach me: ...
- 😄 Pronouns: ...
- ⚡ Fun fact: ...
-->

# Velosity - Task Management System

Backend-приложение для управления задачами и проектами с канбан-доской, разработанное на Spring Boot.

## Функциональность

- **Управление проектами**: создание, редактирование, просмотр проектов
- **Управление задачами**: полный CRUD для задач, назначение исполнителей
- **Фильтрация**: гибкая фильтрация задач и проектов
- **REST API**: полный набор API endpoints

## Технологический стек

- **Java 17** + **Spring Boot 3.x**
- **Безопасность**: Spring Security
- **База данных**: PostgreSQL + Hibernate/JPA
- **Миграции**: Liquibase
- **Тестирование**: JUnit 5, Mockito
- **Сборка**: Maven

## База данных

- Миграции управляются через Liquibase
- Файлы миграций: `resources/db/changelog/changes/001-initial-schema.yaml`
- Тестовые данные: `resources/db/changelog/data/001-test-data.yaml`

## Тестирование

Unit-тесты
- Контроллеры(ProjectControllerTest) - изолированное тестирование web-слоя с MockMvc

- Сервисы(ProjectServiceTest) - тестирование бизнес-логики

- Мапперы(ProjectMapperTest) - тестирование преобразования данных

- Стек: JUnit 5, Mockito, MockMvc
