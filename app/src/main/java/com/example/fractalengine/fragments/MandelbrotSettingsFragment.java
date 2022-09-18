package com.example.fractalengine.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fractalengine.R;
import com.example.fractalengine.canvas.MandelbrotDrawer;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MandelbrotSettingsFragment extends BundlingFragment {


    public MandelbrotSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        // ToDo: Learning... How to use saved states.
        if (savedInstanceState != null) {
//            Bundle bundle = savedInstanceState.getBundle("MandelbrotSettings");
//            Log.w("MandelbrotSettings", bundle.toString());
        }
    }

    /**
     * Learning...
     * Place OnClickListener's Here
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mandelbrot_settings, container, false);

        // Add OnClickListeners
        Button resetBtn = (Button)v.findViewById(R.id.mandelbrot_resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        return v;
    }

    /**
     * Learning...
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBundle("MandelbrotSettings", createBundle());
    }

    public String getEditTextValue (int id) {
        return ((EditText)getActivity().findViewById(id)).getText().toString();
    }

    /**
     * Return settings for the mandelbrot as a Bundle.
     * @return -> <code>Bundle</code>
     */
    @Override
    public Bundle createBundle () {
        String escapeRadius = getEditTextValue(R.id.mandelbrot_escapeRadius);
        String maxIterations = getEditTextValue(R.id.mandelbrot_maxIterations);
        String realStart = getEditTextValue(R.id.mandelbrot_realStart);
        String realEnd = getEditTextValue(R.id.mandelbrot_realEnd);
        String imaginaryStart = getEditTextValue(R.id.mandelbrot_imaginaryStart);
        String imaginaryEnd = getEditTextValue(R.id.mandelbrot_imaginaryEnd);
        String colorRange = getEditTextValue(R.id.mandelbrot_colorRange);

        Bundle b = new Bundle();
        b.putString("fractalType", "Mandelbrot");
        b.putInt("maxIterations", Integer.valueOf(maxIterations));
        b.putDouble("escapeRadius", Double.valueOf(escapeRadius));
        b.putDouble("realStart", Double.valueOf(realStart));
        b.putDouble("realEnd", Double.valueOf(realEnd));
        b.putDouble("imaginaryStart", Double.valueOf(imaginaryStart));
        b.putDouble("imaginaryEnd", Double.valueOf(imaginaryEnd));
        b.putInt("colorRange", Integer.valueOf(colorRange));

        Log.w("Mandelbrot Bundle:", b.toString());

        return b;
    }

    public void setEditText(int id, String val) {
        ((EditText)getActivity().findViewById(id)).setText(val);
    }

    public void setEditText(int id, int val) {
        ((EditText)getActivity().findViewById(id)).setText(String.valueOf(val));
    }

    public void setEditText(int id, double val) {
        ((EditText)getActivity().findViewById(id)).setText(String.valueOf(val));
    }

    /**
     * Reset the mandelbrot settings to their default values.
     */
    public void reset() {
        setEditText(R.id.mandelbrot_escapeRadius, MandelbrotDrawer.ESCAPE_RADIUS_DEFAULT);
        setEditText(R.id.mandelbrot_maxIterations, MandelbrotDrawer.MAX_ITERATIONS_DEFAULT);
        setEditText(R.id.mandelbrot_realStart, MandelbrotDrawer.REAL_START_DEFAULT);
        setEditText(R.id.mandelbrot_realEnd, MandelbrotDrawer.REAL_END_DEFAULT);
        setEditText(R.id.mandelbrot_imaginaryStart, MandelbrotDrawer.IMAGINARY_START_DEFAULT);
        setEditText(R.id.mandelbrot_imaginaryEnd, MandelbrotDrawer.IMAGINARY_END_DEFAULT);
        setEditText(R.id.mandelbrot_colorRange, MandelbrotDrawer.COLOR_SIZE_DEFAULT);
    }

}