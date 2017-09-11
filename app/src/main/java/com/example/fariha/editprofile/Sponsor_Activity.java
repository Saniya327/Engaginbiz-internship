package com.example.fariha.editprofile;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class Sponsor_Activity extends Activity {

    CheckBox c1,c2,c3,c4;
    Button save;
    TextView t1,t2,t3,t4;
    ArrayList<String> choices;
    int count;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_);


        choices=new ArrayList<>();


        c1=(CheckBox)findViewById(R.id.checkbox1);
        c2=(CheckBox)findViewById(R.id.checkbox2);
        c3=(CheckBox)findViewById(R.id.checkbox3);
        c4=(CheckBox)findViewById(R.id.checkbox4);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        save=(Button)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                data.putExtra("myList",choices);
                setResult(RESULT_OK, data);
                finish();
            }
        });


    }

    public  void checkBoxClicked(View view){
        count=0;
        count=checkCount(count);

        if(count<=3){
            switch (view.getId()){
                case R.id.checkbox1:
                    if(!c1.isChecked()){

                        c1.setChecked(false);
                        choices.remove(t1.getText().toString());
                    }

                    else{
                        c1.setChecked(true);
                        choices.add(t1.getText().toString());
                    }
                    break;
                case R.id.checkbox2:
                    if(!c2.isChecked()){

                        c2.setChecked(false);
                        choices.remove(t2.getText().toString());
                    }
                    else{
                        c2.setChecked(true);
                        choices.add(t2.getText().toString());
                    }

                    break;
                case R.id.checkbox3:
                    if(!c3.isChecked()){

                        c3.setChecked(false);
                        choices.remove(t3.getText().toString());
                    }
                    else{
                        c3.setChecked(true);
                        choices.add(t3.getText().toString());
                    }
                    break;
                case R.id.checkbox4:
                    if(!c4.isChecked()){

                        c4.setChecked(false);
                        choices.remove(t4.getText().toString());
                    }
                    else{
                        c4.setChecked(true);
                        choices.add(t4.getText().toString());
                    }
                    break;
            }

        }
        else{

            switch (view.getId()){
                case R.id.checkbox1:
                    c1.setChecked(false);

                    break;
                case R.id.checkbox2:
                    c2.setChecked(false);

                    break;
                case R.id.checkbox3:
                    c3.setChecked(false);

                    break;
                case R.id.checkbox4:
                    c4.setChecked(false);
                    break;
            }
        }
    }

    //Checks if number of choices is less than 3 or not
    int checkCount(int count){
        if(c1.isChecked())
            count++;
        if(c2.isChecked())
            count++;
        if(c3.isChecked())
            count++;
        if(c4.isChecked())
            count++;
        return count;
    }
}
