package com.example.fractalengine.canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;


import com.example.fractalengine.canvas.utils.CanvasUtils;

public class MandelbrotDrawer extends FractalDrawer{


    public static final int COLOR_SIZE_DEFAULT = 64;
    public int COLOR_SIZE;

    // REAL and IMAGINARY START and END variables are used to
    // set the "focus" point of the mandelbrot.
    // They present the borders of the canvas in regards to the graph
    // in which the mandelbrot is drawn.0
    public static final double REAL_START_DEFAULT = -2;
    public static final double REAL_END_DEFAULT = 1;
    public static final double IMAGINARY_START_DEFAULT = -2;
    public static final double IMAGINARY_END_DEFAULT = 2;
    public static final int MAX_ITERATIONS_DEFAULT = 100;
    public static final double ESCAPE_RADIUS_DEFAULT = 2.0;

    public double REAL_START, REAL_END;
    public double IMAGINARY_START, IMAGINARY_END;

    public int MAX_ITERATIONS;
    public double ESCAPE_RADIUS;

    public MandelbrotDrawer(SurfaceHolder surfaceHolder, Bundle bundle) {
        super(surfaceHolder, bundle);
        if (bundle == null) {
            useDefaultSettings();
        } else {
            useBundleSettings();
        }
    }

    /***
     * Use the programs default settings to define the mandelbrot set.
     * This is a good way to rest or initially load the mandelbrot.
     */
    public void useDefaultSettings() {

        MAX_ITERATIONS = MAX_ITERATIONS_DEFAULT;
        ESCAPE_RADIUS = ESCAPE_RADIUS_DEFAULT;

        REAL_START = REAL_START_DEFAULT;
        REAL_END = REAL_END_DEFAULT;
        IMAGINARY_START = IMAGINARY_START_DEFAULT;
        IMAGINARY_END = IMAGINARY_END_DEFAULT;

        COLOR_SIZE = COLOR_SIZE_DEFAULT;
    }

    public void useBundleSettings() {
        useDefaultSettings(); // Remove after all settings have been implemented.
        MAX_ITERATIONS = bundle.getInt("maxIterations");
        ESCAPE_RADIUS = bundle.getDouble("escapeRadius");
        REAL_START = bundle.getDouble("realStart");
        REAL_END = bundle.getDouble("realEnd");
        IMAGINARY_START = bundle.getDouble("imaginaryStart");
        IMAGINARY_END = bundle.getDouble("imaginaryEnd");
        COLOR_SIZE = bundle.getInt("colorRange");
    }




    /***
     * Method called by the application to calculate and draw the
     * mandelbrot set to a canvas. The method for drawing used here
     * uses a SurfaceView to lock the canvas, perform modification,
     * then unlocking a performing those modifications.
     * @param view
     */
    public void draw(View view) {
        Canvas canvas = surfaceHolder.lockCanvas();
        _draw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    /***
     * Create a random schema of colors to draw from.
     * C
     * @param canvas
     */
    private void _draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Random schema of colors.
        int[] colors = new int[64];
        for (int i=0; i < colors.length; i++) {
            colors[i] = CanvasUtils.randomColor();
        }

        // Store copy of canvas dimensions.
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Set graph size for mandelbrot calculations and drawing.
        double[] real = new double[] {REAL_START, REAL_END};
        double[] imaginary = new double[] {IMAGINARY_START, IMAGINARY_END};

        // for each pixel in the canvas
        for (int x=0; x < width; x++) {
            for (int y=0; y < height; y++) {

                // Create a complex number for this pixel.
                // EASY -> Use the graph size and the pixel location to calculate a complex number.
                // HARD -> Extending Easy -> Add a magnification or zoom factor to the calculations.
                double[] complex = new double[] {
                        real[0] + ((double)x / width) * (real[1] - real[0]),
                        imaginary[0] + ((double)y / height) * (imaginary[1] - imaginary[0]),
                };

                // Check to see if the calculated complex number is in the mandelbrot set.
                Object[] results = isInMandelbrot(complex);

                // If the complex number is a mandelbrot number assign a color
                // to the pixel by using the number of iterations.
                // How many iterations were needed of the mandelbrot equation
                // before the complex number surpassed the escape radius.
                if (results[1].equals(false)) {
                    // Modulo the iteration value against the length of the
                    // color array to give each iteration value a color.
                    int colorIdx = (int) results[0] % (colors.length-1);
                    paint.setColor(colors[colorIdx]);
                }
                else {
                    // Otherwise the complex number is a true mandelbrot number
                    // and should be drawn black until a more complex color schema (gradient)
                    // can be developed for additional coloring.
                    paint.setColor(Color.BLACK);
                }
                // Draw the pixel with the calculated color.
                canvas.drawPoint(x, y, paint);
            }
        }

    }


    /***
     * Given a 2d array that represents a complex number,
     * determine if it is a mandelbrot number and return the number
     * of iterations were needed for the complex number to surpass
     * the escape radius.
     * @param complex
     * @return
     */
    public Object[] isInMandelbrot (double[] complex) {
        int n = 0;
        double d = 0;
        double zx = 0, zy=0;
        double px = 0, py=0;
        do {
            px = (zx * zx) - (zy * zy);
            py = 2 * zx * zy;
            zx = px + complex[0];
            zy = py + complex[1];
            d = Math.sqrt((zx*zx) + (zy*zy));
            n++;
        } while (d <= ESCAPE_RADIUS && n < MAX_ITERATIONS);
        return new Object[] {n, d <= ESCAPE_RADIUS};
    }

}

