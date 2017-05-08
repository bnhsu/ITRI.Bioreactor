package org.itri.bioreactor2.ui.adpater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.tools.ExpandAndCollapseViewUtil;

import java.util.ArrayList;

/**
 * Created by A20356 on 2017/4/25.
 */

public class StepCardAdapter extends RecyclerView.Adapter<StepCardAdapter.MyViewHolder> {

    private ArrayList<String> mTitle;
    private ArrayList<String> mDescription;
    Context context;


    public StepCardAdapter(ArrayList<String> mDataset, ArrayList<String> Description, Context context){
        mTitle = mDataset;
        mDescription = Description;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public CardView mCardView;
        public TextView mTextTitle, mTextDescription;
        public LinearLayout linearLayoutDetails;
        public ImageView imageView;
        private static final int DURATION = 250;

        ArrayList<String> mDataset = new ArrayList<String>();
        ArrayList<String> mDescription = new ArrayList<String>();

        Context context;
        public MyViewHolder(View v, Context context, ArrayList<String> mDataset, ArrayList<String> mDescription){
            super(v);
            this.mDataset = mDataset;
            this.mDescription = mDescription;
            this.context = context;
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextTitle = (TextView) v.findViewById(R.id.tv_text);
            mTextDescription =  (TextView) v.findViewById(R.id.tv_detail);
            linearLayoutDetails = (LinearLayout) v.findViewById(R.id.linearLayoutDetails);
            imageView = (ImageView) v.findViewById(R.id.card_header_inner_expand_btn);

            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    toggleDetails(v);
                }
            });


        }
/*
        @Override
        public void onClick(View v) {
            toggleDetails(v);
        }
*/
        public void toggleDetails(View view) {
            if (linearLayoutDetails.getVisibility() == View.GONE) {
                ExpandAndCollapseViewUtil.expand(linearLayoutDetails, DURATION);
                imageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                rotate(-180.0f);
            } else {
                ExpandAndCollapseViewUtil.collapse(linearLayoutDetails, DURATION);
                imageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                rotate(180.0f);
            }
        }

        private void rotate(float angle) {
            Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            animation.setDuration(DURATION);
            imageView.startAnimation(animation);
        }


    }


    @Override
    public StepCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_step, parent, false);
        MyViewHolder vh = new MyViewHolder(v, context, mTitle, mDescription);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.mTextTitle.setText(mTitle.get(position));
        holder.mTextDescription.setText(mDescription.get(position));


    }

    @Override
    public int getItemCount() { return mTitle.size(); }





}
