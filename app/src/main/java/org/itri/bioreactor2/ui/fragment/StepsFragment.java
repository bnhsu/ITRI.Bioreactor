package org.itri.bioreactor2.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.itri.bioreactor2.R;
import org.itri.bioreactor2.model.Script;
import org.itri.bioreactor2.model.step;

import java.util.ArrayList;


public class StepsFragment extends android.support.v4.app.ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		Script st = Script.getInstance(getActivity().getApplicationContext());
		StepsAdapter stepsAdapter = new StepsAdapter(getActivity().getApplicationContext(), st.list());
		setListAdapter(stepsAdapter);

		View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

		return rootView;
	}

	public class StepsAdapter extends ArrayAdapter<step> {
		public ArrayList<step> steps;

		public StepsAdapter(Context context, ArrayList<step> steps){
			super(context,0,steps);
			this.steps = steps;
		}

		@Override
		public View getView(int position,View convertView, ViewGroup parent){


			if(convertView == null){
				convertView =  LayoutInflater.from(getContext()).inflate(R.layout.control_steps, parent, false);
			}
			TextView txt1 = (TextView)convertView.findViewById(R.id.text_step_title);
			txt1.setText(steps.get(position).title);
			TextView txt2 = (TextView)convertView.findViewById(R.id.text_step_description);
			txt2.setText(steps.get(position).description);
			return convertView;
		}
	}
}
