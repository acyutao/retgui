/**
 * 
 */
package org.acca.retgui.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;

/**
 * @author yutao
 * 
 */
public class ConfigHelper {

	public static final String UPLOAD_FILE_PATH = "uploadFilePath";
	public static final String SIZE_CATEGORY="size";
	public static final String CATEGORY_CAPACITY="category";
	static final XMLConfiguration XMLINSTANCE;

	static {

		try {
			XMLINSTANCE = new XMLConfiguration("AppConfig.xml");
			ReloadingStrategy strategy = new FileChangedReloadingStrategy();
			XMLINSTANCE.setReloadingStrategy(strategy);

			

		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static XMLConfiguration getInstance() {
		return XMLINSTANCE;
	}

}
