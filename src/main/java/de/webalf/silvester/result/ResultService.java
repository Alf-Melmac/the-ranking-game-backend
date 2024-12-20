package de.webalf.silvester.result;

import de.webalf.silvester.game.Game;
import de.webalf.silvester.game.GameService;
import de.webalf.silvester.game.Round;
import de.webalf.silvester.question.Question;
import de.webalf.silvester.question.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Alf
 * @since 15.12.2024
 */
@Service
@RequiredArgsConstructor
public class ResultService {
	private final GameService gameService;

	Result getLastResult(int gameId) {
		final Game game = gameService.getGame(gameId);
		final Question lastQuestion = Question.values()[game.getRounds().size() - 1];

		final Round round = game.getRound(lastQuestion);
		final Map<String, Integer> ranks = game.getPlayers().values().stream()
				.collect(Collectors.toMap(Function.identity(), unused -> 0));
		addScoresOfRound(ranks, round);

		return new Result(new QuestionDto(lastQuestion), ranksToScoreList(ranks));
	}

	Result getGameResult(int gameId) {
		final Game game = gameService.getGame(gameId);
		final Map<String, Integer> ranks = game.getPlayers().values().stream()
				.collect(Collectors.toMap(Function.identity(), unused -> 0));
		game.getRounds().values().forEach(round -> addScoresOfRound(ranks, round));

		return new Result(null, ranksToScoreList(ranks));
	}

	/**
	 * Adds the scores of the given round to the given ranks
	 *
	 * @param ranks to add scores to
	 * @param round to calculate scores from
	 */
	private static void addScoresOfRound(Map<String, Integer> ranks, Round round) {
		round.answers().values().forEach(answers -> {
			//Iterate backwards through answers
			for (int i = answers.size() - 1, points = 1; i >= 0; i--, points++) {
				final String player = answers.get(i);
				if (player == null) {
					continue;
				}
				ranks.put(player, ranks.get(player) + points);
			}
		});
	}

	/**
	 * Convert ranks to a list of score ordered by the value
	 */
	private static List<Score> ranksToScoreList(Map<String, Integer> ranks) {
		return ranks.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.map(entry -> new Score(entry.getKey(), entry.getValue()))
				.toList();
	}
}
