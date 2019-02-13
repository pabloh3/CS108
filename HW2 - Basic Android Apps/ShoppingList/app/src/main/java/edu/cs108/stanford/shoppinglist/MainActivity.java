package edu.cs108.stanford.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //clears shopping least
    public void clearList (View view) {
        TextView textView = findViewById(R.id.textOutput);
        textView.setText("");
    }

    //add item to shopping list
    public void addItem (View view) {
        //find all elements
        TextView textView = findViewById(R.id.textOutput);
        EditText editText = findViewById(R.id.editText);
        //extract text from list
        CharSequence list = textView.getText();
        //if list is empty
        if (list.length() == 0){
            //set the list to edit text and empty the edit text
            textView.setText(editText.getText());
            editText.setText("");
        }
        else {
            //add new item to the list
            CharSequence temp = list + "\n" + editText.getText();
            textView.setText(temp);
            editText.setText("");

        }
    }

}
