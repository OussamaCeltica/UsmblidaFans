package com.devs.celtica.usmbapplication;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by celtica on 25/06/18.
 */



public class PublicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   Context c;
   public static ArrayList<Publication> publications;

    public PublicationAdapter(ArrayList<Publication> publications, Context c) {
        this.c=c;
        this.publications=publications;
    }

    public static class InfoJourView extends RecyclerView.ViewHolder  {
        public TextView infoJour;
        public TextView dateInfoJour;
        public InfoJourView(View v) {
            super(v);
            infoJour=(TextView)v.findViewById(R.id.infoJour);
            dateInfoJour=(TextView)v.findViewById(R.id.dateInfoJour);


        }
    }

    public static class ArticleView  extends RecyclerView.ViewHolder  {

        public ImageView articleImage;
        public TextView datePubArticle;
        public TextView titreArticle;
        public TextView textArticle;
        public TextView affplus;

        public ArticleView(View v) {
            super(v);
            datePubArticle=(TextView)v.findViewById(R.id.datePubArticle);
            titreArticle=(TextView)v.findViewById(R.id.titreArticle);
            textArticle=(TextView)v.findViewById(R.id.textArticle);
            affplus=(TextView)v.findViewById(R.id.affplus);
            articleImage=(ImageView) v.findViewById(R.id.articleImage);




        }
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

    public static class VideoView  extends RecyclerView.ViewHolder  {
        public WebView myvideo;
        public TextView datePubVideo;
        public TextView titreVideo;
        public TextView textVideo;
        public TextView affplusVideo;
        public VideoView(View v) {
            super(v);
            myvideo=(WebView)v.findViewById(R.id.myvideo);
            datePubVideo=(TextView)v.findViewById(R.id.datePubVideo);
            titreVideo=(TextView)v.findViewById(R.id.titreVideo);
            textVideo=(TextView)v.findViewById(R.id.textVideo);
            affplusVideo=(TextView)v.findViewById(R.id.affplusVideo);


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
            case "infojour": {return 1;}
            case "article": return 2;
            case "album": return 3;
            case "video": return 4;
            default:return 0;
        }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           switch (viewType) {
               case 1: {
                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_infojour,parent,false);

                   //findViewById...

                   InfoJourView vh = new InfoJourView(v);
                   return vh;
               }
               case 2:{
                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_article,parent,false);

                   //findViewById...

                   ArticleView vh = new ArticleView(v);
                   return vh;
               }
               case 3: {
                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_album,parent,false);

                   //findViewById...

                   AlbumView vh = new AlbumView(v);
                   return vh;
               }
               case 4: {
                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_video,parent,false);

                   //findViewById...

                  VideoView vh = new VideoView(v);
                   return vh;
               }
               default: {
                   View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.butt_plus,parent,false);

                   //findViewById...

                   ButtPlusView vh = new ButtPlusView(v);
                   return vh;
               }

           }




    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (publications.get(position).type.toLowerCase()) {
            case "infojour": {
                //((InfoJourView)holder).setIsRecyclable(false);
                ((InfoJourView)holder).infoJour.setText(publications.get(position).descreption);
                ((InfoJourView)holder).dateInfoJour.setText(publications.get(position).date_pub+" ");
            }break;
            case "article": {

               // ((ArticleView)holder).articleImage.setImageBitmap("");
                //Log.e("image",((Article)publications.get(position)).url_img);

               // Log.e("str=",publications.get(position).descreption.length()+"");
                if(publications.get(position).descreption.length()>220){
                    Log.e("strArt=",publications.get(position).descreption.length()+"");
                    ((ArticleView)holder).affplus.setText("اقرأ اكثر");


                }else {
                    ((ArticleView)holder).affplus.setText("");
                }

                //Picasso.get().load(Accueil.url+((Article)publications.get(position)).url_img).into(((ArticleView)holder).articleImage);
                Glide.with(c)
                        .load(Accueil.url+((Article)publications.get(position)).url_img)
                        .thumbnail(Glide.with(c).load(R.drawable.wait))
                        .apply(new RequestOptions().override(400, 600))
                        .error(Glide.with(c).load(R.drawable.no_img))
                        .timeout(60000)
                        .into(((ArticleView)holder).articleImage);

                ((ArticleView)holder).datePubArticle.setText(publications.get(position).date_pub);
                ((ArticleView)holder).titreArticle.setText(publications.get(position).titre);
                ((ArticleView)holder).textArticle.setText(publications.get(position).descreption);

                    ((ArticleView)holder).affplus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // affiche mettre le content de article a wrap content ;)..
                            ((ArticleView)holder).affplus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    ((ArticleView)holder).textArticle.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    ((ArticleView)holder).affplus.setVisibility(View.INVISIBLE);
                                    notifyItemChanged(position);



                                }
                            });
                        }

                    });

            }break;
            case "album": {
                ((AlbumView)holder).datePubAlbum.setText(publications.get(position).date_pub);
                ((AlbumView)holder).titreAlbum.setText(publications.get(position).titre);
                ((AlbumView)holder).textAlbum.setText(publications.get(position).descreption);

                if(((Album)publications.get(position)).MyImg.size() == 3){

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



                //recupération des images from server ..

                if(publications.get(position).descreption.length()>220){
                    Log.e("strArt=",publications.get(position).descreption.length()+"");
                    ((AlbumView)holder).affplusAlbum.setText("اقرأ اكثر");


                }else {
                    ((AlbumView)holder).affplusAlbum.setText("");
                }

                ((AlbumView)holder).affplusAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // affiche mettre le content de article a wrap content ;)..

                        ((AlbumView)holder).textAlbum.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        ((AlbumView)holder).affplusAlbum.setVisibility(View.INVISIBLE);
                        notifyItemChanged(position);
                    }
                });

                //affichage de tous les images ..
                ((AlbumView)holder).voirAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in=new Intent(c,VoirAlbum.class);
                        in.putExtra("id_album",""+((Album)publications.get(position)).id_album);
                        c.startActivity(in);
                    }
                });


            }break;
            case "video": {

                if(publications.get(position).descreption.length()>220){
                    Log.e("strArt=",publications.get(position).descreption.length()+"");
                    ((VideoView)holder).affplusVideo.setText("اقرأ اكثر");


                }else {
                    ((VideoView)holder).affplusVideo.setText("");
                }


                ((VideoView)holder).myvideo.getSettings().setJavaScriptEnabled(true);
                ((VideoView)holder).myvideo.setWebChromeClient(new WebChromeClient());
                ((VideoView)holder).myvideo.loadData(((Video)publications.get(position)).url_video, "text/html", "utf-8");
               // ((VideoView)holder).myvideo.loadUrl(((Video)publications.get(position)).url_video);

                ((VideoView)holder).datePubVideo.setText(publications.get(position).date_pub);
                ((VideoView)holder).titreVideo.setText(publications.get(position).titre);
                ((VideoView)holder).textVideo.setText(publications.get(position).descreption);

                ((VideoView)holder).affplusVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // affiche mettre le content de article a wrap content ;)..
                        ((VideoView)holder).affplusVideo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ((VideoView)holder).textVideo.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                ((VideoView)holder).affplusVideo.setVisibility(View.INVISIBLE);
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
                        notifyDataSetChanged();
                        Accueil.AfficherPublication(PublicationAdapter.this);
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

