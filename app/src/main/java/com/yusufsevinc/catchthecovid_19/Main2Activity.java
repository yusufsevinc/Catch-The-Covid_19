package com.yusufsevinc.catchthecovid_19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    TextView time ,skor ,yüksekskor;
    Button start ,finish;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView0;
    int score , times , highscore,speed ;
    Intent intent;
    ImageView[] images;
    Handler handler;
    Runnable runnable;
    CountDownTimer countDownTimer;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        time = findViewById(R.id.textViewtime);
        yüksekskor = findViewById(R.id.textViewhighscore);
        skor = (TextView) findViewById(R.id.textViewscore);
        start = findViewById(R.id.button);
        finish = findViewById(R.id.button);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView0 = findViewById(R.id.imageView0);
        //Görsellerin hepsini bir arada tutmak için dizi oluşturduk
        images = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView0};
        //1. aktivideteden alacağımız değişkler
        intent = getIntent();
        times = intent.getIntExtra("timevariable", 0);
        speed = intent.getIntExtra("speedvariable", 0);
        //Sharedpreference bizim oyun içindeyken oyunun veri tabanına veri kaydetmemizi sağlıyor , oyunda yüksek skoru tutmak için sharedpreference kullandım.
        sharedPreferences = this.getSharedPreferences("com.yusufsevinc.denme", Context.MODE_PRIVATE);
        highscore = sharedPreferences.getInt("yüksekskor",0 );
        //Oyun başlerken kullanıcının yüksek skoru görmesi için
        yüksekskor.setText("High Score: " + highscore);


        hidecovid();
        /*oyunun belirli bir sürede çalışmasını istediğim için countDownTimer kullandım
        levelleri oluşturmak için times adında değişkeni 1. aktiviteden aldım */
        countDownTimer = new CountDownTimer(times, 1000 / 100) {

            @Override
            public void onTick(long l) {
                //ekran süreyi kullanıcaya göstermek
                time.setText("Time: " + l / 1000);

            }//end onTick
            @Override
            public void onFinish() {

                //oyun bitince skorla karşılaştırıp yüksek skoru kaydediyoruz.
                if(score > highscore){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("yüksekskor",(score));
                    editor.commit();
                    Toast.makeText(Main2Activity.this, "NEW HİGH SCORE", Toast.LENGTH_LONG).show();
                }
                else{
                    yüksekskor.setText("High Score: " + highscore);

                }
                //Süre bitince kullanıcı isterse aynı levelden tekrar edebilir yada ana menüye dönebilir
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(Main2Activity.this);
                alertdialog.setTitle("Score: " + score);
                alertdialog.setMessage("Do you want to play again ?");
                    /*setcancelable false kullanmamın sebibi kullanıcı çıkan dialoğa kesinlikle
                    cevap vermesi için*/
                alertdialog.setCancelable(false);

                alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*tekar aynı aktivitede devam etmek için*/
                        recreate();


                    }
                });
                alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Kullanıcı no derse ana menüye intent sayesinde dönüyor.
                        intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
                alertdialog.show();
                //süre bitice artık görsellerin hareket etmesini istemiyoruz.
                handler.removeCallbacks(runnable);

            }//end finish
        }.start();//end countDownTimer
    }//end oncreate

    /*Herhangi bir şeyi belirli periyotlarda yapmak için runnable kulladık
    bu sayede görselleri istediğimiz aralıkta ekranda kapatıp açacağım
    handlerı ise runnableye kullanmamızın sebibi yapılcak işlemi kuyruğa
     ekleyip gerçekleştirmek içindir */
    public void hidecovid() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < images.length; i++) {
                    images[i].setVisibility(View.INVISIBLE);

                }
                images[random()].setVisibility(View.VISIBLE);
                /*kuyruğa aldığımız işlem ve gerçekleşme süresi
                yani biz burda ilk kullanacağımız işlemi yazdık
                sonrada işlem sıklığını yazdık levellerı bunun
                sayesinde oluşturdum*/
                handler.postDelayed(runnable, speed);
            }
        };//end runnable
        handler.post(runnable);


    }//end hidecovid

    //Herhangi bir görsele tıklayınca skor artacağı için bütün görsellere aynı isimde click metodu isimini verdim.
    public void scorecount(View view){

        skor.setText("Score: " + (score+1));
        score++;
        //eğer skor yüksek skoru geçerse skor artarken yüksekskorda artacak
        if(score > highscore){
            yüksekskor.setText("High Score: " + score);
        }
    }//end scorecount
    //Ekrana görüntüleri rastgele göstermek istediğimiz için oluşturduğumuz random metodu.
    public int random(){
        Random random = new Random();
        int number = random.nextInt(9);

        return number;
    }//end random


}//end class



