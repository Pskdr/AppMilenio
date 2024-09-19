package com.example.milenioapp.ui.ordenes.crearOrdenServicio.certificado;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.ui.utilidades.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CertificadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CertificadoFragment extends Fragment {


    private TextView tvFecha,tvNombre,tvNit,tvDireccion,tvFechaServicio,
            tvPlagaEncontrada,tvTecnico, tvSede,tvObservaciones;
    private ImageView ivFirmaOperario,ivFirmaAyudante;
    private RecyclerView rvZonas;
    private Orden orden;
    private Cliente cliente;
    public CertificadoFragment() {
        // Required empty public constructor
    }


    public ProgressDialog prgDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_certificado, container, false);

        tvFecha = view.findViewById(R.id.tvFecha);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvNit = view.findViewById(R.id.tvNit);
        tvDireccion = view.findViewById(R.id.tvDireccion);
        tvFechaServicio = view.findViewById(R.id.tvFechaServicio);
        tvPlagaEncontrada = view.findViewById(R.id.tvPlagaEncontrada);
        tvTecnico = view.findViewById(R.id.tvTecnicoAsignado);
        tvObservaciones = view.findViewById(R.id.tvObservaciones);
        tvSede = view.findViewById(R.id.tvSede);
        rvZonas = view.findViewById(R.id.rvZonas);
        ivFirmaAyudante = view.findViewById(R.id.ivFirmaAyudante);
        ivFirmaOperario = view.findViewById(R.id.ivFirmaOperario);

        rvZonas.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();

        long idOrden = bundle.getLong("idOrden",-1);
        long idCliente = bundle.getLong("idCliente",-1);

        if(idOrden != -1){
            traerOrdenCliente(idOrden,idCliente);
        }else{
            getActivity().onBackPressed();
        }


        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Generando PDF...");
        prgDialog.setIndeterminate(true);
        prgDialog.setMax(100);
        //prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.setCancelable(false);
        prgDialog.show();


        return view;
    }

    private void traerOrdenCliente(long idOrden, long idCliente) {
        new Thread(() -> {

            orden = AppDataBase.getInstance(getContext()).getOrdenDAO().getByid(idOrden);
            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(idCliente);

            getActivity().runOnUiThread(() -> {

                Utilities utilities = new Utilities();
                Calendar fecha = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fecha.setTimeInMillis(orden.getFechaUsuario());

                tvFecha.setText("Fecha: "+utilities.split(utilities.getFechaString(fechaActual),0));
                tvNombre.setText(cliente.getNombre());
                tvNit.setText(cliente.getNIT());
                tvDireccion.setText(cliente.getDireccion());
                tvFechaServicio.setText(utilities.split(utilities.getFechaString(fecha),0));
                tvTecnico.setText(orden.getOperario());
                tvSede.setText(cliente.getSede());


                ivFirmaOperario.setImageBitmap(utilities.stringToBitMap(orden.getFirmaOperario()));
                ivFirmaAyudante.setImageBitmap(utilities.stringToBitMap(orden.getFirmaAyudante()));

                llenarAdapter();

            });

        }).start();
    }

    private ArrayList<ItemZonasMostrar> itemZonasMostrars;
    private AdapterCertificado adapterCertificado;
    private void llenarAdapter() {

        new Thread(() -> {

            itemZonasMostrars = (ArrayList<ItemZonasMostrar>) AppDataBase.getInstance(getContext()).getGrupoZonaDAO().getZonasAgregadasPdf(orden.getId());

            getActivity().runOnUiThread(() -> {

                adapterCertificado = new AdapterCertificado(new AdapterCertificado.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                },itemZonasMostrars);

                rvZonas.setAdapter(adapterCertificado);
                Utilities utilities = new Utilities();
                convertXLtoPDF();
            });

        }).start();

    }

    public void convertXLtoPDF(){
        // Inflate the XML layout file
        View view = getView();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getDisplay().getRealMetrics(displayMetrics);
        } else
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));
        Log.d("mylog", "Width Now " + view.getMeasuredWidth());
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        // Create a new PdfDocument instance
        PdfDocument document = new PdfDocument();

        // Obtain the width and height of the view
        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();
        // Create a PageInfo object specifying the page attributes
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();

        // Start a new page
        PdfDocument.Page page = document.startPage(pageInfo);

        // Get the Canvas object to draw on the page
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling the view
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // Draw the view on the canvas
        view.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Specify the path and filename of the output PDF file
        Utilities utilities = new Utilities();
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        String fileName = "CERTIFICADO "+cliente.getNombre()+" - "+ orden.getOperario()+" - "+rand_int1+".pdf";
        File filePath = new File(downloadsDir, fileName);

        try {
            // Save the document to a file
            FileOutputStream fos = new FileOutputStream(filePath);
            document.writeTo(fos);
            document.close();
            fos.close();
            // PDF conversion successful

            byte[] fileContent = Files.readAllBytes(filePath.toPath());

            Toast.makeText(getContext(), "PDF convertido exitosamente en: "+downloadsDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
            Uri excelPath;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                excelPath = FileProvider.getUriForFile(((MainMenu) getActivity()), "com.example.milenioapp.provider", filePath);
            } else {
                excelPath = Uri.fromFile(filePath);
            }


            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(excelPath, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            try{
                startActivity(pdfIntent);
            }catch(ActivityNotFoundException e){
                Toast.makeText(getContext(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while converting to PDF
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            Bundle bundle = new Bundle();
            bundle.putLong("id",orden.getId());

            prgDialog.dismiss();
            getActivity().onBackPressed();

        }
    }

}