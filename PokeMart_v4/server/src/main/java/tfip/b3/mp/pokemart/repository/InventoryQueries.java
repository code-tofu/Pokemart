package tfip.b3.mp.pokemart.repository;

public class InventoryQueries {

    public static final String INSERT_NEW_INVENTORY_ITEM = "INSERT INTO inventory (product_id,stock) VALUES (?,?)";

    public static final String UPDATE_INVENTORY_ITEM_DISCOUNT ="UPDATE inventory SET discount = ? WHERE product_id = ?";

    public static final String UPDATE_INVENTORY_ITEM_COMMENTS ="UPDATE inventory SET comments = ? WHERE product_id = ?";

    public static final String UPDATE_INVENTORY_ITEM_STOCK ="UPDATE inventory SET stock = ? WHERE product_id = ?";

    public static final String SELECT_INVENTORY_ITEM_DETAILS_BY_ID="""
        SELECT product_id, stock, discount, comments
        FROM inventory
        WHERE product_id = ?
        """;

    public static final String SELECT_CATALOGUE_ITEM_WLIMIT = """
        SELECT product_data.product_id, product_data.name_id, product_data.product_name,
        product_data.cost, inventory.stock, inventory.discount,inventory.comments
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0
        LIMIT ? OFFSET ? """;

    public static final String SELECT_CATALOGUE_ITEM_BY_ID = """
        SELECT product_data.product_id, product_data.name_id, product_data.product_name,
        product_data.cost, inventory.stock, inventory.discount, inventory.comments
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE product_data.product_id = ? """;

    public static final String SELECT_ALL_CATEGORIES_IN_INVENTORY = """
        SELECT product_data.category, COUNT(product_data.category) AS count
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0
        GROUP BY category
        ORDER BY category
        LIMIT ? OFFSET ?
        """;

    public static final String SELECT_INVENTORY_BY_CATEGORY_WLIMIT = """
        SELECT product_data.product_id,product_data.name_id,product_data.product_name,
        product_data.cost, inventory.stock, inventory.discount, inventory.comments
        FROM product_data 
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0 AND product_data.category = ?
        LIMIT ? OFFSET ?
        """;

    public static final String SELECT_INVENTORY_BY_SEARCH_WLIMIT = """
        SELECT product_data.product_id,product_data.name_id,product_data.product_name,
        product_data.cost, inventory.stock, inventory.discount, inventory.comments
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0 AND product_data.product_name LIKE ?
        LIMIT ? OFFSET ?
        """;

    public static final String EXISTS_INVENTORY_BY_PRODUCT_ID = """
        SELECT EXISTS(SELECT * from inventory WHERE product_id= ? )
        """;

    public static final String SELECT_STOCK_OF_PRODUCT = """
        SELECT stock FROM inventory WHERE product_id = ?
        """;

    public static final String SELECT_TOTAL_COUNT_ALL_PRODUCTS = """
        SELECT COUNT(product_id) FROM inventory WHERE stock>0
        """;

    public static final String SELECT_TOTAL_COUNT_CATEGORIES = """
    SELECT COUNT(DISTINCT category) FROM product_data 
    JOIN inventory ON product_data.product_id = inventory.product_id
    WHERE inventory.stock>0
    """;

    public static final String SELECT_TOTAL_COUNT_BY_CATEGORY = """
        SELECT COUNT(product_data.product_id)
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0 AND product_data.category = ?
        """;

    public static final String SELECT_TOTAL_COUNT_BY_SEARCH = """
        SELECT COUNT(product_data.product_id)
        FROM product_data
        JOIN inventory ON product_data.product_id = inventory.product_id
        WHERE inventory.stock>0 AND product_data.product_name LIKE ?
        """;

}
