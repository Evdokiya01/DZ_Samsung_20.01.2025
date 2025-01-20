package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    private SurfaceHolder holder;
    private boolean flag; // false

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public MyThread(SurfaceHolder h) {
        this.holder = h;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); // сглаживание
    }

    public long getTime() {
        return System.currentTimeMillis(); // 01.01.1970
    }

    private long lastTime;

    @Override
    public void run() {
        // логика отрисовки
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - lastTime;
            if(elapsedTime < 1000) {
                continue;
            }
            // блокировка Canvas чтобы на ней нарисовать
            canvas = holder.lockCanvas();
            // нарисовали
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.rgb(
                    (int) (255*Math.random()),
                    (int) (255*Math.random()),
                    (int) (255*Math.random())));
            canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,
                    (float)(300*Math.random()),paint);
            // разблокируем и показываем
            holder.unlockCanvasAndPost(canvas);
            lastTime = getTime();
        }
    }
}
