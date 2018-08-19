package com.fpa.usercenter.service;

import com.fpa.usercenter.dto.sforce.OauthTokenResponseDto;
import com.fpa.usercenter.dto.sforce.QueryDataResponseDto;

public interface SalesforceOAuthService {

	/**
	 * Gets OAuth access token via an authorization code issued by Salesforce.
	 *
	 * @param code Authorization Code returned from SalesForce;
	 * @return OauthTokenResponseDto
	 */
	public OauthTokenResponseDto getOAuthAccessToken(final String code);

	/**
	 * Gets Salesforce user details via the identity URL provided by Salesforce.
	 *
	 * @param accessToken Salesforce access token
	 * @param sfIdentityUrl Salesforce identity URL
	 * @return QueryDataResponseDto containing the user's basic information in Salesforce
	 */
	public QueryDataResponseDto getUserData(final String accessToken, final String sfIdentityUrl);
}
