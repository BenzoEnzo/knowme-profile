package pl.benzo.enzo.knowme.profile.server.api;

import pl.benzo.enzo.kmservicedto.profile.*;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.model.User;

import java.util.List;

public interface UserApi extends FacadeApi {
    SendCrypto generateCrypto();

    ReadUserResponse readUser(ReadUserRequest readUserRequest);
    ValidateCrypto validateAccount(SendCrypto sendCrypto);
    List<User> findAllUsers();

    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);

    public User findUser(String crypto);

    ChatSession getRandomPairs();
}