package webflux.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		Mono.just("Hello Reactor")
			.subscribe(message -> System.out.println("message = " + message));
//		SpringApplication.run(ExampleApplication.class, args);
	}

}
