package de.webalf.silvester.result;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alf
 * @since 15.12.2024
 */
@RestController
@RequestMapping("/game/{gameId}/result")
@RequiredArgsConstructor
public class ResultController {
	private final ResultService resultService;

	@GetMapping("/last")
	public Result getLastResult(@PathVariable int gameId) {
		return resultService.getLastResult(gameId);
	}

	@GetMapping
	public Result getGameResult(@PathVariable int gameId) {
		return resultService.getGameResult(gameId);
	}
}
