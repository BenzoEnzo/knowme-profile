package pl.benzo.enzo.knowme.profile.server.api;

import pl.benzo.enzo.knowme.profile.server.model.Key;

import java.util.List;

public interface KeyApi {
    List<Key> findAllKeys();

    boolean saveKey(String name);

}
