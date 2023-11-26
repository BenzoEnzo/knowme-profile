package pl.benzo.enzo.knowme.profile.server.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.benzo.enzo.kmservicedto.profile.AreaSizeDto;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.kmservicedto.profile.QueueJoinDto;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.api.area.AreaService;

import java.util.List;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class AreaFacade implements AreaApi{
    private final AreaService areaService;
    @Override
    public Set<AreaUserDto> createArea(CreateAreaRequest createAreaRequest) {
        return areaService.createArea(createAreaRequest);
    }

    @Override
    public QueueJoinDto addUserToQueue(AreaUserDto areaUserDto) {
        return areaService.addUserToQueue(areaUserDto);
    }

    @Override
    public ChatSession getRandomPairs() {
        return areaService.getRandomPairs();
    }

    @Override
    public List<AreaUserDto> getAllAreas() {
        return areaService.getAllAreas();
    }

    @Override
    public void refreshAreaState(AreaUserDto areaUserDto) {
            areaService.refreshAreaState(areaUserDto);
    }

    @Override
    public AreaSizeDto getAllUserIdsFromArenaSize(Long keyId) {
        return areaService.getAllUserIdsFromArenaSize(keyId);
    }

    @Override
    public void deleteArea(Long id) {
            areaService.deleteArea(id);
    }
}
