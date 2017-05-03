package org.itri.bioreactor2.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.itri.bioreactor2.R;

public class AutoControlFragment extends android.support.v4.app.Fragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_autocontrol, container, false);
		return rootView;
	}

}
