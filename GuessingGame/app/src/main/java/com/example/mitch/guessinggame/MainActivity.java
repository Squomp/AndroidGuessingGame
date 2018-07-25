package com.example.mitch.guessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> games = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWebsite();
    }

    private void getWebsite(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181").get();
                    Elements allTitles = doc.select("b");
                    List titles = allTitles.subList(2, 12);
                    Elements allImages = doc.select("a>img[src]");
                    List images = allImages.subList(8,18);
                    for (int i = 0; i < 10; i++) {
                        Element img = (Element)images.get(i);
                        Element t = (Element)titles.get(i);
                        games.put(t.text(), img.attr("src"));
                    }
                } catch (IOException e) {
                    Log.w("get document error", "error retrieving website");
                }
            }
        }).start();
    }
}
