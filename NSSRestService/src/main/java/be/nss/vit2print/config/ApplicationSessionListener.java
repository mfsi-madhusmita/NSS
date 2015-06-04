package be.nss.vit2print.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session Listener to configure session time out
 */
public class ApplicationSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(10 * 60);// in seconds
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// Default implementation
	}

}
