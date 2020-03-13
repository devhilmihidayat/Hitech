package phonebase.hilmi.hitech.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import phonebase.hilmi.hitech.R;
import phonebase.hilmi.hitech.view.barcode.ScreenDetilBarcode;
import phonebase.hilmi.hitech.models.ListDataBarang;

/**
 * Created by User on 12/03/2020.
 */

public class AdapterBarangRecyler extends RecyclerView.Adapter<AdapterBarangRecyler.ViewHolder> {
    private List<ListDataBarang> list_data;
    private Context context;

    public AdapterBarangRecyler(List<ListDataBarang> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_barang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListDataBarang listData=list_data.get(position);

        Picasso.with(context).load(listData.getUrlImage()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putInt("id", position);
                Intent i = new Intent(context, ScreenDetilBarcode.class);
                i.putExtras(b);
                context.startActivity(i);
            }
        });



        holder.tvNmProduk.setText(listData.getNm_barang());
        holder.tvPrice.setText(listData.getHrg_barang());
        holder.tvUrlImage.setText(listData.getUrlImage());
        holder.txtCrtDate.setText(listData.getCrdDate());


        holder.crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clicked element "+getItemCount() + position + listData.getNm_barang(), Snackbar.LENGTH_LONG).show();
                Bundle b = new Bundle();

                b.putInt("id", position );
                b.putString("nm_barang", position + listData.getNm_barang());
                Intent i = new Intent(context, ScreenDetilBarcode.class);
                i.putExtras(b);
                context.startActivity(i);
            }
        });






    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tvNmProduk;
        private TextView tvPrice;
        private TextView txtFotoProduk;
        private TextView tvUrlImage;
        private TextView txtCrtDate;
        private CardView crd;

        public ViewHolder(View itemView) {
            super(itemView);
            crd=(CardView)itemView.findViewById(R.id.crd_listbarang);
            img=(ImageView)itemView.findViewById(R.id.ivProduk);
            tvNmProduk=(TextView)itemView.findViewById(R.id.txtName);
            tvPrice=(TextView)itemView.findViewById(R.id.txtPrice);
            tvUrlImage=(TextView)itemView.findViewById(R.id.txturlImage);
            txtCrtDate=(TextView)itemView.findViewById(R.id.tvcrtdate);

        }
    }

}