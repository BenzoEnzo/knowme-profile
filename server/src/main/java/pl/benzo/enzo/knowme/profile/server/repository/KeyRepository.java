package pl.benzo.enzo.knowme.profile.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.benzo.enzo.knowme.profile.server.model.Key;

@Repository
public interface KeyRepository extends JpaRepository<Key,Long> {
    Key findKeyByName(String name);
}
