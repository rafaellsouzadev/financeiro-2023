package com.rafael.financeiro.resources;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafael.financeiro.config.properties.AppFinanceiroProperties;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private AppFinanceiroProperties appFinanceiroProperties;
	
	@DeleteMapping("/revoke")
	public void revoke(HttpServletResponse request, HttpServletResponse response) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(appFinanceiroProperties.getSeguranca().isEnableHttps()); 
		cookie.setPath(request.getContentType() + "/oauth/token");
		cookie.setMaxAge(0);
		
		response.addCookie(cookie);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

}
