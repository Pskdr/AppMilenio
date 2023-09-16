package com.example.milenioapp.ui.gallery;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.milenioapp.R;
import com.example.milenioapp.ui.utilidades.Utilities;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {


    private final ArrayList<String> daysOfMonth;
    private final ArrayList<String> daysHolidays;
    private final onItemListener onItemListener;
    private final String month;
    private final ArrayList<Date> arrayDates;
    private final Context view;
    private final int diaActual;
    private LocalDate selectedDate;
    Utilities utilities = new Utilities();
    public Calendar calendar = Calendar.getInstance();
    final ArrayList<String> daysholidays = new ArrayList<>();




    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<String> daysHolidays,
                           CalendarAdapter.onItemListener onItemListener, String month,
                           ArrayList<Date> arrayDates, Context view)
    {

        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.daysHolidays = daysHolidays;
        this.month = month;
        this.arrayDates = arrayDates;
        this.view = view;
        this.diaActual = Integer.parseInt(utilities.split(calendar.getTime().toString(), 2));;
    }


    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.calendar_cell, parent, false );
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.17);


        return new CalendarViewHolder(view, onItemListener);


    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {

        holder.month.setText(month);

        if(daysOfMonth.get(position).equals("")){
            //Log.d("psk", daysOfMonth.get(position) + " <-daysofmonth ");
            holder.dayOfMonth.setBackground(new Drawable() {
                @Override
                public void draw(@NonNull Canvas canvas) {

                }

                @Override
                public void setAlpha(int i) {

                }

                @Override
                public void setColorFilter(@Nullable ColorFilter colorFilter) {

                }

                @Override
                public int getOpacity() {
                    return PixelFormat.OPAQUE;
                }
            });
        }else{
            holder.dayOfMonth.setText(daysOfMonth.get(position));

            if((Integer.parseInt(daysOfMonth.get(position))) == diaActual && utilities.split(month,0).equals((utilities.numeroAmes(utilities.mesANumero(utilities.split(calendar.getTime().toString(),1)))))){
                holder.dayOfMonth.setBackground(view.getResources().getDrawable(R.drawable.et_style_dia));
            }


            for(int i = 0; i<arrayDates.size(); i++){
                int n = Integer.parseInt(utilities.split(arrayDates.get(i).toString(),2));

                if(daysOfMonth.get(position).equals(String.valueOf(n))){
                    holder.dayOfMonth.setBackground(view.getResources().getDrawable(R.drawable.et_style2));
                }
            }

            for(int i = 0; i<daysHolidays.size(); i++){
                if(daysOfMonth.get(position).equals(daysHolidays.get(i))){
                    holder.dayOfMonth.setTextColor(Color.RED);
                }
            }
            if(daysOfMonth.size()-1 == position){
                daysHolidays.clear();
            }
        }



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

    @Override
    public int getItemCount() { return daysOfMonth.size(); }

    public interface onItemListener
    {
        void onItemClick(int position, String dayText);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView dayOfMonth;
        private final CalendarAdapter.onItemListener onItemListener;
        public final TextView month;

        public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.onItemListener onItemListener) {

            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            this.onItemListener = onItemListener;
            month = itemView.findViewById(R.id.monthText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());

            if(dayOfMonth.getText().toString().length() == 0){
                return;
            }

            Bundle bundle=new Bundle();
            bundle.putString("day-month",dayOfMonth.getText() + " "+ month.getText());
            findNavController(view).navigate(R.id.action_nav_gallery_to_cronogramaServicios, bundle);



        }
    }

}
