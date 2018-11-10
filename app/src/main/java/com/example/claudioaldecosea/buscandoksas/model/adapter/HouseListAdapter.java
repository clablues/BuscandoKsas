package com.example.claudioaldecosea.buscandoksas.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.claudioaldecosea.buscandoksas.R;
import com.example.claudioaldecosea.buscandoksas.domain.Fotos;
import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.HouseListViewHolder> {
    private final ArrayList<House> mHouseList;
    private LayoutInflater mLayoutInflater;
    private RecycleViewClickListener mClickListener;

    public HouseListAdapter(Context context, ArrayList<House> houseList, RecycleViewClickListener clickListener){
        mLayoutInflater = LayoutInflater.from(context);
        this.mHouseList = houseList;
        this.mClickListener = clickListener;
    }

    @Override
    public HouseListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View houseDetailView = mLayoutInflater.inflate(R.layout.houses_card_item, viewGroup, false);
        return new HouseListViewHolder(houseDetailView, mClickListener);
    }

    @Override
    public void onBindViewHolder(HouseListViewHolder houseListViewHolder, int i) {
        House currentHouse = mHouseList.get(i);
        houseListViewHolder.barrio.setText(currentHouse.getInmuebleBarrio());
        houseListViewHolder.precio.setText("$U " + currentHouse.getInmueblePrecio());
        houseListViewHolder.cantDormitorios.setText(currentHouse.getInmuebleCantDormitorio());

        ArrayList<Fotos> imagenes = currentHouse.getFotos();

        if (imagenes != null && imagenes.size() > 0 ) {
            Picasso.get().load(imagenes.get(0).getInmuebleImagenUrl()).into(houseListViewHolder.houseHomeImage);
        }else{
            Picasso.get().load(R.drawable.no_image).into(houseListViewHolder.houseHomeImage);
        }
    }

    @Override
    public int getItemCount() {
        return this.mHouseList.size();
    }

    public class HouseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView barrio;
        public final TextView precio;
        public final TextView cantDormitorios;
        public final ImageView houseHomeImage;
        private RecycleViewClickListener clickListener;

        public HouseListViewHolder(View itemView, RecycleViewClickListener clickListener) {
            super(itemView);
            this.barrio = itemView.findViewById(R.id.txt_list_barrio);
            this.precio = itemView.findViewById(R.id.txt_list_precio);
            this.cantDormitorios = itemView.findViewById(R.id.txt_list_cant_dormitorios);
            this.houseHomeImage = itemView.findViewById(R.id.house_home_image_view);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClick(v,getLayoutPosition());

        }
    }
}
