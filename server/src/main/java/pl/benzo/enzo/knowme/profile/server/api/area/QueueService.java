package pl.benzo.enzo.knowme.profile.server.api.area;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.QueueJoinDto;
import pl.benzo.enzo.kmservicedto.socket.ChatSession;
import pl.benzo.enzo.knowme.profile.server.model.Area;
import pl.benzo.enzo.knowme.profile.server.model.User;
import pl.benzo.enzo.knowme.profile.server.repository.AreaRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueService {
    private static final Logger loggerQueueService = LoggerFactory.getLogger(QueueService.class);
    private final AreaRepository areaRepository;
    private final AreaService areaService;

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
}
