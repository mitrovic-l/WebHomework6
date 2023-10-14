package com.example.demo5.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo5.entities.User;
import com.example.demo5.repositories.user.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.Date;

public class UserService {
    @Inject
    UserRepository userRepository;

    public String login(String username, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(username);
        if (user == null || !user.getPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("193bd2dd9a2f7032e322679cf643219d58fb835c87a8883a50d7154fcd921098");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("193bd2dd9a2f7032e322679cf643219d58fb835c87a8883a50d7154fcd921098");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();
        User user = this.userRepository.findUser(username);

        if (user == null){
            return false;
        }

        return true;
    }


}
