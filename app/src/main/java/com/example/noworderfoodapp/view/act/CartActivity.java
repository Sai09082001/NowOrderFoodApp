package com.example.noworderfoodapp.view.act;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityCartBinding;
import com.example.noworderfoodapp.entity.OrderItems;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.adapter.OrderItemsAdapter;
import com.example.noworderfoodapp.view.adapter.ShopDetailAdapter;
import com.example.noworderfoodapp.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends BaseActivity<ActivityCartBinding, CartViewModel>{
    private ArrayList<OrderItems> orderItems;
    private Orders orders;
    private Shop shop;
    public HashMap<Integer, Integer> getProductMap() {
        return productMap;
    }
    private List<Products> productsList = new ArrayList<>();
    private OrderItemsAdapter orderItemsAdapter;

    public void setProductMap(HashMap<Integer, Integer> productMap) {
        this.productMap = productMap;
    }

    private HashMap<Integer, Integer> productMap = new HashMap<>();

    @Override
    protected Class<CartViewModel> getViewModelClass() {
        return CartViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initViews() {
        int sumOrder = 0;
        orderItems = new ArrayList<>();
        shop = (Shop) getIntent().getSerializableExtra(ShopDetailActivity.SHOP_NAME) ;
        productsList = (List<Products>) getIntent().getSerializableExtra(ShopDetailActivity.LIST_PRODUCT);
        productMap = (HashMap<Integer, Integer>)getIntent().getSerializableExtra(ShopDetailActivity.DATA_ORDER_FRAGMENT);
        for (Map.Entry<Integer, Integer> entry : getProductMap().entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            boolean isDuplicate = false;
            for (int i = 0; i < productsList.size(); i++) {
                if (productId == productsList.get(i).getId()) {
                    // Kiểm tra xem mục đã tồn tại trong danh sách orderItems chưa
                    for (OrderItems orderItem : orderItems) {
                        if (orderItem.getProduct().getId() == productId) {
                            // Mục đã tồn tại, không thêm vào orderItems nữa
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        // Mục không tồn tại trong danh sách, thêm vào orderItems
                        orderItems.add(new OrderItems(productsList.get(i), quantity, productsList.get(i).getPrice() * quantity));
                    }

                    // Đặt lại biến cờ
                    isDuplicate = false;
                }
            }
            // Tìm sản phẩm tương ứng trong danh sách sản phẩm
            Log.i("KMFG", "Cart productID: "+productId+"-quantity"+quantity);
            sumOrder++;
        }
        binding.tvSumOrder.setText(""+sumOrder+" món");
        binding.tvMoneyOrder.setText(calculateTotalPrice()+"");
        double sumMoney = calculateTotalPrice()+15000;
        binding.tvSumMoney.setText(sumMoney+"");
        binding.tvOrderConfirm.setText("Đặt đơn : "+sumMoney);
        orderItemsAdapter = new OrderItemsAdapter(orderItems,this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvOrders.setLayoutManager(manager);
        binding.rcvOrders.setAdapter(orderItemsAdapter);
        binding.tvOrderConfirm.setOnClickListener(view -> {
//            List<String> states = new ArrayList<>();
//            states.add("Delivery");
            orders = new Orders(orderItems,sumMoney,binding.edtComment.getText().toString(),
                                App.getInstance().getUser(),shop.getName(),"Delivery",shop.getAddress());
            viewModel.postOrdersData(orders);
            App.getInstance().setOrder(false);
            finish();
        });
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;

        // Duyệt qua các sản phẩm trong HashMap
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            // Tìm sản phẩm tương ứng trong danh sách sản phẩm
            for (Products product : productsList) {
                if (product.getId() == productId) {
                    // Tính tổng giá tiền bằng cách nhân giá tiền của sản phẩm với số lượng
                    totalPrice += product.getPrice() * quantity;
                    break;
                }
            }
        }

        return totalPrice;
    }


    @Override
    public void callBack(String key, Object data) {

    }

}