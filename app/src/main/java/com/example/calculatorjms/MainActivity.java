package com.example.calculatorjms;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDivide, btnMultiply,
            btnSubstract, btnAdd, btnCalculate, btnDelete;

    private TextView tvResult;

    private String typeOfOperation;

    private Double firstNumber;
    private Double secondNumber;
    private Double result;
    private Integer positionTypeOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        btn1.setOnClickListener(v -> {

            addDigit("1");

        });

        btn2.setOnClickListener(v -> {

            addDigit("2");

        });

        btn3.setOnClickListener(v -> {

            addDigit("3");

        });

        btn4.setOnClickListener(v -> {

            addDigit("4");

        });

        btn5.setOnClickListener(v -> {

            addDigit("5");

        });

        btn6.setOnClickListener(v -> {

            addDigit("6");

        });

        btn7.setOnClickListener(v -> {

            addDigit("7");

        });

        btn8.setOnClickListener(v -> {

            addDigit("8");

        });

        btn9.setOnClickListener(v -> {

            addDigit("9");

        });

        btn0.setOnClickListener(v -> {

            addDigit("0");

        });

        btnDelete.setOnClickListener(v -> {

            tvResult.setText("");
            firstNumber=null;
            secondNumber=null;
            positionTypeOperation=null;
            typeOfOperation="";
            result=null;

        });

        btnCalculate.setOnClickListener(v -> {

            if(!typeOfOperation.isEmpty()){
                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

                if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){
                    if(isWhole(firstNumber)){
                        Integer integerFirstNumber = (int) firstNumber.doubleValue();
                        tvResult.setText(integerFirstNumber.toString());
                    }else{
                        tvResult.setText(String.format("%.2f", firstNumber));
                    }
                    tvResult.setText(tvResult.getText()+typeOfOperation);
                }else{
                    if (firstNumber<0 && typeOfOperation.equalsIgnoreCase("-")){
                        positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                        if(!tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){
                            secondNumber=Double.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));
                        }

                    }else{
                        secondNumber=Double.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));
                    }

                    if (secondNumber!=null){

                        if(typeOfOperation.equalsIgnoreCase("+")){

                            result=firstNumber+secondNumber;

                        }else if(typeOfOperation.equalsIgnoreCase("-")){

                            result=firstNumber-secondNumber;

                        }else if(typeOfOperation.equalsIgnoreCase("*")){

                            result=firstNumber*secondNumber;

                        }else{
                            if (secondNumber==0){
                                Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_LONG).show();
                                tvResult.setText("");
                            }else{
                                result=firstNumber/secondNumber;
                            }
                        }

                        if(isWhole(result)){
                            Integer integerResult = (int) result.doubleValue();
                            tvResult.setText(integerResult.toString());
                        }else{
                            tvResult.setText(String.format("%.2f", result));
                            result = Double.parseDouble(String.format("%.2f", result));
                            BigDecimal bigDecimal = new BigDecimal(result.toString());
                            tvResult.setText(bigDecimal.toPlainString());

                        }

                        firstNumber=result;
                        secondNumber=null;
                        typeOfOperation="";

                    }
                }
            }
        });

        btnAdd.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Double.parseDouble(tvResult.getText().toString());
                typeOfOperation="+";
                tvResult.setText(tvResult.getText()+"+");
            }else if (!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

                if(firstNumber<0 || tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                    if(typeOfOperation.equalsIgnoreCase("-") && firstNumber<0){
                        positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                    }
                    if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){

                        if(isWhole(firstNumber)){
                            Integer integerFirstNumber = (int) firstNumber.doubleValue();
                            tvResult.setText(integerFirstNumber.toString());
                        }else{
                            BigDecimal bigDecimal = new BigDecimal(firstNumber.toString());
                            String valorFormateado = bigDecimal.toPlainString();
                            tvResult.setText(valorFormateado.toString());
                        }
                        tvResult.setText(tvResult.getText()+"+");
                        typeOfOperation="+";
                    }
                }
            }

        });

        btnSubstract.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Double.parseDouble(tvResult.getText().toString());
                typeOfOperation="-";
                tvResult.setText(tvResult.getText()+"-");
            }else if (!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

                if(firstNumber<0 || tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                    if(typeOfOperation.equalsIgnoreCase("-") && firstNumber<0){
                        positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                    }
                    if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){

                        if(isWhole(firstNumber)){
                            Integer integerFirstNumber = (int) firstNumber.doubleValue();
                            tvResult.setText(integerFirstNumber.toString());
                        }else{
                            BigDecimal bigDecimal = new BigDecimal(firstNumber.toString());
                            String valorFormateado = bigDecimal.toPlainString();
                            tvResult.setText(valorFormateado.toString());
                        }
                        tvResult.setText(tvResult.getText()+"-");
                        typeOfOperation="-";
                    }
                }
            }

        });

        btnMultiply.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Double.parseDouble(tvResult.getText().toString());
                typeOfOperation="*";
                tvResult.setText(tvResult.getText()+"*");
            }else if (!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

                if(firstNumber<0 || tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                    if(typeOfOperation.equalsIgnoreCase("-") && firstNumber<0){
                        positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                    }
                    if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){

                        if(isWhole(firstNumber)){
                            Integer integerFirstNumber = (int) firstNumber.doubleValue();
                            tvResult.setText(integerFirstNumber.toString());
                        }else{
                            BigDecimal bigDecimal = new BigDecimal(firstNumber.toString());
                            String valorFormateado = bigDecimal.toPlainString();

                            tvResult.setText(valorFormateado.toString());
                        }
                        tvResult.setText(tvResult.getText()+"*");
                        typeOfOperation="*";
                    }
                }
            }

        });

        btnDivide.setOnClickListener(v -> {

            if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
                firstNumber = Double.parseDouble(tvResult.getText().toString());
                typeOfOperation="/";
                tvResult.setText(tvResult.getText()+"/");
            }else if (!typeOfOperation.isEmpty()){

                positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

                if(firstNumber<0 || tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                    if(typeOfOperation.equalsIgnoreCase("-") && firstNumber<0){
                        positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                    }
                    if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){

                        if(isWhole(firstNumber)){
                            Integer integerFirstNumber = (int) firstNumber.doubleValue();
                            tvResult.setText(integerFirstNumber.toString());
                        }else{
                            BigDecimal bigDecimal = new BigDecimal(firstNumber.toString());
                            String valorFormateado = bigDecimal.toPlainString();

                            tvResult.setText(valorFormateado.toString());
                        }
                        tvResult.setText(tvResult.getText()+"/");
                        typeOfOperation="/";
                    }
                }
            }
        });
    }

    private boolean isWhole(Double number){

        if(number<0){
            number=number*-1;
        }

        Log.i("NUMBERMETODO", number.toString());

        number=number-number.intValue();

        Log.i("NUMBERRESULTADOMETODO", number.toString());

        if(number==0){
            return true;
        }else{
            return false;
        }
    }

    private void addDigit(String digit){

        if(result!=null && typeOfOperation.isEmpty()){
            if(!isWhole(Double.parseDouble(tvResult.getText().toString()))){
                String numberDecimalScreen = tvResult.getText().toString();

                String decimalpart = numberDecimalScreen.substring(numberDecimalScreen.indexOf('.') + 1);

                Log.i(numberDecimalScreen, decimalpart);
                if(!decimalpart.equalsIgnoreCase(numberDecimalScreen)){
                    Long wholePart = Long.parseLong(numberDecimalScreen.substring(0, numberDecimalScreen.indexOf('.')));
                    if(Double.parseDouble(numberDecimalScreen)<0 && wholePart==0){
                        tvResult.setText(String.valueOf("-"+wholePart)+digit+"."+decimalpart);
                    }else{
                        tvResult.setText(String.valueOf(wholePart)+digit+"."+decimalpart);
                    }

                }else{
                    tvResult.setText(tvResult.getText()+digit);
                }
            }else{
                if(tvResult.getText().toString().equalsIgnoreCase("0.0")){
                    tvResult.setText(digit);
                }else{

                    if(tvResult.getText().toString().substring(tvResult.getText().toString().indexOf('.') + 1).equalsIgnoreCase("0")){
                        tvResult.setText(tvResult.getText().toString().substring(0, tvResult.getText().toString().indexOf('.'))+digit);
                    }else{
                        tvResult.setText(tvResult.getText()+digit);
                    }

                }
            }

        }else{
            tvResult.setText(tvResult.getText()+digit);
        }

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