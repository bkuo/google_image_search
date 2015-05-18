package us.ridiculousbakery.imagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageSearchActivity extends ActionBarActivity {
    private GridView gvResults;
    private ArrayList<ImageResult> results;
    private ImageResultsAdapter  aImageResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        gvResults = (GridView) findViewById(R.id.gvResults);
        results = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, results);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ImageSearchActivity.this, ImageDisplayActivity.class);
                i.putExtra("data", (ImageResult)parent.getItemAtPosition(position));
                startActivity(i);
            }
        });
        gvResults.setAdapter(aImageResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&imgsz=large&q=" + query;
                client.get(url, api_response_handler());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

//    public void onImageSearch(query){
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&imgsz=large&q=" + query;
//        client.get(url, api_response_handler());
//    }
    private JsonHttpResponseHandler api_response_handler(){
        return new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsjson = null;
                try{
                    imageResultsjson  = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.clear();
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsjson));
                }catch(JSONException e){
                    e.printStackTrace();
                }
                aImageResults.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "query failed", Toast.LENGTH_SHORT);
            }
        };

    }
}
