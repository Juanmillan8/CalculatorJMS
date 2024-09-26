package com.example.calculatorjms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDivide, btnMultiply,
            btnSubstract, btnAdd, btnCalculate, btnDelete;

    private TextView tvResult;

    private String typeOfOperation;

    private Integer firstNumber;
    private Integer secondNumber;
    private Integer result;
    private Integer positionTypeOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        btn1.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"1");

        });

        btn2.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"2");

        });

        btn3.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"3");

        });

        btn4.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"4");

        });

        btn5.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"5");

        });

        btn6.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"6");

        });

        btn7.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"7");

        });

        btn8.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"8");

        });

        btn9.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"9");

        });

        btn0.setOnClickListener(v -> {

            tvResult.setText(tvResult.getText()+"0");

        });

        btnAdd.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Integer.parseInt(tvResult.getText().toString());
                typeOfOperation="+";
                tvResult.setText(tvResult.getText()+"+");
            }else if(!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);
                secondNumber=Integer.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));



                if (secondNumber==null){
                    tvResult.setText(firstNumber.toString());
                    tvResult.setText(tvResult.getText()+"+");
                }else{

                    if(typeOfOperation.equalsIgnoreCase("+")){
                        result= firstNumber+secondNumber;
                    }else if (typeOfOperation.equalsIgnoreCase("-")){
                        result= firstNumber-secondNumber;
                    }else if (typeOfOperation.equalsIgnoreCase("*")){
                        result= firstNumber*secondNumber;
                    }else{
                        result= firstNumber/secondNumber;
                    }
                    firstNumber=result;
                    typeOfOperation="-";
                    tvResult.setText(result.toString()+"-");
                }
            }


        });

        btnSubstract.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Integer.parseInt(tvResult.getText().toString());
                typeOfOperation="-";
                tvResult.setText(tvResult.getText()+"-");
            }else if(!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);
                secondNumber= Integer.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));

                if (secondNumber==null){
                    tvResult.setText(firstNumber.toString());
                    tvResult.setText(tvResult.getText()+"-");
                }else{

                    if(typeOfOperation.equalsIgnoreCase("+")){
                        result= firstNumber+secondNumber;
                    }else if (typeOfOperation.equalsIgnoreCase("-")){
                        result= firstNumber-secondNumber;
                    }else if (typeOfOperation.equalsIgnoreCase("*")){
                        result= firstNumber*secondNumber;
                    }else{
                        result= firstNumber/secondNumber;
                    }
                    firstNumber=result;
                    typeOfOperation="-";
                    tvResult.setText(result.toString()+"-");
                }
            }


        });


    }

    /**
     * Este método se encarga de inicializar todos los componentes de la interfaz de usuario y otros
     * objetos necesarios para el funcionamiento de la actividad
     */
    private void initializeComponents(){
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnDivide = findViewById(R.id.btnDivide);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnSubstract = findViewById(R.id.btnSubstract);
        btnAdd = findViewById(R.id.btnAdd);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnDelete = findViewById(R.id.btnDelete);
        tvResult = findViewById(R.id.tvResult);
        typeOfOperation = "";

    }

}