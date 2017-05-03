package org.itri.bioreactor2.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.ui.component.uiHeatBlanket;
import org.itri.bioreactor2.ui.component.uiNFC;
import org.itri.bioreactor2.ui.component.uiPump;
import org.itri.bioreactor2.ui.component.uiSensor;
import org.itri.bioreactor2.ui.component.uiStepMotor;
import org.itri.bioreactor2.ui.component.uiStir;


public class MonitorUIFragment extends Fragment {

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_autoui, container, false);
		RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.autoui);


		uiNFC CaliCheck = new uiNFC(getActivity().getApplicationContext(), "NFC");

		uiPump Pump1 = new uiPump(getActivity().getApplicationContext(), "Pump1", "PUMP 1", "rpm");
		Pump1.setX(50);
		Pump1.setY(0);
		layout.addView(Pump1);

		uiPump Pump2 = new uiPump(getActivity().getApplicationContext(), "Pump2", "PUMP 2", "rpm");
		Pump2.setX(50);
		Pump2.setY(400);
		layout.addView(Pump2);

		uiPump Pump3 = new uiPump(getActivity().getApplicationContext(), "Pump3", "PUMP 3", "rpm");
		Pump3.setX(50);
		Pump3.setY(750);
		layout.addView(Pump3);

		uiStir stir = new uiStir(getActivity().getApplicationContext(), "RPM", "STIR", "rpm");
		stir.setX(700);
		stir.setY(150);
		layout.addView(stir);

		uiSensor pH = new uiSensor(getActivity().getApplicationContext(), "pH", "pHErr");
		pH.setX(1450);
		pH.setY(50);
		layout.addView(pH);

		uiSensor oxygen = new uiSensor(getActivity().getApplicationContext(), "DO", "DOErr");
		oxygen.setX(1450);
		oxygen.setY(250);
		layout.addView(oxygen);

		uiSensor temp = new uiSensor(getActivity().getApplicationContext(), "Temp", "");
		temp.setX(1450);
		temp.setY(450);
		layout.addView(temp);

		uiHeatBlanket HeatBlanket = new uiHeatBlanket(getActivity().getApplicationContext(), "Heating");
		HeatBlanket.setX(1440);
		HeatBlanket.setY(670);
		layout.addView(HeatBlanket);

		uiStepMotor stepmotor = new uiStepMotor(getActivity().getApplicationContext(), "STEPMOTOR", "STEP MOTOR");
		stepmotor.setX(1450);
		stepmotor.setY(810);
		layout.addView(stepmotor);
/*
		For activity
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("activity", "Switch to autocontrol");
				Intent intent = new Intent(getActivity(), AutoControlListFragment.class);
				startActivity(intent);
			}
		});
*/
		return rootView;



	}

}
