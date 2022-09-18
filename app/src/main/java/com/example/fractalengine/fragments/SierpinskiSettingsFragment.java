package com.example.fractalengine.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fractalengine.R;
import com.example.fractalengine.canvas.SierpinskiDrawer;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SierpinskiSettingsFragment extends BundlingFragment {


    public SierpinskiSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sierpinski_settings, container, false);

        // Add onClickListener for the reset button
        Button resetBtn = v.findViewById(R.id.mandelbrot_resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });


        return v;
    }


    public void setEditText(int id, String value) {
        ((EditText)getActivity().findViewById(id)).setText(value);
    }

    public void setEditText(int id, int value) {
        ((EditText)getActivity().findViewById(id)).setText(String.valueOf(value));
    }

    public String getEditTextValue(int id) {
        return ((EditText)getActivity().findViewById(id)).getText().toString();
    }

    public void reset () {
        setEditText(R.id.sierpinski_depth, SierpinskiDrawer.MAX_DEPTH_DEFAULT);
        setEditText(R.id.sierpinski_strokeWidth, SierpinskiDrawer.STROKE_WIDTH_DEFAULT);
    }


    @Override
    public Bundle createBundle() {
        String depth = getEditTextValue(R.id.sierpinski_depth);
        String strokeWidth = getEditTextValue(R.id.sierpinski_strokeWidth);

        Bundle b = new Bundle();
        b.putInt("depth", Integer.valueOf(depth));
        b.putInt("strokeWidth", Integer.valueOf(strokeWidth));

        Log.w("Sierpinski Bundle:", b.toString());

        return b;
    }













}