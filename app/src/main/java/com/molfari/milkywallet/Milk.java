package com.molfari.milkywallet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.molfari.milkywallet.GameView.screenRatioX;
import static com.molfari.milkywallet.GameView.screenRatioY;

public class Milk {

    int x, y, width, height, milk_counter = 1;


    Bitmap milky1, milky2, milky3, milky4;

    Milk(Resources res){
        milky1 = BitmapFactory.decodeResource(res, R.drawable.milk1);
        milky2 = BitmapFactory.decodeResource(res, R.drawable.milk2);
        milky3 = BitmapFactory.decodeResource(res, R.drawable.milk3);
        milky4 = BitmapFactory.decodeResource(res, R.drawable.milk4);

        width = milky1.getWidth();
        height = milky1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        milky1 = Bitmap.createScaledBitmap(milky1, width, height, false);
        milky2 = Bitmap.createScaledBitmap(milky2, width, height, false);
        milky3 = Bitmap.createScaledBitmap(milky3, width, height, false);
        milky4 = Bitmap.createScaledBitmap(milky4, width, height, false);
    }

    Bitmap getPulias () {

        if (milk_counter == 1){
            milk_counter++;
            return milky1;
        }
        if (milk_counter == 2){
            milk_counter++;
            return milky2;
        }
        if (milk_counter == 3){
            milk_counter++;
            return milky3;
        }
        milk_counter = 1;

        return milky4;
    }

    Rect getCollisionShape (){
        return  new Rect (x, y, x + width, y + height);
    }
}

