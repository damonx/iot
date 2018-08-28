package com.fpa.usercenter.dto;

import java.io.Serializable;

public class GetAccessTokenResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String retCode;

	private String retInfo;

	private String accessToken;

	private String userId;

	private String tpAccessToken;

	private String tpUserId;

	public static GetAccessTokenResponseDto empty() {
		return new GetAccessTokenResponseDto();
	}

	public String getTpAccessToken() {
		return this.tpAccessToken;
	}

	public void setTpAccessToken(final String tpAccessToken) {
		this.tpAccessToken = tpAccessToken;
	}

	public String getTpUserId() {
		return this.tpUserId;
	}

	public void setTpUserId(final String tpUserId) {
		this.tpUserId = tpUserId;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(final String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getRetCode() {
		return this.retCode;
	}

	public void setRetCode(final String retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return this.retInfo;
	}

	public void setRetInfo(final String retInfo) {
		this.retInfo = retInfo;
	}

}
