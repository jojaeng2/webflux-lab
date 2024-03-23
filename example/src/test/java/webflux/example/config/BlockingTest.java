package webflux.example.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class BlockingTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() throws Exception {
        // given
        mockMvc.perform(get("http://localhost:8080/block"));

        // when

        // then
    }
}
