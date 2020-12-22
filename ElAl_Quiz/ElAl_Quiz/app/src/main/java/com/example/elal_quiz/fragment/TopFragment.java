package com.example.elal_quiz.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.elal_quiz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopFragment extends Fragment {

    private static final String TAG = "TopFragment";

    public class RecordItem {
        String name;
        int record;
    }

    public class CustomComparator implements Comparator<RecordItem> {
        @Override
        public int compare(RecordItem o1, RecordItem o2) {
            return o2.record - o1.record;
        }
    }

    @Nullable
    @Override
    public View onCreateView(final @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_top, null);
        final LinearLayout linearLayout = root.findViewById(R.id.top_list);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Toplist");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<RecordItem> records = new ArrayList();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    RecordItem item = new RecordItem();
                    item.name = dataSnap.getKey().replace("-",".");
                    item.record = Integer.parseInt(dataSnap.getValue(String.class));
                    records.add(item);
                }
                Collections.sort(records, new CustomComparator());
                int u=0;
                for(RecordItem i:records)
                {
                    View item_layout = inflater.inflate(R.layout.item_record, null);
                    TextView user = item_layout.findViewById(R.id.email);
                    TextView scores = item_layout.findViewById(R.id.record);
                    user.setText(++u +" : "+i.name);
                    scores.setText(String.valueOf(i.record));
                    linearLayout.addView(item_layout);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


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