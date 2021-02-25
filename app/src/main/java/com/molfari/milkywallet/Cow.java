package com.molfari.milkywallet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.molfari.milkywallet.GameView.screenRatioX;
import static com.molfari.milkywallet.GameView.screenRatioY;

public class Cow {
    boolean isGoingUp = false;
    int toShoot = 0;

    int x, y, width, height, win_counter = 0, shoo_counter = 1;
    Bitmap kow1, kow2, cow_sho1, cow_sho2, cow_sho3, cow_sho4, shoot5, cow_dead;
    private GameView gameView;

    Cow(GameView gameView, int screenY, Resources res){

        this.gameView = gameView;

        kow1 = BitmapFactory.decodeResource(res, R.drawable.her_cow1);
        kow2 = BitmapFactory.decodeResource(res, R.drawable.hero_cow2);

        width = kow1.getWidth();
        height = kow1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        kow1 = Bitmap.createScaledBitmap(kow1, width, height, false);
        kow2 = Bitmap.createScaledBitmap(kow2, width, height, false);

        cow_sho1 = BitmapFactory.decodeResource(res, R.drawable.cow_shoot1);
        cow_sho2 = BitmapFactory.decodeResource(res, R.drawable.cow_shoot2);
        cow_sho3 = BitmapFactory.decodeResource(res, R.drawable.cow_shoot3);
        cow_sho4 = BitmapFactory.decodeResource(res, R.drawable.cow_shoot4);


        cow_sho1 = Bitmap.createScaledBitmap(cow_sho1, width, height, false);
        cow_sho2 = Bitmap.createScaledBitmap(cow_sho2, width, height, false);
        cow_sho3 = Bitmap.createScaledBitmap(cow_sho3, width, height, false);
        cow_sho4 = Bitmap.createScaledBitmap(cow_sho4, width, height, false);


        cow_dead = BitmapFactory.decodeResource(res, R.drawable.hero_cow_dead);
        cow_dead = Bitmap.createScaledBitmap(cow_dead, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap getFlight (){

        if (toShoot != 0){
            if (shoo_counter == 1){
                shoo_counter++;
                return cow_sho1;
            }
            if (shoo_counter == 2){
                shoo_counter++;
                return cow_sho2;
            }
            if (shoo_counter == 3){
                shoo_counter++;
                return cow_sho3;
            }

            shoo_counter = 1;
            toShoot --;
            gameView.newBullet();

            return cow_sho4;
        }

        if (win_counter == 0) {
            win_counter++;
            return kow1;
        }
        win_counter--;
        return kow2;
    }

    Rect getCollisionShape (){
        return  new Rect (x, y, x + width, y + height);
    }

    Bitmap getCow_dead(){
        return cow_dead;
    }
}

