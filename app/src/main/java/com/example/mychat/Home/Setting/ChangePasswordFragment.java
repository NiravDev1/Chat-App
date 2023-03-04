package com.example.mychat.Home.Setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mychat.Authentication.ForgotPasswordFragment;
import com.example.mychat.Authentication.LoginFragment;
import com.example.mychat.R;
import com.example.mychat.databinding.FragmentChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLOutput;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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

    FragmentChangePasswordBinding binding;
    FirebaseUser user;
    Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String Email = user.getEmail();
        System.out.println(Email);

        binding.forgotPasswordTOFId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framlayout_id, new ForgotPasswordFragment());
                fragmentTransaction.addToBackStack(String.valueOf(new SettingFragment()));
                fragmentTransaction.commit();
            }
        });
        binding.changePasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Old_Password = binding.OldPassordInput.getEditText().getText().toString().trim();
                String New_Password = binding.NewPasswordInput.getEditText().getText().toString().trim();
                if (Old_Password.isEmpty() || New_Password.isEmpty()) {
                    Toast.makeText(getContext(), "Fill the filds", Toast.LENGTH_SHORT).show();
                } else {

                    System.out.println("old password" + Old_Password);
                    System.out.println("new password" + New_Password);
                    Toast.makeText(getContext(), "new" + binding.NewPasswordInput.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();
                    AuthCredential credential = EmailAuthProvider.getCredential(Email, Old_Password);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(New_Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Successfully Change a Password", Toast.LENGTH_SHORT).show();
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                                            HashMap hashMap = new HashMap();
                                            hashMap.put("userPassword", New_Password);
                                            reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.main_framlayout_id, new SettingFragment());
                                                        fragmentTransaction.addToBackStack(null);
                                                        fragmentTransaction.commit();
                                                    } else {
                                                        Toast.makeText(getContext(), "fail ", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "Fail to change Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getContext(), "Error::" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } else {
                                snackbar = Snackbar.make(getContext(), getView(), "auth fail ", Snackbar.LENGTH_SHORT);
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
        });

        return binding.getRoot();
    }
}