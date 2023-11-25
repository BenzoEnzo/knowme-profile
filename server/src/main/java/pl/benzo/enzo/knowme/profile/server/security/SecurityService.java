package pl.benzo.enzo.knowme.profile.server.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.knowme.profile.server.api.UserApi;
import pl.benzo.enzo.knowme.profile.server.api.user.BasicService;
import pl.benzo.enzo.knowme.profile.server.model.User;


import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserApi userApi;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = Optional.ofNullable(userApi.findUser(username));

        return userDetail.map(ImplUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
