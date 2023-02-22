package com.example.mychat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.mychat.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
    FragmentForgotPasswordBinding binding;
    private String Email;
    FirebaseAuth auth =FirebaseAuth.getInstance();
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentForgotPasswordBinding.inflate(inflater, container, false);

        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);
        binding.forgotpasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=binding.emailForgot.getEditText().getText().toString();
                if (Email.isEmpty())
                {
                    Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    Toast.makeText(getContext(), "Enter a valid Email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "fill", Toast.LENGTH_SHORT).show();
                   CheckEmail(Email);
                }
            }
        });




        return binding.getRoot();

    }

    private void CheckEmail(String email) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean e=task.getResult().getSignInMethods().isEmpty();
                if (e)
                {
                    Toast.makeText(getContext(), "This is email is new ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "exist", Toast.LENGTH_SHORT).show();
                    Forgotpassword(email);
                    dialog.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void Forgotpassword(String email) {
    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
            {
                Toast.makeText(getContext(), "Forgot link send on email", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Password Forgot link has been send your Email");
                builder.setIcon(R.drawable.outline_email_24);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.auh_framlayout,new LoginFragment());
                        fragmentTransaction.addToBackStack(String.valueOf(new ForgotPasswordFragment()));
                        fragmentTransaction.commit();
                    }
                });
                builder.show();

            }
            else
            {
                Toast.makeText(getContext(), "fail to send link", Toast.LENGTH_SHORT).show();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getContext(), "Error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }

}