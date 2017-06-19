package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.sql.BatchUpdateException;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String CHEATER = "cheater";
    private static final String CLICKED = "clicked";

    private boolean mIsClicked;
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context pakageContext, boolean answerIsTrue){
        Intent i = new Intent(pakageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerTextView(TextView mAnswerTextView, boolean mAnswerIsTrue)
    {
        if(mAnswerIsTrue){
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
    }

    private void buttonAnimationOnClick()
    {
        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mIsClicked = true;
                setAnswerTextView(mAnswerTextView, mAnswerIsTrue);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        if(savedInstanceState != null) {
            mIsClicked = savedInstanceState.getBoolean(CLICKED, false);
            if (mIsClicked) {
                mShowAnswer = (Button) findViewById(R.id.show_answer_button);
                mShowAnswer.setVisibility(View.INVISIBLE);
                setAnswerTextView(mAnswerTextView, mAnswerIsTrue);
            }
            buttonAnimationOnClick();
        } else {
            buttonAnimationOnClick();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState");
        savedInstanceState.putBoolean(CLICKED, mIsClicked);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }


}
