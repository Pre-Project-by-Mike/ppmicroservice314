package ru.itmentor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Client {

    private RestTemplate restTemplate;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getHeader(String headerKey) {
       return restTemplate.headForHeaders("http://94.198.50.185:7081/api/users")
                .get(headerKey);
    }

    public String getCookie() {
        return restTemplate.headForHeaders("http://94.198.50.185:7081/api/users")
                .get("Set-Cookie").get(0);
    }

    public String getSessionId() {
        return restTemplate.headForHeaders("http://94.198.50.185:7081/api/users")
                .get("Set-Cookie")
                .get(0)
                .split(";")[0]
                .split("=")[1];
    }
    public ResponseEntity<User[]> requestUsers() {
        return restTemplate.getForEntity("http://94.198.50.185:7081/api/users", User[].class);
    }

    public void createUser(@RequestBody User user) {
        HttpHeaders headers = restTemplate.headForHeaders("http://94.198.50.185:7081/api/users");//Это HttpHeaders получили
        headers.set("Set-Cookie", getCookie());//header("Set-Cookie", getCookie()).body(user);

    }
}
