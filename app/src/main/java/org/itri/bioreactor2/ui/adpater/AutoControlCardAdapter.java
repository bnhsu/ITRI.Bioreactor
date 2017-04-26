package org.itri.bioreactor2.ui.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.StepViewActivity;

/**
 * Created by A20356 on 2017/4/25.
 */

public class AutoControlCardAdapter extends RecyclerView.Adapter<AutoControlCardAdapter.MyViewHolder> {

    private String[] mDataset;
    Context context;
    public AutoControlCardAdapter(String[] myDataset, Context context){
        mDataset = myDataset;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView mCardView;
        public TextView mTextView;
        String[] mDataset = new String[]{};
        Context context;
        public MyViewHolder(View v, Context context, String[] mDataset){
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
            String mDatasetTitle = this.mDataset[position];
            Intent intent = new Intent(this.context, StepViewActivity.class);
            intent.putExtra("StepList_Title", mDatasetTitle);
            this.context.startActivity(intent);
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
        holder.mTextView.setText(mDataset[position]);




    }

    @Override
    public int getItemCount() { return mDataset.length; }



}
