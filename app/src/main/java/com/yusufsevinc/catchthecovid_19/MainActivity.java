package com.yusufsevinc.catchthecovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    int timeoneandtwo , timethereandfour ,speedoneandthere,speedtwoandfour ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Leveller için hız ve zaman değerleri
        timeoneandtwo = 30000;
        speedoneandthere = 500;
        speedtwoandfour = 250;
        timethereandfour = 60000;


        //2. altiviteye geçişimiz için gerekli olan intent
        intent = new Intent(MainActivity.this , Main2Activity.class);

    }//end onCreate
    //level 1
    public void startone(View view){
        //2. aktivitede kullanacağımız değişkleri birinci aktiviteden aldık
        intent.putExtra("timevariable", timeoneandtwo);
        intent.putExtra("speedvariable",speedoneandthere );
        startActivity(intent);

    }//end startone
    //level 2
    public void starttwo(View view){

        intent.putExtra("timevariable", timeoneandtwo);
        intent.putExtra("speedvariable",speedtwoandfour );
        startActivity(intent);

    }//end starttwo
    //level 3
    public void startthere(View view){

        intent.putExtra("timevariable", timethereandfour);
        intent.putExtra("speedvariable",speedoneandthere );
        startActivity(intent);

    }//end startthere
    //level 4
    public void startfour(View view){

        intent.putExtra("timevariable", timethereandfour);
        intent.putExtra("speedvariable",speedtwoandfour );
        startActivity(intent);

    }//end startfour

}//end class

