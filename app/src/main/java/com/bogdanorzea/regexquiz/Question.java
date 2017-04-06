package com.bogdanorzea.regexquiz;

public class Question {
    // Title of the question
    private String mTitle;
    // The body of the question
    private String mDescription;
    // Array of strings used by RadioButtons
    private String[] mSingleChoices;
    // Array of strings used by CheckBoxButtons
    private String[] mMultipleChoices;
    // Correct answer (multiple correct values in case of the multiple choice questions)
    private String[] mAnswers;

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
    public Question(String title, String description, String answer) {
        this(title, description);
        mAnswers = new String[]{answer};
    }

    /**
     * Creates a single option question
     *
     * @param title         Title of the question
     * @param description   Body of the question
     * @param singleChoices Array of possibles choices
     * @param answer        Correct answer
     */
    public Question(String title, String description, String[] singleChoices, String answer) {
        this(title, description, answer);
        mSingleChoices = singleChoices.clone();
    }

    /**
     * Creates a multiple option question
     *
     * @param title           Title of the question
     * @param description     Body of the question
     * @param multipleChoices Array of possibles choices
     * @param answers         Array of correct answers
     */
    public Question(String title, String description, String[] multipleChoices, String[] answers) {
        this(title, description);
        mMultipleChoices = multipleChoices.clone();
        mAnswers = answers.clone();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String[] getSingleChoices() {
        return mSingleChoices;
    }

    public String[] getMultipleChoices() {
        return mMultipleChoices;
    }

    public String[] getAnswers() {
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