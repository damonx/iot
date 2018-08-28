package com.fpa.usercenter.service;

import com.fpa.usercenter.dto.GetAccessTokenResponseDto;
import com.fpa.usercenter.dto.UplusCloudRequestDto;
import com.fpa.usercenter.dto.UplusDeviceRequestDto;
import com.fpa.usercenter.dto.UplusDeviceResponseDto;

public interface UplusCloudService {

	/**
	 * Gets a new or existing U+ Cloud access token given the Salesforce id
	 *
	 * @param requestDto which contains Salesforce id
	 * @return GetAccessTokenResponseDto containing U+ access token
	 */
	public GetAccessTokenResponseDto getSmartucCloudAccessToken(UplusCloudRequestDto requestDto);

	/**
	 * Gets U+ devices associated with the user specified by U+ user ID and U+ access token
	 *
	 * @param requestDto which contains U+ Id
	 * @return UplusDeviceResponseDto containing a list of devices
	 */
	public UplusDeviceResponseDto getUplusDevices(UplusDeviceRequestDto requestDto);
}
