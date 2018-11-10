package com.example.claudioaldecosea.buscandoksas.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.claudioaldecosea.buscandoksas.R;
import com.example.claudioaldecosea.buscandoksas.domain.Fotos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseDetailAdapter extends RecyclerView.Adapter<HouseDetailAdapter.HouseDetailViewHolder> {

    private ArrayList<Fotos> imageUrlList;
    private LayoutInflater mLayoutInflater;
    private RecycleViewClickListener mClickListener;

    public HouseDetailAdapter(Context context, ArrayList<Fotos> imageUrlList, RecycleViewClickListener clickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        this.imageUrlList = imageUrlList;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public HouseDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mWorldWonderView = mLayoutInflater.inflate(R.layout.card_view_house_detail_image, viewGroup, false);
        return new HouseDetailViewHolder(mWorldWonderView, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseDetailViewHolder houseDetailViewHolder, int i) {
        if(imageUrlList.size() > 0) {
            String imageUrl = imageUrlList.get(i).getInmuebleImagenUrl();
            Picasso.get().load(imageUrl).into(houseDetailViewHolder.imagen);
        }else{
            Picasso.get().load(R.drawable.no_image).into(houseDetailViewHolder.imagen);
        }
    }

    @Override
    public int getItemCount() {
        if (imageUrlList.size() > 0) {
            return imageUrlList.size();
        }else{
            //retorno 1 para usar la imagen por defecto
            return 1;
        }
    }

    public class HouseDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecycleViewClickListener clickListener;
        ImageView imagen;

        public HouseDetailViewHolder(View itemView, RecycleViewClickListener clickListener) {
            super(itemView);
            imagen = itemView.findViewById(R.id.house_detail_image_view);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
