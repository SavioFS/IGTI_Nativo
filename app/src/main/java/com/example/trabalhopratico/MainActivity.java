package com.example.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edt_salBruto;
    EditText edt_numDependentes;
    EditText edt_outrosDescontos;
    Button btn_button_calcular;


    Double salBruto;
    Integer numDependentes;
    Double outrosDescontos;
    Double descontoInss;
    Double irrf;
    Double salLiquido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_salBruto = (EditText) findViewById(R.id.edit_salBruto);
        edt_numDependentes = (EditText) findViewById(R.id.edit_numDependentes);
        edt_outrosDescontos = (EditText) findViewById(R.id.edit_outrosDescontos);
        btn_button_calcular = (Button) findViewById(R.id.button_calcular);

        btn_button_calcular.setOnClickListener(new View.OnClickListener() {
                                                   public void onClick(View v) {
                                                       salBruto = Double.parseDouble(edt_salBruto.getText().toString());
                                                       numDependentes = Integer.parseInt(edt_numDependentes.getText().toString());
                                                       outrosDescontos = Double.parseDouble(edt_outrosDescontos.getText().toString());
                                                       descontoInss = calculoDescontoInss(salBruto);
                                                       irrf = calculoDescontoIRRF(salBruto, descontoInss, numDependentes);
                                                       salLiquido = Liquido(salBruto, descontoInss, irrf, outrosDescontos);
                                                       System.out.println(salLiquido);
                                                   }

                                               }
        );

    }


    protected void soma(Integer p1, Integer p2) {
        System.out.println(p1 + p2);
    }

    //Até um salário mínimo (R$ 1.045,00) 7,5% -
    //De R$ 1.045,01 até 2.089,60 9% R$ 15,67
    //De R$ 2.089,61 até 3.134,40 12% R$ 78,36
    //De R$ 3.134,41 até 6.101,06 14% R$ 141,05

    private Double calculoDescontoInss(Double salBruto) {
        if (salBruto < 1045) {
            return salBruto * 0.075;
        } else if (salBruto > 1045 && salBruto <= 2089.60) {
            return (salBruto * 0.09) - 15.67;
        } else if (salBruto > 2089.60 && salBruto <= 3134.40) {
            return (salBruto * 0.12) - 78.36;
        } else if (salBruto > 3134.40 && salBruto <= 6101.06) {
            return (salBruto * 0.14) - 141.05;
        } else {
            return 713.10;
        }
    }

    /*
    O imposto de renda retido na fonte (IRRF) tem como base para cálculo o seguinte valor:
    Base de cálculo = salário bruto – contribuição para o INSS – número de dependentes x 189,59
    A partir desta base de cálculo, a seguinte tabela é aplicada:
    Base de cálculo Alíquota Dedução
    Até R$ 1.903,98 0 -
    De R$ 1.903,99 até R$ 2.826,65 7,5% R$ 142,80
    De R$ 2.826,66 até R$ 3.751,05 15,0% R$ 354,80
    De R$ 3.751,06 até R$ 4.664,68 22,5% R$ 636,13
    Acima de R$ 4.664,68 27,5% R$ 869,36
    */

    private Double calculoDescontoIRRF(Double salBruto, Double descontoInss, Integer numDependentes) {
        Double baseDeCalculo;
        baseDeCalculo = salBruto - descontoInss - (numDependentes * 189.59);

        Double desconto;

        if (baseDeCalculo < 1903.98) {
            desconto = Double.valueOf(0);
        } else if (baseDeCalculo > 1903.99 && baseDeCalculo <= 2826.65) {
            desconto = (baseDeCalculo * 0.075) - 142.80;
        } else if (baseDeCalculo > 2826.66 && baseDeCalculo <= 3751.05) {
            desconto = (baseDeCalculo * 0.15) - 354.80;
        } else if (baseDeCalculo > 3751.06 && baseDeCalculo <= 4664.68) {
            desconto = (baseDeCalculo * 0.225) - 636.13;
        } else {
            desconto = (baseDeCalculo * 0.275) - 869.36;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(desconto));
    }

    protected Double Liquido(Double salarioBruto, Double Inss, Double Imposto, Double outros){
        return salarioBruto - Inss - Imposto - outros;
    }
}