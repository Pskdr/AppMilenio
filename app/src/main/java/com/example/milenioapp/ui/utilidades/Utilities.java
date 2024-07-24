package com.example.milenioapp.ui.utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Utilities {
    private LocationManager ubicacion;

    public String split(String string, int num) {
        String[] split = string.split(" ");
        return (split[num]);
    }

    public String inputStreamToString(InputStream is) {
        String rLine;
        StringBuilder answer = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }


    public String splitDate(String string, int num) {
        String[] split = string.split("-");
        return (split[num]);
    }

    public Calendar getCalendar(String fecha) throws ParseException {

        String year = splitDate(fecha, 2);
        String mes = splitDate(fecha, 1);
        String dia = splitDate(fecha, 0);

        String fechaRegistro = dia + "-" + mes + "-" + year + " " + split(fecha, 1);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = format.parse(fechaRegistro);
        calendar.setTime(date);

        return calendar;

    }

    public String spliWith(String texto, int num) {
        String[] split = texto.split(":");
        return (split[num]);
    }

    public String getLatitudLongitud(String texto, int num) {
        String[] split = texto.split("/");
        return (split[num]);
    }

    public String getNombreArchivo(String text) {
        if(text == null){
            return "";
        }

        String[] split = text.split("/");

        return split[split.length - 1];
    }

    public String getExtension(String s) {
        String extension = "";

        extension = s.substring(s.lastIndexOf("."));
        //extension=s.substring(s.lastIndexOf(".") + 1);

        /*String extension = ".";
        int num = 99999;
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) == '.'){
                num = i;
            }
            if(i>num){
                extension += s.charAt(i);
            }
        }*/
        return extension;
    }

    public Date getStringTipoDate(String dia, String mes, String year) {

        String mesTraducido = monthTraductor(mes);
        String mesNum = getMes(mesTraducido);
        String fechaRegistro = dia + "/" + mesNum + "/" + year;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Log.d("inizefe", mes);
        Date tiempo;
        try {
            tiempo = (Date) format.parse(fechaRegistro);
        } catch (ParseException e) {
            tiempo = new Date();
            Log.d("inizefe", e.toString());
        }

        return tiempo;
    }

    public String getMes(String mes) {
        String mesNum = "";
        switch (mes) {
            case ("Enero"):
                mesNum = "01";
                break;
            case ("Febrero"):
                mesNum = "02";
                break;
            case ("Marzo"):
                mesNum = "03";
                break;
            case ("Abril"):
                mesNum = "04";
                break;
            case ("Mayo"):
                mesNum = "05";
                break;
            case ("Junio"):
                mesNum = "06";
                break;
            case ("Julio"):
                mesNum = "07";
                break;
            case ("Agosto"):
                mesNum = "08";
                break;
            case ("Septiembre"):
                mesNum = "09";
                break;
            case ("Octubre"):
                mesNum = "10";
                break;
            case ("Noviembre"):
                mesNum = "11";
                break;
            case ("Diciembre"):
                mesNum = "12";
                break;
        }
        return mesNum;
    }

    public String monthTraductor(String date) {
        String mes = "";
        switch (date) {
            case "Jan":
                mes = "Enero";
                break;
            case "Feb":
                mes = "Febrero";
                break;
            case "Mar":
                mes = "Marzo";
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
                mes = "Julio";
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


    public String getFechaActual() {
        Calendar fecha;
        Long salida;

        fecha = Calendar.getInstance();
        salida = fecha.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy k:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(salida);

        return formatter.format(calendar.getTime());
    }

    public Calendar getFechaCalendar() {
        Calendar fecha;
        Long salida;

        fecha = Calendar.getInstance();
        salida = fecha.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy k:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(salida);

        return calendar;
    }

    public String getFechaString(Calendar fecha) {
        Long salida = fecha.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(salida);

        return formatter.format(calendar.getTime());

    }


    public float sizeBitmap(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public int mesANumero(String month) {
        int num = 0;
        switch (month) {
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

    public String numeroAmes(int n) {
        String mes = "";

        switch (n) {
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

    public String bitMapToString(Bitmap bitmap) {
        if (bitmap == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;

        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    public Calendar getCalendarByString(String fechaRegistro) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date tiempo;
        try {
            tiempo = (Date) format.parse(fechaRegistro);
        } catch (ParseException e) {
            tiempo = new Date();
            Log.d("inizefe", e.toString());
        }
        Calendar fecha = Calendar.getInstance();

        fecha.setTime(tiempo);

        return fecha;

    }

    public String generarSerial() {
        return UUID.randomUUID().toString();
    }


    public long generarID() {
        return 0;
    }

    public String deleteCero(String s) {
        String finalS = "";
        for(int i = 0; i<s.length(); i++){
            if((s.charAt(i) == '.' || s.charAt(i) == ',')){
                finalS += ".";
            }else{
                finalS+=s.charAt(i);
            }
        }
        return finalS;
    }
    public String refactorFecha(String fecha){
        String primerosNumeros = spliWith(fecha,0);
        String segundosNumeros = spliWith(fecha,1);
        int primerNumero = Integer.parseInt(primerosNumeros);
        if(primerNumero > 12){
            return (primerNumero - 12)+":"+ segundosNumeros +" PM";
        }else{
            return primerNumero+":"+segundosNumeros+" AM";
        }
    }
}
