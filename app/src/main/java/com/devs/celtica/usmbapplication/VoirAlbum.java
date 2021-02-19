package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class VoirAlbum extends AppCompatActivity {

    FlexboxLayout affAlbum;
    LinearLayout slider;
    ProgressDialog progressDialog;
    ImageView img_slider;

    Button next,prev,fermer;

    public static int click;
    public ArrayList<Bitmap> MyImg=new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voir_album);


        affAlbum=(FlexboxLayout) findViewById(R.id.afficher_image_album);
        slider=(LinearLayout)findViewById(R.id.slider_album);
        img_slider=(ImageView)findViewById(R.id.img_slider);

        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        fermer=(Button)findViewById(R.id.fermer);

        Bundle b=getIntent().getExtras();

        Accueil.BD.read("select url_image from photoalbum where id_album='" + b.getString("id_album")+ "' order by id_image asc ", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {

                progressDialog=progressDialog = ProgressDialog.show(VoirAlbum.this,"تحميل","جاري التحميل ..",false,false);

            }

            @Override
            public void After()  {
                progressDialog.dismiss();

                try {
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    int i=0;

                    while(i<r.length()){
                     //   MyImg.add(new PhotoAlbum(r.getJSONObject(i).getString("url_image"),0));
                        LayoutInflater li=getLayoutInflater();
                        final View v=li.inflate(R.layout.div_image,null);
                        final ImageView img=(ImageView)v.findViewById(R.id.img);

                        //afficher le slider ..
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ImageView img= (ImageView)view;
                                click=MyImg.indexOf(((BitmapDrawable)((ImageView)img).getDrawable()).getBitmap());
                                slider.setVisibility(View.VISIBLE);
                                img_slider.setImageBitmap(MyImg.get(click));

                                // activation des button next et précédent ..
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(( click+1) !=  MyImg.size()){
                                             click++;
                                           img_slider.setImageBitmap(MyImg.get(click));
                                        }
                                    }
                                });

                                prev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(( click) != 0){
                                            click--;
                                            img_slider.setImageBitmap( MyImg.get(click));
                                        }
                                    }
                                });

                                //quité le slider ..
                                fermer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        slider.setVisibility(View.GONE);
                                    }
                                });


                            }
                        });

                        //
                        /*
                        Picasso.get().load(Accueil.url+r.getJSONObject(i).getString("url_image")).into(img,new com.squareup.picasso.Callback(){

                            @Override
                            public void onSuccess() {
                                MyImg.add(((BitmapDrawable)img.getDrawable()).getBitmap());
                                affAlbum.addView(v);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
                         */

                        affAlbum.addView(v);

                        Glide.with(VoirAlbum.this)
                                .load(Accueil.url+r.getJSONObject(i).getString("url_image"))
                                .thumbnail(Glide.with(VoirAlbum.this).load(R.drawable.wait))
                                .apply(new RequestOptions().override(400, 600))
                                .error(Glide.with(VoirAlbum.this).load(R.drawable.no_img))
                                .timeout(60000)
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        MyImg.add(((BitmapDrawable)resource).getBitmap());

                                        return false;
                                    }
                                })
                                .into(img);



                        i++;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }
}
