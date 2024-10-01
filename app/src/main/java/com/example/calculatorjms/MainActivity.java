package com.example.calculatorjms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    //Declaración de los botones, textView donde se muestra la información por pantalla y variables necesarias para el cálculo
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDivide, btnMultiply,
            btnSubstract, btnAdd, btnCalculate, btnDelete;
    private TextView tvResult;
    private String typeOfOperation;
    private Double firstNumber;
    private Double secondNumber;
    private Double result;
    private Integer positionTypeOperation;
    private Integer integerFirstNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los componentes de la interfaz y otros objetos necesarios
        initializeComponents();

        //Botones numéricos donde se añadirá el dígito que hayamos seleccionado en el lugar de la pantalla donde le corresponda
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

        //Botón para borrar el contenido de la calculadora y reiniciar variables
        btnDelete.setOnClickListener(v -> {
            delete();
        });

        //Botón para realizar la operación que hayamos seleccionado y mostrar su resultado por pantalla
        btnCalculate.setOnClickListener(v -> {
            calculate();
        });

        //Botones de operaciones (+, -, *, ÷) donde elegiremos la operación que queremos realizar
        btnAdd.setOnClickListener(v -> {
            chooseTypeOfOperation("+");
        });

        btnSubstract.setOnClickListener(v -> {
            chooseTypeOfOperation("-");
        });

        btnMultiply.setOnClickListener(v -> {
            chooseTypeOfOperation("*");
        });

        btnDivide.setOnClickListener(v -> {
            chooseTypeOfOperation("÷");
        });
    }

    // ======================== Métodos de la calculadora ========================

    /**
     * Método que verifica si un número es entero
     * @param number Número a verificar
     * @return true si es entero, false si tiene decimales
     */
    private boolean isWhole(Double number){

        //Primero convertimos a positivo el número que pasamos al método en el caso de que sea negativo, esto lo hacemos para poder restar su parte entera
        //entre sí misma y posteriormente poder ver si aun así dicha variable sigue conteniendo un número mayor a 0, si dicha variable contiene un número
        //mayor a 0 después de restar su parte entera significaría que el número pasado al método era un número decimal, ya que todavía seguiría conteniendo
        //los dígitos de después de la coma, por ejemplo, si para el número 2,78 restamos solo su parte entera quedaría 0,78, no es igual a 0, por lo que
        //significaría que es un número decimal, pero si para el número 3,00 le restamos su parte entera quedaría 0,00 por lo que significaría que es un
        //número entero
        if(number<0){
            number=number*-1;
        }

        //Al número pasado al método le restamos su parte entera
        number=number-number.intValue();

        //Si el resultado es 0 devolvemos true indicando que es un número entero, de lo contrario devolvemos false
        if(number==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Añade un dígito a la pantalla
     * @param digit Dígito a añadir
     */
    private void addDigit(String digit){

        //Si la variable result no es nula significa que vamos a añadir dígitos después de haber realizado ya un cálculo, esto hay que tenerlo en cuenta,
        //ya que debido a eso, el número que podemos tener ya por pantalla podría ser un decimal y tendríamos que comprobar dónde colocar el siguiente dígito,
        //también si la variable typeOfOperation está vacía significa que el usuario todavía no ha elegido un tipo de operación, por lo que estaríamos añadiendo
        //dígitos para el firstNumber, teniendo eso en cuenta vamos a hacer lo que hay dentro de este if
        if(result!=null && typeOfOperation.isEmpty()){
            if(!isWhole(Double.parseDouble(tvResult.getText().toString()))){
                //Si el número por pantalla es decimal lo almacenamos en un String
                String numberDecimalScreen = tvResult.getText().toString();

                //Posteriormente sacamos la parte decimal de dicho numero y la almacenamos en otro string
                String decimalpart = numberDecimalScreen.substring(numberDecimalScreen.indexOf('.') + 1);

                //Comprobamos que la parte decimal no sea igual al número decimal que había por pantalla, ya que algunos números se detectaban como si fuesen
                //decimales aunque no lo fuesen y se cogía como parte decimal al número entero mostrado por pantalla, por ello comprobamos que la parte
                //decimal no sea la misma que el número mostrado por pantalla, ya que por ejemplo, sí se detecta como un número decimal y en realidad no lo es
                //la variable decimalPart contendrá el número 498 y la variable numberDecimalScreen contendrá el número 498 también, por lo tanto es un
                //número entero, pero si en realidad es un número decimal la variable decimalPart contendrá el número 23 y la variable numberDecimalScreen
                //contendrá el número 89.23 por ejemplo
                if(!decimalpart.equalsIgnoreCase(numberDecimalScreen)){

                    //Si el número por pantalla en realidad es un decimal almacenamos ahora su parte entera, osea, la parte de antes de la coma
                    Long wholePart = Long.parseLong(numberDecimalScreen.substring(0, numberDecimalScreen.indexOf('.')));

                    //Había un problema en los casos en los que el número con el que tratábamos era un -0 seguido de cualquier dígito (por ejemplo -0,67), dicho problema
                    //era que a la hora de almacenar la parte entera de este número (-0) se almacenaba sin el signo -, por lo tanto, si queríamos añadir más dígitos a
                    //ese tipo de números, el signo - se eliminaba, es por ello que antes de añadir el dígito seleccionado comprobamos si el número por pantalla
                    //es menor de 0 y si su parte entera es 0
                    if(Double.parseDouble(numberDecimalScreen)<0 && wholePart==0){
                        //Si el número es menor de 0 y su parte entera es 0 haremos lo siguiente
                        //A la hora de mostrar el número por pantalla añadiéndole el nuevo dígito, también tenemos en cuenta el signo -, por lo tanto, primero añadimos al
                        //texto por pantalla el signo, posteriormente la parte entera, luego el nuevo dígito, la coma y por último la parte decimal
                        tvResult.setText(String.valueOf("-"+wholePart)+digit+"."+decimalpart);
                    }else{
                        //De lo contrario, si el número es mayor de 0 o su parte entera no es 0, simplemente añadimos la parte entera, el nuevo dígito,
                        //la coma y la parte decimal, sin signo ni nada
                        tvResult.setText(String.valueOf(wholePart)+digit+"."+decimalpart);
                    }
                }else{
                    //Si el número mostrado por pantalla en realidad es un entero, simplemente añadimos el dígito que hayamos seleccionado al final
                    tvResult.setText(tvResult.getText()+digit);
                }
            }else{
                //Otro problema que había era a la hora en la que por ejemplo dividíamos un número pequeño por uno más mayor y daba como resultado 0.0,
                //en estos casos el metodo isWhole lo detecta como entero que si a 0.0 le quitamos 0.0 da 0, por lo tanto si añadíamos un nuevo dígito,
                //este se añadía al final, por ejemplo, si queríamos añadir el dígito 8 se quedaba así 0.08, para que esto no suceda hemos colocado este if,
                //aquí indicamos que si el número mostrado por pantalla es igual a 0.0 simplemente se sustituya dicho número por el nuevo dígito que hayamos
                //seleccionado
                if(tvResult.getText().toString().equalsIgnoreCase("0.0")){
                    tvResult.setText(digit);
                }else{

                    //Hay veces en las que tenemos un número cuya parte decimal es 0, en este caso el método isWhole tambien lo detecta como entero ya
                    //que si a 78.0 le quitamos 78 da 0, aun así sigue teniendo parte decimal, esto lo controlamos en este if, aquí indicamos que si el
                    //dígito después de la coma es 0 lo que tenemos que hacer es obtener solo la parte entera y seguidamente añadir el nuevo dígito después
                    if(tvResult.getText().toString().substring(tvResult.getText().toString().indexOf('.') + 1).equalsIgnoreCase("0")){
                        tvResult.setText(tvResult.getText().toString().substring(0, tvResult.getText().toString().indexOf('.'))+digit);
                    }else{
                        //Si el texto en pantalla no tiene ninguna parte decimal simplemente añadimos el nuevo dígito al final del tvResult
                        tvResult.setText(tvResult.getText()+digit);
                    }
                }
            }
        }else{
            //Si no hemos realizado ningún cálculo previo o si ya hemos seleccionado el tipo de operación que queremos realizar simplemente colocamos
            //el dígito seleccionado al final de la pantalla
            tvResult.setText(tvResult.getText()+digit);
        }

    }

    /**
     * Este método elimina el contenido de la pantalla y resetea las variables
     */
    private void delete(){

        tvResult.setText("");
        firstNumber=null;
        secondNumber=null;
        positionTypeOperation=null;
        typeOfOperation="";
        result=null;
    }

    /**
     * Método que realiza el cálculo de los números que el usuario ha insertado
     */
    private void calculate(){

        //Primero debemos comprobar que el usuario haya seleccionado un tipo de operación, ya que de lo contrario no se podría saber qué operación realizar
        if(!typeOfOperation.isEmpty()){

            //Si el usuario ha elegido un tipo de operación buscamos en el tvResult la posición donde aparece el operador que el usuario haya seleccionado
            //(por ejemplo, "+" o "-")
            positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

            //Aquí se comprueba si después del operador no hay ningún número, la forma de comprobarlo es viendo que hay después del operador utilizando
            //substring, si no hay nada significa que no hay un segundo número con el que realizar la operación, también comprobamos si se encontró el
            //operador en el tvResult (positionTypeOperation==-1), si esto sucede mostramos un mensaje por pantalla indicando que debemos introducir un
            //segundo número para poder llevar a cabo la operación
            if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                Toast.makeText(this, "Debes introducir un segundo número para poder llevar a cabo la operación", Toast.LENGTH_LONG).show();

            }else{
                //Un problema que puede haber aquí es cuando tenemos un primer número menor de 0 y el tipo de operación sea restar, antes conseguimos saber
                //que no hay un segundo número gracias a mirar lo que hay después del signo de operación, por ejemplo, si tenemos lo siguiente por pantalla
                //"67+" y miramos que después del signo de operación no hay nada entonces mostramos un mensaje de error por pantalla, pero hay casos en los
                //que podemos tener un número menor de 0 y el tipo de operación sea restar, por ejemplo "-82-" en estos casos si buscamos en el texto por
                //pantalla el primer signo de operación encontraremos el signo - del primer número (-82), si esto sucede y posteriormente miramos lo que hay
                //después del signo de operación veremos que hay lo siguiente (82-), entonces el programa pensará que hay un segundo número después del signo
                //de operación aunque en realidad no sea así, esto lo controlamos en este if donde nos introduciremos en el caso de tener un primer número
                //menor de 0 y el tipo de operación sea restar
                if (firstNumber<0 && typeOfOperation.equalsIgnoreCase("-")){

                    //Aquí lo que hacemos es buscar la siguiente aparición del tipo de operación - empezando desde la posición del primer tipo de operación encontrado
                    //osea, si encontramos el signo - pero hay un número menor de 0 significa que no hemos encontrado la posición real del tipo de operación, por lo tanto
                    //seguimos buscando la posición del siguiente tipo de operación hasta encontrar la posición del tipo de operación que en realidad estamos buscando
                    positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);


                    //Una vez encontrada la posición real del tipo de operación comprobamos si hay un segundo número después del signo de operación, si lo hay
                    //lo almacenamos en la variable secondNumber de lo contrario no haremos nada
                    if(!tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){
                        secondNumber=Double.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));
                    }
                }else{
                    //Si llegamos a aquí significa que después del primer signo de operación encontrado hay un segundo número, por lo tanto buscamos el segundo número
                    //que hay después del signo de operación y lo almacenamos en la variable secondNumber
                    secondNumber=Double.valueOf(tvResult.getText().toString().substring(positionTypeOperation + 1));
                }
                //Si hemos conseguido obtener el segundo número podremos llevar a cabo la operación, aqui sumaremos, restaremos, multiplicaremos o dividiremos
                //dependiendo del tipo de operación que el usuario haya seleccionado, se calcula el resultado y se almacena en la variable result
                if (secondNumber!=null){
                    if(typeOfOperation.equalsIgnoreCase("+")){
                        result=firstNumber+secondNumber;
                    }else if(typeOfOperation.equalsIgnoreCase("-")){
                        result=firstNumber-secondNumber;
                    }else if(typeOfOperation.equalsIgnoreCase("*")){
                        result=firstNumber*secondNumber;
                    }else{
                        //Si queremos dividir algo entre 0 mostramos un mensaje de error por pantalla y eliminamos el texto que había en el tvResult, de lo contrario
                        //si queremos realizar una división válida la llevaremos a cabo y almacenaremos el resultado en la variable result
                        if (secondNumber==0){
                            Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_LONG).show();
                            tvResult.setText("");
                        }else{
                            result=firstNumber/secondNumber;
                        }
                    }

                    //Si el secondNumber es diferente de 0 significa que la operación se ha podido llevar a cabo, por lo tanto haremos lo siguiente
                    if(secondNumber!=0){

                        //Aquí se llama al método isWhole para verificar si el resultado es un entero, si es un entero se convierte
                        //el número Double a int y posteriormente se sustituye lo que hay en el tvResult por dicho número, por ejemplo, si la variable
                        //result contiene un 5.0 se convierte a 5 pasándolo a int, de esta forma se mostrará por pantalla como "5"
                        if(isWhole(result)){
                            Integer integerResult = (int) result.doubleValue();
                            tvResult.setText(integerResult.toString());
                        }else{
                            //De lo contrario, si el resultado no es un entero dicho número lo almacenamos primero con solo dos decimales, posteriormente se
                            //convierte a BigDecimal, esto se hace para manejar los decimales de forma precisa y evitar errores de representación y de redondeo
                            //que pueden ocurrir con los tipo Double, luego de convertir el número a BigDecimal lo convertimos a una cadena de texto sin notación
                            //científica y se sustituye lo que hay en el tvResult por dicho número, con esto conseguimos mostrar el primer número de forma
                            //correcta sin ningún tipo de error
                            result = Double.parseDouble(String.format("%.2f", result));
                            BigDecimal bigDecimalResult = new BigDecimal(result.toString());
                            tvResult.setText(bigDecimalResult.toPlainString());
                        }

                        //Una vez realizada la operación almacenaremos el resultado en la variable firstNumber para así poder seguir posteriormente realizando otras
                        //operaciones con el resultado de esta operación, posteriormente la variable result pasa a ser nula para que no haya ningún error en el
                        //resto del código y el tipo de operación que había almacenado pasa a estar vacío
                        firstNumber=result;
                        secondNumber=null;
                        typeOfOperation="";

                    }


                }
            }
        }
    }

    /**
     * Escoge el tipo de operación (suma, resta, multiplicación o división)
     * @param operationType Tipo de operación a realizar
     */
    private void chooseTypeOfOperation(String operationType){

        //Primero comprobamos que todavia no hayamos seleccionado ningún tipo de operacion y de que haya un digito en la pantalla
        if (typeOfOperation.isEmpty() && !tvResult.getText().toString().isEmpty()){
            //Almacenamos el tipo de operación seleccionada y el número que había por pantalla, también mostramos el tipo de operación por pantalla
            firstNumber = Double.parseDouble(tvResult.getText().toString());
            typeOfOperation=operationType;
            tvResult.setText(tvResult.getText()+operationType);

            //Si ya hemos seleccionado un tipo de operación haremos lo siguiente
        }else if (!typeOfOperation.isEmpty()){

            //Almacenamos en una variable la posición por pantalla del signo de operación
            positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation);

            //Si el primer número es menor de 0, o después del signo de operación no hay nada o si no se ha encontrado ningún signo de operación nos introduciremos en este if
            if(firstNumber<0 || tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("") || positionTypeOperation==-1){

                //Al igual que en el método calculate aqui tambien pueden haber problemas cuando el tipo de operación seleccionada es - y el primer número es
                //menor de 0, en dicho metodo calculate ya explicamos el porque es tan importante tener esto tan en cuenta y poder controlarlo
                if(typeOfOperation.equalsIgnoreCase("-") && firstNumber<0){

                    //Buscamos la siguiente aparición del tipo de operación - y almacenamos su posición
                    positionTypeOperation = tvResult.getText().toString().indexOf(typeOfOperation, positionTypeOperation+1);
                }

                //Si no hay nada después del tipo de operación seleccionado (por ejemplo, tenemos 7+) y el usuario selecciona otro tipo de operación significa
                //que este usuario quiere cambiar el tipo de operación que previamente había seleccionado, por lo tanto haremos lo siguiente
                if(tvResult.getText().toString().substring(positionTypeOperation + 1).equalsIgnoreCase("")){

                    //Aquí hacemos lo mismo que en el método calculate, así que no hay que explicar mucho, simplemente aquí se convierte
                    //el firstNumber de Double a int comprobando previamente que dicho número sea entero con el método isWhole para así conseguir mostrar
                    //por pantalla el número de forma correcta, a la hora de mostrar dicho número se sustituye lo que hay en el tvResult, de
                    //esta forma también habremos eliminado el signo de operación que había para posteriormente mostrar el nuevo
                    //signo de operación que el usuario haya elegido
                    if(isWhole(firstNumber)){
                        integerFirstNumber = (int) firstNumber.doubleValue();
                        tvResult.setText(integerFirstNumber.toString());
                    }else{
                        //Esto también lo hacemos en el método calculate, si el primer número no es un entero lo convertimos a BigDecimal con el objetivo de mostrar dicho
                        //número de forma correcta por pantalla, luego convertimos el número a una cadena de texto sin notación científica y se sustituye lo que hay en el
                        //tvResult por dicho número, así mostramos el número de forma correcta habiendo eliminado también el signo de operación por pantalla que el usuario
                        //había seleccionado previamente
                        BigDecimal bigDecimalFirstNumber = new BigDecimal(firstNumber.toString());
                        tvResult.setText(bigDecimalFirstNumber.toPlainString());
                    }
                    //Ahora mismo solo tenemos por pantalla el primer número sin ningún signo de operación ni nada, en este momento lo que nos queda es
                    //colocar al final del tvResult el nuevo signo de operación que el usuario haya seleccionado
                    tvResult.setText(tvResult.getText()+operationType);

                    //También almacenamos en la variable typeOfOperation el tipo operación que el usuario haya seleccionado para poder saber en cualquier momento
                    //que operación se está realizando
                    typeOfOperation=operationType;
                }
            }
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