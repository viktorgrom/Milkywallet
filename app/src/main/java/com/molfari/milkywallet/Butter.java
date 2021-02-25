package com.molfari.milkywallet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.molfari.milkywallet.GameView.screenRatioX;
import static com.molfari.milkywallet.GameView.screenRatioY;

public class Butter {
    public int speed = 20;
    public boolean wasShotButer = true;
    int x=0, y, width, height, buter_ciunter = 1;
    Bitmap buter1, buter2, buter3, buter4;

    Butter(Resources res){
        buter1 = BitmapFactory.decodeResource(res, R.drawable.buter1);
        buter2 = BitmapFactory.decodeResource(res, R.drawable.buter2);
        buter3 = BitmapFactory.decodeResource(res, R.drawable.buter3);
        buter4 = BitmapFactory.decodeResource(res, R.drawable.buter4);


        width = buter1.getWidth();
        height = buter1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        buter1 = Bitmap.createScaledBitmap(buter1, width, height, false);
        buter2 = Bitmap.createScaledBitmap(buter2, width, height, false);
        buter3 = Bitmap.createScaledBitmap(buter3, width, height, false);
        buter4 = Bitmap.createScaledBitmap(buter4, width, height, false);


        y = -height;
    }

    Bitmap getPie () {

        if (buter_ciunter == 1){
            buter_ciunter++;
            return buter1;
        }
        if (buter_ciunter == 2){
            buter_ciunter++;
            return buter2;
        }
        if (buter_ciunter == 3){
            buter_ciunter++;
            return buter3;
        }

        buter_ciunter = 1;

        return buter4;
    }

    Rect getCollisionShape (){
        return  new Rect (x, y, x + width, y + height);
    }
}

