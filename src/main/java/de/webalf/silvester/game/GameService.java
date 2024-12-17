package de.webalf.silvester.game;

import de.webalf.silvester.SchedulerService;
import de.webalf.silvester.question.Question;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.ZoneOffset.UTC;

/**
 * @author Alf
 * @since 13.12.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
	private final SchedulerService schedulerService;

	private static final Map<Integer, Game> games = new ConcurrentHashMap<>();
	private static final Random random = new Random();

	public Game getGame(int id) {
		final Game game = games.get(id);
		if (game == null) {
			throw new IllegalArgumentException("Game with id " + id + " not found");
		}
		return game;
	}

	int createGame() {
		final int id = random.nextInt(10000, 100000);
		final Game game = new Game(id);
		games.put(id, game);
		log.info("Created game with id {}", id);
		return id;
	}

	void joinGame(int id, @NonNull String player) {
		final Game game = getGame(id);
		game.join(player);
		log.info("Player {} joined game with id {}", player, id);
	}

	List<String> getPlayers(int id) {
		return getGame(id).getPlayerNamesOrdered();
	}

	LocalDateTime startGame(int id) {
		final Game game = getGame(id);

		if (game.getRounds().size() == Question.values().length) {
			return null;
		}

		final LocalDateTime startTime = LocalDateTime.now(UTC).plusSeconds(6);
		game.setStartTime(startTime);
		schedulerService.schedule(() -> game.setStartTime(null), 11);
		log.info("Game with id {} starting at {}", id, startTime);
		return startTime;
	}

	LocalDateTime getStartTime(int id) {
		final Game game = getGame(id);
		return game.getStartTime();
	}
}
