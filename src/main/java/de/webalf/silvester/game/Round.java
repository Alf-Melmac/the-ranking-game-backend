package de.webalf.silvester.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alf
 * @since 13.12.2024
 */
public record Round(Map<String, List<String>> answers) {
	Round() {
		this(new HashMap<>());
	}
}
