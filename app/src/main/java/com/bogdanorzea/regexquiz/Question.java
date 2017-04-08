package com.bogdanorzea.regexquiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Question implements Serializable {

    // Title of the question
    private String mTitle;

    // The body of the question
    private String mDescription;

    // Array of choices
    private String[] mAvailableChoices;

    // Correct answer (multiple correct values in case of the multiple choice questions)
    private List<String> mAnswers;

    // Choices made by user
    private List<String> mUserChoice;

    // Boolean set to verify if question was answered
    private boolean hasAnswer;

    // Generic private constructor that handles title and description
    private Question(String title, String description) {
        mTitle = title;
        mDescription = description;
        mUserChoice = new ArrayList<>();
        hasAnswer = false;
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
     * @param title       Title of the question
     * @param description Body of the question
     * @param choices     Array of possibles choices
     * @param answer      Correct answer
     */
    Question(String title, String description, String[] choices, String answer) {
        this(title, description, answer);
        mAvailableChoices = choices.clone();
    }

    /**
     * Creates a multiple option question
     *
     * @param title       Title of the question
     * @param description Body of the question
     * @param choices     Array of possibles choices
     * @param answers     Array of correct answers
     */
    Question(String title, String description, String[] choices, String[] answers) {
        this(title, description);
        mAvailableChoices = choices.clone();
        mAnswers = new ArrayList<>(Arrays.asList(answers));
    }

    public boolean wasAnswered() {
        return hasAnswer;
    }

    /**
     * Marks the question as answered
     */
    public void markAnswered() {
        this.hasAnswer = true;
    }

    public void addExclusiveUserChoice(String option) {
        mUserChoice.clear();
        mUserChoice.add(option);
    }

    public boolean hasSufficientChoices() {
        return (mAnswers.size() == mUserChoice.size());
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

    public List<String> getUserChoices() {
        return mUserChoice;
    }

    public boolean isCorrectlyAnswered() {
        List<String> a = mAnswers;
        List<String> b = mUserChoice;
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }

    public void addUserChoice(String s) {
        if (!mUserChoice.contains(s)) {
            mUserChoice.add(s);
        }
    }

    public void removeUserChoice(String s) {
        if (mUserChoice.contains(s)) {
            mUserChoice.remove(s);
        }
    }

    public boolean hasAsAnswer(String s) {
        return mUserChoice.contains(s);
    }

    public boolean hasChoices() {
        return mUserChoice.size() != 0;
    }
}
