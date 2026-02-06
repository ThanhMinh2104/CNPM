package iuh.fit.demo.controller;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import iuh.fit.demo.dto.request.AuthenticationRequest;
import iuh.fit.demo.dto.request.IntrospectRequest;
import iuh.fit.demo.dto.response.ApiResponse;
import iuh.fit.demo.dto.response.AuthenticationResponse;
import iuh.fit.demo.dto.response.IntrospectResponse;
import iuh.fit.demo.service.AuthencationService;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthencationController {
    private AuthencationService authencationService;

    @Autowired
    public AuthencationController(AuthencationService authencationService) {
        this.authencationService = authencationService;
    }

    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .data(authencationService.authenticate(authenticationRequest))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> Introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authencationService.Introspect(introspectRequest))
                .build();
    }
}
