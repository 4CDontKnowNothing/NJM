package org.ht.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
	private static ApplicationContext applicationContext = null;
	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("inject ApplicationContext into SpringContextHolder:" + applicationContext);
		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder's ApplicationContext get covered, original ApplicationContext was:"
					+ SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	public static void clear() {
		logger.debug("clear SpringContextHolder's ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext not injected,please define SpringContextHolder in applicationContext.xml");
		}
	}
}
