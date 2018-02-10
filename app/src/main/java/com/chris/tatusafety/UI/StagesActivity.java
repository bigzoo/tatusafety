//package com.chris.tatusafety.UI;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.chris.tatusafety.R;
//import com.chris.tatusafety.Stage;
//import com.chris.tatusafety.adapters.FirebaseStageViewHolder;
//import com.chris.tatusafety.adapters.FirebaseStagesAdapter;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//public class StagesActivity extends AppCompatActivity {
//    private DatabaseReference mStageReference;
//    private FirebaseStagesAdapter mFirebaseAdapter;
//    @Bind(R.id.recylerView)
//    RecyclerView mRecyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stages);
//        ButterKnife.bind(this);
//        setUpFirebaseAdapter();
//    }
//        String search = "kencom";
//    private void setUpFirebaseAdapter() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child("stops").child("0").equalTo(search);
//        mFirebaseAdapter = new FirebaseStagesAdapter(Stage.class, R.layout.stage_list_item,FirebaseStageViewHolder.class,query,this,this);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mFirebaseAdapter);
//
////query.addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
//    }
//}
