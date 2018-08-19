/*
 * Copyright (c) Fisher and Paykel Appliances.
 *
 * This document is copyright. Except for the purpose of fair reviewing, no part
 * of this publication may be reproduced or transmitted in any form or by any
 * means, electronic or mechanical, including photocopying, recording, or any
 * information storage and retrieval system, without permission in writing from
 * the publisher. Infringers of copyright render themselves liable for
 * prosecution.
 */
package com.fpa.usercenter.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

public class HttpClientBuilder {

	private CloseableHttpClient httpClient;

	private final int connectionRequestTimeout = 5000;
	private int socketTimeout = 30000;
	private int connectTimeout = 5000;
	private final int maxTotal = 500;
	private final int maxPerRoute = 100;

	private final String detailHostName = "http://www.google.com";
	private final int detailPort = 80;
	private final int detailMaxPerRoute = 100;

	public HttpClientBuilder withConnectTimeout(final int connTimeout) {
		this.connectTimeout = connTimeout;
		return this;
	}

	public HttpClientBuilder withSocketTimeout(final int socTimeOut) {
		this.socketTimeout = socTimeOut;
		return this;
	}

	public CloseableHttpClient build() {
		if (null == this.httpClient) {
			synchronized (HttpClientBuilder.class) {
				if (null == this.httpClient) {
					this.httpClient = init();
				}
			}
		}
		return this.httpClient;
	}

	public CloseableHttpClient init() {
		final ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		final LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf)
				.register("https", sslsf).build();
		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		cm.setMaxTotal(this.maxTotal);
		cm.setDefaultMaxPerRoute(this.maxPerRoute);

		final HttpHost httpHost = new HttpHost(this.detailHostName, this.detailPort);
		cm.setMaxPerRoute(new HttpRoute(httpHost), this.detailMaxPerRoute);
		final HttpRequestRetryHandler httpRequestRetryHandler = (final IOException exception, final int executionCount,
				final HttpContext context) -> {
			if (executionCount >= 2) {
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// connection lost
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				return false;
			}
			if (exception instanceof InterruptedIOException) {
				// time out
				return false;
			}
			if (exception instanceof UnknownHostException) {
				return false;
			}
			if (exception instanceof ConnectionPoolTimeoutException) {
				return false;
			}
			if (exception instanceof SSLException) {
				return false;
			}
			final HttpClientContext clientContext = HttpClientContext.adapt(context);
			final HttpRequest request = clientContext.getRequest();

			if (!(request instanceof HttpEntityEnclosingRequest)) {
				return true;
			}

			return false;
		};

		final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(this.connectionRequestTimeout)
				.setConnectTimeout(this.connectTimeout).setSocketTimeout(this.socketTimeout).build();
		return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig)
				.setRetryHandler(httpRequestRetryHandler).build();
	}
}
