package com.example.noworderfoodapp.view.act;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.OnMapCallBack;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityMapBinding;
import com.example.noworderfoodapp.entity.OrderAddressEntity;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.User;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.view.adapter.OrderItemsAdapter;
import com.example.noworderfoodapp.view.fragment.MapManager;
import com.example.noworderfoodapp.viewmodel.MapViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends BaseActivity<ActivityMapBinding, MapViewModel> implements OnMapCallBack, OrderAdapter.OnItemClick {
    private List<Orders> listOrder;

    private User user;
    private OrderAdapter orderAdapter;
    private List<OrderAddressEntity>  orderAddressList;
    private Map<String, List<Orders>>  shopAddressOrdersMap;

    @Override
    public void callBack(String key, Object data) {

    }

    @Override
    public void showAlert(String distance, LatLng start, LatLng end, String key) {
        if (user != null) {
            AlertDialog alert=new AlertDialog.Builder(this).create();
            alert.setTitle("Địa chỉ nhận hàng");
            alert.setMessage("Đến đó khoảng: "+distance);
            alert.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Chỉ đường", (dialog, which) -> showDirection(start,end));
            alert.show();
        } else {
            RecyclerView recyclerView = new RecyclerView(this);
            orderAdapter = new OrderAdapter(getOrderListFromKey(key),this);
            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(orderAdapter);
            orderAdapter.setOnItemClick(this);
            AlertDialog alert=new AlertDialog.Builder(this).create();
            alert.setTitle("Danh sách đơn hàng");
            alert.setMessage("Đến đó khoảng: "+distance);
            alert.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Chỉ đường", (dialog, which) -> showDirection(start,end));
            alert.setView(recyclerView);
            alert.show();
        }

    }

    private void showDirection(LatLng start, LatLng end) {
        String text=String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",start.latitude,start.longitude,end.latitude,end.longitude);
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(text));
        startActivity(intent);
    }

    @Override
    protected Class<MapViewModel> getViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    protected void initViews() {
        listOrder = new ArrayList<>();
        listOrder = (List<Orders>) getIntent().getSerializableExtra("list") ;
        user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {

        } else {
            filterDataOrder(listOrder);
        }
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (user != null) {
                    MapManager.getInstance().setUsers(user);
                } else {
                    MapManager.getInstance().setOrderAddressList(orderAddressList);
                }
                MapManager.getInstance().setMap(googleMap);
                MapManager.getInstance().initMap();
                MapManager.getInstance().setCallBack(MapActivity.this);
            }
        });

    }

    private List<Orders> getOrderListFromKey(String key){
        List<Orders> orders = new ArrayList<>();
        for (Map.Entry<String, List<Orders>> entry : shopAddressOrdersMap.entrySet()) {
            String pass = entry.getKey();
            List<Orders> orderList = entry.getValue();

            if (key.equals(pass)) {
                orders.addAll(orderList);
            }

        }
        return orders;
    }

    private void filterDataOrder(List<Orders> listOrder) {
        shopAddressOrdersMap = new HashMap<>();

        for (int i = 0; i < listOrder.size(); i++) {
            Orders order = listOrder.get(i);
            String shopName = order.getShopName();
            String address = order.getAddress();
            String key = shopName + "-" + address;

            // Kiểm tra xem cặp "shopName-address" đã tồn tại trong map hay chưa
            if (shopAddressOrdersMap.containsKey(key)) {
                // Nếu đã tồn tại, lấy danh sách đơn hàng tương ứng và thêm đơn hàng mới vào
                List<Orders> orderList = shopAddressOrdersMap.get(key);
                orderList.add(order);
            } else {
                // Nếu chưa tồn tại, tạo danh sách mới và thêm vào map
                List<Orders> orderList = new ArrayList<>();
                orderList.add(order);
                shopAddressOrdersMap.put(key, orderList);
            }
        }

// Duyệt qua các cặp "shopName-address" trong map và tạo các đối tượng PlaceEntity tương ứng
        orderAddressList = new ArrayList<>();
        for (Map.Entry<String, List<Orders>> entry : shopAddressOrdersMap.entrySet()) {
            String key = entry.getKey();
            List<Orders> orderList = entry.getValue();

            // Tách giá trị "shopName-address" từ key
            String[] parts = key.split("-");
            String shopName = parts[0];
            String address = parts[1];

            // Tiếp tục xử lý để lấy thông tin vị trí từ địa chỉ nếu cần thiết
            // ...

            // Tạo đối tượng PlaceEntity và thêm vào danh sách
            orderAddressList.add(new OrderAddressEntity(shopName, address, orderList));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void onItemClick(Orders orders) {
        if (orders.getStates().equals("Delivery")) {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Nhận đơn hàng này");
            alert.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Nhận đơn", (dialog, which) -> {
                        receiverOrder(orders);
                        alert.cancel();
                    });
            alert.show();
        } else if (orders.getStates().equals("Receiver")) {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Hoàn thành đơn hàng này");
            alert.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Hoàn thành", (dialog, which) -> {
                        doneOrder(orders);
                        alert.cancel();
                    });
            alert.show();
        }

    }

    private void doneOrder(Orders orders) {
        orders.setStates("Done");
        viewModel.updateOrder(orders);

    }
    private void receiverOrder(Orders orders){
        orders.setShipper(App.getInstance().getUser());
        orders.setStates("Receiver");
        viewModel.updateOrder(orders);

    }
}