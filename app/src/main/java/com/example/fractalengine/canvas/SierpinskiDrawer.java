package com.example.fractalengine.canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

public class SierpinskiDrawer extends FractalDrawer {

    public int DEFAULT_MAX_DEPTH = 1;
    public int DEFAILT_STROKE_WIDTH = 3;

    public int depth = 1;
    public int strokeWidth = 3;

    public SierpinskiDrawer(SurfaceHolder surfaceHolder, Bundle bundle) {
        super(surfaceHolder, bundle);
        if (bundle == null) {
          useDefaultSettings();
        } else {
            useBundleSettings();
        }
    }


    public void useDefaultSettings () {
        depth = DEFAULT_MAX_DEPTH;
        strokeWidth = DEFAILT_STROKE_WIDTH;
    }

    public void useBundleSettings() {
        depth = bundle.getInt("depth");
        strokeWidth = bundle.getInt("strokeWidth");
    }

    @Override
    public void draw(View view) {
        Log.w("Sierpinski: ", "draw()");
        Canvas canvas = surfaceHolder.lockCanvas();
        _draw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void _draw(Canvas canvas) {
        Log.w("Sierpinski: ", "_draw()");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        double offsetX = 0.025;
        double offsetY = 0.05;
        int startX = (int)(width * offsetX);;
        int startY = height - (int)(height * offsetY * 2);
        double sidelen = width - (width * offsetX * 2);
        Integer[] pos = new Integer[] {startX, startY};

        paint.setStrokeWidth(strokeWidth);
        Log.w("Sierpinski: ", "Start!");
        createSierpinskiTriangle(pos, sidelen, depth, canvas, paint);
        Log.w("Sierpinski: ", "Done!");

//        canvas.drawLine(
//            0, canvas.getHeight(),
//            canvas.getWidth(), 0, paint
//        );

    }
//
//    public int[] createTriangleLines (int startX, int startY, double sidelen) {
//        // Each triangle is made of three lines
//        // Each Line consits of a start and end point.
//        // Each Triangle is made of 3 lines.
//
//        ArrayList<Integer[]> lines = new ArrayList<Integer[]>();
//
//        int endX, endY;
//        lines.add(startX, startY, endX, endY);
//
//        // Modify startX, startY, endX, endY
//        lines.add(startX, startY, endX, endY);
//
//        // Modify startX, startY, endX, endY
//        lines.add(startX, startY, endX, endY);
//
//
//    }


    public void createSierpinskiTriangle(
            Integer[] pos, double sidelen, int depth,
            Canvas canvas, Paint paint
            ) {

        double innerTriangleSidelen = sidelen / 2; // side length of inner triangles is half the side length of the outer triangle
        ArrayList<Integer[]> innerTrianglesPositions = new ArrayList<Integer[]>();


        // Add first three positions
        innerTrianglesPositions.add(pos);
        innerTrianglesPositions.add(new Integer[]{
                pos[0] + (int)innerTriangleSidelen, pos[1]
        });
        innerTrianglesPositions.add(new Integer[]{
                pos[0] + (int)(innerTriangleSidelen / 2),
                pos[1] - (int)(Math.sin(Math.PI / 3) * innerTriangleSidelen)
        });


        if(depth == 0) {
            for (Integer[] trianglePosition : innerTrianglesPositions) {
                createTriangle(trianglePosition, innerTriangleSidelen, canvas, paint);
            }
        } else {
            for (Integer[] trianglePosition : innerTrianglesPositions) {
                createSierpinskiTriangle(trianglePosition, innerTriangleSidelen, depth - 1, canvas, paint);
            }
        }


    }

    public void createTriangle (Integer[] pos, double sidelen, Canvas ctx, Paint paint) {

//        ctx.strokeStyle = color

        // note that (0,0) in canvas is the top left, so 'up' on the vertical component would use substraction.
        // draw line from left vertex to top vertex
        float x0 = pos[0];
        float y0 = pos[1];
        float x1 = (float) (pos[0] + sidelen / 2);
        float y1 = (float) (pos[1] - sidelen * Math.sin(Math.PI/3));
        ctx.drawLine(x0,y0, x1, y1, paint);

        // draw line from left vertex to top vertex
        float x2 = (float) (pos[0] + sidelen / 2);
        float y2 = (float) (pos[1] - sidelen * Math.sin(Math.PI/3));
        ctx.drawLine(x1, y1, x2, y2, paint);

        // draw line from top vertex to right vertex
        float x3 = (float) (pos[0] + sidelen);
        float y3 = pos[1];
        ctx.drawLine(x2, y2, x3, y3, paint);
    }
}
