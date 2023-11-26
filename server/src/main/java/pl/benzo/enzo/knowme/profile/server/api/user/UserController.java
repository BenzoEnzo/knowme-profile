package pl.benzo.enzo.knowme.profile.server.api.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.benzo.enzo.kmservicedto.profile.*;
import pl.benzo.enzo.knowme.profile.server.api.UserApi;
import pl.benzo.enzo.knowme.profile.server.security.JwtToken;


@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserApi userApi;

    private final JwtToken jwtToken;

    @GetMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(){
        SendCrypto sendCrypto = userApi.generateCrypto();
        return ResponseEntity.ok()
                .body(sendCrypto);
    }

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody SendCrypto sendCrypto){
        final ValidateCrypto validateCrypto = userApi.validateAccount(sendCrypto);
        if(validateCrypto != null){
            String token = jwtToken.generateToken(sendCrypto.crypto());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","Bearer " + token);
            return new ResponseEntity<>(validateCrypto, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UpdateUserResponse> update(@RequestBody UpdateUserRequest updateUserRequest) {
        final UpdateUserResponse updateUserResponse = userApi.updateUser(updateUserRequest);
        return ResponseEntity.ok()
                .body(updateUserResponse);
    }

    @PostMapping(value = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ReadUserResponse> read(@RequestBody ReadUserRequest readUserRequest) {
        final ReadUserResponse readUserResponse = userApi.readUser(readUserRequest);
        return ResponseEntity.ok()
                .body(readUserResponse);
    }

    @GetMapping(value = "/query")
    @ResponseBody
    public ResponseEntity<?> queryAllUsers(){
        return ResponseEntity.ok()
                .body(userApi.findAllUsers());
    }




}
