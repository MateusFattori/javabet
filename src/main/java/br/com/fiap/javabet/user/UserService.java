package br.com.fiap.javabet.user;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        // Verifica se o usuário já existe, caso contrário, insere no banco
        String email = user.getAttribute("email");
        User existingUser = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(user.getAttribute("name"));
            newUser.setAvatar(user.getAttribute("picture"));
            return userRepository.save(newUser);
        });

        return existingUser; // Retorna o usuário existente ou recém-criado
    }

    public void register(User user) {
        userRepository.save(user);
    }
}
