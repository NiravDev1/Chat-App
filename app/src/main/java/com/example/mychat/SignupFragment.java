package com.example.mychat;

import android.app.Dialog;
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

import com.example.mychat.databinding.FragmentSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

    FragmentSignupBinding signupBinding;
    String Name, Email, Phone, Password, CPassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Dialog dialog;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signupBinding = FragmentSignupBinding.inflate(inflater, container, false);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);
        signupBinding.ToLoginF.setOnClickListener(new View.OnClickListener() { ///to login fragments
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.auh_framlayout, new LoginFragment());
                fragmentTransaction.addToBackStack(String.valueOf(new LoginFragment()));
                fragmentTransaction.commit();
            }
        });
        signupBinding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = signupBinding.nameSign.getEditText().getText().toString().trim();
                Email = signupBinding.emailSignup.getEditText().getText().toString().trim();
                Phone = signupBinding.phoneSignup.getEditText().getText().toString().trim();
                Password = signupBinding.passwordSignup.getEditText().getText().toString().trim();
                CPassword = signupBinding.confirmPasswordSignup.getEditText().getText().toString().trim();

                if (Name.isEmpty() || Email.isEmpty() || Phone.isEmpty() || Password.isEmpty() || CPassword.isEmpty()) {
                    Toast.makeText(getContext(), "fill the filed", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(getContext(), "valid Email ", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 8) {
                    Toast.makeText(getContext(), "password must contend 8 character or more ", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(CPassword)) {
                    Toast.makeText(getContext(), "password not match", Toast.LENGTH_SHORT).show();
                } else {
                    CheckEmail(Email, Name, Phone, CPassword);
                }


            }
        });

        return signupBinding.getRoot();
    }

    private void CheckEmail(String email, String name, String phone, String CPassword) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean ce = task.getResult().getSignInMethods().isEmpty();

                        if (ce) {
                            CreateUserAuth(email, name, phone, CPassword);
                            dialog.show();
                        } else {
                            Toast.makeText(getContext(), "This Email is already use! try another email", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void CreateUserAuth(String email, String name, String phone, String cPassword) {
        auth.createUserWithEmailAndPassword(email, cPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String Uid = task.getResult().getUser().getUid();
                    UserModelClass userModelClass = new UserModelClass(Uid, email, name, phone, cPassword);
                    reference.child("Users").child(Uid).setValue(userModelClass);
                    Toast.makeText(getContext(), "auth successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getFragmentManager().beginTransaction().replace(R.id.auh_framlayout, new LoginFragment()).commit();
                } else {
                    Toast.makeText(getContext(), "Signup fail try agin", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
    }
}