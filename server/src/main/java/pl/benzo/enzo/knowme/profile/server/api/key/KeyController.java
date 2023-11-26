package pl.benzo.enzo.knowme.profile.server.api.key;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.benzo.enzo.knowme.profile.server.api.KeyApi;


@RestController
@RequestMapping("/api/key")
@RequiredArgsConstructor
public class KeyController {
    private final KeyApi keyApi;



    @GetMapping(value = "/query")
    public ResponseEntity<?> getKeys() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(keyApi.findAllKeys());
    }
    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody String name) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(keyApi.saveKey(name));
    }
}
