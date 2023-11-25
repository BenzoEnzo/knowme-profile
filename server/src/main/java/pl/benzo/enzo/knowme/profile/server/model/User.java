package pl.benzo.enzo.knowme.profile.server.model;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.benzo.enzo.kmservicedto.shared.Gender;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private String name;
    @Column(unique = true)
    private String crypto;
    private String describe;
    private LocalDateTime deleteAt;
    private Gender gender;
    private String photoId;
    public User(){}

    public User(Long id, String name, Gender gender){
        this.id = id;
        this.name = name;
        this.gender = gender;
    }
}
