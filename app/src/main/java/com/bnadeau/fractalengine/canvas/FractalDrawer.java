package com.bnadeau.fractalengine.canvas;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;

public abstract class FractalDrawer {

    public Bundle bundle;
    public SurfaceHolder surfaceHolder;

    public FractalDrawer(SurfaceHolder surfaceHolder, Bundle bundle) {
        this.surfaceHolder = surfaceHolder;
        this.bundle = bundle;
    }

    public abstract void draw(View view);

}
