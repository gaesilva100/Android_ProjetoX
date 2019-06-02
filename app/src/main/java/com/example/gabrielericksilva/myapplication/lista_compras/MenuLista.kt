package com.example.gabrielericksilva.myapplication.lista_compras

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.gabrielericksilva.myapplication.R
import kotlinx.android.synthetic.main.activity_menu_lista.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import android.support.v7.app.AlertDialog
import android.widget.EditText
import java.text.NumberFormat
import java.util.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select



class MenuLista : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_lista)

        //Implementação do adaptador
        // val adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)


        val adapter = ProdutoAdapter(this)

        //definindo o adaptador na lista
        list_view_produtos.adapter = adapter


        //Codigo retirado pois o o programa já possui o  setOnItemLongClickListener

        list_view_produtos.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.list_update, null)
            val txt_qtd = view.findViewById<EditText>(R.id.username)
            builder.setView(view)

            builder.show()
        }


        //definição do ouvinte da lista para clicks longos
        list_view_produtos.setOnItemLongClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->


            //SOLUÇÃO PARA ATUALZIAR UM REGISTRO

            val opcoes = listOf("editar", "excluir")

            val opc_editar = 0;
            val opc_excluir = 1;

            selector("O que deseja fazer?", opcoes) { dialogInterface, position ->

                var a = 6
                when (position) {

                //Editar
                    opc_editar -> {

                        alert("Editar").show()
                        val item = adapter.getItem(i)
                        // Chamada da função Atulizar.Produto
                        atulizarProduto(item.id)

                    }

                //Excluir.
                    opc_excluir -> {

                        //buscando o item clicado
                        val item = adapter.getItem(i)

                        //removendo o item clicado da lista
                        adapter.remove(item)

                        //deletando do banco de dados
                        deletarProduto(item.id)

                        toast("item deletado com sucesso")

                    }
                }


            }





            true
        }


        btn_adicionar.setOnClickListener {
            startActivity<CadastroActivity>()


            //val intent = Intent(this, CadastroActivity::class.java)
            //startActivity(intent)

        }

    }

    fun atulizarProduto(idProduto: Int) {

        // Atualiza o banco localmente.

        database.use {
            val updateResult =
                    update("produtos",
                            "nome" to "1")
                            .whereArgs("id = {id}", "id" to idProduto)
                            .exec()


            toast("Update result code is $updateResult")


        }
    }

    fun deletarProduto(idProduto: Int) {

        database.use {


            delete("produtos", "id = {id}", "id" to idProduto)
            super.onResume()

        }

    }

    override fun onResume() {
        super.onResume()

        val adapter = list_view_produtos.adapter as ProdutoAdapter

        database.use{

            select("produtos").exec {

                val parser = rowParser {

                    id: Int, nome: String,
                    quantidade: Int,
                    valor:Double,
                    foto:ByteArray? ->
                    //Colunas do banco de dados


                    //Montagem do objeto Produto com as colunas do banco
                    Produto(id, nome, quantidade, valor, foto?.toBitmap() )
                }


                var listaProdutos = parseList(parser)


                adapter.clear()
                adapter.addAll(listaProdutos)


                val soma = listaProdutos.sumByDouble { it.valor * it.quantidade }

                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txt_total.text = "TOTAL: ${ f.format(soma)}"

            }

        }

    }

 }






