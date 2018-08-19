package com.fpa.usercenter.dto.sforce;

import java.io.Serializable;

public class OauthTokenResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String access_token;
	private String token_type;
	private String id_token;
	private String refresh_token;
	private String scope;
	private String instance_url;
	private String id;
	private String signature;
	private String issued_at;
	private String error_description;
	private String error;

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

	public String getToken_type() {
		return this.token_type;
	}

	public void setToken_type(final String token_type) {
		this.token_type = token_type;
	}

	public String getId_token() {
		return this.id_token;
	}

	public void setId_token(final String id_token) {
		this.id_token = id_token;
	}

	public String getRefresh_token() {
		return this.refresh_token;
	}

	public void setRefresh_token(final String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getInstance_url() {
		return this.instance_url;
	}

	public void setInstance_url(final String instance_url) {
		this.instance_url = instance_url;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(final String signature) {
		this.signature = signature;
	}

	public String getIssued_at() {
		return this.issued_at;
	}

	public void setIssued_at(final String issued_at) {
		this.issued_at = issued_at;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public void setAccess_token(final String access_token) {
		this.access_token = access_token;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

}
