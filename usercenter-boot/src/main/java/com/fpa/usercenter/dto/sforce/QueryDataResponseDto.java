package com.fpa.usercenter.dto.sforce;

import java.io.Serializable;

public class QueryDataResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String user_id;
	private String organization_id;
	private String username;
	private String nick_name;
	private String email;
	private String instance_url;
	private String error_description;
	private String error;
	private String asserted_user;

	public static QueryDataResponseDto empty() {
		return new QueryDataResponseDto();
	}

	public String getAsserted_user() {
		return this.asserted_user;
	}

	public void setAsserted_user(final String asserted_user) {
		this.asserted_user = asserted_user;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(final String user_id) {
		this.user_id = user_id;
	}

	public String getOrganization_id() {
		return this.organization_id;
	}

	public void setOrganization_id(final String organization_id) {
		this.organization_id = organization_id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getNick_name() {
		return this.nick_name;
	}

	public void setNick_name(final String nick_name) {
		this.nick_name = nick_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getInstance_url() {
		return this.instance_url;
	}

	public void setInstance_url(final String instance_url) {
		this.instance_url = instance_url;
	}

	public String getError_description() {
		return this.error_description;
	}

	public void setError_description(final String error_description) {
		this.error_description = error_description;
	}

	public String getError() {
		return this.error;
	}

	public void setError(final String error) {
		this.error = error;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
