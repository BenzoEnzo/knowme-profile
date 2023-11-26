package pl.benzo.enzo.knowme.profile.server.api.key;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.knowme.profile.server.api.KeyApi;
import pl.benzo.enzo.knowme.profile.server.model.Key;
import pl.benzo.enzo.knowme.profile.server.repository.KeyRepository;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeyService {
    private final KeyRepository keyRepository;

    public List<Key> findAllKeys(){
        return keyRepository.findAll();
    }
    public boolean saveKey(String name){
        final Key key = keyRepository.findKeyByName(name);
        if(Objects.isNull(key)){
            keyRepository.save(new Key(name));
            return true;
        } else return false;
    }
}
