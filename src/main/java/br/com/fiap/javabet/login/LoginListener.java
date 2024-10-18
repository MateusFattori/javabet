package br.com.fiap.javabet.login;

import br.com.fiap.javabet.user.User;
import br.com.fiap.javabet.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;

    public LoginListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        var principal = (OAuth2User) event.getAuthentication().getPrincipal();
        User user = new User(principal); // Certifique-se de que o construtor de User aceite OAuth2User
        userService.register(user); // Agora, esse método deve existir
        log.info("User registered: {}", user.getEmail());
    }
}
