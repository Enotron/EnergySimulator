package org.enotron.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan
@EnableAutoConfiguration
@ImportResource({"classpath:simulator-context.xml"})
public class SimulatorRestMain {

	public static void main(String[] args) {
        SpringApplication.run(SimulatorRestMain.class, args);


	}

}
