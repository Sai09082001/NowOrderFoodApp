package com.example.noworderfoodapp.view.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.OnMapCallBack;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.entity.OrderAddressEntity;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.PlaceEntity;
import com.example.noworderfoodapp.entity.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapManager implements GoogleMap.OnInfoWindowClickListener {
    private static final long TIME_DURATION = 2000;
    private static MapManager instance;
    private GoogleMap mMap;
    private Marker myPos;
    private OnMapCallBack mCallBack;

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    private User user;
    private List<OrderAddressEntity>  orderAddressList;

    public List<OrderAddressEntity> getOrderAddressList() {
        return orderAddressList;
    }

    public void setOrderAddressList(List<OrderAddressEntity> orderAddressList) {
        this.orderAddressList = orderAddressList;
    }

    private List<PlaceEntity> listPlace;

    private MapManager() {

    }

    public static MapManager getInstance() {
        if (instance == null) {
            instance = new MapManager();
        }
        return instance;
    }

    public void setCallBack(OnMapCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void initMap() {
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setInfoWindowAdapter(initWindow());
        mMap.setOnInfoWindowClickListener(this);
        if (ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        findMyLocation();
        // for dummy data
        if (getUsers() != null) {
            initPlacesUser() ;
        } else {
            initPlaces();
        }

        addPlaceToMap();
    }

    private GoogleMap.InfoWindowAdapter initWindow() {
        return new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return initViewAdapter(marker);
            }

            @Override
            public View getInfoContents(Marker marker) {
                return initViewAdapter(marker);
            }
        };
    }

    private View initViewAdapter(Marker marker) {
        if (marker.getTag() == null) return null;
        PlaceEntity place = (PlaceEntity) marker.getTag();
        View view = LayoutInflater.from(App.getInstance()).inflate(R.layout.item_view_info, null);
        ImageView ivPlace = view.findViewById(R.id.iv_place);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvAddress = view.findViewById(R.id.tv_address);
        TextView tvContent = view.findViewById(R.id.tv_content);
//        Glide.with(App.getInstance()).load(""+ ApiClient.BASE_URL+"/user/download?filename="+
//                App.getInstance().getUser().getAvatarUrl()).into(ivPlace);
        if (getUsers() != null) {
            ivPlace.setImageResource(R.drawable.ic_user_receiver);
        } else {
            ivPlace.setImageResource(R.drawable.ic_now_shop);
        }
        tvName.setText(place.getName());
        tvAddress.setText(place.getAddress());
        if (getUsers() != null) {
            tvContent.setText("Giao tới cho " + user.getName());
        } else {
            tvContent.setText("Shop có đơn hàng cho bạn");
        }

        return view;
    }

    private void initPlaces() {
        listPlace = new ArrayList<>();
        //getLatLngUser();
        getLatLngFromAddress();
//        listPlace.add(new PlaceEntity(new LatLng(21.02893496469526, 105.85217157797551),
//                App.getInstance().getString(R.string.txt_ho_guom),
//                App.getInstance().getString(R.string.txt_ho_guom_address),
//                App.getInstance().getString(R.string.txt_ho_guom_content), R.drawable.ic_quan_an_namfood));
//        listPlace.add(new PlaceEntity(new LatLng(21.036970133569955, 105.83463051050553),
//                App.getInstance().getString(R.string.txt_lang_bac),
//                App.getInstance().getString(R.string.txt_lang_bac_address),
//                App.getInstance().getString(R.string.txt_lang_bac_content), R.drawable.bg_lang_bac));

    }

    private void initPlacesUser() {
        listPlace = new ArrayList<>();
        //getLatLngUser();
        getLatLngUser();
    }

    private void getLatLngUser() {
        //String address = "162 Chiến Thắng, Hà Đông, Hà Nội";
        Geocoder geocoder = new Geocoder(App.getInstance());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(getUsers().getHomeAddress(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                double latitude = addressResult.getLatitude();
                double longitude = addressResult.getLongitude();
                // LatLng latLng = new LatLng(latitude, longitude);
                listPlace.add(new PlaceEntity(new LatLng(latitude, longitude),
                        getUsers().getUsername(),
                        getUsers().getHomeAddress(),
                        "" + ApiClient.BASE_URL + "/user/download?filename=" +
                                App.getInstance().getUser().getAvatarUrl()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLatLngFromAddress() {
       //String address = "162 Chiến Thắng, Hà Đông, Hà Nội";
        Geocoder geocoder = new Geocoder(App.getInstance());
        List<Address> addresses = null;
        for (int i = 0; i < getOrderAddressList().size(); i++) {

            try {
                addresses = geocoder.getFromLocationName(getOrderAddressList().get(i).getAddress(), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    double latitude = addressResult.getLatitude();
                    double longitude = addressResult.getLongitude();
                   // LatLng latLng = new LatLng(latitude, longitude);
                    listPlace.add(new PlaceEntity(new LatLng(latitude, longitude),
                            getOrderAddressList().get(i).getName(),
                            getOrderAddressList().get(i).getAddress(),
                            ""+ApiClient.BASE_URL+"/user/download?filename="+
                                    App.getInstance().getUser().getAvatarUrl()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocationName(address, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (addresses != null && !addresses.isEmpty()) {
//            Address addressResult = addresses.get(0);
//            double latitude = addressResult.getLatitude();
//            double longitude = addressResult.getLongitude();
//            LatLng latLng = new LatLng(latitude, longitude);
//            listPlace.add(new PlaceEntity(new LatLng(latitude, longitude),
//                    App.getInstance().getString(R.string.txt_ho_guom),
//                    App.getInstance().getString(R.string.txt_ho_guom_address),
//                    App.getInstance().getString(R.string.txt_ho_guom_content), R.drawable.ic_quan_an_namfood));
//            // Sử dụng tọa độ LatLng cho mục đích của bạn (ví dụ: hiển thị trên Map).
//        } else {
//            // Không tìm thấy địa chỉ hoặc có lỗi xảy ra.
//        }

    }


    private void updateMyLocation(LocationResult locationResult) {
        double lat = locationResult.getLastLocation().getLatitude();
        double lgn = locationResult.getLastLocation().getLongitude();
        if (myPos == null) {
            MarkerOptions marker = new MarkerOptions();
            marker.title("I'm here");
            marker.icon(BitmapDescriptorFactory.defaultMarker());
            marker.position(new LatLng(lat, lgn));
            String address = getAddress(lat, lgn);

            marker.snippet(address);

            myPos = mMap.addMarker(marker);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos.getPosition(), 16));
        }
        if (myPos.getPosition().latitude != lat || myPos.getPosition().longitude != lgn) {
            String address = getAddress(lat, lgn);
            myPos.setSnippet(address);
            myPos.setPosition(new LatLng(lat, lgn));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos.getPosition(), 16));
        }
        //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos.getPosition(),16));
        // Toast.makeText(App.getInstance(),"My pos"+lat+" - "+lgn,Toast.LENGTH_SHORT).show();
    }

    private String getAddress(double lat, double lgn) {
        try {
            Geocoder geocoder = new Geocoder(App.getInstance(), Locale.getDefault());
            List<Address> rs = geocoder.getFromLocation(lat, lgn, 1);
            if (rs != null && !rs.isEmpty()) {
                return rs.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không xác định";
    }

    @SuppressLint("VisibleForTests")
    private void findMyLocation() {
        FusedLocationProviderClient client
                = new FusedLocationProviderClient(App.getInstance());

        if (ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(App.getInstance(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationRequest req = new LocationRequest();
        req.setInterval(TIME_DURATION);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        client.requestLocationUpdates(req,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        updateMyLocation(locationResult);
                    }
                }, Looper.getMainLooper());
    }

    private void addPlaceToMap() {
        BitmapDescriptor iconPlace = BitmapDescriptorFactory.fromResource(R.drawable.ic_place);
        for (PlaceEntity place : listPlace) {
            MarkerOptions op = new MarkerOptions();
            op.title(place.getName());
            op.snippet(place.getAddress());
            op.icon(iconPlace);
            op.position(place.getLocation());
            Marker marker = mMap.addMarker(op);
            marker.setTag(place);
        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        PlaceEntity place = (PlaceEntity) marker.getTag();
        LatLng end = place.getLocation();
        LatLng start = myPos.getPosition();
        String key = place.getName() + "-" + place.getAddress();
        String distance = calcDistance(start, end);
        mCallBack.showAlert(distance, start, end, key);
    }

    private String calcDistance(LatLng start, LatLng end) {
        double lat1 = start.latitude;
        double lat2 = end.latitude;
        double lgn1 = start.longitude;
        double lgn2 = end.longitude;
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lgn2 - lgn1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return new DecimalFormat("#.#").format(d) + " km";
    }

    double deg2rad(double deg) {
        return deg * (Math.PI / 180);

    }
}
