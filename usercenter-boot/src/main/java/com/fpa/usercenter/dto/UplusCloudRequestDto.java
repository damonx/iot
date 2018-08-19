package com.fpa.usercenter.dto;

import java.io.Serializable;

public class UplusCloudRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientId;

	private String userId;

	private String email;

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
