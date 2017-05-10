package org.itri.bioreactor2.ui.adpater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.model.step;
import org.itri.bioreactor2.ui.tools.ExpandAndCollapseViewUtil;

import java.util.ArrayList;

/**
 * Created by A20356 on 2017/4/25.
 */

public class StepCardAdapter extends RecyclerView.Adapter<StepCardAdapter.StepViewHolder> {

    private ArrayList<step> mStep;
    Context context;

    public StepCardAdapter(ArrayList<step> mStep, Context context){
        this.mStep = mStep;
        this.context = context;
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView mCardView;
        public TextView mTextTitle, mTextDescription, mTextSetTo, mTextEndIf;
        public LinearLayout linearLayoutDetails;
        public ImageView imageView;
        private static final int DURATION = 250;
        ArrayList<step> mStep = new ArrayList<step>();
        Context context;

        public StepViewHolder(View v, Context context, ArrayList<step> mStep){
            super(v);
            this.mStep = mStep;
            this.context = context;
            itemView.setOnClickListener(this);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextTitle = (TextView) v.findViewById(R.id.tv_text);
            mTextDescription =  (TextView) v.findViewById(R.id.tv_detail);
            mTextSetTo = (TextView) v.findViewById(R.id.tv_setto);
            mTextEndIf =  (TextView) v.findViewById(R.id.tv_endif);
            linearLayoutDetails = (LinearLayout) v.findViewById(R.id.linearLayoutDetails);
            imageView = (ImageView) v.findViewById(R.id.card_header_inner_expand_btn);
/*
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    toggleDetails(v);
                }
            });
*/
        }

        @Override
        public void onClick(View v) {
            toggleDetails(v);
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
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_step, parent, false);
        StepViewHolder vh = new StepViewHolder(v, context, mStep);
        return vh;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position){
        String Title, Description, Setto, Endif;
        step Step = mStep.get(position);
        Title = Step.getStepTitle();
        Description = Step.getStepDescription();
        Setto = Step.listAllSetTo();
        Endif = Step.listAllEndIF();

        holder.mTextTitle.setText(Title);
        holder.mTextDescription.setText(Description);
        holder.mTextSetTo.setText(Setto);
        holder.mTextEndIf.setText(Endif);
    }

    @Override
    public int getItemCount() { return mStep.size(); }

}
