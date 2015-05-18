package us.ridiculousbakery.imagesearch.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import us.ridiculousbakery.imagesearch.util.EndlessScrollListener;
import us.ridiculousbakery.imagesearch.detail.ImageDisplayActivity;
import us.ridiculousbakery.imagesearch.ImageResult;
import us.ridiculousbakery.imagesearch.R;
import us.ridiculousbakery.imagesearch.settings.Settings;
import us.ridiculousbakery.imagesearch.settings.SettingsFragment;


public class ImageSearchActivity extends ActionBarActivity {
    private GridView gvResults;
    private ArrayList<ImageResult> results;
    private ImageResultsAdapter aImageResults;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        settings = new Settings();
        gvResults = (GridView) findViewById(R.id.gvResults);
        results = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, results);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ImageSearchActivity.this, ImageDisplayActivity.class);
                i.putExtra("data", (ImageResult) parent.getItemAtPosition(position));
                startActivity(i);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                nextPage(page);
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
                settings.query = query;
                AsyncHttpClient client = new AsyncHttpClient();
                Log.d("URL", url());
                client.get(url(), new_query_response_handler());
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

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            FragmentManager fm = getSupportFragmentManager();
            SettingsFragment frSettings = SettingsFragment.newInstance(settings);
            frSettings.show(fm, "settings_fragment");
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFinishSettingsFragment(Settings new_settings) {
        Log.d("SETTINGS", new_settings.get_selectedColor()+"");
        settings = new_settings;
    }

    private String url() {
        String u = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
        if (settings.get_selectedSize() > 0) {
            u = u + "&imgsz=" + getResources().getStringArray(R.array.apisizes)[settings.get_selectedSize()];
        }
        if (settings.get_selectedColor() > 0) {
            u = u + "&imgcolor=" + getResources().getStringArray(R.array.colors)[settings.get_selectedColor()];
        }
        if (settings.get_selectedType() > 0) {
            u = u + "&imgtype=" + getResources().getStringArray(R.array.apitypes)[settings.get_selectedType()];
        }
        if (settings.get_selectedSite().length() > 0) {
            u = u + "&as_sightsearch=" + settings.get_selectedSite();
        }
        if(settings.query != null){
            u=u+ "&q=" + settings.query;
        }

        return u;
    }

    private JsonHttpResponseHandler new_query_response_handler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsjson = null;
                try {
                    imageResultsjson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.clear();
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsjson));
                } catch (JSONException e) {
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
    public void nextPage(int offset){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url()+8*offset, next_response_handler());

    }
    private JsonHttpResponseHandler next_response_handler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsjson = null;
                try {
                    imageResultsjson = response.getJSONObject("responseData").getJSONArray("results");

                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsjson));
                } catch (JSONException e) {
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
