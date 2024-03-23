package webflux.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import reactor.blockhound.BlockHound;

@Slf4j
@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		BlockHound.install();
		SpringApplication.run(ExampleApplication.class, args);
	}

}
