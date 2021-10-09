package com.example.takeleaplivetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textEDT;
    private TextView draggableTV;
    private EditText customSizeEDT;
    private Button minusBTN, plusBTN;
    private EditText customRotationEDT;
    private Button rotateLeftBTN, rotateRightBTN;

    //For textview current positions
    static float currentX, currentY;
    static int defaultSizeOfTV = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIAndVariables();
        initializeClickActions();

    }

    private void initializeUIAndVariables() {
        textEDT = findViewById(R.id.idEDTText);
        draggableTV = findViewById(R.id.idTVDraggableTextView);
        minusBTN = findViewById(R.id.idBTNMinus);
        plusBTN = findViewById(R.id.idBTNPlus);
        customSizeEDT = findViewById(R.id.idEDTCustomSize);
        rotateLeftBTN = findViewById(R.id.idBTNRotateLeft);
        rotateRightBTN = findViewById(R.id.idBTNRotateRight);
        customRotationEDT = findViewById(R.id.idEDTCustomRotation);

        currentX = draggableTV.getX();
        currentY = draggableTV.getY();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeClickActions() {

        textEDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                draggableTV.setText(editable.toString());
            }
        });

        draggableTV.setOnTouchListener(new CustomTouchListener());

        minusBTN.setOnClickListener(view -> {
            draggableTV.setTextSize(defaultSizeOfTV -= 2);
            customSizeEDT.setText(String.valueOf(defaultSizeOfTV));

        });

        plusBTN.setOnClickListener(view -> {
            draggableTV.setTextSize(defaultSizeOfTV += 2);
            customSizeEDT.setText(String.valueOf(defaultSizeOfTV));
        });

        customSizeEDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString())) {
                    int newSize = Integer.parseInt(editable.toString());
                    defaultSizeOfTV = newSize;
                    draggableTV.setTextSize(newSize);
                }
            }
        });

        rotateLeftBTN.setOnClickListener(view -> {
            float currentRotation = draggableTV.getRotation() - 2;
            draggableTV.setRotation(currentRotation);
            customRotationEDT.setText(String.valueOf(currentRotation));
        });

        rotateRightBTN.setOnClickListener(view -> {
            float currentRotation = draggableTV.getRotation() + 2;
            draggableTV.setRotation(currentRotation);
            customRotationEDT.setText(String.valueOf(currentRotation));
        });

        customRotationEDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString())) {
                    double newRotation = Double.parseDouble(editable.toString());
                    if (newRotation > 360) {
                        newRotation = 360;
                    }
                    draggableTV.setRotation((float) newRotation);
                }
            }
        });
    }

    //Creating a custom touch listener
    private static class CustomTouchListener implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                //This means we have clicked on the textview and we are still dragging
                // so we are subtraction the current coordinates with th new coordinates
                currentX = view.getX() - motionEvent.getRawX();
                currentY = view.getY() - motionEvent.getRawY();

                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                //Now when are dragging the view then we have to update the x and y coordinates
                // so we are using animate function to make the transaction smooth ans using duration
                // - because we want the effect immediately
                view.animate()
                        .x(motionEvent.getRawX() + currentX)
                        .y(motionEvent.getRawY() + currentY)
                        .setDuration(0)
                        .start();

                return true;
            } else {
                return false;
            }
        }
    }
}