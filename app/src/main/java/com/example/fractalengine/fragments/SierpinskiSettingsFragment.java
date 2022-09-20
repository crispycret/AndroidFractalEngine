package com.example.fractalengine.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fractalengine.R;
import com.example.fractalengine.canvas.SierpinskiDrawer;
import com.example.fractalengine.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import yuku.ambilwarna.AmbilWarnaDialog;

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

        setupListeners(v);

        return v;
    }

    public void setupListeners (View v) {
        // Add onClickListener for the reset button
        Button resetBtn = v.findViewById(R.id.sierpinski_resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        setupColorPicker(v, R.id.sierpinski_colorPicker1, R.id.sierpinski_color1, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[0]);
        setupColorPicker(v, R.id.sierpinski_colorPicker2, R.id.sierpinski_color2, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[1]);
        setupColorPicker(v, R.id.sierpinski_colorPicker3, R.id.sierpinski_color3, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[2]);

    }

    public void setupColorPicker(View v, int btnId, int textId, String defaultHexColor) {
        Utils.setEditTextValue(v, textId, defaultHexColor);
        int defaultColor = Color.parseColor(defaultHexColor);
        Button colorPicker = v.findViewById(btnId);
        colorPicker.setBackgroundColor(defaultColor);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker(v, btnId, textId);
            }
        });
    }

    public void openColorPicker(View v, int btnId, int textId) {
        Activity activity = getActivity();

        String hexColor = Utils.getEditTextValue(activity, textId);
        int color = Color.parseColor(hexColor);

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this.getContext(), color,
            new AmbilWarnaDialog.OnAmbilWarnaListener()
        {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int newColor) {
                Utils.setEditTextValue(activity, textId, Utils.colorToHex(newColor));
                Utils.changeButtonColor(activity, btnId, newColor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
        });

        colorPicker.show();
    }




    public void reset () {
        Activity activity = getActivity();
        Utils.setEditTextValue(activity, R.id.sierpinski_depth, SierpinskiDrawer.MAX_DEPTH_DEFAULT);
        Utils.setEditTextValue(activity, R.id.sierpinski_strokeWidth, SierpinskiDrawer.STROKE_WIDTH_DEFAULT);
        Utils.setEditTextValue(activity, R.id.sierpinski_color1, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[0]);
        Utils.setEditTextValue(activity, R.id.sierpinski_color2, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[1]);
        Utils.setEditTextValue(activity, R.id.sierpinski_color3, SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[2]);
        Utils.changeButtonColor(activity, R.id.mandelbrot_resetBtn,
                Color.parseColor(SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[0]));
        Utils.changeButtonColor(activity, R.id.sierpinski_colorPicker2,
                Color.parseColor(SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[1]));
        Utils.changeButtonColor(activity, R.id.sierpinski_colorPicker3,
                Color.parseColor(SierpinskiDrawer.COLOR_GRADIENT_DEFAULT[2]));
    }






    @Override
    public Bundle createBundle() {
        Activity activity = getActivity();

        String depth = Utils.getEditTextValue(activity, R.id.sierpinski_depth);
        String strokeWidth = Utils.getEditTextValue(activity, R.id.sierpinski_strokeWidth);

        String color1 = Utils.getEditTextValue(activity, R.id.sierpinski_color1);
        String color2 = Utils.getEditTextValue(activity, R.id.sierpinski_color2);
        String color3 = Utils.getEditTextValue(activity, R.id.sierpinski_color3);

        String[] colors = new String[] {color1, color2, color3};

        Bundle b = new Bundle();
        b.putInt("depth", Integer.valueOf(depth));
        b.putInt("strokeWidth", Integer.valueOf(strokeWidth));
        b.putStringArrayList("colors", new ArrayList<String>(Arrays.asList(colors)));

        return b;
    }













}