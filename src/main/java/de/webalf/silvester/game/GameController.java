package de.webalf.silvester.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * @author Alf
 * @since 13.12.2024
 */
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
	private final GameService gameService;

	@PostMapping("/host")
	public int getHostGame() {
		return gameService.createGame();
	}

	@GetMapping("/{id}/players")
	public List<String> getPlayers(@PathVariable int id) {
		return gameService.getPlayers(id);
	}

	@GetMapping("/{id}")
	public Game getGame(@PathVariable int id) {
		return gameService.getGame(id);
	}

	@PutMapping("/{id}/start")
	public LocalDateTime putStart(@PathVariable int id) {
		return gameService.startGame(id);
	}

	@PutMapping(path = "/{id}/join", consumes = TEXT_PLAIN_VALUE)
	public void postJoin(@PathVariable int id, @RequestBody String player) {
		gameService.joinGame(id, player);
	}

	@GetMapping("/{id}/start-time")
	public LocalDateTime getStartTime(@PathVariable int id) {
		return gameService.getStartTime(id);
	}
}
