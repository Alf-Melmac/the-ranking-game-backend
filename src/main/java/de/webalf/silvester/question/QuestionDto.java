package de.webalf.silvester.question;

/**
 * @author Alf
 * @since 15.12.2024
 */
public record QuestionDto(String id, String title, String description, Question.Category category) {
	public QuestionDto(Question question) {
		this(question.name(), question.getTitle(), question.getDescription(), question.getCategory());
	}
}
