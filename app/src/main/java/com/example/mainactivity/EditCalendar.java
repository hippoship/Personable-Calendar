package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.gridlayout.widget.GridLayout;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mainactivity.Database.MyDatabase;
import com.example.mainactivity.DialogueObjects.DiscardDialogue;
import com.example.mainactivity.DialogueObjects.TimePickerFragment;
import com.example.mainactivity.DialogueObjects.TimePickerFragmentTwo;

import java.util.Calendar;

public class EditCalendar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    // loading calendar
    private int hour;
    private int hourPlusOne;
    private int minute;
    private String AM_PM;

    private int flag = 0;
    private static final int FLAG_START_ONE = 1;
    private static final int FLAG_START_TWO = 2;

    //elements in gridlayout 1
    private ImageView arrowRowOne;
    private Button saveButtonRowOne;

    //elements in gridlayout 2
    private EditText addTitle;

    //elements in gridlayout 3
    private TextView editDateRowOne;
    private TextView editTimeRowOne;
    private TextView editDateRowTwo;
    private TextView editTimeRowTwo;

    //elements in gridlayout 4
    private EditText notificationMessage;

    //elements in gridlayout 5
    private GridLayout locationButton;

    //elements in gridlayout 6
    private ImageView[] imgs = new ImageView[6];
    private ImageView blueCircle, redCircle, yellowCircle, lightBlueCircle, orangeCircle, greenCircle;
    private String chosenColor;

    // add database object
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_calendar);
        //prevents the keyboard from showing up from EditText
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //loading calendar to get current dates / times
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int hourPlusOne = 1 + c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        if(c.get(Calendar.AM_PM) == 1){
            AM_PM = "PM";
        } else{
            AM_PM = "AM";
        }

        //GRID LAYOUT ONE
        saveButtonRowOne = (Button) findViewById(R.id.saveButton);
//        saveButtonRowOne.setOnClickListener(this);
        arrowRowOne = (ImageView) findViewById(R.id.arrow);
        arrowRowOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                discardMessage(v);
            }
        });

        //GRID LAYOUT TWO
        addTitle = (EditText) findViewById(R.id.addTitle);

        // GRID LAYOUT THREE
        editDateRowOne = (TextView) findViewById(R.id.editDateRowOne);
        editTimeRowOne = (TextView) findViewById(R.id.editTimeRowOne);
        editDateRowTwo = (TextView) findViewById(R.id.editDateRowTwo);
        editTimeRowTwo = (TextView) findViewById(R.id.editTimeRowTwo);

        //onClick opens the TIME DIALOGUE ( ROW ONE )
        editTimeRowOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
                flag = FLAG_START_ONE;
            }
        });
        //
        editTimeRowOne.setText(Integer.toString(hour) + ":" + Integer.toString(minute) + AM_PM);

        //onClick opens the TIME DIALOGUE ( ROW TWO )
        editTimeRowTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragmentTwo();
                timePicker.show(getSupportFragmentManager(),"time picker");
                flag = FLAG_START_TWO;
            }
        });
        editTimeRowTwo.setText(Integer.toString(hourPlusOne) + ":" + Integer.toString(minute)+ AM_PM);

        //GRID LAYOUT FOUR
        notificationMessage = (EditText) findViewById(R.id.notificationMessage);

        //GRID LAYOUT FIVE
        locationButton = (GridLayout) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditCalendar.this, AddLocationActivity.class);
                startActivity(i);
            }
        });

        //GRID LAYOUT SIX
        imgs[0]=findViewById(R.id.blueCircle);
        imgs[1]=findViewById(R.id.redCircle);
        imgs[2]=findViewById(R.id.yellowCircle);
        imgs[3]=findViewById(R.id.lightBlueCircle);
        imgs[4]=findViewById(R.id.orangeCircle);
        imgs[5]=findViewById(R.id.greenCircle);

        for(int j = 0; j < imgs.length; j++){
            final int finalJ = j;
            imgs[j].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    chosenColor = "@colors/boxColor" + (finalJ + 1);
                    Toast.makeText(getApplicationContext(), chosenColor, Toast.LENGTH_SHORT).show();
                }
            });
        }

//      instantiate database object
        db = new MyDatabase(this);
    }


    public void addActivity(View view){
        String title = addTitle.getText().toString();
        String dateOne = editDateRowOne.getText().toString();
        String timeOne = editTimeRowOne.getText().toString();
        String dateTwo = editDateRowTwo.getText().toString();
        String timeTwo = editTimeRowTwo.getText().toString();
        String message = notificationMessage.getText().toString();

        Toast.makeText(this, title + dateOne + timeOne + dateTwo + timeTwo + message, Toast.LENGTH_SHORT).show();

        long id = db.insertData(title, dateOne, timeOne, dateTwo, timeTwo, message);

//        Toast.makeText(this, "SEND BUTTON CLICKED", Toast.LENGTH_SHORT).show();
    }



    public void discardMessage(View view){
        DiscardDialogue discardDialogue = new DiscardDialogue();
        discardDialogue.show(getSupportFragmentManager(), "example dialogue");

//        if(SHAREDPREFERENCES SOMETHING, RUN HOME){
//            Intent intent = new Intent (this, MainActivity.class);
//            startActivity(intent);
//        }
    }

    // (USING THE BACK BUTTON)
    // returns the user back to the homepage
//    public void home(View view){
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        if(flag == FLAG_START_ONE) {
            editTimeRowOne.setText(Integer.toString(hour) + ":" + Integer.toString(minute) + AM_PM);
        } else if(flag == FLAG_START_TWO){
            editTimeRowTwo.setText(Integer.toString(hour) + ":" + Integer.toString(minute) + AM_PM);
        }

    }
}