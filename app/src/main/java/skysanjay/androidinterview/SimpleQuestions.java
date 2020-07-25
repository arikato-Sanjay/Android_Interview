package skysanjay.androidinterview;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;

public class SimpleQuestions extends AppCompatActivity implements View.OnClickListener {

    String[] simple_ques,simple_ans;
    TextView questionTV,answersTV,tvTotalLength_yy,tvCurrentIndex_xx;
    Button back_button,ans_button,forward_button;
    int index;
    private static final String default_ans = "Press A button below for answer";
    //variable and object of tts
    TextToSpeech tts_object;
    int result;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);

        // code to add title bar
        LinearLayout question_title = (LinearLayout)findViewById(R.id.question_title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.questions_title_bar);

        Button sound = (Button)findViewById(R.id.soundB);
        Button mute = (Button)findViewById(R.id.muteB);
        TextView cate_tv = (TextView)findViewById(R.id.ques_cate);
        cate_tv.setText("Simple Questions");

        //initialisation for textView
        questionTV = findViewById(R.id.questionsTV);
        answersTV = findViewById(R.id.answerTV);
        tvCurrentIndex_xx = findViewById(R.id.xxTV);
        tvTotalLength_yy = findViewById(R.id.yyTV);

        //initialisation of buttons
        back_button = findViewById(R.id.backB);
        ans_button = findViewById(R.id.answerB);
        forward_button = findViewById(R.id.forwardB);

        //importing string array from values
        simple_ques = getResources().getStringArray(R.array.simple_question);
        simple_ans = getResources().getStringArray(R.array.simple_answer);

        //setting on click listener
        back_button.setOnClickListener(this);
        ans_button.setOnClickListener(this);
        forward_button.setOnClickListener(this);
        sound.setOnClickListener(this);
        mute.setOnClickListener(this);

        //setting values to our variables and 4 tv
        index=0;
        questionTV.setText(simple_ques[index]);
        answersTV.setText(default_ans);
        tvCurrentIndex_xx.setText(String.valueOf(index+1));
        tvTotalLength_yy.setText("/"+ simple_ques.length);

         //tts object and listener initialization
        tts_object = new TextToSpeech(SimpleQuestions.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    result = tts_object.setLanguage(Locale.US);
                else {
                    Toast.makeText(getApplicationContext(),"Feature not Supported",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backB:
                answersTV.setText(default_ans);
                index--;
                if (index==-1)
                    index = simple_ques.length - 1;
                questionTV.setText(simple_ques[index]);
                tvCurrentIndex_xx.setText(String.valueOf(index+1));
                break;
            case R.id.answerB:
                answersTV.setText(simple_ans[index]);
                break;
            case R.id.forwardB:
                answersTV.setText(default_ans);
                index++;
                if (index == simple_ques.length)
                    index = 0;
                questionTV.setText(simple_ques[index]);
                tvCurrentIndex_xx.setText(String.valueOf(index+1));
                break;
            case R.id.soundB:
                if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
                    Toast.makeText(getApplicationContext(),"Feature not Supported",Toast.LENGTH_SHORT).show();
                else
                    if (answersTV.getText().toString().equals(default_ans)){}
                    else     
                    tts_object.speak(simple_ans[index], TextToSpeech.QUEUE_FLUSH,null);
                break;
            case R.id.muteB:
                    if (tts_object != null)
                        tts_object.stop();
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (tts_object != null){
            tts_object.stop();
            tts_object.shutdown();
        }
    }
}
