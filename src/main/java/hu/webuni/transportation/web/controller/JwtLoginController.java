package hu.webuni.transportation.web.controller;

import hu.webuni.transportation.dto.LoginDTO;
import hu.webuni.transportation.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class JwtLoginController {

    @Resource
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping
    public String login(@RequestBody LoginDTO loginDTO){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));
    log.debug("Authentication SUCCESSFUL");

    return jwtService.createJwtToken((UserDetails) authentication.getPrincipal());
    }
}
