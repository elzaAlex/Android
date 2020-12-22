package com.example.elal_quiz.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;


import com.example.elal_quiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResultFragment extends Fragment {

    TextView t1, t2, t3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result, null);

        t1 = (TextView)root.findViewById((R.id.textView4));
        t2 = (TextView)root.findViewById((R.id.textView5));
        t3 = (TextView)root.findViewById((R.id.textView6));

        String questions = getArguments().getString("total");
        String correct = getArguments().getString("correct");
        String wrong = getArguments().getString("incorrect");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Toplist");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usersRef.child(user.getEmail().replace(".","-")).setValue(correct);

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);

        t1.setPaintFlags(t1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        t2.setPaintFlags(t2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        t3.setPaintFlags(t3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getFragmentManager().beginTransaction().replace(R.id.frame_content,new QuizFragment()).commit();
                    return true;
                }
                return false;
            }
        } );

        return root;
    }

}