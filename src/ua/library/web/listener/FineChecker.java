package ua.library.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class FineChecker implements HttpSessionListener {

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// Do here the job.
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}
}