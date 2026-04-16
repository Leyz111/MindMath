package com.example.mindmath;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.graphics.shapes.Feature;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mindmath.tasks.Question;
import com.example.mindmath.tasks.QuestionQueue;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {

    MaterialButton nextButton, skipButton;
    TextView questionNumberTextView, questionTextView, attemptsTextView;
    EditText answerEditText;
    ProgressBar progressBar;

    Question q1 = new Question("2+2", "3");
    Question q2 = new Question("3+2", "3");
    Question q3 = new Question("4+2", "3");
    Question q4 = new Question("5+2", "3");

    ArrayList<Question> queue = new ArrayList<>();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    QuestionQueue questionQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        skipButton = view.findViewById(R.id.skipButton);
        questionTextView = view.findViewById(R.id.questionTextView);
        answerEditText = view.findViewById(R.id.answerEditText);
        attemptsTextView = view.findViewById(R.id.attemptsTextView);
        progressBar = view.findViewById(R.id.progressBar);

        queue.add(q1);
        queue.add(q2);
        queue.add(q3);
        queue.add(q4);



        questionQueue = new QuestionQueue(queue);

        updateQuestion(questionTextView, questionQueue.getCurrentQuestion().getEquation());


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = answerEditText.getText().toString().trim();
                Question current = questionQueue.getCurrentQuestion();

                if (current == null) return;

                if (userInput.equals(current.getAnswer())) {
                    questionQueue.nextQuestion();
                    answerEditText.setText("");

                    Question next = questionQueue.getCurrentQuestion();
                    if (next != null) {
                        updateQuestion(questionTextView, next.getEquation());
                        attemptsTextView.setText("");
                    } else {
                        questionTextView.setText("Конец заданий");
                        nextButton.setEnabled(false);
                    }
                } else {
                    int attempts = current.getAttempts() - 1;
                    current.setAttempts(attempts);

                    if (attempts <= 0) {
                        questionQueue.nextQuestion();
                        answerEditText.setText("");

                        Question next = questionQueue.getCurrentQuestion();
                        if (next != null) {
                            updateQuestion(questionTextView, next.getEquation());

                        } else {
                            questionTextView.setText("");
                            nextButton.setEnabled(false); // От греха подальше
                        }
                    } else {
                        attemptsTextView.setText(String.valueOf(attempts));
                    }
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionQueue.skipCurrentQuestion();
                updateQuestion(questionTextView, questionQueue.getCurrentQuestion().getEquation());
            }
        });


        return view;
    }

    private void updateQuestion(TextView question, String equation) {
        question.setText(equation);
    }

    private void updateQuestionNum(TextView questionNum, int number) {
        questionNum.setText(number);
    }
}