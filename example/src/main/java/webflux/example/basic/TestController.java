package webflux.example.basic;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@RestController
public class TestController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getController(@PathVariable("id") String id) {
        log.warn("result = {}", id);
        return new ResponseEntity<>("ok", null, HttpStatusCode.valueOf(200));
    }
}
