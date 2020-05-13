package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class GuidelinesActivity extends AppCompatActivity {

    private HashMap<String, String> hmap = new HashMap<>();;
    private Spinner collegeSpin;
    private Button sendBtn;
    private WebView mWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collegeSpin = findViewById(R.id.collegeGuidSpin);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_dropdown_item);
        collegeSpin.setAdapter(adapter);

        makeUrl();

        sendBtn = findViewById(R.id.sendGuidBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collegeSpin.getSelectedItem().toString().contains("대학 선택")) Toast.makeText(getApplicationContext(), "대학을 선택해 주세요", Toast.LENGTH_SHORT).show();
                else  onWebView(hmap.get(collegeSpin.getSelectedItem().toString()));
            }
        });

        mWebView = findViewById(R.id.GuidWebView);
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setMimeType(mimeType);
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file");
                    String fileName = contentDisposition.replace("inline; filename=", "");
                    fileName = fileName.replaceAll("\"", "");
                    request.setTitle(fileName);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    if (ContextCompat.checkSelfPermission(GuidelinesActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(GuidelinesActivity.this,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(GuidelinesActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        } else {
                            Toast.makeText(getBaseContext(), "첨부파일 다운로드를 위해\n동의가 필요합니다.", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(GuidelinesActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    110);
                        }
                    }
                }
            }
        });
    }

    void makeUrl(){
        hmap.put("가천대학교", "https://admission.gachon.ac.kr/gachon/");
        hmap.put("가톨릭대학교", "http://ipsi.catholic.ac.kr/pages/?p=24&mj=04");
        hmap.put("강남대학교", "https://admission.kangnam.ac.kr/jeonhyung/yogang.htm?ctg_cd=transfer");
        hmap.put("건국대학교", "http//enter.konkuk.ac.kr/seoul/transfer/guide.jsp");
        hmap.put("경기대학교", "http://enter.kyonggi.ac.kr/pages/?p=24&mj=03");
        hmap.put("경희대학교", "https://iphak.khu.ac.kr/PageLink.do");
        hmap.put("광운대학교", "https://iphak.kw.ac.kr/entry/entry_01.php");
        hmap.put("국민대학교", "https://admission.kookmin.ac.kr/transfer/application.php");
        hmap.put("단국대학교", "http://ipsi.dankook.ac.kr/jukjeon/doumi/mojip.html?bbsid=juk_paper&ctg_cd=05");
        hmap.put("덕성여자대학교", "https://enter.duksung.ac.kr/contents/contents.do?ciIdx=305&menuId=852");
        hmap.put("동국대학교", "https://ipsi.dongguk.edu/admission/html/transfer/guide.asp");
        hmap.put("동덕여자대학교", "https://ipsi.dongduk.ac.kr/ipsi/tc/common.do");
        hmap.put("명지대학교", "https://ipsi.mju.ac.kr/pages/?p=26&mj=04");
        hmap.put("산업기술대학교", "https://iphak.kpu.ac.kr/transfer/guide.htm");
        hmap.put("삼육대학교", "http://ipsi.syu.ac.kr/2016_syu/pages/index.asp?p=20&mj=04");
        hmap.put("상명대학교", "https://admission.smu.ac.kr:2012/_seoul/iphak/mojip.html?bbsid=seoul_mojib&ctg_cd=pyunip");
        hmap.put("서강대학교", "http://admission.sogang.ac.kr/admission/html/transfer/guide.asp");
        hmap.put("서울시립대학교", "https://admission.uos.ac.kr/admission/pyunib/info.do?epTicket=LOG");
        hmap.put("서울여자대학교", "http://admission.swu.ac.kr/ipsi/mojip.htm?ctg_cd=transfer");
        hmap.put("성균관대학교", "https://admission.skku.edu/iphak/sub_pyunip.htm");
        hmap.put("세종대학교", "http://ipsi.sejong.ac.kr/sub_page/sub4/0101.asp?tab1=4");
        hmap.put("수원대학교", "https://ipsi.suwon.ac.kr/index.html?menuno=2068");
        hmap.put("숙명여자대학교", "http://admission.sookmyung.ac.kr/enter/html/transfer/guide.asp");
        hmap.put("숭실대학교", "http://iphak.ssu.ac.kr/2014/pyunip/req.asp");
        hmap.put("아주대학교", "http://www.iajou.ac.kr/pyuniphak/mojip.do?gb=mojip");
        hmap.put("이화여자대학교", "https://admission.ewha.ac.kr/enter/doc/transfer/guide.asp");
        hmap.put("인하대학교", "https://admission.inha.ac.kr/cms/FR_CON/index.do?MENU_ID=620");
        hmap.put("중앙대학교", "https://admission.cau.ac.kr/guide/plan.html?ctg_cd=entry");
        hmap.put("한국외국어대학교", "https://adms.hufs.ac.kr/enter/html/transfer/guide.asp");
        hmap.put("한국항공대학교", "http://ibhak.kau.ac.kr/admission/html/transfer/guide.asp");
        hmap.put("한성대학교", "http://enter.hansung.ac.kr/?m1=menu03%25&m2=sub00%25&board=application%25&application=8%25");
        hmap.put("한양대에리카", "http://goerica.hanyang.ac.kr/admission/html/transfer/guide.asp");
        hmap.put("한양대학교", "https://go.hanyang.ac.kr/new/2015/03/req.html");
        hmap.put("홍익대학교", "https://admission.hongik.ac.kr/guide/guide?kind=transfer");
    }

    void onWebView(String url){
        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스크립트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(true); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(true); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        mWebView.loadUrl(url); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }

    @Override
    public void onBackPressed(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
