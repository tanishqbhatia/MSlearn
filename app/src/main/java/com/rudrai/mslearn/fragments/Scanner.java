package com.rudrai.mslearn.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.pixplicity.easyprefs.library.Prefs;
import com.rudrai.mslearn.activities.MainActivity;
import com.rudrai.mslearn.activities.RegisterActivity;
import com.rudrai.mslearn.activities.ResultActivity;
import com.rudrai.mslearn.utils.Cons;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(Scanner.this);
            }
        }, 2000);

        String result = rawResult.getText();
        if(result.length() == 15 && result.startsWith("plati")) {
            result = result.substring(8, result.length());
            result = "mslearn2017_".concat(result);
        }

        Log.i("ID", result);
        Log.i("UID", Prefs.getString(Cons.UID, ""));

        String url = "http://rudrai.com/mslearn/qr.php?id=".concat(result).concat("&uid=").concat(Prefs.getString(Cons.UID, ""));
        sendResult(url);
    }

    private void sendResult(String result) {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        intent.putExtra("url", result);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}