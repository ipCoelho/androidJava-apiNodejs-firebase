package com.example.java_nodejs_firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.java_nodejs_firebase.remote.ApiUtil;
import com.example.java_nodejs_firebase.remote.ImageInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView ivImagem;
    Button btnCadastrar;
    EditText etTitulo;
    final int Gallery = 1;
    ImageInterface imageInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Instantiating Views.
        ivImagem = findViewById(R.id.iv_imagem);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        etTitulo = findViewById(R.id.et_titulo);

        imageInterface = ApiUtil.getUploadInterface();

        ivImagem.setOnClickListener(event -> {
//        Accessing internal Image Gallery.
            Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            intent.setType("image/*");

            startActivityForResult(intent, Gallery);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) return;
        if (requestCode == Gallery && data != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore
                    .Images
                    .Media
                    .getBitmap(this.getContentResolver(), uri);

                ivImagem.setImageBitmap(bitmap);
                Log.d("cd-bitmap", bitmap.toString());

//                Upload process:
                uploadRetrofitImage(bitmap);
            }
            catch (IOException error) {
                Log.d("image-error", error.getMessage());
            }
        }
    }

    private void uploadRetrofitImage(Bitmap bitmap) {
//        Stream object.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        File compress.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        Converting to base64.
        String file = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        String title = etTitulo.getText().toString();
        
        Call<String> call = imageInterface.uploadImage(file, title);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(
                    MainActivity.this,
                    "File uploaded.",
                    Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(
                    "code-error", t.getMessage()
                );
            }
        });
    }
}