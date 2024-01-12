package ru.itmentor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
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
    RestTemplate restTemplate;
    @Test
    void contextLoads() {
        // Шаг 1: Получение списка всех пользователей
        ResponseEntity<User[]> getUsersResponse = restTemplate.getForEntity(API_URL, User[].class);
        User[] users = getUsersResponse.getBody();

        // Шаг 2: Получение session id из заголовка ответа
        HttpHeaders headers = getUsersResponse.getHeaders();
        String responeSetCookie = headers.getFirst(HttpHeaders.SET_COOKIE);
        LOGGER.info(headers);
        LOGGER.info(responeSetCookie);

        // Шаг 3: Сохранение пользователя с id = 3
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders addUserHeaders = new HttpHeaders();
        addUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        addUserHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        addUserHeaders.set(HttpHeaders.HOST, "94.198.50.185:7081");
        LOGGER.info(addUserHeaders);
        HttpEntity<User> addUserEntity = new HttpEntity<>(newUser, addUserHeaders);


        ResponseEntity<String> addUserResponse = restTemplate.
                exchange(API_URL, HttpMethod.POST, addUserEntity, String.class);


        /*ResponseEntity<Void> addUserResponse = restTemplate
                .exchange(API_URL, HttpMethod.POST, addUserEntity, Void.class);*/
        Assertions.assertNotNull(addUserResponse.getBody());

        LOGGER.info(addUserResponse.getBody());

        // Шаг 4: Изменение пользователя с id = 3
        /*User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpHeaders updateUserHeaders = new HttpHeaders();
        updateUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        HttpEntity<User> updateUserEntity = new HttpEntity<>(updatedUser, updateUserHeaders);
        ResponseEntity<Void> updateUserResponse = restTemplate.exchange(API_URL, HttpMethod.PUT, updateUserEntity, Void.class);

        // Шаг 5: Удаление пользователя с id = 3
        HttpHeaders deleteUserHeaders = new HttpHeaders();
        deleteUserHeaders.set(HttpHeaders.COOKIE, responeSetCookie);
        HttpEntity<Void> deleteUserEntity = new HttpEntity<>(deleteUserHeaders);
        ResponseEntity<Void> deleteUserResponse = restTemplate.exchange(API_URL + "/3", HttpMethod.DELETE, deleteUserEntity, Void.class);*/

        // Итоговый код
       // String finalCode = users.length + addUserResponse.getStatusCodeValue() + updateUserResponse.getStatusCodeValue() + deleteUserResponse.getStatusCodeValue();
        //System.out.println("Итоговый код: " + finalCode);
        /*User user = new User(3L, "James", "Brown", (byte) 40);
        Client client = new Client(restTemplate);

        HttpHeaders headers = restTemplate.headForHeaders("http://94.198.50.185:7081/api/users");//Это HttpHeaders получили
        headers.set("Set-Cookie", client.getCookie());//header("Set-Cookie", getCookie()).body(user);
        client.createUser(user);
        System.out.println(headers.entrySet());*/
        // GET-запрос для получения списка пользователей
       // Client client = new Client(restTemplate);
       /* String getUsersUrl = "http://94.198.50.185:7081/api/users";
        ResponseEntity<User[]> getUsersResponse = restTemplate.getForEntity(getUsersUrl, User[].class);
        System.out.println(getUsersResponse.getHeaders());
        String cookie = getUsersResponse.getHeaders().get("Set-Cookie").get(0);
        System.out.println(cookie);

// POST-запрос для добавления пользователя в сессии
        String addUserUrl = "http://94.198.50.185:7081/api/users";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie.toString());
        System.out.println(headers);*/

// Создаем пользователя
       /* User newUser = new User(3L, "James", "Brown", (byte) 40);
        HttpEntity<User> addUserEntity = new HttpEntity<>(newUser, headers);
        ResponseEntity<User> addUserResponse = restTemplate.
        exchange(addUserUrl, HttpMethod.POST, addUserEntity, User.class);
        User addedUser = addUserResponse.getBody();
        System.out.println(addUserResponse.getHeaders().entrySet());
        */
        }
}
