package org.bluevox.inc.util;

public class StringUtils {
	
	/**
	 * <p>
	 * Returns a cleaned up version of the string. If the original string is
	 * <code>null</code> return that. Otherwise, trim the original
	 * string. If the result is empty, return <code>null</null>;
	 * otherwise return the trimmed string.
	 * </p>
	 * 
	 * @param s
	 * 	original string
	 * @return cleaned up version of the string s
	 */
	public static String cleanup(String s) {
		if (s == null) {
				return null;
		} else {
			String trimmed = s.trim();
			return ("".equals(trimmed) ? null : trimmed);
		}
	}

}
