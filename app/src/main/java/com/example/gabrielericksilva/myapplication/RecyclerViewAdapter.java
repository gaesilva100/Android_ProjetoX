package com.example.gabrielericksilva.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gabrielericksilva.myapplication.calculadora.Calculadora;
import com.example.gabrielericksilva.myapplication.lista_compras.MenuLista;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageNames1 = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images,ArrayList<String> imageNames1 ) {
        mImageNames = imageNames;
        mImageNames1 = imageNames1;
        mImages = images;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.imageName1.setText(mImageNames1.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

                switch(position) {
                    case 0:
                        Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, Calculadora.class);
                        intent.putExtra("image_url", mImages.get(position));
                        intent.putExtra("image_name", mImageNames.get(position));
                        intent.putExtra("image_name1", mImageNames1.get(position));
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext, MenuLista.class);
                        mContext.startActivity(intent1);
                        Toast.makeText(mContext, "Nada não", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView imageName1;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            imageName1 = itemView.findViewById(R.id.image_name1);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}









