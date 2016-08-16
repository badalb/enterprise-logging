package com.test.restapi;


public class RestClient {
	private String host = "localhost";
	private String port;
	//private final String usernameInputFieldName = "username";
	//private final String passwordInputFieldName = "password";
	private final StatefullRestTemplate template = new StatefullRestTemplate();

	/**
	 * This method logs into a service by doing an standard http using the
	 * configuration in this class.
	 * 
	 * @param username
	 *            the username to log into the application with
	 * @param password
	 *            the password to log into the application with
	 * 
	 * @return the url that the login redirects to
	 */
	//public void login(String username, String password) {
		//MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		//form.add(usernameInputFieldName, username);
		//form.add(passwordInputFieldName, password);
		//String uri = "http://localhost:" + getPort() + "/login";
		//this.template.postForObject(uri, form, Map.class);
	//}

	/**
	 * Logout by doing an http get on the logout url
	 * 
	 * @return result of the get as ResponseEntity
	 */

	public StatefullRestTemplate template() {
		return template;
	}

	public String serverUrl() {
		return "http://" + host + ":" + port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RestClient [\n serverUrl()=");
		builder.append(serverUrl());
		builder.append("\n]");
		return builder.toString();
	}
}