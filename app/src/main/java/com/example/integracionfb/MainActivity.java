package com.example.integracionfb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.lang.annotation.Target;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {


    LoginButton loginButton;
    CallbackManager callbackManager;
    Button buttonEnlace, buttonVideo, buttonFoto;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //System.out.print("KeyHashes"+ KeyHashes());

        loginButton = findViewById(R.id.login_button);
        buttonEnlace = findViewById(R.id.btnCompartirLink);
        //buttonFoto = findViewById(R.id.btnCompartirFoto);
        //buttonVideo = findViewById(R.id.btnCompartirVideo);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(MainActivity.this);


        buttonEnlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setQuote("Link")
                        .setContentUrl(Uri.parse("https://www.twitch.tv/FexelCorp"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(shareLinkContent);
                }
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"Sesión iniciada por " + loginResult.getAccessToken().getUserId(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String KeyHashes(){
    PackageInfo info;
    String KeyHashes = null;
    try{
        info = getPackageManager().getPackageInfo("com.example.integracionfb", PackageManager.GET_SIGNATURES);
        for(Signature signature : info.signatures){
            MessageDigest md;
            md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            KeyHashes = new String(Base64.encode(md.digest(),0));
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    return KeyHashes;
    }
}
