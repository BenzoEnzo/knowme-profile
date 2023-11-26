package pl.benzo.enzo.knowme.profile.server.api.area;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.kmservicedto.profile.AreaSizeDto;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.kmservicedto.profile.QueueJoinDto;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.api.AreaApi;
import pl.benzo.enzo.knowme.profile.server.model.Area;
import pl.benzo.enzo.knowme.profile.server.model.User;
import pl.benzo.enzo.knowme.profile.server.repository.AreaRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaService implements AreaApi {
    private static final Logger loggerQueueService = LoggerFactory.getLogger(AreaService.class);
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

    public QueueJoinDto addUserToQueue(AreaUserDto areaUserDto) {
        areaRepository.findByUser_Id(areaUserDto.userId()).ifPresent(a -> {
            a.setInQueue(true);
            areaRepository.save(a);
        });
        return new QueueJoinDto(true, areaUserDto.keyId(),areaUserDto.userId(),false);
    }

    public ChatSession getRandomPairs() {
        final Set<Pair<Long, Long>> pairedUsersHistory = new HashSet<>();
        List<Area> usersInQueue = areaRepository.findAllByIsInQueueAndDuringConversation(true, false);

        for (Area a : usersInQueue) {
            loggerQueueService.info("Aktywni Uczestnicy:" + a.getId());
        }

        Map<Long, List<Area>> groupedByRoom = usersInQueue.stream()
                .collect(Collectors.groupingBy(area -> area.getKey().getId()));

        for (Long l : groupedByRoom.keySet()) {
            loggerQueueService.info("Aktywne pokoje:" + l);
        }

        for (Map.Entry<Long, List<Area>> entry : groupedByRoom.entrySet()) {
            List<Long> usersInRoom = entry.getValue()
                    .stream()
                    .map(Area::getUser)
                    .map(User::getId)
                    .collect(Collectors.toList());

            Collections.shuffle(usersInRoom);

            for (int i = 0; i < usersInRoom.size() - 1; i += 2) {
                Long talkerId1 = usersInRoom.get(i);
                Long talkerId2 = usersInRoom.get(i + 1);


                if (!pairedUsersHistory.contains(Pair.of(talkerId1, talkerId2)) && !pairedUsersHistory.contains(Pair.of(talkerId2, talkerId1))) {

                    pairedUsersHistory.add(Pair.of(talkerId1, talkerId2));
                    return ChatSession.builder()
                            .talkerId1(talkerId1)
                            .talkerId2(talkerId2).build();
                }
            }
        }
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
