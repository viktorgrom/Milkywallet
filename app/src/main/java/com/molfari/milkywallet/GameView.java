package com.molfari.milkywallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private  Thread thread;
    private  boolean isPlaying, isGameOver = false;
    private BackLand backLand1, backLand2;
    private int screenX, screenY, score = 0, life_counter;
    private Paint paint;
    private Bee[] bees;

    //private Life [] lifes;
    private Bitmap life[] = new Bitmap[2];

    private Cow cow;
    private List<Milk> milkies;
    private Butter[] butter;

    private Random random;
    private SharedPreferences prefs;
    private GameActivity activity;
    private SoundPool soundPool;
    private int sound, width, height;;
    public static float screenRatioX, screenRatioY;

    public Button btn_fire;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else
            soundPool = new SoundPool(1 , AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.shoot, 1);


        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        backLand1 = new BackLand(screenX, screenY, getResources());
        backLand2 = new BackLand(screenX, screenY, getResources());

        backLand2.x = screenX;

        cow = new Cow(this, screenY, getResources());

        butter = new Butter[1];
        for (int i = 0; i<1; i++){

            Butter butter = new Butter(getResources());
            this.butter[i] = butter;
        }

        milkies = new ArrayList<>();

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.GRAY);

        bees = new Bee[5];

        for (int i = 0; i<5; i++){

            Bee bee = new Bee(getResources());
            bees[i] = bee;
        }
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.life_o);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.life_of);

        width = life[0].getWidth();
        height = life[1].getHeight();

        width /= 4;
        height /= 4;

        life[0] = Bitmap.createScaledBitmap(life[0], width, height, false);
        life[1] = Bitmap.createScaledBitmap(life[1], width, height, false);

        life_counter = 3;

        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update(){

        backLand1.x -= 0 * screenRatioX;
        backLand2.x -= 0 * screenRatioX;



        if (backLand1.x + backLand1.bg.getWidth() < 0){
            backLand1.x = screenX;
        }
        if (backLand2.x + backLand2.bg.getWidth() < 0){
            backLand2.x = screenX;
        }

        if (cow.isGoingUp)
            cow.y -= 15 * screenRatioY;
        else
            cow.y += 15 * screenRatioY;

        if (cow.y < 0)
            cow.y = 0;

        if (cow.y >= screenY - cow.height)
            cow.y = screenY - cow.height;

        List<Milk> trash = new ArrayList<>();

        for (Milk milk : milkies){
            if (milk.x > screenX)
                trash.add(milk);

            milk.x += 40 * screenRatioX;

            for (Bee bee : bees){
                if (Rect.intersects(bee.getCollisionShape(), milk.getCollisionShape() )){

                    score++;
                    bee.x = -500;
                    milk.x = screenX + 500;
                    bee.wasShot = true;
                }

            }

            for (Butter butter : this.butter){
                if (Rect.intersects(butter.getCollisionShape(), milk.getCollisionShape())){

                    score=score -2;
                    butter.x = -500;
                    milk.x = screenX +500;
                    butter.wasShotButer = true;
                }
            }
        }



        for (Milk milk : trash)
            milkies.remove(milk);

        for (Bee bee : bees) {

            bee.x -= bee.speedy;
            if (bee.x + bee.width < 0) {

                if (!bee.wasShot) {
                    life_counter--;
                }


                if (life_counter == 0) {

                    isGameOver = true;
                    return;
                }

                if (score <= 30) {

                    int bound = (int) (10 * screenRatioX);
                    bee.speedy = random.nextInt(bound);

                    if (bee.speedy < 5 * screenRatioX)
                        bee.speedy = (int) (5 * screenRatioX);
                }
                if (score >= 60) {
                    int bound = (int) (14 * screenRatioX);
                    bee.speedy = random.nextInt(bound);

                    if (bee.speedy < 6 * screenRatioX)
                        bee.speedy = (int) (6 * screenRatioX);
                }
                if (score >= 100) {
                    int bound = (int) (18 * screenRatioX);
                    bee.speedy = random.nextInt(bound);

                    if (bee.speedy < 8 * screenRatioX)
                        bee.speedy = (int) (8 * screenRatioX);
                }
                if (score >= 200) {
                    int bound = (int) (21 * screenRatioX);
                    bee.speedy = random.nextInt(bound);

                    if (bee.speedy < 10 * screenRatioX)
                        bee.speedy = (int) (10 * screenRatioX);
                }

                bee.x = screenX;
                bee.y = random.nextInt(screenY - bee.height);

                bee.wasShot = false;

            }
            if (Rect.intersects(bee.getCollisionShape(), cow.getCollisionShape())){
                isGameOver = true;
                return;
            }
        }

        for (Butter butter : this.butter) {

            butter.x -= butter.speed;
            if (butter.x + butter.width < 0) {

                if (score <= 100) {

                    int bound = (int) (20 * screenRatioX);
                    butter.speed = random.nextInt(bound);

                    if (butter.speed < 12 * screenRatioX)
                        butter.speed = (int) (12 * screenRatioX);
                } else {
                    int bound = (int) (25 * screenRatioX);
                    butter.speed = random.nextInt(bound);

                    if (butter.speed < 15 * screenRatioX)
                        butter.speed = (int) (15 * screenRatioX);
                }


                butter.x = screenX;
                butter.y = random.nextInt(screenY - butter.height);

                butter.wasShotButer = false;

            }


            if (Rect.intersects(butter.getCollisionShape(), cow.getCollisionShape())) {
                life_counter = life_counter +1;
                if (life_counter >= 4)
                    life_counter = 3;
                butter.x = -500;

                butter.wasShotButer = true;

            }
        }

    }
    private void draw(){

        if (getHolder().getSurface().isValid()){

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backLand1.bg, backLand1.x, backLand1.y, paint);
            canvas.drawBitmap(backLand2.bg, backLand2.x, backLand2.y, paint);



            canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isGameOver){
                isPlaying = false;
                canvas.drawBitmap(cow.getCow_dead(), cow.x, cow.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveHighScore();
                waitBeforeExiting ();
                return;
            }



            canvas.drawBitmap(cow.getFlight(), cow.x, cow.y, paint);

            for (Bee bee : bees)
                canvas.drawBitmap(bee.getBird(), bee.x, bee.y, paint);

            for (Butter butter : this.butter)
                canvas .drawBitmap(butter.getPie(), butter.x, butter.y, paint);



            for (Milk milk : milkies)
                canvas.drawBitmap(milk.getPulias(), milk.x, milk.y, paint);

            for (int i=0; i<3; i++){
                int x = (int) (380 + life[0].getWidth()  *i);
                int y = 30;

                if (i < life_counter){
                    canvas.drawBitmap(life[0], x, y, null);
                }
                else {
                    canvas.drawBitmap(life[1], x, y, null);
                }
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveHighScore() {

        if (prefs.getInt("highscore", 0) < score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }


    }

    private  void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    cow.isGoingUp = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                cow.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    cow.toShoot++;

                break;
        }

        return true;
    }

    public void newBullet() {

        if (!prefs.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0,0,1);

        Milk milk = new Milk(getResources());
        milk.x = cow.x + cow.width;
        milk.y = cow.y + (cow.height/4);
        milkies.add(milk);
    }
}
