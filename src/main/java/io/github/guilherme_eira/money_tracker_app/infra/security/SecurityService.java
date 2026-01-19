package io.github.guilherme_eira.money_tracker_app.infra.security;

import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.infra.mapper.UserMapper;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UserJpaRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmailOrDocument(username)
                .map(mapper::toDomain)
                .orElseThrow(UserNotFoundException::new);

        return new SecurityUser(user);
    }
}
