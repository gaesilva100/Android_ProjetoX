package com.example.gabrielericksilva.myapplication.calculadora


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.gabrielericksilva.myapplication.R
import java.net.URL
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.alert
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*

class Calculadora : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"
    var cotacaoBitcoin: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_1)

        buscarCotacao()

        //listener do botão calcular
        btn_calcular.setOnClickListener {calcular()
        }
    }

    fun buscarCotacao() {

        doAsync {
            //Acessar a API e buscar seu resultado
            val resposta = URL(API_URL).readText()
            cotacaoBitcoin =
                    JSONObject(resposta)
                            .getJSONObject("ticker")
                            .getDouble("last")
            val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val cotacaoFormatada = f.format(cotacaoBitcoin)

            uiThread {
                txt_cotacao.setText("$cotacaoFormatada")
            }
        }
    }

    fun calcular(){

        if(txt_valor.text.isEmpty()) {
            txt_valor.error = "Preencha um valor"
            return
        }
        //valor digitado pelo usuário
        val valor_digitado = txt_valor.text.toString()
                .replace(",", ".")
                .toDouble()
        //calculando o resultado
        //caso o valor cotação seja maior que zero, efetuamos o cálculo
        //caso contrário devolvemos 0
        val resultado = if(cotacaoBitcoin > 0) valor_digitado / cotacaoBitcoin else 0.0
        //atualizando a TextView com o resultado formatado com 8 casas decimais
        txt_qtd_bitcoins.text = "%.8f".format(resultado)
        alert("O seu Resultado é $resultado").show()


    }
}
