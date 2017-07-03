package com.example.dialogfragment;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button alertButton;

    Button promptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertButton = (Button) findViewById(R.id.button);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        promptButton = (Button) findViewById(R.id.button2);
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialogFragment adf = PromptDialogFragment.newInstance("this is AlertDialogFragment");
                adf.show(getFragmentManager(), "tag");

            }
        });

    }

    public void onDialogDone(String tag, boolean cancelled, CharSequence charSequence) {
        String s = tag + " response with" + charSequence;
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
}
