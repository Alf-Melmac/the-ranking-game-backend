package de.webalf.silvester.result;

import de.webalf.silvester.question.QuestionDto;

import java.util.List;

/**
 * @author Alf
 * @since 15.12.2024
 */
public record Result(QuestionDto question, List<Score> scores) {}
