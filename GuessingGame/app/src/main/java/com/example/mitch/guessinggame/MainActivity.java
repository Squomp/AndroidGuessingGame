package com.example.mitch.guessinggame;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    class Game {
        private String title;
        private String imageURL;

        public Game(String title, String imageUrl) {
            this.title = title;
            this.imageURL = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
    }

    private List<Game> games = new ArrayList<>();
    private ImageView gameImage;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private int counter = 0;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameImage = findViewById(R.id.gameImage);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        getWebsite();
        switchImage(counter);
    }

    private void getWebsite(){
        Thread t = new Thread(new Runnable() {
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
                        String text = t.text().substring(t.text().indexOf(' ') + 1);
                        games.add(new Game(text, img.attr("src")));
                    }
                    int x = 2;
                } catch (IOException e) {
                    Log.w("get document error", "error retrieving website");
                }
            }
        });
        t.start();
        try {
        t.join();
        } catch (Exception e) {

        }
    }

    public void switchImage(int index) {
        List<Integer> usedIndecies = new ArrayList<>();
        usedIndecies.add(index);
        switch (rand.nextInt(4)) {
            case 0:
                button1.setText(games.get(index).getTitle());
                break;
            case 1:
                button2.setText(games.get(index).getTitle());
                break;
            case 2:
                button3.setText(games.get(index).getTitle());
                break;
            case 3:
                button4.setText(games.get(index).getTitle());
                break;
        }
        if (button1.getText().length() < 1) {
            boolean valid = false;
            int num = -1;
            while (!valid) {
                valid = true;
                num = rand.nextInt(10);
                if (usedIndecies.contains(num)) {
                    valid = false;
                } else {
                    usedIndecies.add(num);
                }
            }
            button1.setText(games.get(num).getTitle());
        }
        if (button2.getText().length() < 1) {
            boolean valid = false;
            int num = -1;
            while (!valid) {
                valid = true;
                num = rand.nextInt(10);
                if (usedIndecies.contains(num)) {
                    valid = false;
                } else {
                    usedIndecies.add(num);
                }
            }
            button2.setText(games.get(num).getTitle());
        }
        if (button3.getText().length() < 1) {
            boolean valid = false;
            int num = -1;
            while (!valid) {
                valid = true;
                num = rand.nextInt(10);
                if (usedIndecies.contains(num)) {
                    valid = false;
                } else {
                    usedIndecies.add(num);
                }
            }
            button3.setText(games.get(num).getTitle());
        }
        if (button4.getText().length() < 1) {
            boolean valid = false;
            int num = -1;
            while (!valid) {
                valid = true;
                num = rand.nextInt(10);
                if (usedIndecies.contains(num)) {
                    valid = false;
                } else {
                    usedIndecies.add(num);
                }
            }
            button4.setText(games.get(num).getTitle());
        }
        Picasso.get().load(games.get(index).imageURL).into(gameImage);
    }

    public static Drawable getImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public void buttonClicked(View view) {
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        switchImage(++counter);
    }
}
