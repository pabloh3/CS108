package edu.cs108.stanford.colorpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeColor (View view) {
        //find all the elements
        TextView textView = findViewById(R.id.textView);
        SeekBar red = findViewById(R.id.red);
        SeekBar green = findViewById(R.id.green);
        SeekBar blue = findViewById(R.id.blue);
        View color = findViewById(R.id.color);

        //extract the progress of each bar as an int
        int redColor = red.getProgress();
        int greenColor = green.getProgress();
        int blueColor = blue.getProgress();

        //set the text to the right number and change screen color
        textView.setText("Red: " + redColor + ", Green: " + greenColor + ", Blue: "+blueColor);
        color.setBackgroundColor(Color.rgb(redColor,greenColor,blueColor));
    }
}
