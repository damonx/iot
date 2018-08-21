package com.fpa.usercenter.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
import com.fpa.usercenter.dto.sforce.OauthTokenResponseDto;
import com.fpa.usercenter.dto.sforce.QueryDataResponseDto;
import com.fpa.usercenter.service.SalesforceOAuthService;
import com.fpa.usercenter.util.HttpClientFactory;

@PropertySources(value = { @PropertySource("classpath:upluscloud.properties") })
@Service
public class SalesforceOAuthServiceImpl implements SalesforceOAuthService {

	private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(SalesforceOAuthServiceImpl.class);

	@Autowired
	private Environment environment;

	@Override
	public OauthTokenResponseDto getOAuthAccessToken(final String code) {
		Objects.requireNonNull(code, "Code cannont be null!");
		final CloseableHttpClient httpClient = new HttpClientFactory().init();
		final String salesForceOauthUrl = this.environment.getProperty("fpa.salesforce.oauth");

		final HttpPost httpPost = new HttpPost(salesForceOauthUrl);

		try {
			final String oauthPayload = buildOauthPostPayload(code);
			// content.append("&format=json");

			logger.info("SalesforceOAuthServiceImpl -> content -> response:" + oauthPayload.toString());

			final HttpEntity uefEntity = new StringEntity(oauthPayload.toString(), DEFAULT_CHARACTER_ENCODING);

			httpPost.setEntity(uefEntity);

			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

			final CloseableHttpResponse response = httpClient.execute(httpPost);

			final String response_str = EntityUtils.toString(response.getEntity(), DEFAULT_CHARACTER_ENCODING);

			final ObjectMapper mapper = new ObjectMapper();
			logger.info("SalesforceOAuthServiceImpl -> OauthToken -> response:" + response_str);

			mapper.setSerializationInclusion(Include.NON_NULL);
			return mapper.readValue(response_str, OauthTokenResponseDto.class);
		} catch (final Exception e) {
			logger.info("SalesforceOAuthServiceImpl-> error:" + e);
		} finally {
			httpPost.releaseConnection();
			try {
				httpClient.close();
			} catch (final IOException e) {
				logger.info("SalesforceOAuthServiceImpl-> error:" + e);
			}
		}
		return null;
	}

	private String buildOauthPostPayload(final String code) throws UnsupportedEncodingException {
		final StringBuilder content = new StringBuilder();

		content.append("grant_type=authorization_code");

		content.append("&client_id=").append(this.environment.getProperty("fpa.client.id"));

		content.append("&client_secret=").append(this.environment.getProperty("fpa.client.secret"));

		content.append("&redirect_uri=").append(URLEncoder.encode(this.environment.getProperty("fpa.client.callback"), "ISO-8859-1"));

		content.append("&code=").append(code);

		return content.toString();
	}

	@Override
	public QueryDataResponseDto getUserData(final String accessToken, final String sfIdentityUrl) {

		final CloseableHttpClient httpClient = new HttpClientFactory().init();
		final StringBuilder content = new StringBuilder();
		content.append(sfIdentityUrl).append("?");
		content.append("oauth_token=").append(accessToken);
		final HttpGet httpGet = new HttpGet(content.toString());

		try {

			// HttpEntity uefEntity = new StringEntity(content.toString(), "UTF-8");
			// httpPost.setEntity(uefEntity);

			httpGet.setHeader("Content-Type", "application/json");
			final ObjectMapper mapper = new ObjectMapper();
			final CloseableHttpResponse response = httpClient.execute(httpGet);

			final String response_str = EntityUtils.toString(response.getEntity(), DEFAULT_CHARACTER_ENCODING);

			logger.info("SalesforceOAuthServiceImpl -> QueryData -> response:" + response_str);
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(response_str, QueryDataResponseDto.class);
		} catch (final Exception e) {
			logger.error("SalesforceOAuthServiceImpl-> error:" + e);
		} finally {
			httpGet.releaseConnection();
			try {
				httpClient.close();
			} catch (final IOException e) {
				logger.error("SalesforceOAuthServiceImpl-> error:" + e);
			}
		}
		return QueryDataResponseDto.empty();
	}

	// TODO: Unit Test
	public static void main(final String[] agrs) {
		// SalesforceOAuthServiceImpl impl = new SalesforceOAuthServiceImpl();

		// impl.QueryData("00Dp0000000DCjJ!AQMAQDgnn25Kg4ywgs_brShxoo5KA0vG1b6fUcml90Kg6FAVdxryMZXA9Uf5QwkY6guek1QDJ.ODRFAo2nrUE.o_H5Ku6U9B",
		// "https://test.salesforce.com/id/00Dp0000000DCjJEAW/005p0000001j6UvAAI");

		final String id = "005p0000001j6UvAAI";
		final char[] ids = id.toCharArray();
		final StringBuffer sb = new StringBuffer();

		for (final char d : ids) {
			if (d != '0') {
				// System.out.println();
				final int s = d;
				sb.append(s);
			}
		}
		String strid = sb.toString();
		System.out.println(strid);
		if (strid.length() > 15) {
			strid = strid.substring(0, 15);
		}

		System.out.println(Long.valueOf(strid));
	}

}
