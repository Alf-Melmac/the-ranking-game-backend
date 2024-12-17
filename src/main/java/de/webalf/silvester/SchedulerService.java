package de.webalf.silvester;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Alf
 * @since 07.08.2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
	private ScheduledExecutorService scheduler;

	@PostConstruct
	private void init() {
		scheduler = Executors.newScheduledThreadPool(1);
	}

	/**
	 * Schedules the given {@link Runnable} to be executed after the given delay. Errors are caught and logged.
	 *
	 * @param runnable task to execute after given delay in seconds
	 * @param delay    after which the runnable is executed
	 */
	public void schedule(Runnable runnable, long delay) {
		scheduler.schedule(errorHandlingWrapper(runnable), delay, TimeUnit.SECONDS);
	}

	/**
	 * Wraps the given action in a try-catch block and executes the cleanup afterwards
	 *
	 * @param action  that may throw an exception
	 * @return wrapped runnable
	 */
	private static Runnable errorHandlingWrapper(Runnable action) {
		return () -> {
			try {
				action.run();
			} catch (Exception e) {
				log.warn("Exception in scheduled task", e);
			}
		};
	}

	@PreDestroy
	private void cleanUp() {
		scheduler.shutdown();
	}
}
