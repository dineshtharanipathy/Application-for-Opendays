package project.isep.com.opendays;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by Tharanipathy PC on 11-01-2016.
 */
public class WebsiteActivity extends AppCompatActivity {

    WebView web;
    ProgressDialog pd;
    Toolbar toolbar;
    String website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        website = getIntent().getStringExtra("website");

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        web = (WebView)findViewById(R.id.web);
        web.requestFocus(View.FOCUS_DOWN);
        web.getSettings().setJavaScriptEnabled(true);
        pd = ProgressDialog.show(this, "OpenDays", "Loading...", true);
        pd.setCancelable(true);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("fahad", url);
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (pd != null && !pd.isShowing()) {
                    pd.show();
                }
            }
        });

        web.loadUrl(website);
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()){
            web.goBack();
        }else{
            finish();
        }
    }
}
