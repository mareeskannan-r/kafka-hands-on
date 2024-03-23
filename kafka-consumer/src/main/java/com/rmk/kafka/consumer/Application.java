package com.rmk.kafka.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			var ab = new AB();
			ab.print();
			ab.data();
		};
	}
}

class AB implements A, B {

	@Override
	public void print() {
		System.out.println("*****" + Instant.now() + "******");
	}
}

interface A {
	void print();

	default void data1() {
		System.out.println("AAAAA******");
	}
}

interface B {
	void print();

	default void data() {
		System.out.println("BBBBB******");
	}
}