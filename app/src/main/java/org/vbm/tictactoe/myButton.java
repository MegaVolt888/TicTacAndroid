package org.vbm.tictactoe;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableRow;

/**
 * Created by vbm on 29/05/2017.
 */

public class myButton extends android.support.v7.widget.AppCompatButton {
    private int a, b ;

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }


    public myButton(Context context) {
        super(context);
    }

    public myButton( Context context, int a, int b, int width, int heigth,  OnClickListener listener){
        super( context);
        this.a = a;
        this.b = b;
        this.setPadding(2,2,2,2);
        this.setFrame(1,1,1,1);
        this.setBackgroundResource(android.R.drawable.btn_default_small);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
           setClipToOutline(true);
        /*setMaxHeight(heigth);
        setMaxWidth(width);
        setWidth(width);
        setHeight(heigth);*/

        TableRow.LayoutParams trParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        setText(" ");
        setOnClickListener(listener);
        //TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        setLayoutParams(trParams);
    }
}
