package com.example.java_nodejs_firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView ivImagem;
    Button btnCadastrar;
    EditText etTitulo;
    final int Gallery = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Instantiating Views.
        ivImagem = findViewById(R.id.iv_imagem);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        etTitulo = findViewById(R.id.et_titulo);

        btnCadastrar.setOnClickListener(event -> {
//        Accessing internal Image Gallery.
            Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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
                Bitmap bitman = MediaStore
                        .Images
                        .Media
                        .getBitmap(this.getContentResolver(), uri);

                ivImagem.setImageBitmap(bitman);

            } catch (IOException error) {
                Log.d("ioexp", "onActivityResult: " + error.getMessage());
            }
        }
    }
}