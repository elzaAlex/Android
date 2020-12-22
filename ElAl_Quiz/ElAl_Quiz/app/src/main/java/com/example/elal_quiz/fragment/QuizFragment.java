package com.example.elal_quiz.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import android.app.Fragment;


import com.example.elal_quiz.R;
import com.example.elal_quiz.model.Question;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizFragment extends Fragment {

    Button b1, b2, b3, b4;
    TextView t1_question, timerTxt, logout, toplist;
    int total = 0;
    int correct = 0;
    int wrong = 0;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, null);

        b1 = (Button)root.findViewById(R.id.button1);
        b2 = (Button)root.findViewById(R.id.button2);
        b3 = (Button)root.findViewById(R.id.button3);
        b4 = (Button)root.findViewById(R.id.button4);


        AdView mAdView = root.findViewById(R.id.banner_ads);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         mAdView.setAdListener(new AdListener() {
                                   @Override
                                   public void onAdFailedToLoad(LoadAdError loadAdError) {
                                       System.err.println(loadAdError.getResponseInfo());
                                   }
                               }
         );

        t1_question = (TextView)root.findViewById(R.id.questionsTxt);
        timerTxt = (TextView)root.findViewById(R.id.timerTxt);

        logout = (TextView)root.findViewById(R.id.sign_out);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if(getFragmentManager()!=null)
                    getFragmentManager().beginTransaction().replace(R.id.frame_content,new LoginFragment()).commit();
            }
        });

        toplist = root.findViewById(R.id.top_list);

        toplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager()!=null)
                    getFragmentManager().beginTransaction().replace(R.id.frame_content,new TopFragment()).commit();
            }
        });

        updateQuestion();
        reverseTimer(300, timerTxt);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void SetButtonColor(Button button, @ColorRes int clr) {
        if(getContext()!=null)
            button.setBackgroundTintList((AppCompatResources.getColorStateList(getContext(),clr)));
    }

    private void updateQuestion()
    {
        if (total == 5)
        {
            Bundle bundle = new Bundle();
            bundle.putString("total", String.valueOf(total));
            bundle.putString("correct", String.valueOf(correct));
            bundle.putString("incorrect", String.valueOf(wrong));
            ResultFragment fragobj = new ResultFragment();
            fragobj.setArguments(bundle);
            if(getFragmentManager()!=null)
            getFragmentManager().beginTransaction().replace(R.id.frame_content,fragobj).commit();
        }
        else
        {
            total++;
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final Question question = snapshot.getValue(Question.class);

                    t1_question.setText(question.getQuestion());

                    System.err.println(question.getOption1());
                    System.err.println(question.getOption2());
                    System.err.println(question.getOption3());
                    System.err.println(question.getOption4());

                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());

                    b1.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (b1.getText().toString().equals(question.getAnswer()))
                            {
                                SetButtonColor(b1,R.color.green);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        SetButtonColor(b1,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                            else {
                                wrong++;
                                SetButtonColor(b1,R.color.red);

                                if (b2.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b2,R.color.green);
                                }
                                else if (b3.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b3,R.color.green);
                                }
                                else if (b4.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b4,R.color.green);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()  {
                                    @Override
                                    public void run() {
                                        SetButtonColor(b1,R.color.theme);
                                        SetButtonColor(b2,R.color.theme);
                                        SetButtonColor(b3,R.color.theme);
                                        SetButtonColor(b4,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });

                    b2.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (b2.getText().toString().equals(question.getAnswer()))
                            {
                                SetButtonColor(b2,R.color.green);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        SetButtonColor(b2,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                            else {
                                wrong++;
                                SetButtonColor(b2,R.color.red);

                                if (b1.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b1,R.color.green);
                                }
                                else if (b3.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b3,R.color.green);
                                }
                                else if (b4.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b4,R.color.green);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()  {
                                    @Override
                                    public void run() {
                                        SetButtonColor(b1,R.color.theme);
                                        SetButtonColor(b2,R.color.theme);
                                        SetButtonColor(b3,R.color.theme);
                                        SetButtonColor(b4,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });

                    b3.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (b3.getText().toString().equals(question.getAnswer()))
                            {
                                SetButtonColor(b3,R.color.green);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        SetButtonColor(b3,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                            else {
                                wrong++;
                                SetButtonColor(b3,R.color.red);

                                if (b1.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b1,R.color.green);
                                }
                                else if (b2.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b2,R.color.green);
                                }
                                else if (b4.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b4,R.color.green);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()  {
                                    @Override
                                    public void run() {
                                        SetButtonColor(b1,R.color.theme);
                                        SetButtonColor(b2,R.color.theme);
                                        SetButtonColor(b3,R.color.theme);
                                        SetButtonColor(b4,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });

                    b4.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (b4.getText().toString().equals(question.getAnswer()))
                            {
                                SetButtonColor(b4,R.color.green);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        SetButtonColor(b4,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                            else {
                                wrong++;
                                SetButtonColor(b4,R.color.red);

                                if (b1.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b1,R.color.green);
                                }
                                else if (b2.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b2,R.color.green);
                                }
                                else if (b3.getText().toString().equals(question.getAnswer())) {
                                    SetButtonColor(b3,R.color.green);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable()  {
                                    @Override
                                    public void run() {
                                        SetButtonColor(b1,R.color.theme);
                                        SetButtonColor(b2,R.color.theme);
                                        SetButtonColor(b3,R.color.theme);
                                        SetButtonColor(b4,R.color.theme);
                                        updateQuestion();
                                    }
                                }, 1500);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void reverseTimer(int seconds, final TextView tv) {
        new CountDownTimer(seconds * 1000 + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                tv.setText("Completed");
                Bundle bundle = new Bundle();
                bundle.putString("total", String.valueOf(total));
                bundle.putString("correct", String.valueOf(correct));
                bundle.putString("incorrect", String.valueOf(wrong));
                ResultFragment fragobj = new ResultFragment();
                fragobj.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_content,fragobj).commit();
            }
        }.start();
    }
}