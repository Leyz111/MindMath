package com.example.mindmath.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mindmath.R;
import com.example.mindmath.person.LocalPerson;
import com.example.mindmath.person.Person;
import com.example.mindmath.questions.Question;
import com.example.mindmath.questions.QuestionGenerator;
import com.example.mindmath.questions.QuestionQueue;
import com.example.mindmath.repository.PersonRepository;
import com.example.mindmath.repository.RepositoryCallback;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MaterialButton nextButton, skipButton;
    TextView questionNumberTextView, questionTextView, attemptsTextView;

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, backButton;
    EditText answerEditText;
    ProgressBar progressBar;
    ArrayList<Question> queue = new ArrayList<>();
    int correctAnswers = 0;
    QuestionQueue questionQueue;
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

    PersonRepository personRepository = new PersonRepository();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        skipButton = view.findViewById(R.id.mainPageButton);
        questionTextView = view.findViewById(R.id.questionTextView);
        answerEditText = view.findViewById(R.id.answerEditText);
        attemptsTextView = view.findViewById(R.id.attemptsTextView);
        progressBar = view.findViewById(R.id.progressBar);

        button0 = view.findViewById(R.id.button0);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);
        backButton = view.findViewById(R.id.buttonBack);

        for (int i = 0; i < 3; i++) {
            queue.add(QuestionGenerator.generate());
        }

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
                    attemptsTextView.setText("");

                    Question next = questionQueue.getCurrentQuestion();

                    if (current.getAttempts() == 3) {
                        correctAnswers++;
                    }

                    if (next != null) {
                        updateQuestion(questionTextView, next.getEquation());
                        attemptsTextView.setText("");
                    } else {
                        LocalPerson.getInstance().setTopResult(String.valueOf(Integer.parseInt(LocalPerson.getInstance().getTopResult()) + correctAnswers));

                        personRepository.updatePerson(LocalPerson.getInstance(), new RepositoryCallback<Person>() {
                            @Override
                            public void onSuccess(Person result) {
                                Toast.makeText(getContext(), LocalPerson.getInstance().getTopResult().toString(), Toast.LENGTH_SHORT).show();
                                LocalPerson.getInstance().saveToShaSharedPreferences(requireContext());
                            }

                            @Override
                            public void onFail(String error) {

                            }
                        });

                        loadFragment(new TaskStatsFragment());
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
                            attemptsTextView.setText("");
                        } else {
                            LocalPerson.getInstance().setTopResult(String.valueOf(Integer.parseInt(LocalPerson.getInstance().getTopResult()) + correctAnswers));


                            personRepository.updatePerson(LocalPerson.getInstance(), new RepositoryCallback<Person>() {
                                @Override
                                public void onSuccess(Person result) {
                                    Toast.makeText(getContext(), LocalPerson.getInstance().getTopResult().toString(), Toast.LENGTH_SHORT).show();
                                    LocalPerson.getInstance().saveToShaSharedPreferences(requireContext());
                                }

                                @Override
                                public void onFail(String error) {

                                }
                            });

                            loadFragment(new TaskStatsFragment());
                        }
                    } else {
                        attemptsTextView.setText("Осталось попыток: " + String.valueOf(attempts));
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

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "0");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerEditText.setText(answerEditText.getText() + "9");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answerEditText.getText().toString().isEmpty()) {
                    answerEditText.setText(answerEditText.getText().toString().substring(0, answerEditText.getText().toString().length() - 1));
                }
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

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();

        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}