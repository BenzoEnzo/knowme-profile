package pl.benzo.enzo.knowme.profile.server.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.benzo.enzo.kmservicedto.profile.*;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.api.user.UserService;
import pl.benzo.enzo.knowme.profile.server.model.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserFacade implements UserApi{
    private final UserService userService;
    @Override
    public SendCrypto generateCrypto() {
        return userService.generateCrypto();
    }

    @Override
    public ReadUserResponse readUser(ReadUserRequest readUserRequest) {
        return userService.readUser(readUserRequest);
    }

    @Override
    public ValidateCrypto validateAccount(SendCrypto sendCrypto) {
        return userService.validateAccount(sendCrypto);
    }

    @Override
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @Override
    public User findUser(String crypto) {
        return userService.findUser(crypto);
    }

}
