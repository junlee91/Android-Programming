package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEATER = "cheater";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private ArrayList<Integer> mCheatedList;
    private int mCurrentIdx = 0;
    private boolean mIsCheater;

    private Question[] mQuestionBook = new Question[]{
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, true)
    };

    private void updateQuestion(){
        int question = mQuestionBook[mCurrentIdx].getTextRedId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressTrue) {
        boolean answerIsTrue = mQuestionBook[mCurrentIdx].isAnswerTrue();

        int messageResId = 0;

        //TODO: Cheated index using an ArrayList<int> to identify the user has cheated.
        if (mCheatedList.contains(mCurrentIdx))
        {
            mIsCheater = true;
        }

        if(mIsCheater){
            messageResId = R.string.judgement_toast;
        } else {

            if(userPressTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mCheatedList = new ArrayList<Integer>();

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIdx = (mCurrentIdx+1)%mQuestionBook.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mIsCheater) mCheatedList.add(mCurrentIdx);

                mCurrentIdx = (mCurrentIdx + 1) % mQuestionBook.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mIsCheater) mCheatedList.add(mCurrentIdx);

                mCurrentIdx = (mCurrentIdx - 1);

                if(mCurrentIdx < 0) mCurrentIdx = mQuestionBook.length - 1;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // CheatActivity Starts!
                // Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBook[mCurrentIdx].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                // startActivity(i);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });

        if(savedInstanceState != null){
            mCurrentIdx = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEATER, false);
            mCheatedList = savedInstanceState.getIntegerArrayList(CHEATER);
        }

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        Log.d(TAG, "onActivityResult");

        if(resultCode != Activity.RESULT_OK){
            mIsCheater = false;
            Log.d(TAG, "onActivityResult: " + mIsCheater);
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null) return;
        }

        mIsCheater = CheatActivity.wasAnswerShown(data);

        Log.d(TAG, "onActivityResult: " + mIsCheater);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIdx);
        savedInstanceState.putBoolean(CHEATER, mIsCheater);
        savedInstanceState.putIntegerArrayList(CHEATER, mCheatedList);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
