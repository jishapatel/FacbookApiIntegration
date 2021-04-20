package com.example.facebookapiintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView e;
    private LoginButton b;
    private CallbackManager c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e=findViewById(R.id.e);
        b=findViewById(R.id.b);
        c=CallbackManager.Factory.create();
        b.setPermissions(Arrays.asList("email","user_birthday"));
        b.registerCallback(c, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        c.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    AccessTokenTracker t =new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){
                e.setText("");
                Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_SHORT).show();
            }
            else {
                loaduserProfile(currentAccessToken);
            }
        }
    };
    private void loaduserProfile(AccessToken newAccessToken){
        GraphRequest request=GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object!=null){
                    try {
                        String email=object.getString("email");
                        String id=object.getString("id");
                        e.setText(email);
                    }
                    catch (JSONException ex){
                        ex.printStackTrace();
                    }}

            }
        });

        Bundle parameters=new Bundle();
            parameters.putString("fields","email,id");
            request.setParameters(parameters);
            request.executeAsync();

    }
}