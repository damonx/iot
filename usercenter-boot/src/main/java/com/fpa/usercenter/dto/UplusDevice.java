package com.fpa.usercenter.dto;

import java.io.Serializable;

public class UplusDevice implements Serializable {

	private static final long serialVersionUID = -477266114506832433L;

	private String id;

	private String name;

	private String mac;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(final String mac) {
		this.mac = mac;
	}

}
