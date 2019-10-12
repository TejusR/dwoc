package com.example.srmhack1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.CameraPosition;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.SimpleMarkerBalloon;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteType;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Serializable {
    TextView textView;
    private ProgressDialog dialog;

    private TomtomMap map1;
    Double lat1, lng1,lat2,lng2,distance,time;
    EditText to,from,seat,regnum;
    Button select,dt,finalb;
    Button ecometer,leader,offer;

    Date value;
    String datetime,email,date;
    int k=2,c=0,error=0;
    UserApi userApi;
    Call<String> mCall;
    Date date_find,date_get;
    Long date_from,date_dest;
    ArrayList <LatLng> fromcoord = new ArrayList<LatLng>();
    ArrayList <LatLng> tocoord = new ArrayList<LatLng>();
    ArrayList <String> driver_name = new ArrayList<String>();
    int n,k1=0,p1=0;
    Double lat_1,lat_2,lng_1,lng_2;

    final Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.4.61.44:8000/account/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    LocationManager locationManager;


    private final static int ALL_PERMISSIONS_RESULT = 101;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        this.map1 = tomtomMap;
        tomtomMap.setMyLocationEnabled(true);
        // tomtomMap.centerOn(CameraPosition.builder(new LatLng(tomtomMap.getUserLocation().getLatitude(),tomtomMap.getUserLocation().getLongitude())).zoom(15).build());
        //Location location = tomtomMap.getUserLocation();
        //LatLng amsterdam = new LatLng(5.2, 2.3);
        //SimpleMarkerBalloon balloon = new SimpleMarkerBalloon("Amsterdam");
        //tomtomMap.addMarker(new MarkerBuilder(amsterdam).markerBalloon(balloon));
        // tomtomMap.centerOn(CameraPosition.builder(amsterdam).zoom(15).build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        email = pref.getString("email", "none");

        if(!pref.contains("co2"))
        editor.putFloat("co2",(float)10.5);
        if(!pref.contains("distance"))
            editor.putFloat("distance",(float)1.7);
        if(!pref.contains("fare"))
            editor.putFloat("fare", (float) 20.3);
        if(!pref.contains("point"))
            editor.putFloat("point",(float)100.0);

        if (email == "none") {
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
            dialog = new ProgressDialog(this);
            ecometer = (Button)findViewById(R.id.ecometer);
            leader = (Button)findViewById(R.id.leaderboard);
            offer = (Button)findViewById(R.id.offers);

            ecometer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),Ecometer.class);
                    startActivity(intent);
                    finish();
                }
            });
            offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),Offer.class);
                    startActivity(intent);
                    finish();
                }
            });
            MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapFragment);
            mapFragment.getAsyncMap(this);
            mapFragment.getAsyncMap(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull TomtomMap tomtomMap) {
                    map1 = tomtomMap;
                    tomtomMap.setMyLocationEnabled(true);
                    // tomtomMap.centerOn(CameraPosition.builder(new LatLng(tomtomMap.getUserLocation().getLatitude(),tomtomMap.getUserLocation().getLongitude())).zoom(15).build());
                    tomtomMap.zoomIn();
                }
            });

            to = (EditText) findViewById(R.id.to);
            from = (EditText) findViewById(R.id.from);
            select = (Button)findViewById(R.id.select);
            dt = (Button)findViewById(R.id.datetime);
            seat = (EditText) findViewById(R.id.seat);
            regnum = (EditText) findViewById(R.id.regnum);
            dt.setBackgroundColor(Color.LTGRAY);
            regnum.setBackgroundColor(Color.CYAN);
            seat.setBackgroundColor(Color.CYAN);
            finalb = (Button)findViewById(R.id.finalb);


            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapFragment.getAsyncMap(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull TomtomMap tomtomMap) {
                            tomtomMap.clearRoute();
                        }
                    });
                    if(select.getText().toString().equals("Offer Ride")) {
                        select.setText("Share Ride");
                        select.setBackgroundColor(Color.GREEN);
                        regnum.setVisibility(View.GONE);
                        seat.setVisibility(View.VISIBLE);
                        finalb.setText("SHARE RIDE");
                        k=1;
                    }
                    else {
                        select.setText("Offer Ride");
                        select.setBackgroundColor(Color.RED);
                        regnum.setVisibility(View.VISIBLE);
                        seat.setVisibility(View.GONE);
                        finalb.setText("OFFER RIDE");
                        k=2;
                    }
                }
            });

            dt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    c=1;
                    getdate();
                    dt.setText(datetime);
                }
            });

            finalb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapFragment.getAsyncMap(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull TomtomMap tomtomMap) {
                            tomtomMap.clearRoute();
                        }
                    });
                    if(c!=1) {
                        dt.setError("Please pick date");
                    }
                    else if(k==1 && seat.getText().toString().equalsIgnoreCase("")) {
                        seat.setError("Please enter number of seats");
                    }
                    else if(k==1 && (Integer.parseInt(seat.getText().toString())>=6 || Integer.parseInt(seat.getText().toString())<1)){
                        seat.setError("Must be between 1 and 6");
                    }
                    else if(k==2 && regnum.getText().toString().equalsIgnoreCase("")) {
                        regnum.setError("Please enter reg number");
                    }
                    else if(to.getText().toString().equalsIgnoreCase("")) {
                        to.setText("Enter");
                    }
                    else if(from.getText().toString().equalsIgnoreCase("")) {
                        from.setText("Enter");
                    }
                    else {
                        if(Geocoder.isPresent()) {
                            date=new String();
                            datetime = new String();
                            error = 0;
                            try {
                                String location = from.getText().toString();
                                Geocoder gc = new Geocoder(getApplicationContext());
                                List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

                                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                                for (Address a : addresses) {
                                    if (a.hasLatitude() && a.hasLongitude()) {
                                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                    }
                                }
                                if (ll.size() >= 1) {
                                    lat1 = ll.get(0).getLatitude();
                                    lng1 = ll.get(0).getLongitude();
                                } else {
                                    error = 1;
                                    from.setError("Invalid");
                                    from.setText("");
                                }
                            } catch (IOException e) {
                                // handle the exception
                            }
                            try {
                                String location = to.getText().toString();
                                Geocoder gc = new Geocoder(getApplicationContext());
                                List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

                                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                                for (Address a : addresses) {
                                    if (a.hasLatitude() && a.hasLongitude()) {
                                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                    }
                                }
                                if (ll.size() >= 1) {
                                    lat2 = ll.get(0).getLatitude();
                                    lng2 = ll.get(0).getLongitude();
                                } else {
                                    error = 1;
                                    to.setError("Invalid");
                                    to.setText("");
                                }
                            } catch (IOException e) {
                                // handle the exception
                            }


                            if (error != 1) {
                                LatLng loc1 = new LatLng(lat1, lng1);
                                LatLng loc2 = new LatLng(lat2, lng2);
                                mapFragment.getAsyncMap(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(@NonNull TomtomMap tomtomMap) {
                                        map1 = tomtomMap;
                                        SimpleMarkerBalloon balloon = new SimpleMarkerBalloon("Destination");
                                        tomtomMap.addMarker(new MarkerBuilder(loc2).markerBalloon(balloon));
                                        tomtomMap.addMarker(new MarkerBuilder(loc1).icon(Icon.Factory.fromResources(getApplicationContext(), R.drawable.pin2)));
                                        tomtomMap.centerOn(CameraPosition.builder(loc1).zoom(12).build());
                                        tomtomMap.centerOn(CameraPosition.builder(loc2).zoom(12).build());
                                    }
                                });

                                RoutingApi routingApi = OnlineRoutingApi.create(getApplicationContext());
                                RouteQuery routeQuery = new RouteQueryBuilder(loc1, loc2).withRouteType(RouteType.FASTEST).build();
                                routingApi.planRoute(routeQuery)
                                        .subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(routeResult -> {
                                            for (FullRoute fullRoute : routeResult.getRoutes()) {
                                                RouteBuilder routeBuilder = new RouteBuilder(
                                                        fullRoute.getCoordinates());
                                                //for(int i=0;i<routeBuilder.getCoordinates().size();i++) {
                                                   // Log.d("routecoord",routeBuilder.getCoordinates().get(i).getLatitudeAsString() + "  " + routeBuilder.getCoordinates().get(i).getLongitudeAsString());
                                                //}
                                                map1.addRoute(routeBuilder);
                                            }
                                        });
                            }
                        }

                        if(k==2 && error!=1) {
                            date_from=date_find.getTime()/1000;
                            date_dest = date_find.getTime()/1000;


                            Retrofit retrofit1 = new Retrofit.Builder()
                                    .baseUrl("https://api.tomtom.com/routing/1/calculateRoute/" + lat1 + "," + lng1 + ":" +lat2 + "," + lng2  + "/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();

                            userApi = retrofit1.create(UserApi.class);
                            Call<String> call = userApi.requestDistance("F7KXUjsM4wu8uAZtJCQ4eCYGdjWxRqYj");
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.body()!=null) {
                                       // Log.d("MYFILE",response.body());
                                        String []res = response.body().split(",");
                                        distance = Double.parseDouble(res[1].split(":")[3])/1000.0;
                                        time = Double.parseDouble(res[2].split(":")[1])/60.0;
                                        time = Math.floor(time);
                                        date_dest = date_dest + Long.parseLong(Integer.toString(time.intValue()))*60;
                                        Ride ride = new Ride(email,"driver",lat1+","+lng1,lat2+","+lng2,String.valueOf(date_from),String.valueOf(date_dest),6);
                                        userApi = retrofit.create(UserApi.class);
                                        mCall = userApi.requestSetup(ride.email,ride.user_type,ride.nop,ride.start_location,ride.end_location,ride.start_time,ride.end_time);
                                        mCall.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                dialog.show();
                                                dialog.setMessage("Posting Ride....");
                                                if(response.body()!=null) {
                                                    String res = response.body();
                                                    //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                                                    String []arr = res.split(",");

                                                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();

                                                }
                                                else {
                                                    dialog.dismiss();
                                                    Toast.makeText(getApplicationContext(),"Response Null",Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();


                                            }
                                        });

                                        Log.d("DISTANCE AND TIME",distance + " " + time);

                                        Toast.makeText(getApplicationContext(),distance + " " + time,Toast.LENGTH_LONG).show();
                                        Log.d("FROM DEST",String.valueOf(date_from) + "  " + String.valueOf(date_dest) );

                                    }

                                    else
                                        Toast.makeText(getApplicationContext(),"Response null",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });


                        }
                        else if(k==1 && error!=1) {

                            date_from = date_find.getTime()/1000;
                            date_dest = date_find.getTime()/1000;

                            Retrofit retrofit1 = new Retrofit.Builder()
                                    .baseUrl("https://api.tomtom.com/routing/1/calculateRoute/" + lat1 + "," + lng1 + ":" +lat2 + "," + lng2  + "/")
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();

                            userApi = retrofit1.create(UserApi.class);
                            Call<String> call = userApi.requestDistance("F7KXUjsM4wu8uAZtJCQ4eCYGdjWxRqYj");
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.body()!=null) {
                                        // Log.d("MYFILE",response.body());
                                        String []res = response.body().split(",");
                                        distance = Double.parseDouble(res[1].split(":")[3])/1000.0;
                                        time = Double.parseDouble(res[2].split(":")[1])/60.0;
                                        time = Math.floor(time);

                                        date_dest = date_dest + Long.parseLong(Integer.toString(time.intValue()))*60;

                                        Ride ride = new Ride(email,"passenger",lat1+","+lng1,lat2+","+lng2,String.valueOf(date_from),String.valueOf(date_dest),Integer.parseInt(seat.getText().toString()));
                                        userApi = retrofit.create(UserApi.class);
                                        mCall = userApi.requestSetup(ride.email,ride.user_type,ride.nop,ride.start_location,ride.end_location,ride.start_time,ride.end_time);
                                        mCall.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                dialog.show();

                                                dialog.setMessage("Searching for rides nearby... ");
                                                if(response.body()!=null) {
                                                    String res = response.body();

                                                    String []arr = res.split(",");
                                                    n=arr.length;
                                                    //Toast.makeText(getApplicationContext(),arr[0].split(":")[1],Toast.LENGTH_LONG).show();
                                                    for(int i=0;i<arr.length;i++) {
                                                        String myarr = arr[i].split(":")[1];
                                                        myarr.replace("\"","");
                                                        Log.d("one",myarr);
                                                        String myarr1 = myarr.split("/")[0];
                                                        String myarr2 = myarr.split("/")[1];
                                                        lat_1 =  Double.parseDouble(myarr1.split("x")[0].replace("\"",""));
                                                        lng_1 = Double.parseDouble(myarr1.split("x")[1].replace("\"",""));
                                                        Log.d("latlng",lat1 + " " + lng1);
                                                        LatLng latLng = new LatLng(lat1,lng1);
                                                        fromcoord.add(i,latLng);
                                                        lat_2 =  Double.parseDouble(myarr2.split("x")[0].replace("\"",""));
                                                        lng_2 = Double.parseDouble(myarr2.split("x")[1].replace("\"",""));
                                                        latLng = new LatLng(lat2,lng2);
                                                        Log.d("latlng",lat2 + " " + lng2);
                                                        tocoord.add(i,latLng);
                                                        driver_name.add(i,myarr.split("/")[2].replace("\"",""));
                                                    }
                                                    if(n>=1) {
                                                        check(lat_1, lng_1, lat_2, lng_2);
                                                    }
                                                    else {
                                                        Toast.makeText(getApplicationContext(),"No response",Toast.LENGTH_LONG).show();

                                                    }
                                                    Toast.makeText(getApplicationContext(),String.valueOf(n) + "number resp",Toast.LENGTH_LONG).show();


                                                }
                                                else {
                                                    dialog.dismiss();
                                                    Toast.makeText(getApplicationContext(),"Response Null",Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                dialog.dismiss();
                                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();


                                            }
                                        });

                                        Log.d("DISTANCE AND TIME",distance + " " + time);

                                        Log.d("FROM DEST",String.valueOf(date_from) + "  " + String.valueOf(date_dest) );

                                        Toast.makeText(getApplicationContext(),distance + " " + time,Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Response null",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });



                        }

                    }

                }
            });



        }
    }
    void check(double lat1,double lng1,double lat2,double lng2) {
        dialog.show();

        for(int i=0;i<n;i++) {
            k1 = 0;
            p1 = 0;
            LatLng loc1 = new LatLng(fromcoord.get(i).getLatitude(), fromcoord.get(i).getLongitude());
            LatLng loc2 = new LatLng(tocoord.get(i).getLatitude(), tocoord.get(i).getLongitude());

            RoutingApi routingApi = OnlineRoutingApi.create(getApplicationContext());
            RouteQuery routeQuery = new RouteQueryBuilder(loc1, loc2).withRouteType(RouteType.FASTEST).build();
            routingApi.planRoute(routeQuery)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(routeResult -> {

                        for (FullRoute fullRoute : routeResult.getRoutes()) {
                            RouteBuilder routeBuilder = new RouteBuilder(
                                    fullRoute.getCoordinates());
                            for (int j = 0; j < routeBuilder.getCoordinates().size(); j++) {
                                Log.d("routecoord", routeBuilder.getCoordinates().get(j).getLatitudeAsString() + "  " + routeBuilder.getCoordinates().get(j).getLongitudeAsString());
                                String l1 = routeBuilder.getCoordinates().get(j).getLatitudeAsString();
                                String l2 = routeBuilder.getCoordinates().get(j).getLongitudeAsString();
                                if (String.valueOf(lat1) == l1 && String.valueOf(lng1) == l2) {
                                    k1 = 1;
                                    if (p1 == 1)
                                        break;
                                }
                                if (String.valueOf(lat1) == l1 && String.valueOf(lng1) == l2) {
                                    p1 = 1;
                                    if (k1 == 1)
                                        break;
                                }
                            }

                        }
                    });
            if (k1 == 1 && p1 == 1) {
                dialog.dismiss();

                userApi = retrofit.create(UserApi.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                String email = pref.getString("email","none");
                mCall = userApi.requestPair(email,driver_name.get(i),lat1 + "," + lng1,lat2 + "," +lng2,String.valueOf(date_from),String.valueOf(date_dest));
                mCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body()!=null) {
                            Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Null resp",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
                // add post method


                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("driver", driver_name.get(0));
                intent.putExtra("from",from.getText().toString());
                intent.putExtra("to",to.getText().toString());
                intent.putExtra("date",datetime);
                intent.putExtra("distance",distance);
                intent.putExtra("time",time);
                startActivity(intent);
                finish();
            }
            dialog.dismiss();
        }
        dialog.dismiss();

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("driver", driver_name.get(0));
        intent.putExtra("from",from.getText().toString());
        intent.putExtra("to",to.getText().toString());
        intent.putExtra("date",datetime);
        intent.putExtra("distance",distance);
        intent.putExtra("time",time);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void getdate() {
        datetime = date = new String();

        value = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(value);
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        view.setMinDate(System.currentTimeMillis()-1000);
                        cal.set(Calendar.YEAR, y);
                        cal.set(Calendar.MONTH, m);
                        cal.set(Calendar.DAY_OF_MONTH, d);
                        if(d/10==0)
                            datetime = y + "-0" + d + "-";
                        else
                            datetime = y + "-" + d + "-";
                        if(m/10==0)
                            datetime = datetime + "0" + m + " ";
                        else
                            datetime = datetime + m + " ";

                        date = datetime;

                        // now show the time picker
                        new TimePickerDialog(MainActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int h, int min) {
                                        cal.set(Calendar.HOUR_OF_DAY, h);
                                        cal.set(Calendar.MINUTE, min);
                                        value = cal.getTime();
                                        if(h/10==0) {
                                            datetime = datetime + "0" +  h + ":";
                                        }
                                        else
                                            datetime = datetime + h + ":";
                                        if(min/10==0) {
                                            datetime = datetime + "0" + min + ":00";
                                        }
                                        else
                                            datetime = datetime + min + ":00" ;

                                        dt.setText(datetime);
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss");
                                        try {
                                            date_find = formatter.parse(datetime);
                                            date_get = date_find;
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, cal.get(Calendar.HOUR_OF_DAY),
                                cal.get(Calendar.MINUTE), true).show();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show();

        //datetime = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(cal.get(Calendar.MONTH)) + "/" + cal.get(Calendar.YEAR) +  " , " + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) ;


    }

 }