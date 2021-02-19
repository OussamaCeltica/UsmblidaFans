package com.devs.celtica.usmbapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {
    ProgressDialog dialog;

    public static MySqlBdServer BD = new MySqlBdServer("https://logical-movers.000webhostapp.com");
    public static String url = "https://logical-movers.000webhostapp.com/";
    private DrawerLayout mDrawerLayout;
    public static MyBD BDloc;

    public static int nbrPub;
    public static int pubAfficher = 0;
    static Accueil me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);
        me = this;

        //instancier la bD ..
        BDloc = new MyBD("usmbdb.db", Accueil.this);

        sendBroadcast(new Intent(Accueil.this, WifiBroadcast.class));


        ImageView drawer = (ImageView) findViewById(R.id.drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);

            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.menu_calendrier) {
                            startActivity(new Intent(Accueil.this, Afficher_Calendrier.class));

                        } else if (menuItem.getItemId() == R.id.menu_interv) {
                            startActivity(new Intent(Accueil.this, InterviewVideo.class));


                        } else if (menuItem.getItemId() == R.id.menu_fans) {
                            startActivity(new Intent(Accueil.this, VideoFans.class));


                        } else if (menuItem.getItemId() == R.id.menu_live) {
                            startActivity(new Intent(Accueil.this, MatchDirect.class));

                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        //les buttopn de menu ..
        LinearLayout infoJr, art, photos, videos;
        infoJr = (LinearLayout) findViewById(R.id.info_jour_butt);
        art = (LinearLayout) findViewById(R.id.article_butt);
        photos = (LinearLayout) findViewById(R.id.photo_butt);
        videos = (LinearLayout) findViewById(R.id.video_butt);

        // afficher les articles ..
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Accueil.this, AfficherArticle.class));
            }
        });

        //afficher les album ..
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Accueil.this, AfficherAlbum.class));

            }
        });

        //Afficher les videos ..
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Accueil.this, AfficherVideo.class));

            }
        });

        //Afficher les info du jour ..
        infoJr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Accueil.this, AfficherInfoJour.class));

            }
        });

        // pour l affichage des publications ..

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.div_affich);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Accueil.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final PublicationAdapter mAdapter = new PublicationAdapter(new ArrayList<Publication>(),this);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.publications.add(new InfoDuJour("", ""));
        mAdapter.notifyDataSetChanged();


        //affichage info du jour ..
        BD.read("select * from infojour order by jour desc limit 1", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                dialog = new ProgressDialog(Accueil.this);
                dialog.setMessage("جاري التحميل ..");
                dialog.show();

            }

            @Override
            public void After() throws JSONException {
                mAdapter.publications.remove(0);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();

                try {
                    JSONArray r = new JSONArray(BD.queryResult);
                    int i = 0;

                    while (i < r.length()) {

                        mAdapter.publications.add(new InfoDuJour(r.getJSONObject(i).getString("info"), r.getJSONObject(i).getString("jour")));
                        i++;
                        mAdapter.notifyDataSetChanged();

                    }


                    //affichage des publications ..

                    AfficherPublication(mAdapter);


                } catch (JSONException e) {

                }


            }
        });

        // count number de publications ...


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pubAfficher = 0;
    }

    public static void AfficherPublication(final PublicationAdapter mAdapter) {


        // mAdapter.publications.add(new Article(0,"article","","","attend ..",4,"http://logical-movers.000webhostapp.com/usmb_photos/default.png"));
        //  mAdapter.notifyDataSetChanged();


        // mAdapter.publications.add(new Article(0,"article","","","attend ..",4,"http://logical-movers.000webhostapp.com/usmb_photos/default.png"));
        //  mAdapter.notifyDataSetChanged();

        BD.read("SELECT * FROM publications order by date_pub desc limit 10 offset " + (pubAfficher), new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {
            }

            @Override
            public void After() {
                // mAdapter.publications.remove(1);
                // mAdapter.notifyDataSetChanged();


                try {
                    final JSONArray r = new JSONArray(BD.queryResult);
                    int i = 0;


                    while (i < r.length()) {

                        switch (r.getJSONObject(i).getString("type")) {
                            case "article": {


                                BD.read("SELECT * FROM publications pub , article ar WHERE pub.id_pub=ar.id_pub and ar.id_pub='" + r.getJSONObject(i).getString("id_pub") + "'", new MySqlBdServer.doBeforAndAfterGettingData() {
                                    @Override
                                    public void before() {

                                    }

                                    @Override
                                    public void After() {
                                        try {
                                            JSONArray r2 = new JSONArray(BD.queryResult);
                                            int i2 = 0;

                                            while (i2 < r2.length()) {
                                                mAdapter.publications.add(new Article(r2.getJSONObject(i2).getInt("id_pub"), "article", r2.getJSONObject(i2).getString("date_pub"), r2.getJSONObject(i2).getString("titre"), r2.getJSONObject(i2).getString("descreption"), r2.getJSONObject(i2).getInt("id_article"), r2.getJSONObject(i2).getString("url_img")));
                                                i2++;
                                                mAdapter.notifyDataSetChanged();

                                            }


                                            // Log.e("count:",publications.size()+" ");
                                        } catch (JSONException e) {
                                            e.printStackTrace();

                                        }
                                    }


                                });


                            }
                            break;

                            case "album": {


                                BD.read("SELECT * FROM publications pub , album ar ,photoalbum ph WHERE pub.id_pub=ar.id_pub and ph.id_album=ar.id_album AND ar.id_pub='" + r.getJSONObject(i).getString("id_pub") + "'  order by ph.id_image asc limit 3", new MySqlBdServer.doBeforAndAfterGettingData() {
                                    @Override
                                    public void before() {

                                    }

                                    @Override
                                    public void After() {
                                        try {
                                            JSONArray r2 = new JSONArray(BD.queryResult);
                                            int i2 = 0;
                                            ArrayList<PhotoAlbum> MyImg = new ArrayList<PhotoAlbum>();

                                            while (i2 < r2.length()) {
                                                MyImg.add(new PhotoAlbum(r2.getJSONObject(i2).getString("url_image"), r2.getJSONObject(i2).getInt("id_album")));
                                                i2++;


                                            }
                                            i2 = 0;
                                            Album a = new Album(r2.getJSONObject(i2).getInt("id_pub"), "album", r2.getJSONObject(i2).getString("date_pub"), r2.getJSONObject(i2).getString("titre"), r2.getJSONObject(i2).getString("descreption"), r2.getJSONObject(i2).getInt("id_album"));
                                            a.MyImg = MyImg;
                                            mAdapter.publications.add(a);
                                            mAdapter.notifyDataSetChanged();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });


                            }
                            break;
                            case "video": {
                                BD.read("SELECT * FROM publications pub , videos vid WHERE pub.id_pub=vid.id_pub and vid.id_pub='" + r.getJSONObject(i).getString("id_pub") + "'", new MySqlBdServer.doBeforAndAfterGettingData() {
                                    @Override
                                    public void before() {

                                    }

                                    @Override
                                    public void After() {

                                        try {
                                            JSONArray r2 = new JSONArray(BD.queryResult);
                                            int i2 = 0;
                                            while (i2 < r2.length()) {
                                                mAdapter.publications.add(new Video(r2.getJSONObject(i2).getInt("id_pub"), "video", r2.getJSONObject(i2).getString("date_pub"), r2.getJSONObject(i2).getString("titre"), r2.getJSONObject(i2).getString("descreption"), r2.getJSONObject(i2).getInt("id_video"), r2.getJSONObject(i2).getString("url_video")));
                                                mAdapter.notifyDataSetChanged();
                                                i2++;
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                            break;

                        }
                        i++;


                    }

                    //ajouter le button d affichage de plus de publications ..
                    BD.read("select count(id_pub) as nbrpub from publications", new MySqlBdServer.doBeforAndAfterGettingData() {
                        @Override
                        public void before() {

                        }

                        @Override
                        public void After() throws JSONException {
                            JSONArray r = new JSONArray(BD.queryResult);
                            nbrPub = r.getJSONObject(0).getInt("nbrpub");
                            pubAfficher = pubAfficher + 10;

                            if (nbrPub > pubAfficher) {
                                mAdapter.publications.add(new Publication(-5, "plus", "", "", ""));
                                mAdapter.notifyDataSetChanged();


                            }


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}
