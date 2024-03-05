package webflux.example.forwebclient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AController {

    @GetMapping("/a/geta")
    public ResponseEntity<String> getA() throws Exception {
        log.warn("#AController ## getA");
        throw new Exception();
//        return new ResponseEntity<>("a", HttpStatus.OK);
    }

    @GetMapping("/a/time")
    public ResponseEntity<Date> getTime() {
        return new ResponseEntity(LocalDateTime.now(), HttpStatus.OK);
    }
}
