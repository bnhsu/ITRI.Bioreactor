package org.itri.bioreactor2.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.itri.bioreactor2.R;

import java.util.Calendar;

public class DateTimePickerDialogFragment extends DialogFragment {
 
    private DatePicker.OnDateChangedListener mDateSetListener;
    private TimePicker.OnTimeChangedListener mTimeSetListener;
    private DialogInterface.OnDismissListener mDismissListener;
    private Calendar mCal;

    public void setDateSetListener(DatePicker.OnDateChangedListener dateSetCallback){
        mDateSetListener = dateSetCallback;
    }

    public void setTimeSetListener(TimePicker.OnTimeChangedListener timeSetCallback){
        mTimeSetListener = timeSetCallback;
    }

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener){
        mDismissListener = dismissListener;
    }

    public void setCalendar(Calendar cal){
        mCal = cal;
    }

 
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dateTimeDialog = new Dialog(this.getActivity());

        dateTimeDialog.setContentView(R.layout.dialog_datetime_pick);
        
        dateTimeDialog.setTitle("Choose date time");
        
        dateTimeDialog.show();
        
        Calendar cal;
        if(mCal == null)
            cal = Calendar.getInstance();
        else 
            cal = mCal;
        
        DatePicker datePicker = (DatePicker)dateTimeDialog.findViewById(R.id.historyDatePicker);
        TimePicker timePicker = (TimePicker)dateTimeDialog.findViewById(R.id.historyTimePicker);
        Button okButton = (Button)dateTimeDialog.findViewById(R.id.btnOK);
        //Initialize Date Picker
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 
                cal.get(Calendar.DAY_OF_MONTH), mDateSetListener);
        
        //Initialize Time Picker
        timePicker.setOnTimeChangedListener(mTimeSetListener);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


 
        return dateTimeDialog;
    }


    @Override
    public void onDismiss (DialogInterface dialog){
        super.onDismiss(dialog);
        mDismissListener.onDismiss(dialog);
    }
}
