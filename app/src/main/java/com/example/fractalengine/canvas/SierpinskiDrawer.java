package com.example.fractalengine.canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class SierpinskiDrawer extends FractalDrawer {

    public static int MAX_DEPTH_DEFAULT = 4;
    public static int STROKE_WIDTH_DEFAULT = 6;
    public static String[] COLOR_GRADIENT_DEFAULT = new String[] {
            "#ff00ff", "#0000ff", "#ff0000"
    };

    public String force_hex_color(String color, String defaultColor) {
        boolean isHexColor = true;
        // Apply hex regex or use default color
        if (isHexColor)
            return color;
        return defaultColor;
    }

    public int depth = MAX_DEPTH_DEFAULT;
    public int strokeWidth = STROKE_WIDTH_DEFAULT;
    public ArrayList<String> colors = new ArrayList<String>(Arrays.asList(COLOR_GRADIENT_DEFAULT));

    public SierpinskiDrawer(SurfaceHolder surfaceHolder, Bundle bundle) {
        super(surfaceHolder, bundle);
        if (bundle == null) {
          useDefaultSettings();
        } else {
            useBundleSettings();
        }
    }


    public void useDefaultSettings () {
        depth = MAX_DEPTH_DEFAULT;
        strokeWidth = STROKE_WIDTH_DEFAULT;
        colors = new ArrayList<String>(Arrays.asList(COLOR_GRADIENT_DEFAULT));
    }

    public void useBundleSettings() {
        depth = bundle.getInt("depth");
        strokeWidth = bundle.getInt("strokeWidth");
        colors = bundle.getStringArrayList("colors");
    }

    @Override
    public void draw(View view) {
        Canvas canvas = surfaceHolder.lockCanvas();
        _draw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void _draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

//
//        gradient = ctx.createLinearGradient(canvasHeight/2, 0, canvasHeight, canvasWidth);
//        gradient.addColorStop(0, "magenta");
//        gradient.addColorStop(0.5 ,"blue");
//        gradient.addColorStop(1.0, "red");



        // Since the Sierpinksi is a perfect triangle we must center the height
        // Find the difference of the width and height and divide by two
        float offsetY = (height / 2) - (height - width) / 2;
        float offsetX = 0.025f;
        float startX = width * offsetX;
        float startY = height - (offsetY / 2);
        float sidelen = width - (width * offsetX * 2);
        Float[] pos = new Float[] {startX, startY};



        int[] _colors = new int[colors.size()];


        int i=0;
        for (String color : colors) {
            _colors[i] = Color.parseColor(color);
            i++;
        }


        paint.setShader(new LinearGradient(
                startX, startY, width-startX, height-startY,
                _colors, null, Shader.TileMode.MIRROR
        ));


        createSierpinskiTriangle(pos, sidelen, depth, canvas, paint);

    }




    public void createSierpinskiTriangle(
        Float[] pos, float sidelen, int depth, Canvas canvas, Paint paint
    ) {

        float innerTriangleSidelen = sidelen / 2; // side length of inner triangles is half the side length of the outer triangle
        ArrayList<Float[]> innerTrianglePositions = new ArrayList<Float[]>();

        /*
         Add the first 3 triangle positions to start from.
         These 3 points create a triangle.
         These positions are the same as what was used in the createTriangle function
        */
        innerTrianglePositions.add(pos);
        innerTrianglePositions.add(new Float[] {
                pos[0] + innerTriangleSidelen,
                pos[1]
        });
        innerTrianglePositions.add(new Float[] {
                pos[0] + innerTriangleSidelen / 2,
                (float) (pos[1] - Math.sin(Math.PI / 3) * innerTriangleSidelen)
        });



        if(depth == 0) {
            for (Float[] trianglePosition : innerTrianglePositions) {
                createTriangle(trianglePosition, innerTriangleSidelen, canvas, paint);
            }
        } else {
            for (Float[] trianglePosition : innerTrianglePositions) {
                createSierpinskiTriangle(
                    trianglePosition, innerTriangleSidelen, depth - 1, canvas, paint
                );
            }
        }


    }

    public void createTriangle (Float[] pos, double sidelen, Canvas ctx, Paint paint) {

        float startX, startY, endX, endY;

        // note that (0,0) in canvas is the top left, so 'up' on the vertical component would use substraction.
        // draw line from left vertex to top vertex
        startX = pos[0]; startY = pos[1];
        endX = (float) (pos[0] + sidelen / 2);
        endY = (float) (pos[1] - sidelen * Math.sin(Math.PI/3));
        ctx.drawLine(startX, startY, endX, endY, paint);

        // draw line from top vertex to right vertex
        startX = endX; startY = endY;
        endX = (float) (pos[0] + sidelen);
        endY = pos[1];
        ctx.drawLine(startX, startY, endX, endY, paint);

        // draw line from right vertex back to left vertex
        startX = endX; startY = endY;
        endX = pos[0];endY = pos[1];
        ctx.drawLine(startX, startY, endX, endY, paint);
    }
}
