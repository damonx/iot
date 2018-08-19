package com.fpa.usercenter.dto;

import java.io.Serializable;

public class UplusDeviceRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientId;

	private String token;

	private String userId;

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

}
