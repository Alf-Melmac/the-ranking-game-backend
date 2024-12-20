package de.webalf.silvester.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static de.webalf.silvester.question.Question.Category.*;

/**
 * @author Alf
 * @since 13.12.2024
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Question {
	Q_1("Title1", "Description1", WARM_UP),
	Q_2("Title2", "Description2", WARM_UP),
	Q_3("Title3", "Description3", CHALLENGER),
	Q_4("Title4", "Description4", CHALLENGER),
	Q_5("Title5", "Description5", SPICY),
	Q_6("Title6", "Description6", SPICY);

	private final String title;
	private final String description;
	private final Category category;

	public enum Category {
		WARM_UP,
		CHALLENGER,
		SPICY
	}
}
