package com.example.iknow243;

import androidx.annotation.Nullable;

public interface QuestionResponseHandler {


    void onQuestionSubmitted(boolean isSuccess,@Nullable QuestionModel modelValue );

    void onFirstQuestionReceived();
}
