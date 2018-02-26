package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;

import org.jsoup.nodes.Document;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Bashir on 8/4/16.
 */
public class WebViewFragment extends BaseFragment {

    static String userAgent = null;
    int preState = -1;
    Document doc = null;
    Context mContext;
    String webViewRemoveJs = "";
    private WebView webView;
    private String link;
    private View appBar;
    private String baseUrl = "";
    //WiseWeWebClient myWebClient;

    public static WebViewFragment newInstance(String link) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
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
        SharedPreferences sp = getActivity().getSharedPreferences(getString(R.string.PREFS), MODE_PRIVATE);
        webViewRemoveJs = sp.getString("Web_View_Remove_js", "");
        webViewRemoveJs = webViewRemoveJs.replace("\n", "");
        Log.v("@karan : From Shared ", " @karan:" + sp.getString("Web_View_Remove_js", ""));

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
        return inflater.inflate(R.layout.web_view_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setVisibility(View.GONE);
        userAgent = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(userAgent + " collegeapp");
        final ProgressBar webProgress = (ProgressBar) view.findViewById(R.id.web_view_progress_bar);
        applyWebViewSettings(getActivity(), webView, webProgress);
        webView.addJavascriptInterface(new WebAppInterface(mContext), "Android");
        //webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_INSET);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalFadingEdgeEnabled(false);
        webView.setVerticalFadingEdgeEnabled(false);
        webView.setHorizontalScrollBarEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.v("shouldOverrideUrl", "shouldOverride" + view.getSettings().getUserAgentString());
                webView.getSettings().setUserAgentString(userAgent + " collegeapp");
                webView.addJavascriptInterface(new WebAppInterface(mContext), "Android");


                if (url.startsWith("http:") || url.startsWith("https:")) {
                    if (url.contains("www.collegedekho.com/")) {
                        webView.setVisibility(View.GONE);
                        webProgress.setVisibility(View.VISIBLE);
                        loadUrl(url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    return false;
                }

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "exception on overriding url");
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.v("karan makejscall", " makejscall");
                webView.loadUrl("javascript:" + webViewRemoveJs);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        view.findViewById(R.id.web_view_internet_refresh).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Log.v("user agent", "user agent : " + webView.getSettings().getUserAgentString());

        super.onClick(v);
        switch (v.getId()) {
            case R.id.web_view_internet_refresh:
                if (baseUrl != null && !baseUrl.isEmpty())
                    webView.clearCache(true);
                webView.clearHistory();
                webView.clearFormData();
                webView.getSettings().setUserAgentString(userAgent + " collegeapp");
                loadUrl(baseUrl);
                break;
            default:
                break;
        }
    }

    public boolean canGoBack() {
        boolean goBack = webView.canGoBack();
        Log.v("go back ", "user agent : " + webView.getSettings().getUserAgentString());

        WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
        if (mWebBackForwardList.getCurrentIndex() > 0) {
            String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
            if (historyUrl.equalsIgnoreCase("about:blank")) {
                webView.clearCache(true);
                webView.clearHistory();
                webView.clearFormData();
                return false;
            }
        }
        if (goBack) {
            webView.goBack();
        }
        return goBack;
    }

    public void loadUrl(String url) {
        webView.getSettings().setUserAgentString(userAgent + " collegeapp");
        if (url.contains("www.collegedekho.com/") || url.contains("www.collegedekho.com/")) {
            webView.loadUrl(url);
            webView.loadUrl("javascript:" + webViewRemoveJs);
        } else {
            webView.loadUrl(url);
            webView.loadUrl("javascript:" + webViewRemoveJs);
        }
    }

    private void applyWebViewSettings(final Activity context, final WebView webView, final ProgressBar webProgress) {

        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setLoadWithOverviewMode(true);
        webView.getSettings().setUserAgentString(userAgent + " collegeapp");
        Log.v("set agent", "user agent : " + webSettings.getUserAgentString());
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.clearCache(true);
        webProgress.setVisibility(View.VISIBLE);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webProgress.setProgress(newProgress);
                if (newProgress >= 100) {
                    webProgress.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
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
    public String getEntity() {
        return null;
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToastJS(String toast) {
            try {
            } catch (Exception e) {
                Log.v("karan showjs fun", " showjscall");

            }
        }

        @JavascriptInterface
        public void makeCallJS(String number) {
            try {

            } catch (Exception e) {
                Log.e("makeCallJS", e.getMessage());
            }
        }
    }
}

