package com.pillows.springbootpillowsapi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {
    @Value("${API_SECRET_KEY}")
    private String API_SECRET_KEY;

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestParam("name") String name,
                                                         @RequestParam("password") String password) {

        if((Objects.equals(name, "admin")) && Objects.equals(password, "admin")) {
            return new ResponseEntity<>(generateJWTToken(name), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> generateJWTToken(String name) {
        long timestamp = System.currentTimeMillis();
        long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + TOKEN_VALIDITY))
                .claim("userName", name)
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
