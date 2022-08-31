package net.seniorsoftwareengineer.testing.option;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Proxy configuration: before you connecting to url you pass from a proxy
 * Can be used to simulate different country 
 * Not use now, for future implementation
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProxyConfiguration {
	String proxyHost;
	String port;
	Proxy.ProxyType proxyType;
	String username;
	String password;
	Boolean https;
	Proxy proxy;

	public ProxyConfiguration(String proxyHost, String proxyPort, String proxyType) {
		this.proxyHost = proxyHost;
		this.port = proxyPort;
		this.proxyType = ProxyType.valueOf(proxyType);
	}

	public Proxy getConfigurationProxy() {
		final Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyHost + ":" + port);
		proxy.setSslProxy(proxyHost + ":" + port);
		if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(username)) {
			proxy.setSocksUsername(username);
			proxy.setSocksPassword(password);
		}
//		proxy.setAutodetect(false);
		proxy.setProxyType(proxyType);
		return proxy;
	}

}
