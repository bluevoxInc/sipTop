/**
 * 
 */
package org.bluevox.inc.web;

import org.springframework.util.StringUtils;

/**
 * @author wnorman
 *
 */
public final class WebUtils {
	
	public static String cleanupWebUrl(String webUrl) {
		webUrl = StringUtils.trimWhitespace(webUrl);
		if (webUrl == null || "".equals(webUrl)) {
			return webUrl;		
		} else {
			webUrl = webUrl.toLowerCase();
			if (webUrl.startsWith("http://") || webUrl.startsWith("https://")) {
				return webUrl;
			} else if (webUrl.startsWith("javascript:")) {
				return "#";
			} else {
				return "http://" + webUrl;
			}

		}
	}

}
