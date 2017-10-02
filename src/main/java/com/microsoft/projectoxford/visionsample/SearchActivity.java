package com.microsoft.projectoxford.visionsample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import java.io.IOException;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.out;

public class SearchActivity extends ActionBarActivity {

    private EditText sEditText;

    private String url ;

    private String[] array;

    private String title;

    private ListView listview;

    private ArrayAdapter<String> adapter;

    private final ArrayList<String> mobileArray = new ArrayList<String>();
   // private EditText captionText;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_search);

         Bundle b=this.getIntent().getExtras();
         array = b.getStringArray("taggs");
         url = "https://www.brainyquote.com/search_results.html?q=" + array[1];
        // Log.d("tag0",array[4]);

     //   captionText = (EditText) findViewById(R.id.captionSearchText);

         adapter = new ArrayAdapter<String>(this, R.layout.list_view,R.id.textlabel, mobileArray);

         listview = (ListView) findViewById(R.id.mobile_list);
         listview.setAdapter(adapter);

         try {
             new doCaption().execute(url);
         } catch (Exception e)
         {
       //      captionText.setText("Error encountered. Exception is: " + e.toString());
         }

       }


    class doCaption extends AsyncTask<String, String, Elements> {

        //private Exception e=null;

        protected Elements doInBackground(String... args) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
               // return document.title();

                Elements quotes = document.select("a[title=view quote]");
                return quotes;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Elements data) {
            int i=0;
            mobileArray.clear();
            for(Element p : data) {
                Log.d("quote",p.text());
               mobileArray.add(p.text());
               // captionText.append(p.text());
                //captionText.append("\n");

            }
            adapter.notifyDataSetChanged();
            
        }
    }

}