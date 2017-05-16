package org.itri.bioreactor2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.component.step;

/**
 * Created by A20356 on 2017/5/11.
 */

public class EditStepDialog extends Dialog {
    private Toolbar mToolbar;
    private step mStep;
    private EditText editTitle, editDescription, editSetto, editEndif;
    public EditStepDialog(Context context, step mStep) {
        super(context, R.style.MyDialogTheme);
        this.mStep = mStep;
        setContentView(R.layout.dialog_editstep);
        editTitle = (EditText)findViewById(R.id.edit_title);
        editDescription = (EditText)findViewById(R.id.edit_description);
        editSetto = (EditText)findViewById(R.id.edit_setto);
        editEndif = (EditText)findViewById(R.id.edit_endif);

        editTitle.setText(mStep.getStepTitle());
        editDescription.setText(mStep.getStepDescription());
        editSetto.setText(mStep.listAllSetTo());
        editEndif.setText(mStep.listAllEndIF());

        setmToolbar();
        editTitle.clearFocus();
        editDescription.clearFocus();
        editSetto.clearFocus();
        editEndif.clearFocus();
/*
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editDescription.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editSetto.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editEndif.getWindowToken(), 0);
*/
    }
    private void setmToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Step editor");
        mToolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_stepedit);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_save:
                        mStep.setStepTitle(editTitle.getText().toString());
                        mStep.setStepDescription(editDescription.getText().toString());
                        dismiss();
                        // Save the step edit
                        //Toast.makeText(mContext, "Edit is clicked!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public step getEditStep(){
        return mStep;
    }
}
