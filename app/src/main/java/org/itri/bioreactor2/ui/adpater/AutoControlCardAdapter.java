package org.itri.bioreactor2.ui.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.fragment.StepViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A20356 on 2017/4/25.
 */

public class AutoControlCardAdapter extends RecyclerView.Adapter<AutoControlCardAdapter.MyViewHolder> {

    private ArrayList<String> mDataset;
    Context context;
    private static String previousTitle = null;
    private static String mDatasetTitle;


    public AutoControlCardAdapter(ArrayList<String> myDataset, Context context){
        mDataset = myDataset;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView mCardView;
        public TextView mTextView;
        ArrayList<String> mDataset = new ArrayList<>();
        Context context;

        public MyViewHolder(View v, Context context, ArrayList<String> mDataset){
            super(v);
            this.mDataset = mDataset;
            this.context = context;
            itemView.setOnClickListener(this);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mDatasetTitle = this.mDataset.get(position);
            Log.d("test", "onClick :" +mDatasetTitle + ", previousTitle: "+ previousTitle);

            if(!mDatasetTitle.equals(previousTitle)) {
                previousTitle = mDatasetTitle;
                //FragmentTransaction ft= getSupportFragmentManager().beginTransaction();;
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment StepViewFragment = new StepViewFragment();

                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_stepview, StepViewFragment).addToBackStack(null).commit();

                String[] fileFullName = mDatasetTitle.split("\\.");
                String fileName = fileFullName[0];
                Bundle bundle = new Bundle();
                bundle.putString("file_Title", fileName);
                StepViewFragment.setArguments(bundle);
            }
            //Intent intent = new Intent(this.context, StepViewFragment.class);
            //intent.putExtra("StepList_Title", mDatasetTitle);
            //this.context.startActivity(intent);
        }
    }


    @Override
    public AutoControlCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_autocontrol, parent, false);
        MyViewHolder vh = new MyViewHolder(v, context, mDataset);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        String fileName = null;
        String[] fileFullName = mDataset.get(position).split("\\.");
        fileName = fileFullName[0];
        holder.mTextView.setText(fileName);




    }

    @Override
    public int getItemCount() { return mDataset.size(); }

}
