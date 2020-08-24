package com.yalianxun.mingshi.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.Objects;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        changStatusIconColor(true);
        TextView textView = findViewById(R.id.av_title);
        if(Objects.equals(getIntent().getStringExtra("key"), "notify")){
            textView.setText("通知详情");
            textView = findViewById(R.id.label_title);
            textView.setVisibility(View.VISIBLE);
            textView.setText(getIntent().getStringExtra("title"));
            HtmlTextView htmlTextView = findViewById(R.id.html_text);
            htmlTextView.setVisibility(View.VISIBLE);
            String html = getIntent().getStringExtra("content");
            assert html != null;
            htmlTextView.setHtml(html,
                    new HtmlHttpImageGetter(htmlTextView));
        }else {
            textView.setText("详情");
            WebView webView = findViewById(R.id.web);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(getIntent().getStringExtra("url"));
        }

    }


    public void goBack(View view) {
        finish();
    }
}
