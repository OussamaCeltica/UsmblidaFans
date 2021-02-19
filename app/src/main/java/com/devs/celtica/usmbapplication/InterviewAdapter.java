package com.devs.celtica.usmbapplication;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by celtica on 11/07/18.
 */

public class InterviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context c;
    public static ArrayList<Publication> publications;

    public InterviewAdapter(ArrayList<Publication> publications, Context c) {
        this.c = c;
        this.publications = publications;
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

            case "video": return 2;

            default:return 0;
        }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 2: {

                //Video view ..
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_video,parent,false);
                VideoView vh = new  VideoView(v);
                return vh;
            }
            default:{
                //Button plus view ..
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.butt_plus,parent,false);
                ButtPlusView vh = new  ButtPlusView(v);
                return vh;
            }
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (publications.get(position).type) {
            case "video": {

                if(publications.get(position).descreption.length()>198){
                    //Log.e("strArt=",publications.get(position).descreption.length()+"");
                    (( VideoView)holder).affplusVideo.setText("اقرأ اكثر");


                }else {
                    (( VideoView)holder).affplusVideo.setText("");
                }


                (( VideoView)holder).myvideo.getSettings().setJavaScriptEnabled(true);
                (( VideoView)holder).myvideo.setWebChromeClient(new WebChromeClient());
                (( VideoView)holder).myvideo.loadData(((Video)publications.get(position)).url_video, "text/html", "utf-8");
                // ((VideoView)holder).myvideo.loadUrl(((Video)publications.get(position)).url_video);

                (( VideoView)holder).datePubVideo.setText(publications.get(position).date_pub);
                (( VideoView)holder).titreVideo.setText(publications.get(position).titre);
                (( VideoView)holder).textVideo.setText(publications.get(position).descreption);

                ((VideoView)holder).affplusVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // affiche mettre le content de article a wrap content ;)..
                        (( VideoView)holder).affplusVideo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                (( VideoView)holder).textVideo.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                (( VideoView)holder).affplusVideo.setVisibility(View.INVISIBLE);
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
                        InterviewVideo.afficherVideos(InterviewAdapter.this);
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
