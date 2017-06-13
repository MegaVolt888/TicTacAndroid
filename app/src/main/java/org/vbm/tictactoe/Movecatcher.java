package org.vbm.tictactoe;

import android.content.Context;

/**
 * Created by vbm on 29/05/2017.
 * Interface for presenter to put move clear play area and to provide Context for toasts
 */

interface Movecatcher {
    Boolean putMove( int a, int b, char moveChar, int moveColor);
    void clearButtons();
    Context getContext();
}
