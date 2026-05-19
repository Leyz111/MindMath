package com.example.mindmath;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mindmath.person.LocalPerson;
import com.example.mindmath.person.Person;
import com.example.mindmath.repository.PersonRepository;
import com.example.mindmath.repository.RepositoryCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    EditText nameET, passwordET;
    Button nameButton, passwordButton;
    ImageButton toAccountButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        nameET = v.findViewById(R.id.changeNameET);
        passwordET = v.findViewById(R.id.changePasswordET);

        nameButton = v.findViewById(R.id.changeNameButton);
        passwordButton = v.findViewById(R.id.changePasswordButton);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalPerson.getInstance().setName(nameET.getText().toString());

                Person personToUpdate = new Person();
                personToUpdate.setLogin(LocalPerson.getInstance().getLogin());
                personToUpdate.setPassword(Hasher.hashString(LocalPerson.getInstance().getPassword(), personToUpdate.getLogin()));
                personToUpdate.setName(LocalPerson.getInstance().getName());
                personToUpdate.setRole(LocalPerson.getInstance().getRole());
                personToUpdate.setTopResult(LocalPerson.getInstance().getTopResult());

                personRepository.updatePerson(personToUpdate, new RepositoryCallback<Person>() {
                    @Override
                    public void onSuccess(Person result) {
                        LocalPerson.getInstance().saveToShaSharedPreferences(requireContext());
                        Toast.makeText(requireContext(), "Имя профиля успешно изменено", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
            }
        });

        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = Hasher.hashString(passwordET.getText().toString(), LocalPerson.getInstance().getLogin());

                LocalPerson.getInstance().setPassword(password);

                personRepository.updatePerson(LocalPerson.getInstance(), new RepositoryCallback<Person>() {
                    @Override
                    public void onSuccess(Person result) {
                        LocalPerson.getInstance().setPassword(passwordET.getText().toString());
                        LocalPerson.getInstance().saveToShaSharedPreferences(requireContext());
                        Toast.makeText(requireContext(), "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
            }
        });

        return v;
    }
}