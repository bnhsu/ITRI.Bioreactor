package org.itri.bioreactor2.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.controller.BioreactorController;
import org.itri.bioreactor2.data.database.DBHandler;
import org.itri.bioreactor2.data.database.SensorData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ChartFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private final static String TAG = ChartFragment.class.getSimpleName();

    private View rootView;
    //private DBHandler db;
    static DBHandler db;
    private Calendar startTime;
    private Calendar endTime;
    private int pickerViewId;  // for Datetime Picker Dialog
    static BioreactorController reactor;
    private RadioGroup rg;

    
    Calendar dateTime= Calendar.getInstance();
    //private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    //SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MMM d, yyyy 'at' h:mm a");
    SimpleDateFormat dateFormat = new SimpleDateFormat("EE, MMM d, yyyy 'at' k:mm");



    static LineChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      // save the current data, for instance when changing screen orientatio
    }


    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Test", "onCreate");
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Test","onCreateView");
        db = DBHandler.getInstance(this.getActivity());
        reactor = BioreactorController.getInstance(this.getActivity());
		rootView = inflater.inflate(R.layout.fragment_linechart, container, false);
        //tvX = (TextView) rootView.findViewById(R.id.tvXMax);
        //tvY = (TextView) rootView.findViewById(R.id.tvYMax);
        //mSeekBarX = (SeekBar) rootView.findViewById(R.id.seekBar1);
        //mSeekBarY = (SeekBar) rootView.findViewById(R.id.seekBar2);

       // mSeekBarX.setProgress(45);
       // mSeekBarY.setProgress(100);

        //mSeekBarY.setOnSeekBarChangeListener(this);
        //mSeekBarX.setOnSeekBarChangeListener(this);
        rg = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(this);
        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        //mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        //mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(mTfLight);
        l.setTextSize(25f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);


        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(4000f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextSize(15f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        //rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setTextSize(15f);
        rightAxis.setAxisMaximum(50f);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

        initData();
        return rootView;
	}

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId){
        mChart.clearValues();
        initData();
        if(checkedId == R.id.radioButton){
            Log.d("Test","onCheckedChanged: live");
            liveData = true;
        }else if(checkedId == R.id.radioButton2){
            Log.d("Test","onCheckedChanged: log");
            liveData = false;
            //loadLog();
        }

    }

    private SensorData sensor;
    private void loadLog(){
        LineData data = mChart.getData();
        List<SensorData> datas = db.getAllSensorData();
        for(int i = 0;i<datas.size();i++){
            sensor = datas.get(i);
            if (data != null) {

                LineDataSet set1, set2, set3, set4;
                set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
                set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
                set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);

                set1.addEntry(new Entry(set1.getEntryCount(), (float) sensor.getOxygen()));
                set2.addEntry(new Entry(set2.getEntryCount(), (float) sensor.getPH()));
                set3.addEntry(new Entry(set3.getEntryCount(), (float) sensor.getTemp()));
                set4.addEntry(new Entry(set4.getEntryCount(), (float) stir));

                data.notifyDataChanged();

                mChart.notifyDataSetChanged();
                mChart.setVisibleXRangeMaximum(20);
                mChart.moveViewToX(data.getEntryCount());
            }else{
                Log.d("Test","data null");
            }
        }
    }
    static double oxygen=0,ph=0,temp=0,stir=0;
    static Date time;

    static boolean liveData = true;
    static public class DataParserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            try {
                if (!liveData) return;

                if(intent.getStringExtra("RPM")!=null) {
                    String strStir = intent.getStringExtra("RPM");
                    if(strStir == "Close") strStir = "0.0";
                    stir = Double.valueOf(strStir);
                }

                if(intent.getStringExtra("pH")!=null) {
                    ph = Double.valueOf(intent.getStringExtra("pH"));

                }
                if(intent.getStringExtra("Temp")!=null) {
                    temp = Double.valueOf(intent.getStringExtra("Temp"));
                }
                if(intent.getStringExtra("DO")!=null) {
                    oxygen = Double.valueOf(intent.getStringExtra("DO"));
                }

                LineData data = mChart.getData();

                if (data != null) {

                    LineDataSet set1, set2, set3, set4;
                    set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
                    set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
                    set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
                    set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);

                    set1.addEntry(new Entry(set1.getEntryCount(), (float) oxygen));
                    set2.addEntry(new Entry(set2.getEntryCount(), (float) ph));
                    set3.addEntry(new Entry(set3.getEntryCount(), (float) temp));
                    set4.addEntry(new Entry(set4.getEntryCount(), (float) stir));

                    data.notifyDataChanged();

                    mChart.notifyDataSetChanged();
                    mChart.setVisibleXRangeMaximum(20);
                    mChart.moveViewToX(data.getEntryCount());
                }else{
                    Log.d("Test","data null");
                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void initData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(0, 0));

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        yVals2.add(new Entry(0, 0));

        ArrayList<Entry> yVals3 = new ArrayList<Entry>();
        yVals3.add(new Entry(0, 0));

        ArrayList<Entry> yVals4 = new ArrayList<Entry>();
        yVals4.add(new Entry(0, 0));


        LineDataSet set1, set2, set3, set4;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set4 = (LineDataSet) mChart.getData().getDataSetByIndex(3);


            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            set4.setValues(yVals4);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DO");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(ColorTemplate.getHoloBlue());
            set1.setLineWidth(3f);
            set1.setCircleRadius(4f);
            set1.setFillAlpha(70);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(yVals2, "pH");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(3f);
            set2.setCircleRadius(4f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "TMP");
            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set3.setColor(Color.YELLOW);
            set3.setCircleColor(Color.YELLOW);
            set3.setLineWidth(3f);
            set3.setCircleRadius(4f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            set4 = new LineDataSet(yVals4, "STIR");
            set4.setAxisDependency(YAxis.AxisDependency.LEFT);
            set4.setColor(Color.GREEN);
            set4.setCircleColor(Color.GREEN);
            set4.setLineWidth(3f);
            set4.setCircleRadius(4f);
            set4.setFillAlpha(65);
            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));




            // create a data object with the datasets
            LineData data = new LineData(set1, set2, set3,set4);
            data.setValueTextColor(Color.DKGRAY);
            data.setValueTextSize(11f);

            // set data
            mChart.setData(data);
        }
    }

  @Override
  public void onResume() {
      super.onResume();
      Log.d("Test", "onResume");

    
  }
  
  @Override
  public void onDestroyView() {
      super.onDestroyView();

  }

    
}
