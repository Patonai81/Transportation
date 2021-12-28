package hu.webuni.transportation.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService {

    private  static final String SECRET="MySecret";
    private static final Algorithm algorythm = Algorithm.HMAC256(SECRET);
    public static final String AUTH = "auth";
    private String transport_application = "Transport_Application";;

    public String createJwtToken(UserDetails principal){
        log.debug("Creating JWT Token for: "+principal.getUsername());

        return JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim(AUTH,principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .withIssuer(transport_application)
                .sign(algorythm);

    }

    public UserDetails parseJWT(String encoded_jwt) {

        DecodedJWT decodedJWT = JWT.require(algorythm)
                .withIssuer(transport_application)
                .build()
                .verify(encoded_jwt);

        return new User(decodedJWT.getSubject(),"XXX",decodedJWT.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toCollection(ArrayList::new)));
    }
}
