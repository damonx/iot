package com.fpa.usercenter.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fpa.usercenter.dto.GetAccessTokenResponseDto;
import com.fpa.usercenter.dto.UplusCloudRequestDto;
import com.fpa.usercenter.dto.UplusDeviceRequestDto;
import com.fpa.usercenter.dto.UplusDeviceResponseDto;
import com.fpa.usercenter.service.UplusCloudService;
import com.fpa.usercenter.util.DesUtils;
import com.fpa.usercenter.util.HttpClientBuilder;
import com.fpa.usercenter.util.SignUtils;

@PropertySources(value = { @PropertySource("classpath:upluscloud.properties") })
@Service
public class UplusCloudServiceImpl implements UplusCloudService {

	private static Logger logger = LoggerFactory.getLogger(UplusCloudServiceImpl.class);

	@Autowired
	private Environment environment;

	@Override
	public GetAccessTokenResponseDto getSmartucCloudAccessToken(final UplusCloudRequestDto requestDto) {

		final String haierCloudSign = SignUtils.sha256(this.environment.getProperty("haier.cloud.appId"),
				this.environment.getProperty("haier.cloud.appkey"), this.environment.getProperty("haier.cloud.clientId"));

		final String email = requestDto.getEmail();
		final StringBuffer sb = new StringBuffer();
		sb.append("email=").append(email).append("&userId=").append(requestDto.getUserId())
				.append("&appName=").append(this.environment.getProperty("haier.cloud.appName"));

		logger.info("UplusCloudServiceImpl -> getSmartucCloudAccessToken -> requestDto:", sb.toString());

		String ucEncrpyData = null;
		try {
			final DesUtils desUtils = new DesUtils();
			ucEncrpyData = desUtils.encrypt(sb.toString());
		} catch (final Exception e1) {
			logger.error("getSmartucCloudAccessToken -> encrypt error:" + e1);
		}

		HttpClient httpClient = null;
		final String url = this.environment.getProperty("uplus.login.url");
		final PostMethod postMethod = new PostMethod(url);

		try {
			postMethod.addRequestHeader("appId", this.environment.getProperty("haier.cloud.appId"));
			postMethod.addRequestHeader("clientId", this.environment.getProperty("haier.cloud.clientId"));
			postMethod.addRequestHeader("appVersion", this.environment.getProperty("fpa.app.version"));
			postMethod.addRequestHeader("sign", haierCloudSign);

			postMethod.addRequestHeader("Content-Type", "application/json");

			final ObjectMapper mapper = new ObjectMapper();
			final ObjectNode objectNode = mapper.createObjectNode();
			objectNode.put("ucEncrpyData", ucEncrpyData);
			final RequestEntity re = new StringRequestEntity(mapper.writeValueAsString(objectNode), "application/json", "utf8");
			postMethod.setRequestEntity(re);

			httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(Integer.valueOf(this.environment.getProperty("haier.cloud.connect.timeout")));
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(Integer.valueOf(this.environment.getProperty("haier.cloud.connect.timeout")));
			httpClient.executeMethod(postMethod);
			final String response = postMethod.getResponseBodyAsString();
			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.readValue(response, GetAccessTokenResponseDto.class);
		} catch (final Exception e) {
			logger.error("getSmartucCloudAccessToken -> encrypt error:" + e);
		} finally {
			if (httpClient != null) {
				postMethod.releaseConnection();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
			}
		}
		return GetAccessTokenResponseDto.empty();
	}

	@Override
	public UplusDeviceResponseDto getUplusDevices(final UplusDeviceRequestDto requestDto) {

		final String appId = this.environment.getProperty("haier.cloud.appId");
		final String appKey = this.environment.getProperty("haier.cloud.appkey");
		final String clientId = this.environment.getProperty("haier.cloud.clientId");
		final long timestamp = System.currentTimeMillis();
		final String url = this.environment.getProperty("uplus.device.url").concat(requestDto.getUserId())
				.concat("/devices?type=&subType=&specialCode=&typeIdentifier=");

		// String uwsSign = SignUtils.sha256Uws(appId, appKey, clientId, "", timestamp, signUrl);

		final CloseableHttpClient httpClient = new HttpClientBuilder()
				.withConnectTimeout(Integer.valueOf(this.environment.getProperty("haier.cloud.connect.timeout")))
				.withSocketTimeout(Integer.valueOf(this.environment.getProperty("haier.cloud.connect.timeout"))).build();
		final HttpGet getMethod = new HttpGet(url);

		try {

			getMethod.addHeader("appId", appId);
			getMethod.addHeader("clientId", clientId);
			getMethod.addHeader("appVersion", this.environment.getProperty("fpa.app.version"));
			getMethod.addHeader("appKey", appKey);
			getMethod.addHeader("sequenceId", "20180720001");
			getMethod.addHeader("accessToken", requestDto.getToken());
			getMethod.addHeader("timestamp", String.valueOf(timestamp));
			getMethod.addHeader("language", "en");
			getMethod.addHeader("timezone", "+8");
			getMethod.addHeader("Content-Type", "application/json");

			final String responseBody = EntityUtils.toString(httpClient.execute(getMethod).getEntity(), "UTF-8");
			logger.info("UplusCloudServiceImpl -> getUplusDevices -> response body:", responseBody);

			final ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(responseBody, UplusDeviceResponseDto.class);
		} catch (final Exception e) {
			logger.error("UplusCloudServiceImpl -> getUplusDevices ->  error:" + e);
		} finally {
			if (httpClient != null) {
				getMethod.releaseConnection();
				try {
					httpClient.close();
				} catch (final IOException e) {
					logger.error("Closing http client socket stream failed.");
				}
			}
		}
		return UplusDeviceResponseDto.empty();

	}

	public static void main(final String[] agrs) {
		final UplusCloudServiceImpl impl = new UplusCloudServiceImpl();
		final UplusDeviceRequestDto dto = new UplusDeviceRequestDto();
		dto.setClientId("sfcid531124910654851");
		dto.setToken("TGT3QBN1E1C90JFI2PZ895T50RSXP0");
		impl.getUplusDevices(dto);
	}

}
