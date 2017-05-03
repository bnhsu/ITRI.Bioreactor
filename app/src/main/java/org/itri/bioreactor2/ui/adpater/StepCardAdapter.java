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

/**
 * Created by A20356 on 2017/4/25.
 */

public class StepCardAdapter extends RecyclerView.Adapter<StepCardAdapter.MyViewHolder> {

    private String[] mDataset;
    Context context;


    public StepCardAdapter(String[] myDataset, Context context){
        mDataset = myDataset;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public LinearLayout linearLayoutDetails;
        public ImageView imageView;
        private static final int DURATION = 250;

        String[] mDataset = new String[]{};
        Context context;
        public MyViewHolder(View v, Context context, String[] mDataset){
            super(v);
            this.mDataset = mDataset;
            this.context = context;
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            linearLayoutDetails = (LinearLayout) v.findViewById(R.id.linearLayoutDetails);
            imageView = (ImageView) v.findViewById(R.id.card_header_inner_expand_btn);
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    toggleDetails(v);
                }
            });


        }

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
