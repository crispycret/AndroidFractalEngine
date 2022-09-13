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
 * Use the {@link MandelbrotSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MandelbrotSettingsFragment extends BundlingFragment {

    public static final int DEFAULT_MAX_ITERATIONS = 100;
    public static final double DEFAULT_ESCAPE_RADIUS = 2.0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MandelbrotSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MandelbrotSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MandelbrotSettingsFragment newInstance(String param1, String param2) {
        MandelbrotSettingsFragment fragment = new MandelbrotSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
                Log.w("BUTTON:", "WORKING");
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

    /**
     * Return settings for the mandelbrot as a Bundle.
     * @return -> <code>Bundle</code>
     */
    @Override
    public Bundle createBundle () {
        Bundle b = new Bundle();
        String escapeRadius = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_escapeRadius))
                .getText().toString();
        String maxIterations = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_maxIterations))
                .getText().toString();

        String realStart = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_realStart))
                .getText().toString();

        String realEnd = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_realEnd))
                .getText().toString();

        String imaginaryStart = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_imaginaryStart))
                .getText().toString();

        String imaginaryEnd = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_imaginaryEnd))
                .getText().toString();

        String colorRange = ((EditText)getActivity()
                .findViewById(R.id.mandelbrot_colorRange))
                .getText().toString();

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