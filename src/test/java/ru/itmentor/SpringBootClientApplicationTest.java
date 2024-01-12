package ru.itmentor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = SpringBootClientApplication.class)
public class SpringBootClientApplicationTest {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String API_URL = "http://94.198.50.185:7081/api/users";
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        //Получение списка всех пользователей
        ResponseEntity<User[]> getUsersResponse = restTemplate.getForEntity(API_URL, User[].class);
        User[] users = getUsersResponse.getBody();

        //Получение session id из заголовка ответа
        HttpHeaders headers = getUsersResponse.getHeaders();
        String responeSetCookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        //Сохранение пользователя с id = 3
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders addUserHeaders = new HttpHeaders();
        addUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        addUserHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        addUserHeaders.set(HttpHeaders.HOST, "94.198.50.185:7081");

        HttpEntity<User> addUserEntity = new HttpEntity<>(newUser, addUserHeaders);

        ResponseEntity<String> addUserResponse = restTemplate
                .exchange(API_URL, HttpMethod.POST, addUserEntity, String.class);
        Assertions.assertNotNull(addUserResponse.getBody());

        //Изменение пользователя с id = 3
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpHeaders updateUserHeaders = new HttpHeaders();
        updateUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        updateUserHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        updateUserHeaders.set(HttpHeaders.HOST, "94.198.50.185:7081");

        HttpEntity<User> updateUserEntity = new HttpEntity<>(updatedUser, updateUserHeaders);

        ResponseEntity<String> updateUserResponse = restTemplate
                .exchange(API_URL, HttpMethod.PUT, updateUserEntity, String.class);
        Assertions.assertNotNull(updateUserResponse.getBody());

        //Удаление пользователя с id = 3
        HttpHeaders deleteUserHeaders = new HttpHeaders();
        deleteUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        deleteUserHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        deleteUserHeaders.set(HttpHeaders.HOST, "94.198.50.185:7081");

        HttpEntity<User> deleteUserEntity = new HttpEntity<>(deleteUserHeaders);

        ResponseEntity<String> deleteUserResponse = restTemplate
                .exchange(API_URL + "/3", HttpMethod.DELETE, deleteUserEntity, String.class);
        Assertions.assertNotNull(deleteUserResponse.getBody());

        // Итоговый код
        String finalCode = new StringBuilder(addUserResponse.getBody()).append(updateUserResponse.getBody()).append(deleteUserResponse.getBody()).toString();
        Assertions.assertEquals(18, finalCode.length());

        LOGGER.info("Итоговый код: " + finalCode );

    }
}
