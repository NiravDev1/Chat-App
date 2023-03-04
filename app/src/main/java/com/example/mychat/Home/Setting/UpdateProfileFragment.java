package com.example.mychat.Home.Setting;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.AbstractInputMethodService;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mychat.Authentication.UserModelClass;
import com.example.mychat.R;
import com.example.mychat.databinding.FragmentUpdateProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProfileFragment newInstance(String param1, String param2) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
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

    FragmentUpdateProfileBinding binding;
    Uri uri=null;
    Bitmap bitmap;
    DatabaseReference reference;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.keepSynced(true);
        uid = FirebaseAuth.getInstance().getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModelClass userModelClass = dataSnapshot.getValue(UserModelClass.class);
                    if (uid.equals(userModelClass.getUserId())) {
                        binding.userUpdateNameId.getEditText().setText(userModelClass.getUserName());
                        binding.userUpdatePhoneId.getEditText().setText(userModelClass.getUserPhone());
                        Glide.with(binding.updateCuserProfileId).load(userModelClass.getUserProfile()).into(binding.updateCuserProfileId);

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        binding.updateCuserProfileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(intent, 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });
        binding.updateUserBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name=binding.userUpdateNameId.getEditText().getText().toString().trim();
                String Phone=binding.userUpdatePhoneId.getEditText().getText().toString().trim();
                if (Name.isEmpty()||Phone.isEmpty())
                {
                    Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Uploadtofirebase(Name, Phone);
                }
            }
        });


        return binding.getRoot();
    }

    private void Uploadtofirebase(String name, String phone) {

            if (uri==null)
            {
               withoutUritofirebase(name,phone);
            }
            else
            {
                withUritofirebase(name,phone);
            }

    }

    private void withoutUritofirebase(String name, String phone) {
        HashMap map1=new HashMap<>();
        map1.put("userName",name);
        map1.put("userPhone",phone);
        reference.child(uid).updateChildren(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_framlayout_id, new SettingFragment());
                    fragmentTransaction.addToBackStack(String.valueOf(new SettingFragment()));
                    fragmentTransaction.commit();

                }
                else
                {
                    Toast.makeText(getContext(), "Update fail", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void withUritofirebase(String name, String phone) {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("image1"+new Random().nextInt(25));
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HashMap map=new HashMap<>();
                        map.put("userName",name);
                        map.put("userPhone",phone);
                        map.put("userProfile",uri.toString());

                        reference.child(uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "successfully update profile", Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_framlayout_id, new SettingFragment());
                                    fragmentTransaction.addToBackStack(String.valueOf(new SettingFragment()));
                                    fragmentTransaction.commit();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Fail to update  profile", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data.getData();
            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream);
                binding.updateCuserProfileId.setImageBitmap(bitmap);


            } catch (Exception e) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}