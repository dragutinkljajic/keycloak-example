package com.example.demo.controller;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dto;

import javax.ws.rs.core.Context;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
public class WebRestController {

	@SuppressWarnings("unchecked")
	@RequestMapping(
			value = "/hi",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@PreAuthorize("hasRole('user')")
	public ResponseEntity<Dto> userHi(@Context HttpServletRequest request) {

		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) token.getPrincipal();
		IDToken idToken = principal.getKeycloakSecurityContext().getIdToken();
		
		return new ResponseEntity<Dto>(new Dto(principal.getName(),
				idToken.getEmail(),
				idToken.getGivenName(),
				idToken.getFamilyName()),
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('admin')")
	@RequestMapping("/admin/hi")
	public String adminHi() {
		return "Hello World from Restful API admin";
	}
	
	@PreAuthorize("authenticated")
	@GetMapping(path = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.logout();
	      response.sendRedirect("/home/index.html");
	}
}