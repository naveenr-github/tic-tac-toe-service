package com.game.tictactoe.tictactoeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author NA361613
 *
 */
@SpringBootApplication
@EnableSwagger2
public class TicTacToeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeServiceApplication.class, args);
	}

}
