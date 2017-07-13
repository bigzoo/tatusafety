package com.chris.tatusafety.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.chris.tatusafety.Stage;
import com.chris.tatusafety.UI.StagesActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by Josephine Menge on 05/07/2017.
 */

public class FirebaseStagesAdapter extends FirebaseRecyclerAdapter<Stage,FirebaseStageViewHolder> {
    private DatabaseReference mRef;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Stage> mStage = new ArrayList<>();



    public FirebaseStagesAdapter(Class<Stage> modelClass, int modelLayout, Class<FirebaseStageViewHolder> viewHolderClass, Query ref, StagesActivity stagesActivity, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mContext = context;
        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void populateViewHolder(FirebaseStageViewHolder viewHolder, Stage model, int position) {
       viewHolder.bindProduct(model);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked me .",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
