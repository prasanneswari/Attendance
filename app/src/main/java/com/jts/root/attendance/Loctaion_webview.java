package com.jts.root.attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Loctaion_webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loctaion_webview);

        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://maps.google.com/maps?" + "saddr=43.0054446,-87.9678884" + "&daddr=42.9257104,-88.0508355");

        //webView.loadUrl("https://www.google.com");
    }
}



