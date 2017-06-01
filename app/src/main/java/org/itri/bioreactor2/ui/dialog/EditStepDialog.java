package org.itri.bioreactor2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.autocontrol.component.step;

import java.util.Hashtable;

/**
 * Created by A20356 on 2017/5/11.
 */

public class EditStepDialog extends Dialog {
    private Toolbar mToolbar;
    private step mStep;
    private Hashtable<String, String> setto, endif;
    private EditText editTitle, editDescription, editSetto, editEndif;
    private Spinner spinner_pump1, spinner_stir;
    ArrayAdapter<CharSequence> pump_adapter, adapter_stir;


    public EditStepDialog(Context context, final step mStep) {
        super(context, R.style.MyDialogTheme);
        this.mStep = mStep;
        setto =  new Hashtable<String,String>();
        endif =  new Hashtable<String,String>();
        setContentView(R.layout.dialog_editstep);
        editTitle = (EditText)findViewById(R.id.edit_title);
        editDescription = (EditText)findViewById(R.id.edit_description);
        spinner_pump1 = (Spinner)findViewById(R.id.spinner_pump1);
        spinner_stir = (Spinner)findViewById(R.id.spinner_stir);
        pump_adapter = ArrayAdapter.createFromResource(context, R.array.pumpArr, android.R.layout.simple_spinner_item);
        pump_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapter_stir = ArrayAdapter.createFromResource(context, R.array.stirArr, android.R.layout.simple_spinner_item);
        adapter_stir.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner_pump1.setAdapter(pump_adapter);
        spinner_pump1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setto.put("Pump1", parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_stir.setAdapter(adapter_stir);
        spinner_stir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString() != null){
                    setto.put("Stir", parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editTitle.setText(mStep.getStepTitle());
        editDescription.setText(mStep.getStepDescription());

        setmToolbar();
        editTitle.clearFocus();
        editDescription.clearFocus();

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
                        mStep.setStepSetTo(setto);
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
