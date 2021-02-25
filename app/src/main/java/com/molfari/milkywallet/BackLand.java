package com.molfari.milkywallet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackLand {
    int x=0, y=0;
    Bitmap bg;

    BackLand(int screenX, int screenY, Resources res){
        bg = BitmapFactory.decodeResource(res, R.drawable.back_land);
        bg = Bitmap.createScaledBitmap(bg, screenX, screenY, false);
    }
}