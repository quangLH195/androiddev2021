package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class WeatherActivity extends AppCompatActivity {

    MediaPlayer mp;
    Toolbar toolbar;
    private static final String TAG = "WeatherActivity";
    private static final String LOGO_URL = "https://usth.edu.vn/uploads/logo_moi-eng.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("USTH Weather");

        Log.i(TAG, "onCreate: ");

        FragmentManager fragmentManager = getSupportFragmentManager();
        ForecastFragment firstFragment = new ForecastFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, firstFragment).commit();

        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("HANOI,VIETNAM");
        arrayList.add("PARIS,FRANCE");
        arrayList.add("TOULOUSE,FRANCE");
        tabLayout.setupWithViewPager(pager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_refresh:
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                Response.Listener<Bitmap> listener =
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                ImageView iv = (ImageView) findViewById(R.id.logo);
                                iv.setImageBitmap(response);
                            }
                        };

                ImageRequest imageRequest = new ImageRequest(
                        "http://ict.usth.edu.vn/wp-content/uploads/usth/usthlogo.png",
                        listener, 0, 0, ImageView.ScaleType.CENTER,
                        Bitmap.Config.ARGB_8888,null);

                queue.add(imageRequest);
            case R.id.action_setting:
                msg = "Setting";
                Intent intent = new Intent(this,PrefActivity.class);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    private void copy(int resourceID, String resourceName) throws IOException {
        String path = Environment.getExternalStorageDirectory() + "/Android/" + resourceName;
        try {
            InputStream is = getResources().openRawResource(resourceID);
            FileOutputStream out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = is.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                is.close();
                out.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

