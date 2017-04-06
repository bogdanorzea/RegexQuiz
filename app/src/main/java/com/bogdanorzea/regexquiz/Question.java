package com.bogdanorzea.regexquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Question {

    // Title of the question
    private String mTitle;
    // The body of the question
    private String mDescription;
    // Array of strings used by RadioButtons
    private String[] mAvailableChoices;
    // Array of strings used by CheckBoxButtons
    // Correct answer (multiple correct values in case of the multiple choice questions)
    private List<String> mAnswers;

    // Generic private constructor that handles title and description
    private Question(String title, String description) {
        mTitle = title;
        mDescription = description;
    }

    /**
     * Creates an input type question
     *
     * @param title       Title of the question
     * @param description Body of the question
     * @param answer      Correct answer
     */
    Question(String title, String description, String answer) {
        this(title, description);
        mAnswers = new ArrayList<>();
        mAnswers.add(answer);
    }

    /**
     * Creates a single option question
     *
     * @param title         Title of the question
     * @param description   Body of the question
     * @param choices Array of possibles choices
     * @param answer        Correct answer
     */
    Question(String title, String description, String[] choices, String answer) {
        this(title, description, answer);
        mAvailableChoices = choices.clone();
    }

    /**
     * Creates a multiple option question
     *
     * @param title           Title of the question
     * @param description     Body of the question
     * @param choices Array of possibles choices
     * @param answers         Array of correct answers
     */
    Question(String title, String description, String[] choices, String[] answers) {
        this(title, description);
        mAvailableChoices = choices.clone();
        mAnswers = new ArrayList<>(Arrays.asList(answers));
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mDescription;
    }

    String[] getAvailableChoices() {
        return mAvailableChoices;
    }

    List<String> getAnswers() {
        return mAnswers;
    }
}


//JSON format: http://www.objgen.com/
//
//questions[]
//  title = Question 1
//  description = What does "regex" shorthand stand for?
//  single_choices[4] = Regional Expressions, Regular Expressions, Regal Experience, Regular Exercise
//  multiple_choices[4]
//  answers[] = Regular Expressions