package com.test.restapi

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment;
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {

	@Shared
	@AutoCleanup
	RestTemplate rt;
	
	@Value('${local.server.port}')
	int port;
	
	@Autowired
	private  Environment env;

	def setup(){
		if(rt==null){
			RestClient client = new RestClient();
			client.setPort(String.valueOf(port));
			//String url = client.login(env.getProperty("embrace.user.name"),env.getProperty("embrace.user.password"));
			rt=client.template;
		}
	}

	def cleanup() {
		//restClient =  null
	}

	def setupSpec() {}

	def cleanupSpec() {
		rt =  null
	}

}