package pl.benzo.enzo.knowme.profile.server.api.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.kmservicedto.profile.*;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.api.UserApi;
import pl.benzo.enzo.knowme.profile.server.model.User;
import pl.benzo.enzo.knowme.profile.server.repository.UserRepository;
import pl.benzo.enzo.knowme.profile.server.util.DateOperation;
import pl.benzo.enzo.knowme.profile.server.util.GenerateID;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
   private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
            final User user = findUserById(updateUserRequest.id());

            Optional.ofNullable(updateUserRequest.name()).ifPresent(user::setName);
            Optional.ofNullable(updateUserRequest.describe()).ifPresent(user::setDescribe);
            Optional.ofNullable(updateUserRequest.gender()).ifPresent(user::setGender);

            createOrUpdateUser(user);

            return new UpdateUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getDescribe(),
                    user.getGender()
            );
    }

    public ReadUserResponse readUser(ReadUserRequest readUserRequest){
            final User user = findUserById(readUserRequest.id());

            return new ReadUserResponse(user.getName(), user.getDescribe(),user.getGender());

    }


    public ValidateCrypto validateAccount(SendCrypto sendCrypto) {
        return null;
    }

    public ValidateCrypto validateCrypto(SendCrypto sendCrypto){
        final User user = findUser(sendCrypto.crypto());

        if(user != null){
            return userMapper.validateCryptoMapping(user);
        } else throw new IllegalArgumentException("User doesnt exist");
    }

    public SendCrypto generateCrypto() {
        final int id = findAllUsers().size() + 1;

        final User user = User.builder()
                .crypto(GenerateID.create())
                .name("DUCH_" + id)
                .describe("Tutaj wpisz swój opis.....")
                .deleteAt(DateOperation.addHoursToDate(LocalDateTime.now(),24))
                .build();

        createOrUpdateUser(user);

     //   kafkaLogService.sendLog("Stworzono cryptoUsera o ID = " + id);

        return new SendCrypto(user.getCrypto());
    }


    public void createOrUpdateUser(User user){
        userRepository.save(user);
    }

    public User findUser(String crypto){
        return userRepository.findUserByCrypto(crypto);
    }


    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

}
