package com.bignerdranch.android.geoquiz;

/**
 * Created by Jun on 6/15/2017.
 */

public class Question {

    private int mTextRedId;
    private boolean mAnswerTrue;

    public Question(int TextRedId, boolean answerTrue)
    {
        mTextRedId = TextRedId;
        mAnswerTrue = answerTrue;
    }

    public int getTextRedId() {
        return mTextRedId;
    }

    public void setTextRedId(int textRedId) {
        mTextRedId = textRedId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
