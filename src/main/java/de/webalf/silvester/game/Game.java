package de.webalf.silvester.game;

import de.webalf.silvester.SessionUtils;
import de.webalf.silvester.question.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alf
 * @since 13.12.2024
 */
@Data
@AllArgsConstructor
public final class Game {
	private final int id;
	private final Map<String, String> players = new ConcurrentHashMap<>();
	private LocalDateTime startTime;
	private final Map<Question, Round> rounds = Collections.synchronizedMap(new EnumMap<>(Question.class));

	Game(int id) {
		this(id, null);
	}

	void join(@NonNull String player) {
		getPlayers().put(SessionUtils.getSessionId(), player);
	}

	List<String> getPlayerNamesOrdered() {
		return players.values().stream().sorted().toList();
	}

	public void addAnswer(@NonNull Question question, @NonNull List<String> answers) {
		getRounds()
				.computeIfAbsent(question, q -> new Round())
				.answers().put(SessionUtils.getSessionId(), answers);
	}

	public Round getRound(@NonNull Question question) {
		return getRounds().get(question);
	}

	boolean isEnd() {
		return getRounds().size() == Question.values().length;
	}
}
