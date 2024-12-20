package de.webalf.silvester;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Alf
 * @since 15.12.2024
 */
@UtilityClass
public final class SessionUtils {
	public static String getSessionId() {
		final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return requestAttributes
				.getRequest()
				.getSession(true)
				.getId();
	}
}
