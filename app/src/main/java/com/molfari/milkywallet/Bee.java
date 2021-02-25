package com.molfari.milkywallet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.molfari.milkywallet.GameView.screenRatioX;
import static com.molfari.milkywallet.GameView.screenRatioY;

public class Bee {
    public int speedy = 20;
    public boolean wasShot = true;
    int x=0, y, width, height, beeCounter = 1;
    Bitmap bee1, bee2, bee3, bee4;

    Bee(Resources res){
        bee1 = BitmapFactory.decodeResource(res, R.drawable.bee1);
        bee2 = BitmapFactory.decodeResource(res, R.drawable.bee2);
        bee3 = BitmapFactory.decodeResource(res, R.drawable.bee3);
        bee4 = BitmapFactory.decodeResource(res, R.drawable.bee4);

        width = bee1.getWidth();
        height = bee1.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bee1 = Bitmap.createScaledBitmap(bee1, width, height, false);
        bee2 = Bitmap.createScaledBitmap(bee2, width, height, false);
        bee3 = Bitmap.createScaledBitmap(bee3, width, height, false);
        bee4 = Bitmap.createScaledBitmap(bee4, width, height, false);

        y = -height;
    }

    Bitmap getBird () {

        if (beeCounter == 1){
            beeCounter++;
            return bee1;
        }
        if (beeCounter == 2){
            beeCounter++;
            return bee2;
        }
        if (beeCounter == 3){
            beeCounter++;
            return bee3;
        }
        beeCounter = 1;

        return bee4;
    }

    Rect getCollisionShape (){
        return  new Rect (x, y, x + width, y + height);
    }
}
