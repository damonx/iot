package com.fpa.usercenter.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fpa.usercenter.dto.GetAccessTokenResponseDto;
import com.fpa.usercenter.dto.ResponseDto;
import com.fpa.usercenter.dto.UplusCloudRequestDto;
import com.fpa.usercenter.dto.UplusDeviceRequestDto;
import com.fpa.usercenter.dto.UplusDeviceResponseDto;
import com.fpa.usercenter.dto.sforce.OauthTokenResponseDto;
import com.fpa.usercenter.dto.sforce.QueryDataResponseDto;
import com.fpa.usercenter.service.SalesforceOAuthService;
import com.fpa.usercenter.service.UplusCloudService;
import com.fpa.usercenter.util.DesUtils;

@Controller
public class UplusCloudController {

	Logger logger = LoggerFactory.getLogger(UplusCloudController.class);

	@Autowired
	private UplusCloudService uplusCloudService;

	@Autowired
	private SalesforceOAuthService salesforceOAuthService;

	@RequestMapping(value = { "/authorize" }, method = RequestMethod.GET)
	public ModelAndView getToken(
			// @RequestBody UplusCloudRequestDto uplusCloudRequestDto,
			@RequestParam(name = "code", required = true) final String authorizationCode,
			@SuppressWarnings("unused") final HttpServletRequest request, final ModelAndView model) {
		this.logger.info("UplusCloudController -> getToken -> code:{}", authorizationCode);

		final ResponseDto responseDto = new ResponseDto();
		final OauthTokenResponseDto oauthTokenResponseDto = this.salesforceOAuthService.getOAuthAccessToken(authorizationCode);
		if (StringUtils.isNotBlank(oauthTokenResponseDto.getError())) {
			responseDto.setRetCode(oauthTokenResponseDto.getError());
			responseDto.setRetInfo(oauthTokenResponseDto.getError_description());
			model.addObject("GettingAccessTokenErrorCode", StringUtils.defaultIfBlank(responseDto.getRetCode(), "No Errors"));
			model.addObject("GettingAccessTokenErrorInfo", StringUtils.defaultIfBlank(responseDto.getRetInfo(), "No Error Descpritions"));
			model.setViewName("account-tokens");
			return model;
			// return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
		}

		final QueryDataResponseDto queryDataResponseDto = this.salesforceOAuthService.getUserData(oauthTokenResponseDto.getAccess_token(),
				oauthTokenResponseDto.getId());
		if (StringUtils.isNotBlank(queryDataResponseDto.getError())) {
			responseDto.setRetCode(queryDataResponseDto.getError());
			responseDto.setRetInfo(queryDataResponseDto.getError_description());

			model.addObject("GettingUserInfoErrorCode", StringUtils.defaultIfBlank(queryDataResponseDto.getError(), "No Errors"));
			model.addObject("GettingUserInfoErrorInfo",
					StringUtils.defaultIfBlank(queryDataResponseDto.getError_description(), "No Error Descpritions"));
			model.setViewName("account-tokens");
			return model;
		}

		final String sf_userId = queryDataResponseDto.getUser_id();
		final String sf_id = DesUtils.changIdtoLong(sf_userId);

		responseDto.setSalesForceAccessToken(oauthTokenResponseDto.getAccess_token());
		responseDto.setSalesForceRefreshToken(oauthTokenResponseDto.getRefresh_token());

		final UplusCloudRequestDto uplusCloudRequestDto = new UplusCloudRequestDto();
		uplusCloudRequestDto.setUserId(sf_id);
		uplusCloudRequestDto.setEmail(queryDataResponseDto.getEmail());

		final GetAccessTokenResponseDto getAccessTokenResponseDto = this.uplusCloudService.getSmartucCloudAccessToken(uplusCloudRequestDto);

		responseDto.setUplusAccessToken(getAccessTokenResponseDto.getAccessToken());
		responseDto.setRetCode(getAccessTokenResponseDto.getRetCode());
		responseDto.setRetInfo(getAccessTokenResponseDto.getRetInfo());

		// return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
		model.addObject("GettingAccessTokenErrorCode", "No Errors");
		model.addObject("GettingAccessTokenErrorInfo", "No Error Descpritions");
		model.addObject("GettingUserInfoErrorCode", StringUtils.defaultIfBlank(queryDataResponseDto.getError(), "No Errors"));
		model.addObject("GettingUserInfoErrorInfo",
				StringUtils.defaultIfBlank(queryDataResponseDto.getError_description(), "No Error Descpritions"));
		model.addObject("GettingUplusAccessTokenErrorCode", getAccessTokenResponseDto.getRetCode());
		model.addObject("GettingUplusAccessTokenErrorInfo",
				StringUtils.defaultIfBlank(getAccessTokenResponseDto.getRetInfo(), "No Error Descpritions"));

		model.addObject("sf_access_token", oauthTokenResponseDto.getAccess_token());
		model.addObject("uplus_access_token", getAccessTokenResponseDto.getAccessToken());
		model.addObject("sf_refresh_token", oauthTokenResponseDto.getRefresh_token());
		model.addObject("id", sf_id);
		model.addObject("uplus_id", getAccessTokenResponseDto.getUserId());

		model.addObject("id_token", oauthTokenResponseDto.getId_token());
		model.addObject("instance_url", oauthTokenResponseDto.getInstance_url());
		model.addObject("issued_at", oauthTokenResponseDto.getIssued_at());

		model.addObject("signature", oauthTokenResponseDto.getSignature());
		model.addObject("token_type", oauthTokenResponseDto.getToken_type());

		model.setViewName("account-tokens");
		return model;
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginAction() {
		return "login";
	}

	@RequestMapping(value = { "/querydevicelist" }, method = RequestMethod.GET)
	public ModelAndView queryDeviceList(
			@RequestParam(name = "uplusToken", required = true) final String uplusToken,
			@RequestParam(name = "uplusId", required = true) final String uplusId,
			@SuppressWarnings("unused") final HttpServletRequest request, final ModelAndView model) {

		final UplusDeviceRequestDto requestDto = new UplusDeviceRequestDto();
		requestDto.setToken(uplusToken);
		requestDto.setUserId(uplusId);
		final UplusDeviceResponseDto uplusDeviceResponseDto = this.uplusCloudService.getUplusDevices(requestDto);

		model.addObject("uplusDeviceResponseDto", uplusDeviceResponseDto);
		model.setViewName("devices");
		return model;

	}

}
