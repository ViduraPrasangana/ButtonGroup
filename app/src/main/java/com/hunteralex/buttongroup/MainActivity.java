package com.hunteralex.buttongroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hunteralex.buttonsgroup.ButtonGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("First");
        arrayList.add("Middle");
        arrayList.add("Last");

        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("First");

        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("First");
        arrayList2.add("Last");



        ButtonGroup buttonGroup = findViewById(R.id.buttonGroup1);
        buttonGroup.addButtons(arrayList);
        ButtonGroup buttonGroup1 = findViewById(R.id.buttonGroup2);
        buttonGroup1.addButtons(arrayList1);
        ButtonGroup buttonGroup2 = findViewById(R.id.buttonGroup3);
        buttonGroup2.addButtons(arrayList2);

        buttonGroup.setOnItemClickListener(new ButtonGroup.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                switch (position){
                    case 0:
                        Toast.makeText(getBaseContext(),"First",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(),"Middle",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getBaseContext(),"Last",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
