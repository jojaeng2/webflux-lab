package webflux.example.forwebclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BController {

    @GetMapping("/b/getb")
    public ResponseEntity<String> getB() {
        log.warn("#BController ## getB");
        return new ResponseEntity<>("b", HttpStatus.OK);
    }

    @GetMapping("/b/convert/{date}")
    public ResponseEntity<String> convertTime(@PathVariable String date) {
        return new ResponseEntity<>("converted date = " + date, HttpStatus.OK);
    }
}
