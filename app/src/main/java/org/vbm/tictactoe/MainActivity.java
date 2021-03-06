package org.vbm.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements Movecatcher, View.OnClickListener {
    TicTacToe tictac;
    TableLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitControls();
    }

    void InitControls() {
        tictac = new TicTacToe(this);
        int width, heigth;

        gridLayout = (TableLayout) findViewById(R.id.gLayout);


        //heigth = gridLayout.getHeight() / 7 - 7;
        //width = gridLayout.getWidth() / 7 - 7;
        heigth = this.getResources().getDisplayMetrics().heightPixels / tictac.BOARD_SIZE;
        width = this.getResources().getDisplayMetrics().widthPixels / tictac.BOARD_SIZE;
        for (int i = 0; i < tictac.BOARD_SIZE; ++i) {
            TableRow row = new TableRow(this);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                row.setClipToOutline(true);
            row.setFitsSystemWindows(true);
            for (int j = 0; j < tictac.BOARD_SIZE; ++j)
                row.addView(new myButton(this, i, j, width, heigth, this));
            gridLayout.addView(row);
        }
        gridLayout.refreshDrawableState();

    }

    @Override
    public Boolean putMove(int a, int b, char moveChar, int moveColor) {
        TableRow row = (TableRow) gridLayout.getChildAt(a);
        myButton btn = (myButton) row.getChildAt(b);
        btn.setText(String.valueOf(moveChar));
        btn.setTextColor(moveColor);
        return true;
    }

    @Override
    public void clearButtons() {
        for (int i = 0; i < gridLayout.getChildCount(); ++i) {
            for (int j = 0; j < ((TableRow) gridLayout.getChildAt(i)).getChildCount(); ++j)
                ((myButton) ((TableRow) gridLayout.getChildAt(i)).getChildAt(j)).setText(" ");
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        myButton bt = (myButton) v;
        tictac.uMove(bt.getA(), bt.getB());
    }
}
