package com.edusoft.dam.cafesito.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.edusoft.dam.cafesito.R;

public class LinedEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;

    // constructor necesario para crear nuevos componentes y que funcione LayoutInflater
    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        paint = new Paint();

        //establece las preferencias del pincel
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(getResources().getColor(R.color.secundario)); // Color of the lines on paper

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // variables
        int height = getHeight(); //obtiene altura del view
        int lineHeight = getLineHeight(); //obtiene la distancia entre líneas del view
        int numberOfLines = height / lineHeight;


        int baseline = getLineBounds(0, rect); //donde se empieza a dibujar

        for (int i = 0; i < numberOfLines; i++) {

            canvas.drawLine(rect.left, baseline, rect.right, baseline, paint);

            baseline += lineHeight; //el pincel se mueve una línea
        }

    }

}