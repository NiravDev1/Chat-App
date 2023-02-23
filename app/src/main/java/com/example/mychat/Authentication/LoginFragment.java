package com.example.mychat.Authentication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mychat.Home.HomeActivity;
import com.example.mychat.R;
import com.example.mychat.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    FragmentLoginBinding loginBinding;
    String Email, Password;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);
        loginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = loginBinding.emailLogin.getEditText().getText().toString().trim();
                Password = loginBinding.passwordLogin.getEditText().getText().toString().trim();
                if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(getContext(), "fill the fields", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(getContext(), "Please Enter vaild Email", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 8) {
                    Toast.makeText(getContext(), "Password must content 8 Character or more", Toast.LENGTH_SHORT).show();
                } else {
                    CheckEmail(Email, Password);
                }

            }
        });

        loginBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.auh_framlayout, new ForgotPasswordFragment());
                fragmentTransaction.addToBackStack(String.valueOf(new LoginFragment()));
                fragmentTransaction.commit();
            }
        });


        loginBinding.ToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.auh_framlayout, new SignupFragment());
                fragmentTransaction.addToBackStack(String.valueOf(new LoginFragment()));
                fragmentTransaction.commit();

            }
        });

        return loginBinding.getRoot();
    }

    private void CheckEmail(String email, String password) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean ce = task.getResult().getSignInMethods().isEmpty();
                if (ce) {
                    Toast.makeText(getContext(), "This email is new please create new account", Toast.LENGTH_SHORT).show();
                } else {
                    UserLogin(email, password);
                    dialog.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void UserLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Login fail", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}