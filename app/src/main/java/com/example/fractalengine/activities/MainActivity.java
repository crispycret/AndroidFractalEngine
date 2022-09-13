package com.example.fractalengine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.fractalengine.fragments.BundlingFragment;
import com.example.fractalengine.fragments.MandelbrotSettingsFragment;
import com.example.fractalengine.R;
import com.example.fractalengine.fragments.SierpinskiSettingsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String FRACTAL_TYPE = "com.example.FractalEngineV1.FractalType";

    public static int selectedSpinner;
    public static BundlingFragment selectedFragment;
    public static FragmentManager fm;
    public static FragmentTransaction ft;

    public ArrayList<BundlingFragment> fragments = new ArrayList<BundlingFragment>();

    public static MandelbrotSettingsFragment mandelbrotFragment = new MandelbrotSettingsFragment();
    public static SierpinskiSettingsFragment sierpinskiFragment = new SierpinskiSettingsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w("MAIN ACTIVITY: ", "OnCreate");
        Log.w("SPINNER: ", String.valueOf(selectedSpinner));

        ((Spinner)findViewById(R.id.fractalTypeSpinner)).setSelection(selectedSpinner);

        if (savedInstanceState == null) {
            setupFragments();
            setupFractalTypeDropdownOnItemSelectedListener();
        } else {
        }

    }

    /***
     *
     */
    public void setupFragments() {
        // When creating the fragment container for this activity a fragment had to be
        // selected. So the first thing we should do is remove the fragment
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .remove(fm.findFragmentById(R.id.main_fractalSettingsFragmentContainer))
                .commit();

        fragments.add(mandelbrotFragment);
        fragments.add(sierpinskiFragment);

        String s = "";
        for (BundlingFragment b : fragments) {
         s += b.getClass().toString() + ", ";
        }
        Log.w("FRAGMENTS:", s);
    }



    /**
     *
     * @param view
     */
    public void startFractalEngineActivity(View view) {
        Spinner dropdown = findViewById(R.id.fractalTypeSpinner);
        String selected = dropdown.getSelectedItem().toString();

        Bundle bundle = selectedFragment.createBundle();
        bundle.putString(FRACTAL_TYPE, selected);

        Intent intent = new Intent(this, FractalEngine.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     *
     */
    private void setupFractalTypeDropdownOnItemSelectedListener () {
        Spinner dropdown = (Spinner) findViewById(R.id.fractalTypeSpinner);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();
                selectedSpinner = i;

                Log.w("Selected:", item);
                if (item.equals("Mandelbrot") ) {
                    displayFragment(mandelbrotFragment);
                } else if (item.equals("Sierpinski")) {
                    displayFragment(sierpinskiFragment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }




    /**
     *
     * @param fragment
     */
    private void displayFragment(BundlingFragment fragment) {
        int index = getFragmentIndex(fragment);
        if (index == -1) return;

        selectedFragment = fragment;

        ft = fm.beginTransaction();
        if (fragment.isAdded()) { // if the fragment is already in container
            ft.show(fragment);
        } else { // fragment needs to be added to frame container
            ft.add(R.id.main_fractalSettingsFragmentContainer, fragment);
        }
//         hiding the other fragments
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).isAdded() && i != index) {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    /***
     *
     * @param fragment
     * @return
     */
    private int getFragmentIndex(Fragment fragment) {
        int index = -1;
        for (int i = 0; i < fragments.size(); i++) {
            if (fragment.hashCode() == fragments.get(i).hashCode()){
                return i;
            }
        }
        return index;
    }


}