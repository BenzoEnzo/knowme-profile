package pl.benzo.enzo.knowme.profile.server.api.area;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.kmservicedto.profile.AreaSizeDto;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.kmservicedto.profile.QueueJoinDto;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.api.AreaApi;
import pl.benzo.enzo.knowme.profile.server.model.Area;
import pl.benzo.enzo.knowme.profile.server.repository.AreaRepository;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaService implements AreaApi {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;
   // private final KafkaLogService kafkaLogService;
    public void deleteArea(Long id){
        areaRepository.deleteAreaByUser_Id(id);
    }
    public Set<AreaUserDto> createArea(CreateAreaRequest createAreaRequest){
        final Area area = areaMapper.createAreaRequestMapper(createAreaRequest);
        area.setJoined(true);
        areaRepository.save(area);
        return areaRepository.findAllByKey_Id(area.getKey().getId())
                .stream().map(areaMapper::mapToAreaUserDto).
                collect(Collectors.toSet());
    }

    @Override
    public QueueJoinDto addUserToQueue(AreaUserDto areaUserDto) {
        return null;
    }

    @Override
    public ChatSession getRandomPairs() {
        return null;
    }

    public List<AreaUserDto> getAllAreas(){
        return areaRepository.findAll()
                .stream()
                .map(areaMapper::mapToAreaUserDto)
                .collect(Collectors.toList());
    }

    public void update(Area area){
        areaRepository.save(area);
    }
    public Set<AreaUserDto> getAllUserAreasUser(Long keyId){
        return areaRepository.findAllByKey_Id(keyId)
                .stream().map(areaMapper::mapToAreaUserDto).
                collect(Collectors.toSet());

    }


    public void refreshAreaState(AreaUserDto areaUserDto)  {
        final Area area = areaRepository.findAreaByUser_Id(areaUserDto.userId());
        area.setDuringConversation(true);
        area.setInQueue(false);
        area.setJoined(true);
        areaRepository.save(area);
        try {
            Thread.sleep(5000);
            areaRepository.delete(area);
         //   kafkaLogService.sendLog("Usuwanie areny o id: " + area.getId());
        } catch(InterruptedException e) {
         //   kafkaLogService.sendLog("Wielowątkowość w tym wypadku nie zadziała......");
        }
    }

    public AreaSizeDto getAllUserIdsFromArenaSize(Long keyId){
      return new AreaSizeDto((long) getAllUserAreasUser(keyId).size(), null);
    }
}
