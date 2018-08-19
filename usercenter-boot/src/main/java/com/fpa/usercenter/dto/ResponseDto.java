package com.fpa.usercenter.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uplusAccessToken;

	private String salesForceAccessToken;

	private String salesForceRefreshToken;

	private String retCode;

	private String retInfo;

	public String getUplusAccessToken() {
		return this.uplusAccessToken;
	}

	public void setUplusAccessToken(final String uplusAccessToken) {
		this.uplusAccessToken = uplusAccessToken;
	}

	public String getSalesForceAccessToken() {
		return this.salesForceAccessToken;
	}

	public void setSalesForceAccessToken(final String salesForceAccessToken) {
		this.salesForceAccessToken = salesForceAccessToken;
	}

	public String getSalesForceRefreshToken() {
		return this.salesForceRefreshToken;
	}

	public void setSalesForceRefreshToken(final String salesForceRefreshToken) {
		this.salesForceRefreshToken = salesForceRefreshToken;
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
