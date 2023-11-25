package pl.benzo.enzo.knowme.profile.server.api;

import pl.benzo.enzo.kmservicedto.profile.AreaSizeDto;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.kmservicedto.profile.QueueJoinDto;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;

import java.util.List;
import java.util.Set;

public interface AreaApi extends FacadeApi {
    Set<AreaUserDto> createArea(CreateAreaRequest createAreaRequest);

    QueueJoinDto addUserToQueue(AreaUserDto areaUserDto);

    ChatSession getRandomPairs();

    List<AreaUserDto> getAllAreas();

    void refreshAreaState(AreaUserDto areaUserDto);

    AreaSizeDto getAllUserIdsFromArenaSize(Long keyId);

    void deleteArea(Long id);
}
