package org.itri.bioreactor2.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itri.bioreactor2.R;

public class MonitorFragment extends android.support.v4.app.ListFragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_monitor, container, false);
		return rootView;
	}

}
