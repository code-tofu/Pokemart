package b3.mp.tfip.pokemart.repository;

public class ProductQueries {

    public static final String INSERT_NEW_PRODUCT = "INSERT INTO product_data (product_id, api_id, name_id, category,cost,details,product_name) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_PRODUCT_BY_API_ID = "SELECT * FROM product_data WHERE api_id = ? ";

    public static final String SELECT_PRODUCT_BY_PRODUCT_ID = "SELECT * FROM product_data WHERE product_id = ? ";

    public static final String SELECT_ALL_PRODUCT_ID = "SELECT product_id FROM product_data LIMIT ? OFFSET ?";

}
