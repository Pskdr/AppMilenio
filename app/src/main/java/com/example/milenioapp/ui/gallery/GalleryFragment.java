package com.example.milenioapp.ui.gallery;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.ui.utilidades.Utilities;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    List<Date> listaDates;
    ArrayList<Date> arrayDates;
    private Calendar fecha = Calendar.getInstance();
    private Long salida;
    private String mes;
    int mesActual = 0;
    int añoActual = 0;

    Button btnIzquierda, btnDerecha;

    final ArrayList<String> daysholidays = new ArrayList<>();

    TextView tvFecha;
    public Calendar calendar = Calendar.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


        ((MainMenu)getActivity()).setActionBarTitle("Cronograma de servicios");


        btnDerecha = view.findViewById(R.id.btnDerecha);
        btnIzquierda = view.findViewById(R.id.btnIzquierda);
        Utilities utilities = new Utilities();
        String month = utilities.split(calendar.getTime().toString(), 1);
        mesActual = mesANumero(month);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        tvFecha = view.findViewById(R.id.tvFecha);
        selectedDate = LocalDate.now();
        añoActual = Integer.parseInt(utilities.split(calendar.getTime().toString(), 5));
        setMonthView();


        fecha = Calendar.getInstance();
        salida = fecha.getTimeInMillis();

        btnDerecha.setOnClickListener(vista -> nextMonthAction(vista));
        btnIzquierda.setOnClickListener(vista -> previousMonthAction(vista));

        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        mesActual--;
        selectedDate = selectedDate.minusMonths(1);
        if(mesActual <1){
            mesActual = 12;
            añoActual--;
        }
        daysholidays.clear();
        setMonthView();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view){
        mesActual++;
        if(mesActual >12){
            mesActual = 1;
            añoActual++;
        }
        selectedDate = selectedDate.plusMonths(1);
        daysholidays.clear();
        setMonthView();
    }

    public int mesANumero(String month){
        int num = 0;
        switch(month) {
            case "Jan":
                num = 1;
                break;
            case "Feb":
                num = 2;
                break;
            case "Mar":
                num = 3;
                break;
            case "Apr":
                num = 4;
                break;
            case "May":
                num = 5;
                break;
            case "Jun":
                num = 6;
                break;
            case "Jul":
                num = 7;
                break;
            case "Aug":
                num = 8;
                break;
            case "Sep":
                num = 9;
                break;
            case "Oct":
                num = 10;
                break;
            case "Nov":
                num = 11;
                break;
            case "Dec":
                num = 12;
                break;

            default:
                return 0;
        }
        return num;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        String month = numeroAmes(mesActual);
        calendar.getTime().toString();

        tvFecha.setText(monthTraductor(month) +" "+ añoActual);

        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        //Log.d("month", diasRegistro(daysInMonth)+ "");

        buscarFechaNovedad(numeroAmes(mesActual) +" "+ añoActual,daysInMonth);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date){
        final ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();


        dayOfWeek = dayOfWeek-1;
        if(dayOfWeek == 6){
            dayOfWeek = -1;
        }
        Log.d("psk", dayOfWeek + " <- day of week");
        for (int i = 0; i<42; i++){
            if((i <= dayOfWeek || i > daysInMonth + dayOfWeek)){
                daysInMonthArray.add("");
            }else daysInMonthArray.add(String.valueOf(i - dayOfWeek));

            if(((i % 7)== 0) && (i - dayOfWeek)>0 && daysInMonth>=i){
                daysholidays.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;

    }

    public String numeroAmes(int n){
        String mes = "";

        switch(n) {
            case 1:
                mes = "Jan";
                break;
            case 2:
                mes = "Feb";
                break;
            case 3:
                mes = "Mar";
                break;
            case 4:
                mes = "Apr";
                break;
            case 5:
                mes = "May";
                break;
            case 6:
                mes = "Jun";
                break;
            case 7:
                mes = "Jul";
                break;
            case 8:
                mes = "Aug";
                break;
            case 9:
                mes = "Sep";
                break;
            case 10:
                mes = "Oct";
                break;
            case 11:
                mes = "Nov";
                break;
            case 12:
                mes = "Dec";
                break;

            default:
                return "";
        }
        return mes;
    }

    public void buscarFechaNovedad(String month, ArrayList<String> daysInMonth)  {

        Utilities utilities = new Utilities();
        ArrayList<String> prueba = new ArrayList<>();
        for(int i = 1; i<daysInMonth.size(); i++){
            Log.d("pskprueba", "buscarFechaNovedad: "+daysInMonth.get(i));
        }

        mes = utilities.split(month, 0);
        mes = monthTraductor(mes);
        mes = utilities.getMes(mes);

        arrayDates = (ArrayList<Date>) listaDates;

        ArrayList<Date> arrayFinal = new ArrayList<>();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, daysholidays, new CalendarAdapter.onItemListener() {
            @Override
            public void onItemClick(int position, String dayText) {

            }
        }, month, arrayFinal, getContext());
        RecyclerView.LayoutManager layoutManager = new CustomGridLayoutManager(getContext(), 7);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    private String monthTraductor(String date ){
        String mes = "";
        switch(date) {
            case "Jan":
                mes = "Enero";
                break;
            case "Feb":
                mes = "Febrero";
                break;
            case "Mar":
                mes= "Marzo";
                break;
            case "Apr":
                mes = "Abril";
                break;
            case "May":
                mes = "Mayo";
                break;
            case "Jun":
                mes = "Junio";
                break;
            case "Jul":
                mes="Julio";
                break;
            case "Aug":
                mes = "Agosto";
                break;
            case "Sep":
                mes = "Septiembre";
                break;
            case "Oct":
                mes = "Octubre";
                break;
            case "Nov":
                mes = "Noviembre";
                break;
            case "Dec":
                mes = "Diciembre";
                break;

            default:
                return "";
        }

        return mes;

    }

}