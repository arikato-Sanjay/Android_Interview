package skysanjay.androidinterview;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button simpleB,toughB,otherB,rateB;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // code to add title bar
        LinearLayout front_title = (LinearLayout)findViewById(R.id.frontPage_title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.frontpage_title_bar);

        //initialisation of buttons
        simpleB = findViewById(R.id.simpleQuestionB);
        toughB = findViewById(R.id.toughQuestionB);
        otherB = findViewById(R.id.otherAppsB);
        rateB = findViewById(R.id.rateOurB);

        //setting on click listener
        simpleB.setOnClickListener(this);
        toughB.setOnClickListener(this);
        otherB.setOnClickListener(this);
        rateB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simpleQuestionB:
                Intent i = new Intent(MainActivity.this, SimpleQuestions.class);
                startActivity(i);
                break;
            case R.id.toughQuestionB:
                Intent j = new Intent(MainActivity.this, ToughQuestions.class);
                startActivity(j);
                break;
            case R.id.otherAppsB:
                try{
                    Uri uri1 = Uri.parse("market://search?q=abcdefgh");
                    Intent gotoStore = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(gotoStore);
                } catch (ActivityNotFoundException e){
                    Uri uri1 = Uri.parse("http://play.google.com/store/search?q=abcdefgh");
                    Intent gotoStore = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(gotoStore);
                }
                break;
            case R.id.rateOurB:
                try{
                    Uri uri1 = Uri.parse("market://details?id="+getPackageName());
                    Intent gotoStore = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(gotoStore);
                } catch (ActivityNotFoundException e){
                    Uri uri1 = Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName());
                    Intent gotoStore = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(gotoStore);
                }
                break;
        }
    }
}
