package com.talentica.kafkalog4j2;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.talentica.kafkalog4j2.consumer.SimpleMessageConsumer;

//@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

		SimpleMessageConsumer consumer = (SimpleMessageConsumer) ctx
				.getBean("simpleMessageConsumer");
		System.out.println("Consumer : " + consumer.toString());
		consumer.consume();
	}

	// @Override
	// protected SpringApplicationBuilder configure(
	// SpringApplicationBuilder application) {
	//
	// return application.showBanner(true).parent(Global.class)
	// .sources(Application.class).profiles("container");
	// }
	//
	// @Bean
	// public Filter characterEncodingFilter() {
	// CharacterEncodingFilter characterEncodingFilter = new
	// CharacterEncodingFilter();
	// characterEncodingFilter.setEncoding("UTF-8");
	// characterEncodingFilter.setForceEncoding(true);
	// return characterEncodingFilter;
	// }

}