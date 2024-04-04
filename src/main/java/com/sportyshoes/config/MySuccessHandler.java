package com.sportyshoes.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class MySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    boolean isAdmin = authentication.getAuthorities()
        .stream()
        .anyMatch(a -> a.getAuthority()
            .equals("ADMIN"));
    if (isAdmin) {
      setDefaultTargetUrl("/admin/products");
    } else {
      setDefaultTargetUrl("/");
    }
    // do some logic here if you want something to be done whenever
    // the user successfully logs in.
    super.onAuthenticationSuccess(request, response, authentication);
  }

}
