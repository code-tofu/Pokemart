package b3.mp.tfip.pokemart.model;

public class ProductDAO {

    public static final String SPRITE_API_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
    public static final String SPRITE_TYPE = ".png";

    private String productID;
    private int apiID;
    private String nameID;
    private String category;
    private double cost;
    private String details;
    private String productName;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getApiID() {
        return apiID;
    }

    public void setApiID(int apiID) {
        this.apiID = apiID;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductDAO() {
    }

    public ProductDAO(String productID, int apiID, String nameID, String category, double cost, String details,
            String productName) {
        this.productID = productID;
        this.apiID = apiID;
        this.nameID = nameID;
        this.category = category;
        this.cost = cost;
        this.details = details;
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductDAO [productID=" + productID + ", apiID=" + apiID + ", nameID=" + nameID + ", category="
                + category + ", cost=" + cost + ", details=" + details + ", productName=" + productName + "]";
    }

}
