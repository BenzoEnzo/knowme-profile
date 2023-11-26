package pl.benzo.enzo.knowme.profile.server.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.benzo.enzo.knowme.profile.server.api.key.KeyService;
import pl.benzo.enzo.knowme.profile.server.model.Key;

import java.util.List;


@Component
@RequiredArgsConstructor
public class KeyFacade implements KeyApi {
    private final KeyService keyService;
    @Override
    public List<Key> findAllKeys() {
        return keyService.findAllKeys();
    }

    @Override
    public boolean saveKey(String name) {
        return keyService.saveKey(name);
    }
}
