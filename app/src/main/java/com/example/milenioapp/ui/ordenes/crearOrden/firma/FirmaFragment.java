package com.example.milenioapp.ui.ordenes.crearOrden.firma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrden.CrearOrdenFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FirmaFragment extends Fragment {

    private boolean firmaAcompa;
    private Bitmap firma;
    public FirmaFragment(CrearOrdenFragment crearOrdenFragment, boolean firmaAcompa, Bitmap firma) {
        // Required empty public constructor
        this.crearOrdenFragment = crearOrdenFragment;
        this.firmaAcompa = firmaAcompa;
        this.firma = firma;

    }

    private Button btnGuardar, btnBorrar;
    File sign;

    private Path path = new Path();
    private List<Path> paths = new ArrayList<>();

    private RelativeLayout rlPrueba;
    private CrearOrdenFragment crearOrdenFragment;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_firma, container, false);


        rlPrueba = view.findViewById(R.id.rlPrueba);

        btnBorrar = view.findViewById(R.id.btnBorrar);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        imageView = view.findViewById(R.id.canvasImageView);

        if(firma != null){
            imageView.setImageBitmap(firma);
            btnGuardar.setVisibility(View.GONE);
        }else{
            rlPrueba.addView(new CanvasFirma(getContext()));
        }
        return view;
    }




    private void createWordDocumentWithImage(String root) {
    }


    public void buttonDrawCanvas(View view){
    }

    public class CanvasFirma extends View {

        Path path;
        Paint paint;
        List<Path> paths;
        List<Paint> paints;
        Canvas canvas;
        public CanvasFirma(Context context) {
            super(context);
            paths = new ArrayList<>();
            paints = new ArrayList<>();
            btnBorrar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(firmaAcompa){
                        crearOrdenFragment.guardarFirmaAcompa(null);
                    }else{
                        crearOrdenFragment.guardarFirmaOperario(null);
                    }
                    if(path != null) {
                        path.reset();
                        paths.clear();
                        invalidate();
                    }
                }
            });

            btnGuardar.setOnClickListener(view ->  {
                Bitmap bitmap = saveSignature();
                File imageFile = new File(Environment.getExternalStorageDirectory(), "firma.png");

                try {
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                createWordDocumentWithImage(imageFile.getPath());

                if(firmaAcompa){
                    crearOrdenFragment.guardarFirmaAcompa(bitmap);
                }else{
                    crearOrdenFragment.guardarFirmaOperario(bitmap);
                }

            });

        }

        public Bitmap saveSignature(){

            Bitmap  bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            this.draw(canvas);

            File file = new File(Environment.getExternalStorageDirectory() + "/sign.png");

            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.canvas = canvas;
            int i = 0;
            for(Path path: paths){
                canvas.drawPath(path,paints.get(i++));
            }
        }


        public void clearCanvas() {

        }

        public Canvas getCanvas(){
            return this.canvas;
        }

        private float posx=0, posy=0;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            posx = event.getX();
            posy =event.getY();

            switch (event.getAction()){

                case MotionEvent.ACTION_DOWN:

                    paint = new Paint();
                    paint.setStrokeWidth(5);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLUE);
                    paints.add(paint);
                    path = new Path();
                    path.moveTo(posx,posy);
                    paths.add(path);
                    break;

                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    int puntoHistoricos = event.getHistorySize();
                    for(int i = 0; i<puntoHistoricos; i++){
                        path.lineTo(event.getHistoricalX(i),event.getHistoricalY(i));
                    }

                    break;
            }
            invalidate();
            return true;
        }
    }

}