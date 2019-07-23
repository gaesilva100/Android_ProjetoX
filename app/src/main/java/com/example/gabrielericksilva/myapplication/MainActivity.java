package com.example.gabrielericksilva.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //vars
    private static final String TAG = "MainActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mNames1 = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();

        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            //.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderWithDifferentPieceColor());


            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener(){
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.

                        }
                    });
        }
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://img.timesnownews.com/story/1542978652-1518076824-Bitcoin.jpg?d=600x450");
        mNames.add("Calculadora de Bitcon");
        mNames1.add("Conversor de R$ para Bitcon Kotlin/Anko");

        mImageUrls.add("https://p2.trrsf.com/image/fget/cf/460/0/images.terra.com/2018/04/06/lista-de-compras-no-papel.jpg");
        mNames.add("Lista de Compras");
        mNames1.add("Lista em SQLITE - Kotlin/Anko");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Projeto 3");
        mNames1.add("Projeto em construção");

       initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames,mImageUrls,mNames1);
       // RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames,mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
















