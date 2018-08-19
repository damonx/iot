package com.fpa.usercenter.dto;

import java.io.Serializable;
import java.util.List;

public class UplusDeviceResponseDto implements Serializable {

	private static final long serialVersionUID = 6539531408693048725L;

	private String retCode;

	private String retInfo;

	private List<UplusDevice> devices;

	public static UplusDeviceResponseDto empty() {
		return new UplusDeviceResponseDto();
	}

	public List<UplusDevice> getDevices() {
		return this.devices;
	}

	public void setDevices(final List<UplusDevice> devices) {
		this.devices = devices;
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
