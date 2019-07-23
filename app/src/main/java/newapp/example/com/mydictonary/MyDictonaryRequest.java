package newapp.example.com.mydictonary;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyDictonaryRequest extends AsyncTask<String, Integer ,String> {

    final String app_id = "6abba60f";
    final String app_key = "c96ad79de5ccbf3edbda4a14e06233dc";
    String myurl;
    Context context;

    MyDictonaryRequest(Context context)
    {
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {

        myurl=params[0];
        try {
            URL url = new URL(myurl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute( s );
        //Toast.makeText( context,s,Toast.LENGTH_LONG ).show();
        String def;
        try {
            JSONObject js =new JSONObject(s);
            JSONArray results =js.getJSONArray( "results" );

            JSONObject lEntries=results.getJSONObject( 0 );
            JSONArray laArray= lEntries.getJSONArray( "lexicalEntries" );

            JSONObject entries =laArray.getJSONObject( 0 );
            JSONArray e=entries.getJSONArray( "entries" );

            JSONObject jsonObject =e.getJSONObject( 0 );
            JSONArray sensesArray =jsonObject.getJSONArray( "senses" );

            JSONObject d=sensesArray.getJSONObject( 0 );
            JSONArray de=d.getJSONArray( "definitions" );

            def =de.getString( 0 );

            //t1.setText(def);
            Toast.makeText(context,def,Toast.LENGTH_LONG).show();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
