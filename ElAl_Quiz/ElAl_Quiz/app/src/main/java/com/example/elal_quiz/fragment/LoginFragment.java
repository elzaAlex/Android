package com.example.elal_quiz.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.Fragment;

import com.example.elal_quiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_auth, null);
        final TextView emailView = root.findViewById(R.id.email);
        final TextView passView = root.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        root.findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString();
                String password = passView.getText().toString();
                try {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                if(getFragmentManager()!=null)
                                    getFragmentManager().beginTransaction().replace(R.id.frame_content,new QuizFragment()).commit();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (Throwable throwable){
                    Log.w(TAG, "createUserWithEmail:failure", throwable);
                    Toast.makeText(getContext(), "createUserWithEmail failed. "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        root.findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String email = emailView.getText().toString();
                String password = passView.getText().toString();
                try {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                if(getFragmentManager()!=null)
                                getFragmentManager().beginTransaction().replace(R.id.frame_content,new QuizFragment()).commit();
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (Throwable throwable){
                    Log.w(TAG, "signInWithEmail:failure", throwable);
                    Toast.makeText(getContext(), "Authentication failed. "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}