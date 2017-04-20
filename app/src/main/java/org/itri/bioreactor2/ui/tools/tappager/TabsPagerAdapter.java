package org.itri.bioreactor2.ui.tools.tappager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	Fragment[] fragments;
	String[] titles;

	public TabsPagerAdapter(FragmentManager fm, Fragment[] f, String[] t) {
		super(fm);
		fragments = f;
		titles = t;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public Fragment getItem(int index) {
		return fragments[index];
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return fragments.length;
	}
}
