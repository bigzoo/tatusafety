package com.chris.tatusafety.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chris.tatusafety.R;
import com.chris.tatusafety.Stage;

/**
 * Created by Josephine Menge on 05/07/2017.
 */

public class FirebaseStageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;
    
    public FirebaseStageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext= itemView.getContext();
        itemView.setOnClickListener(this);
    }
    

    @Override
    public void onClick(View v) {
        
    }

    public void bindProduct(Stage stage) {
        TextView busTextView = (TextView) mView.findViewById(R.id.busNo);
        TextView stopsTextView = (TextView) mView.findViewById(R.id.stopName);
        busTextView.setText(stage.getBus());
        stopsTextView.setText(stage.getVariousStages());
    }
}
