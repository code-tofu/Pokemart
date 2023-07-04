package tfip.b3.mp.pokemart.repository;

public class ProductQueries {

    public static final String INSERT_NEW_PRODUCT = """
        INSERT INTO product_data (product_id, api_id, name_id, category,cost,description,product_name) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SELECT_PRODUCT_BY_API_ID = """
        SELECT * FROM product_data WHERE api_id = ?
        """;

    public static final String SELECT_PRODUCT_BY_PRODUCT_ID = """
        SELECT * FROM product_data WHERE product_id = ?
        """;

    public static final String SELECT_ALL_PRODUCT_ID = """
            SELECT product_id FROM product_data LIMIT ? OFFSET ?
        """;

    public static final String INSERT_ATTRIBUTE = """
        INSERT INTO product_attributes (product_id,attribute) VALUES (?,?)
        """;

    public static final String SELECT_ATTRIBUTES_BY_PRODUCT_ID = """
        SELECT attribute FROM product_attributes WHERE product_id = ?
            """;

    public static final String EXISTS_PRODUCT_BY_PRODUCT_ID = """
        SELECT EXISTS(SELECT * FROM product_data WHERE product_id = ?)
        """;

    public static final String EXISTS_PRODUCT_BY_API_ID = """
        SELECT EXISTS(SELECT * FROM product_data WHERE api_id = ? )
        """;

}
