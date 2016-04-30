package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;

/**
 * Created by Bashir on 8/4/16.
 */
public class WebViewFragment extends BaseFragment {

    private WebView webView;
    private String link;
    private View appBar;
    int preState = -1;
    private ProgressBar webProgress;

    public static WebViewFragment newInstance(String link) {

        Bundle args = new Bundle();

        WebViewFragment fragment = new WebViewFragment();
        args.putString("link", link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.link = bundle.getString("link");
        }

        appBar = getActivity().findViewById(R.id.app_bar_layout);
        if (appBar != null) {
            preState = appBar.getVisibility();
            if (preState == View.VISIBLE) {
                appBar.setVisibility(View.GONE);
                CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                getActivity().findViewById(R.id.main_container).setLayoutParams(params);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.web_view_fragment_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.web_view);
        webProgress = (ProgressBar) view.findViewById(R.id.web_view_progress_bar);
        applyWebViewSettings(getActivity(), webView, webProgress);
        loadUrl(link);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (appBar != null) {
            if (preState == View.VISIBLE) {
                appBar.setVisibility(View.VISIBLE);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getActivity().findViewById(R.id.main_container).getLayoutParams();
                params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                getActivity().findViewById(R.id.main_container).setLayoutParams(params);
            }
        }
    }

    public boolean canGoBack() {
        boolean goBack = webView.canGoBack();
        if (goBack) {
            webView.goBack();
        }
        return goBack;
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    private void applyWebViewSettings(final Activity context, final WebView webView, final ProgressBar webProgress) {

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(false);
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + ";NP-ANDROID-GPT");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webProgress.setVisibility(View.VISIBLE);
                webProgress.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                // Otherwise allow the OS to handle it
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                } catch (Exception e) {

                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webProgress.setProgress(100);
                webProgress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                resend.sendToTarget();
            }
        });

        webView.clearCache(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webProgress.setProgress(newProgress);
                if(newProgress>=100){
                    webProgress.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
