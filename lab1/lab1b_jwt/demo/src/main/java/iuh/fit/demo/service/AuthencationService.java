package iuh.fit.demo.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import iuh.fit.demo.dto.request.AuthenticationRequest;
import iuh.fit.demo.dto.request.IntrospectRequest;
import iuh.fit.demo.dto.response.ApiResponse;
import iuh.fit.demo.dto.response.AuthenticationResponse;
import iuh.fit.demo.dto.response.IntrospectResponse;
import iuh.fit.demo.entity.User;
import iuh.fit.demo.exception.AppException;
import iuh.fit.demo.exception.ErrorCode;
import iuh.fit.demo.repository.UserRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RestController
public class AuthencationService {
    private UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signer_key}")
    protected String SINGER_KEY;

    public AuthencationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(isAuthenticated)
        {
            String token = generateToken(authenticationRequest.getUsername());
            authenticationResponse.setToken(token);
        }

        authenticationResponse.setAuthenticated(isAuthenticated);

        return authenticationResponse;
    }

    private String generateToken(String username) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("nghothminz")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("customClaim", "custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot creatr token", e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse Introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        JWSVerifier jwsVerifier = new MACVerifier(SINGER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date dateExpiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verifierd =  signedJWT.verify(jwsVerifier);

        return IntrospectResponse.builder()
                .valid(verifierd && dateExpiration.after(new Date()))
                .build();
    }
}
