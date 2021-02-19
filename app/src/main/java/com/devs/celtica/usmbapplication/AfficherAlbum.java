package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.devs.celtica.usmbapplication.Accueil.BD;

public class AfficherAlbum extends AppCompatActivity {
    public  static int pubAfficher=0;
    public static int nbrPub;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_afficher_album);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_album);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(AfficherAlbum.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final AlbumAdapter mAdapter = new AlbumAdapter(new ArrayList<Publication>(),this);

        mRecyclerView.setAdapter(mAdapter);

       afficherAlbums(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pubAfficher=0;
    }

    public void afficherAlbums(final AlbumAdapter mAdapter){
        Accueil.BD.read("SELECT * FROM publications where type='album' order by date_pub  desc limit 10  offset "+pubAfficher, new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog= new ProgressDialog(AfficherAlbum.this);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After() {
                // mAdapter.publications.remove(1);
                // mAdapter.notifyDataSetChanged();
                dialog.dismiss();


                try {
                    final JSONArray r = new JSONArray(Accueil.BD.queryResult);
                    int i = 0;

                    while (i < r.length()) {

                        Accueil.BD.read("SELECT * FROM publications pub , album ar ,photoalbum ph WHERE pub.id_pub=ar.id_pub and ph.id_album=ar.id_album AND ar.id_pub='"+r.getJSONObject(i).getString("id_pub")+"'  order by ph.id_image asc limit 3", new MySqlBdServer.doBeforAndAfterGettingData() {
                            @Override
                            public void before() {

                            }

                            @Override
                            public void After(){
                                try {
                                    JSONArray r2=new JSONArray(Accueil.BD.queryResult);
                                    int i2=0;
                                    ArrayList<PhotoAlbum> MyImg=new ArrayList<PhotoAlbum>();

                                    while(i2<r2.length()){
                                        MyImg.add(new PhotoAlbum(r2.getJSONObject(i2).getString("url_image"),r2.getJSONObject(i2).getInt("id_album")));
                                        i2++;


                                    }
                                    i2=0;
                                    Album a=new Album(r2.getJSONObject(i2).getInt("id_pub"),"album",r2.getJSONObject(i2).getString("date_pub"),r2.getJSONObject(i2).getString("titre"),r2.getJSONObject(i2).getString("descreption"),r2.getJSONObject(i2).getInt("id_album"));
                                    a.MyImg=MyImg;
                                    mAdapter.publications.add(a);
                                    mAdapter.notifyDataSetChanged();




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                        i++;
                    }
                    //ajouter le button d affichage de plus de publications ..
                    Accueil.BD.read("select count(id_pub) as nbrpub from publications where type='album'", new MySqlBdServer.doBeforAndAfterGettingData() {
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

                }

            }
        });

    }
}
