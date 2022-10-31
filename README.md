<h1 align="center">
    <p>P for Pet</p>
    <img src="https://pixabay.com/get/ga39211e441c4ae7872f51730943824f6daa185c62f2e0103958cc15137682bac70311481054abf2f035a92262dd914998e8c6a7c2989c96613e796a102ce1b57cff2d5a983ba9aecf8113cdbc5f76f0a_1280.jpg" alt="P for Pet">
</h1>

<p align="center">
<a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"></a>
<a href="https://jdk.java.net/18/"><img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white" alt="Java"></a>
<a href="https://git-scm.com/"><img src="https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white" alt="Git"></a>
<a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Maven"></a>
<a href="https://www.jetbrains.com/pt-br/idea/"><img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" alt="Intellij"></a>
<a href="https://www.postgresql.org/"><img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgresSQL"></a>
</p>

> On developing, the P for Pet - API Rest, as the name says, is api on rest architecture
> that provides microservices to others systems. This aims to be a portfolio system for
> studying and improving on spring boot. Feel free to help and teach me something new.

> Future a more specific documentation will be available.

## Introduction

P for Pet, provides end-points for consume. It has CRUD end-points for each model. The routes provided by the controllers need to be authenticated with basic authentication.

Every function, method, service and controller needs to have a unit test. 

 * **Starting**
> Clone the repository and opens with your favored java editor. I recommend IntelliJ or Eclipse. Then wait for Maven to download the needed dependencies. Now you can work.

The idea behind the api is that we have the Secretary and the Admin users. The first have access some end-points of the api when authenticated and the second has access to all end-points. 
So the users of this api are the secretary and the admin. So the secretary can log into a system and will create the animals, owners and other by making requests to the api. As a future update I'm thinking about adding the client of the clinic as a consumers so the client could book the appointment by itself without the need of the secretary.   
## The developing

I originally wrote all the code in portuguese, since I'm brazilian, and now I'm changing to english for a  more international view. So you can sometimes find some method names or variables in portuguese. If you find you can help me on discussion.

## Services

All services have the basic methods `save`,`update`,`remove`,`findById`. I like to do more, such as `removeById`,`FindAll`, to make the controllers job easier.

## Spring Security

I'm using spring security to validate the requests for me controllers. The `WebSecurityConfig` has the method `filterChain` that holds the configuration for access at the routes based on the url path.

## Thymeleaf

It has a thymeleaf part that I was working for study porpoises that maybe I'll delete or refactor.

## Help me

Any help is welcome. Since I'm a very beginner at Spring Boot, anything will help. Opening discussion and making PR telling me what's wrong will be of greater help. 

## Contact

<ul>
<li><a href="https://www.linkedin.com/in/matheus-levy/">Linkedin</a></li>
</ul>


