package com.example.mission_.note.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String username = (String) attributes.get("name");
        String nickname = registrationId + "_" + username;

        Optional<SiteUser> userOptional = userRepository.findByEmail(email);
        SiteUser user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = new SiteUser();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword("");  // OAuth2 사용자는 패스워드가 필요하지 않음
            user.setCreateDate(LocalDateTime.now());
            user.setNickname(nickname);
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "sub"
        );
    }
}