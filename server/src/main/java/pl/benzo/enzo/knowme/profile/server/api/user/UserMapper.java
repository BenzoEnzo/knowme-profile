package pl.benzo.enzo.knowme.profile.server.api.user;


import org.springframework.stereotype.Component;
import pl.benzo.enzo.kmservicedto.profile.ValidateCrypto;
import pl.benzo.enzo.knowme.profile.server.model.User;



@Component
public class UserMapper {
    public ValidateCrypto validateCryptoMapping(User user){
        return new ValidateCrypto(user.getId());
    }
}
