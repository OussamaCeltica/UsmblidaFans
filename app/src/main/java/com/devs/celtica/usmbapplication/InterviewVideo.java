package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.devs.celtica.usmbapplication.Accueil.BD;

public class InterviewVideo extends AppCompatActivity {

    public  static int pubAfficher=0;
    public static int nbrPub;
    static ProgressDialog dialog;

    public static InterviewVideo  me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_interview_video);
        me=this;

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_video_interv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InterviewVideo.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final InterviewAdapter mAdapter = new InterviewAdapter(new ArrayList<Publication>(),getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);



        afficherVideos(mAdapter);
    }


    public static void afficherVideos(final InterviewAdapter mAdapter){
        BD.read("SELECT * from publications pub , videos vid where pub.type='video' and pub.id_pub=vid.id_pub and vid.type='interview' order by pub.date_pub desc limit 10  offset "+pubAfficher, new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog= new ProgressDialog(me);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After() {

                dialog.dismiss();

                try {
                    final JSONArray r = new JSONArray(Accueil.BD.queryResult);
                    int i = 0;

                    while (i < r.length()) {

                        mAdapter.publications.add(new Video(r.getJSONObject(i).getInt("id_pub"),"video",r.getJSONObject(i).getString("date_pub"),r.getJSONObject(i).getString("titre"),r.getJSONObject(i).getString("descreption"),r.getJSONObject(i).getInt("id_video"),r.getJSONObject(i).getString("url_video")));
                        mAdapter.notifyDataSetChanged();

                        i++;
                    }
                    //ajouter le button d affichage de plus de publications ..
                    Accueil.BD.read("select count(id_video) as nbrpub from (SELECT id_video from publications pub , videos vid where pub.type='video' and pub.id_pub=vid.id_pub and vid.type='interview') as counter", new MySqlBdServer.doBeforAndAfterGettingData() {
                        @Override
                        public void before() {

                        }

                        @Override
                        public void After() throws JSONException {
                            JSONArray r=new JSONArray(BD.queryResult);
                            nbrPub=r.getJSONObject(0).getInt("nbrpub");

                            pubAfficher=pubAfficher+10;

                            if(nbrPub>pubAfficher){
                                mAdapter.publications.add(new Publication(-5,"plus","","",""));
                                mAdapter.notifyDataSetChanged();


                            }



                        }
                    });

                } catch (JSONException e) {
                    Log.e("except video","kayen"+e+" \n ");

                }

            }
        });
    }
}
