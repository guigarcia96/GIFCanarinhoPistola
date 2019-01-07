package com.memes.guilherme.gifscanarinhopistola;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private GifImageView imgGif;
    private Button btnAnterior, btnProximo, btnCompartilhar;
    int[] gifCanarinho = {R.drawable.canarinhoandando, R.drawable.canarinhobanheira, R.drawable.canarinhobatuqye, R.drawable.canarinhocama,
    R.drawable.canarinhocavaco, R.drawable.canarinhochapeu, R.drawable.canarinhocomemora, R.drawable.canarinholiga, R.drawable.canarinholouco,
    R.drawable.canarinholoucura, R.drawable.canarinhoneve, R.drawable.canarinhoputo, R.drawable.canarinhoselfie, R.drawable.canarinhoembaixxada,
    R.drawable.canarinhopalmas, R.drawable.canarinhovan};
    private AdView mAdView;
    private InterstitialAd mInterstitialAd, mInterstitialAd1;



    int posic = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,
                "ca-app-pub-9694309658633710~3068471530");


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9694309658633710/8975404332");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());




        mInterstitialAd.setAdListener(new AdListener()
                                      {
                                          @Override
                                          public void onAdLoaded() {
                                              super.onAdLoaded();
                                              mInterstitialAd.show();
                                          }
                                      }

        );

        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        }
        else{
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        imgGif = (GifImageView) findViewById(R.id.imgGif);
        btnAnterior = (Button)findViewById(R.id.btnAnterior);
        btnProximo = (Button) findViewById(R.id.btnProximo);
        btnCompartilhar = (Button) findViewById(R.id.btnCompartilhar);






        imgGif.setImageResource(gifCanarinho[0]);

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarGif();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passarGif();


            }
        });


        btnCompartilhar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                anuncioCompartilhar();

                try {
                    //create an temp file in app cache folder
                    File outputFile = new File(getApplicationContext().getExternalCacheDir(), "canarinho"+ ".gif");
                    FileOutputStream outPutStream = new FileOutputStream(outputFile);

                    //Saving the resource GIF into the outputFile:
                    InputStream is = getResources().openRawResource(gifCanarinho[posic]);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                        baos.write(current);
                    }
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    fos.write(baos.toByteArray());

                    //
                    outPutStream.flush();
                    outPutStream.close();
                    outputFile.setReadable(true, false);

                    //share file
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);



                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID+".provider",
                            outputFile);
                    shareIntent.setDataAndType(photoURI,"image/gif");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
                    shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
                    shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Compartilhar"));

                }
                catch (Exception e) { Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);}





            }









        });



    }

    public void voltarGif()
    {


        if(posic <= 0)
        {
            posic = gifCanarinho.length-1;
            imgGif.setImageResource(gifCanarinho[posic]);
        }
        else
        {
            posic--;
            imgGif.setImageResource(gifCanarinho[posic]);
        }


    }

    public void passarGif()
    {
        if(posic <= gifCanarinho.length)
        {
                posic++;
                if(posic == gifCanarinho.length)
                {
                    posic = 0;
                    imgGif.setImageResource(gifCanarinho[posic]);
                }
                else
                {
                    imgGif.setImageResource(gifCanarinho[posic]);
                }
            }


        }


        public void anuncioCompartilhar()
    {
        mInterstitialAd1 = new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-9694309658633710/8538498054");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        mInterstitialAd1.setAdListener(new AdListener()

                                       {
                                           @Override
                                           public void onAdLoaded() {
                                               super.onAdLoaded();
                                               mInterstitialAd1.show();
                                           }
                                       }

        );

    }

    }







