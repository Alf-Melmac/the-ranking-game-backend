package de.webalf.silvester.question;

import de.webalf.silvester.SessionUtils;
import de.webalf.silvester.game.Game;
import de.webalf.silvester.game.GameService;
import de.webalf.silvester.game.Round;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alf
 * @since 15.12.2024
 */
@Service
@RequiredArgsConstructor
@Slf4j
class QuestionService {
	private final GameService gameService;

	Question getCurrentQuestion(int gameId) {
		final Game game = gameService.getGame(gameId);
		final int round = game.getRounds().size();
		return Question.values()[round];
	}

	void answerQuestion(int gameId, @NonNull Question id, @NonNull List<String> answer) {
		final Game game = gameService.getGame(gameId);
		if (game.getStartTime() != null) {
			log.info("Player already answered in game {} while startTime was still set", gameId);
			game.setStartTime(null);
		}
		game.addAnswer(id, answer);
		final String s = game.getPlayers().get(SessionUtils.getSessionId());
		log.info("{} answered question {} with {}", s, id, answer);
	}

	boolean shouldFastForward(int gameId, @NonNull Question id) {
		final Game game = gameService.getGame(gameId);
		final Round round = game.getRound(id);
		if (round == null) {
			return false;
		}
		final boolean allAnswered = game.getPlayers().size() == round.answers().size();
		if (allAnswered) {
			log.debug("All players finished {}", id);
			game.setStartTime(null);
		}
		return allAnswered;
	}
}
