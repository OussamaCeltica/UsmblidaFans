package com.devs.celtica.usmbapplication;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context c;
    public static ArrayList<Publication> publications;

    public ArticleAdapter(ArrayList<Publication> publications, Context c) {
        this.c = c;
        this.publications = publications;
    }

    public static class ArticleView extends RecyclerView.ViewHolder {

        public ImageView articleImage;
        public TextView datePubArticle;
        public TextView titreArticle;
        public TextView textArticle;
        public TextView affplus;

        public ArticleView(View v) {
            super(v);
            datePubArticle = (TextView) v.findViewById(R.id.datePubArticle);
            titreArticle = (TextView) v.findViewById(R.id.titreArticle);
            textArticle = (TextView) v.findViewById(R.id.textArticle);
            affplus = (TextView) v.findViewById(R.id.affplus);
            articleImage = (ImageView) v.findViewById(R.id.articleImage);


        }
    }

    public static class ButtPlusView  extends RecyclerView.ViewHolder  {
        public Button plus;
        public ButtPlusView(View v) {
            super(v);
            plus=(Button) v.findViewById(R.id.plus);



        }
    }

    @Override
    public int getItemViewType(int i) {


        switch (publications.get(i).type.toLowerCase()) {

            case "article": return 2;

            default:return 0;
        }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 2: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_article, parent, false);

                //findViewById...

                ArticleView vh = new  ArticleView(v);
                return vh;

            }
            default:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.butt_plus,parent,false);

                //findViewById...

                 ButtPlusView vh = new  ButtPlusView(v);
                return vh;

            }
        }





    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (publications.get(position).type){
            case "article" :{
                if (publications.get(position).descreption.length() > 198) {
                    Log.e("strArt=", publications.get(position).descreption.length() + "");
                    ((ArticleView) holder).affplus.setText("اقرأ اكثر");


                } else {
                    ((ArticleView) holder).affplus.setText("");
                }

                //Picasso.get().load(Accueil.url+((Article) publications.get(position)).url_img).into(((ArticleView) holder).articleImage);
                Glide.with(c)
                        .load(Accueil.url+((Article)publications.get(position)).url_img)
                        .thumbnail(Glide.with(c).load(R.drawable.wait))
                        .apply(new RequestOptions().override(400, 600))
                        .error(Glide.with(c).load(R.drawable.no_img))
                        .timeout(60000)
                        .into(((ArticleView)holder).articleImage);

                (( ArticleView) holder).datePubArticle.setText(publications.get(position).date_pub);
                (( ArticleView) holder).titreArticle.setText(publications.get(position).titre);
                (( ArticleView) holder).textArticle.setText(publications.get(position).descreption);

                ((ArticleView) holder).affplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // affiche mettre le content de article a wrap content ;)..
                        (( ArticleView) holder).affplus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                (( ArticleView) holder).textArticle.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                (( ArticleView) holder).affplus.setVisibility(View.INVISIBLE);
                                notifyItemChanged(position);


                            }
                        });
                    }

                });

            }break;
            default:{
                ((ButtPlusView)holder).plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        publications.remove(position);
                        AfficherArticle.AfficherArticles(ArticleAdapter.this);
                    }
                });

            }break;
        }





    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
