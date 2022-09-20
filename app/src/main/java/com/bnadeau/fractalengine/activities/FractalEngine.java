package com.bnadeau.fractalengine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.bnadeau.fractalengine.canvas.FractalDrawer;
import com.bnadeau.fractalengine.R;
import com.bnadeau.fractalengine.canvas.MandelbrotDrawer;
import com.bnadeau.fractalengine.canvas.SierpinskiDrawer;

public class FractalEngine extends AppCompatActivity {

    public SurfaceView surfaceView;
    public SurfaceHolder surfaceHolder;

    public String fractalType;
    public FractalDrawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fractal_engine);

        surfaceView = findViewById(R.id.fractalSurfaceView);
        surfaceHolder = surfaceView.getHolder();

        // Setup
        setFractalType();
        setFractalDrawer();

    // Depending on the loaded fractalType Create a fragment to display that fractal type
    }


    public void setFractalType() {
        Intent intent = getIntent();
        fractalType = intent.getStringExtra(MainActivity.FRACTAL_TYPE);
        TextView textView = (TextView) findViewById(R.id.fractalTypeLabel);
        textView.setText(fractalType);
    }

    public void draw(View view) {
        drawer.draw(view);
    }


    private void setFractalDrawer() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (fractalType.equals("Mandelbrot")) {
            // Use mandelbrot drawer
            drawer = new MandelbrotDrawer(surfaceHolder, bundle);
        }
        else if (fractalType.equals("Sierpinski")) {
            drawer = new SierpinskiDrawer(surfaceHolder, bundle);

        }

    }

}