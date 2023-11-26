package pl.benzo.enzo.knowme.profile.server.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.benzo.enzo.kmservicedto.profile.AreaJoinDto;
import pl.benzo.enzo.kmservicedto.profile.AreaUserDto;
import pl.benzo.enzo.kmservicedto.profile.CreateAreaRequest;
import pl.benzo.enzo.knowme.profile.server.api.AreaApi;


@RestController
@RequestMapping("/api/area")
@RequiredArgsConstructor
public class AreaController {
private final AreaApi areaApi;

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateAreaRequest createAreaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(areaApi.createArea(createAreaRequest));
    }

    @PostMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> joinToQueue(@RequestBody AreaUserDto areaUserDto) {
        return ResponseEntity
                .ok().body(areaApi.addUserToQueue(areaUserDto));
    }

    @PostMapping(value = "/on-conversation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> onConversation(@RequestBody AreaUserDto areaUserDto) {
        areaApi.refreshAreaState(areaUserDto);
        return ResponseEntity
                .ok().build();
    }

    @PostMapping(value = "/area-people", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> peopleOnArea(@RequestBody AreaJoinDto areaJoinDto) {
        return ResponseEntity
                .ok().body(areaApi.getAllUserIdsFromArenaSize(areaJoinDto.keyId()));
    }

    @GetMapping(value = "/query-areas")
    public ResponseEntity<?> queryAreas(){
        return ResponseEntity.ok().body(areaApi.getAllAreas());
    }

    @DeleteMapping(value = "/delete-area/{id}")
    public ResponseEntity<?> deleteArea(@RequestParam ("id") Long id){
        areaApi.deleteArea(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/queue")
    @ResponseBody
    public ResponseEntity<?> getRandomPairs(){
        return ResponseEntity.ok()
                .body(areaApi.getRandomPairs());
    }
}
