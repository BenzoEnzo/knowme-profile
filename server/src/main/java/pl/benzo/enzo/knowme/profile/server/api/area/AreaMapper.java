package pl.benzo.enzo.knowme.profile.server.api.area;

import org.springframework.stereotype.Component;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.knowme.profile.server.model.Area;
import pl.benzo.enzo.knowme.profile.server.model.Key;
import pl.benzo.enzo.knowme.profile.server.model.User;


@Component
public class AreaMapper {

    public AreaUserDto mapToAreaUserDto(Area area){
        return new AreaUserDto(area.getId(),area.getUser().getId(),area.getUser().getName(),area.getKey().getId(),area.isJoined(),area.isDuringConversation(),false);
    }

    public Area createAreaRequestMapper(CreateAreaRequest createAreaRequest){
        final User user = new User(createAreaRequest.user().id(), createAreaRequest.user().name(),createAreaRequest.user().gender());
        final Key key = new Key(createAreaRequest.key().id(),createAreaRequest.key().name());

        return new Area(user,key);
    }
}
