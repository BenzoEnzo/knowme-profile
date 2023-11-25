package pl.benzo.enzo.knowme.profile.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.benzo.enzo.kmservicedto.shared.Gender;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_id")
    private Key key;

    private String sessionId;

    private boolean joined;
    private boolean isInQueue;
    private Gender partnerType;
    private boolean duringConversation;

    public Area(User user, Key key, String sessionId) {
        this.user = user;
        this.key = key;
        this.sessionId = sessionId;
    }

    public Area(User user, Key key) {
        this.user = user;
        this.key = key;
    }

    public Area(Long id, User user, Key key, boolean isInQueue, Gender partnerType){
        this.id = id;
        this.user = user;
        this.key = key;
        this.isInQueue = isInQueue;
        this.partnerType = partnerType;
    }
}
