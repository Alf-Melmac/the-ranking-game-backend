package de.webalf.silvester.question;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alf
 * @since 15.12.2024
 */
@RestController
@RequestMapping("/game/{gameId}/question")
@RequiredArgsConstructor
public class QuestionController {
	private final QuestionService questionService;

	@GetMapping
	public QuestionDto getCurrentQuestion(@PathVariable int gameId) {
		return new QuestionDto(questionService.getCurrentQuestion(gameId));
	}

	@PostMapping("/{id}")
	public void postAnswer(@PathVariable int gameId, @PathVariable Question id, @RequestBody List<String> answer) {
		questionService.answerQuestion(gameId, id, answer);
	}

	@GetMapping("/{id}/fast-forward")
	public boolean getFastForward(@PathVariable int gameId, @PathVariable Question id) {
		return questionService.shouldFastForward(gameId, id);
	}
}
