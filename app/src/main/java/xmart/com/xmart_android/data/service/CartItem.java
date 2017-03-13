package xmart.com.xmart_android.data.service;

import com.google.gson.Gson;



public class CartItem {
    private String Id;
    private String ProductId;
    private String ProductName;
    private String Price;
    private String Quantity;
    private String Amount;

    /**
     * chuyen doi object thanh json
     * @param cartItem
     * @return
     */
    public String objectToJson(CartItem cartItem) {
        Gson gson = new Gson();
        String json = gson.toJson(cartItem);
        return json;
    }

    /**
     * chuyen json thanh object
     * @param json
     * @return
     */
    public CartItem jsonToObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CartItem.class);
    }

    public CartItem[] jsonToArrayObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CartItem[].class);
    }

    public CartItem() {
    }

    public CartItem(String id, String productId, String productName, String price, String quantity, String amount) {
        Id = id;
        ProductId = productId;
        ProductName = productName;
        Price = price;
        Quantity = quantity;
        Amount = amount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
