package ru.itmentor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UserApiClient {

    private static final String API_URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // Шаг 1: Получение списка всех пользователей
        ResponseEntity<User[]> getUsersResponse = restTemplate.getForEntity(API_URL, User[].class);
        User[] users = getUsersResponse.getBody();

        // Шаг 2: Получение session id из заголовка ответа
        HttpHeaders headers = getUsersResponse.getHeaders();
        String sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);

        // Шаг 3: Сохранение пользователя с id = 3
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders addUserHeaders = new HttpHeaders();
        addUserHeaders.set(HttpHeaders.COOKIE, sessionId);
        HttpEntity<User> addUserEntity = new HttpEntity<>(newUser, addUserHeaders);
        ResponseEntity<Void> addUserResponse = restTemplate.exchange(API_URL, HttpMethod.POST, addUserEntity, Void.class);

        // Шаг 4: Изменение пользователя с id = 3
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpHeaders updateUserHeaders = new HttpHeaders();
        updateUserHeaders.set(HttpHeaders.COOKIE, sessionId);
        HttpEntity<User> updateUserEntity = new HttpEntity<>(updatedUser, updateUserHeaders);
        ResponseEntity<Void> updateUserResponse = restTemplate.exchange(API_URL, HttpMethod.PUT, updateUserEntity, Void.class);

        // Шаг 5: Удаление пользователя с id = 3
        HttpHeaders deleteUserHeaders = new HttpHeaders();
        deleteUserHeaders.set(HttpHeaders.COOKIE, sessionId);
        HttpEntity<Void> deleteUserEntity = new HttpEntity<>(deleteUserHeaders);
        ResponseEntity<Void> deleteUserResponse = restTemplate.exchange(API_URL + "/3", HttpMethod.DELETE, deleteUserEntity, Void.class);

        // Итоговый код
       // String finalCode = users.length + addUserResponse.getStatusCodeValue() + updateUserResponse.getStatusCodeValue() + deleteUserResponse.getStatusCodeValue();
       // System.out.println("Итоговый код: " + finalCode);
    }
}