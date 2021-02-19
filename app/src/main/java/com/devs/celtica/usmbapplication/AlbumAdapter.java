package com.devs.celtica.usmbapplication;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celtica on 09/07/18.
 */

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    public static ArrayList<Publication> publications;

    public AlbumAdapter(ArrayList<Publication> publications, Context c) {
        this.c = c;
        this.publications = publications;
    }


    public static class AlbumView  extends RecyclerView.ViewHolder  {
        public ImageView img1;
        public ImageView img2;
        public ImageView img3;
        public TextView datePubAlbum;
        public TextView titreAlbum;
        public TextView textAlbum;
        public TextView affplusAlbum;
        public TextView voirAlbum;

        public AlbumView(View v) {
            super(v);
            datePubAlbum=(TextView)v.findViewById(R.id.datePubAlbum);
            titreAlbum=(TextView)v.findViewById(R.id.titreAlbum);
            textAlbum=(TextView)v.findViewById(R.id.textAlbum);
            affplusAlbum=(TextView)v.findViewById(R.id.affplusAlbum);
            img1=(ImageView)v.findViewById(R.id.img1);
            img2=(ImageView)v.findViewById(R.id.img2);
            img3=(ImageView)v.findViewById(R.id.img3);
            voirAlbum=(TextView) v.findViewById(R.id.voir_album);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_album,parent,false);
        AlbumView vh = new  AlbumView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        (( AlbumView)holder).datePubAlbum.setText(publications.get(position).date_pub);
        (( AlbumView)holder).titreAlbum.setText(publications.get(position).titre);
        (( AlbumView)holder).textAlbum.setText(publications.get(position).descreption);

        if(((Album)publications.get(position)).MyImg.size() == 3){


           // Picasso.get().load(Accueil.url+((Album)publications.get(position)).MyImg.get(0).url_img).into((( AlbumView)holder).img1);

           // Picasso.get().load(Accueil.url+((Album)publications.get(position)).MyImg.get(1).url_img).into((( AlbumView)holder).img2);

           // Picasso.get().load(Accueil.url+((Album)publications.get(position)).MyImg.get(2).url_img).into((( AlbumView)holder).img3);

            Glide.with(c)
                    .load(Accueil.url+((Album)publications.get(position)).MyImg.get(0).url_img)
                    .thumbnail(Glide.with(c).load(R.drawable.wait))
                    .apply(new RequestOptions().override(400, 600))
                    .error(Glide.with(c).load(R.drawable.no_img))
                    .timeout(60000)
                    .into(((AlbumView) holder).img1);

            Glide.with(c)
                    .load(Accueil.url+((Album)publications.get(position)).MyImg.get(1).url_img)
                    .thumbnail(Glide.with(c).load(R.drawable.wait))
                    .apply(new RequestOptions().override(400, 600))
                    .error(Glide.with(c).load(R.drawable.no_img))
                    .timeout(60000)
                    .into(((AlbumView) holder).img2);

            Glide.with(c)
                    .load(Accueil.url+((Album)publications.get(position)).MyImg.get(2).url_img)
                    .thumbnail(Glide.with(c).load(R.drawable.wait))
                    .apply(new RequestOptions().override(400, 600))
                    .error(Glide.with(c).load(R.drawable.no_img))
                    .timeout(60000)
                    .into(((AlbumView) holder).img3);

        }



        if(publications.get(position).descreption.length()>198){
           // Log.e("strArt=",publications.get(position).descreption.length()+"");
            (( AlbumView)holder).affplusAlbum.setText("اقرأ اكثر");


        }else {
            (( AlbumView)holder).affplusAlbum.setText("");
        }

        (( AlbumView)holder).affplusAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // affiche mettre le content de article a wrap content ;)..

                (( AlbumView)holder).textAlbum.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                (( AlbumView)holder).affplusAlbum.setVisibility(View.INVISIBLE);
                notifyItemChanged(position);
            }
        });

        //affichage de tous les images ..
        (( AlbumView)holder).voirAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,VoirAlbum.class);
                in.putExtra("id_album",""+((Album)publications.get(position)).id_album);
                c.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
