package org.itri.bioreactor2.ui.adpater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.component.step;
import org.itri.bioreactor2.ui.dialog.EditStepDialog;
import org.itri.bioreactor2.ui.tools.ExpandAndCollapseViewUtil;

import java.util.ArrayList;
import java.util.Hashtable;

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
        public TextView mTextTitle, mTextDescription,
                mTextPump1, mTextPump2, mTextPump3, mTextstir, mTextpH, mTextDO, mTextTMP, mTextEndif;
        public TableRow tr_pump1, tr_pump2, tr_pump3, tr_stir, tr_pH, tr_do, tr_tmp;
        public LinearLayout linearLayoutDetails;
        public ImageView imageView;
        public Button button;
        private static final int DURATION = 250;
        ArrayList<step> mStep = new ArrayList<step>();
        Context context;

        public StepViewHolder(View v, final Context context, final ArrayList<step> mStep){
            super(v);
            this.mStep = mStep;
            this.context = context;
            itemView.setOnClickListener(this);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextTitle = (TextView) v.findViewById(R.id.tv_text);
            mTextDescription =  (TextView) v.findViewById(R.id.tv_detail);
            mTextPump1 = (TextView) v.findViewById(R.id.tv_pump1value);
            mTextPump2 = (TextView) v.findViewById(R.id.tv_pump2value);
            mTextPump3 = (TextView) v.findViewById(R.id.tv_pump3value);
            mTextstir = (TextView) v.findViewById(R.id.tv_stirvalue);
            mTextpH = (TextView) v.findViewById(R.id.tv_pHvalue);
            mTextDO = (TextView) v.findViewById(R.id.tv_DOvalue);
            mTextTMP = (TextView) v.findViewById(R.id.tv_TMPvalue);
            mTextEndif = (TextView) v.findViewById(R.id.tv_endif);
            tr_pump1 = (TableRow)v.findViewById(R.id.tr_pump1);
            tr_pump2 = (TableRow)v.findViewById(R.id.tr_pump2);
            tr_pump3 = (TableRow)v.findViewById(R.id.tr_pump3);
            tr_stir = (TableRow)v.findViewById(R.id.tr_stir);
            tr_pH = (TableRow)v.findViewById(R.id.tr_pH);
            tr_do = (TableRow)v.findViewById(R.id.tr_do);
            tr_tmp = (TableRow)v.findViewById(R.id.tr_tmp);
            linearLayoutDetails = (LinearLayout) v.findViewById(R.id.linearLayoutDetails);
            imageView = (ImageView) v.findViewById(R.id.card_header_inner_expand_btn);
            button =(Button) v.findViewById(R.id.btn_edit);
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
    public void onBindViewHolder(final StepViewHolder holder, int position){
        String Title, Description;
        Hashtable<String,String> setTo;
        Hashtable<String,String> endIf;
        final step Step = mStep.get(position);
        final step[] newStep = {new step()};
        Title = Step.getStepTitle();
        Description = Step.getStepDescription();
        setTo = Step.getStepSetTo();
        endIf = Step.getStepEndIf();
        //Setto = Step.listAllSetTo();
        //Endif = Step.listAllEndIF();

        holder.mTextTitle.setText(Title);
        holder.mTextDescription.setText(Description);
        if(setTo.get("Pump1") != null) {
            holder.tr_pump1.setVisibility(View.VISIBLE);
            holder.mTextPump1.setText(setTo.get("Pump1"));
        }else if(setTo.get("Pump2") != null) {
            holder.tr_pump2.setVisibility(View.VISIBLE);
            holder.mTextPump2.setText(setTo.get("Pump2"));
        }else if(setTo.get("Pump3") != null) {
            holder.tr_pump3.setVisibility(View.VISIBLE);
            holder.mTextPump3.setText(setTo.get("Pump3"));
        }else if(setTo.get("Stir") != null) {
            holder.tr_stir.setVisibility(View.VISIBLE);
            holder.mTextstir.setText(setTo.get("Stir"));
        }else if(setTo.get("pH") != null) {
            holder.tr_pH.setVisibility(View.VISIBLE);
            holder.mTextpH.setText(setTo.get("pH"));
        }else if(setTo.get("DO") != null) {
            holder.tr_do.setVisibility(View.VISIBLE);
            holder.mTextDO.setText(setTo.get("DO"));
        }else if(setTo.get("TMP") != null) {
            holder.tr_tmp.setVisibility(View.VISIBLE);
            holder.mTextTMP.setText(setTo.get("TMP"));
        }
        holder.mTextEndif.setText(endIf.get("TIME"));
        /*
        holder.mTextSetTo.setText(Setto);
        holder.mTextEndIf.setText(Endif);
*/
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditStepDialog editStepDialog = new EditStepDialog(context, Step);
                editStepDialog.show();
                newStep[0] = editStepDialog.getEditStep();
                holder.mTextTitle.setText(newStep[0].getStepTitle());
                holder.mTextDescription.setText(newStep[0].getStepDescription());
            }
        });


    }

    @Override
    public int getItemCount() { return mStep.size(); }

}
