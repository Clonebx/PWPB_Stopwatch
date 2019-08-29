package com.example.pwpb_stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView labelTime;
    Button btnStart, btnPause, btnReset, btnSave;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;
    ListView listView;
    String[] ListElements = new String[]{};
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
        setComponent();

        handler = new Handler() ;
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,

                ListElementsArrayList

        );
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                eventBtnStart();
                break;
            case R.id.btnStop:
                eventBtnStop();
                break;
            case R.id.btnreset:
                eventBtnReset();
                break;
            case R.id.btnSimpan:
                eventBtnSimpan();
                break;

        }
    }

    private void initComponent() {
        labelTime = findViewById(R.id.labelTime);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnreset);
        btnSave = findViewById(R.id.btnSimpan);
        listView = findViewById(R.id.listview1);
    }

    private void setComponent() {
        labelTime.setText("00:00:00");
        btnStart.setText("Mulai");
        btnStart.setOnClickListener(this);
        btnPause.setText("Berhenti");
        btnPause.setOnClickListener(this);
        btnReset.setText("Reset");
        btnReset.setOnClickListener(this);
        btnSave.setText("Simpan putaran");
        btnSave.setOnClickListener(this);
    }

    private void eventBtnStart() {
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        btnReset.setEnabled(false);
    }

    private void eventBtnStop() {
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
        btnReset.setEnabled(true);
    }

    private void eventBtnReset() {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;
        labelTime.setText("00:00:00");
        ListElementsArrayList.clear();
        adapter.notifyDataSetChanged();
    }

    private void eventBtnSimpan() {
        ListElementsArrayList.add(labelTime.getText().toString());

        adapter.notifyDataSetChanged();
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            labelTime.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }
    };
}
