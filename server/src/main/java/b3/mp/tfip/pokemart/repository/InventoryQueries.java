package b3.mp.tfip.pokemart.repository;

public class InventoryQueries {

    public static final String INSERT_NEW_INVENTORY_ITEM = "INSERT INTO inventory (product_id,quantity) VALUES (?,?)";

    public static final String SELECT_STOREITEMCOMPONENT_DATA_LIMITED = """
            SELECT product_data.product_id, product_data.name_id, product_data.product_name,
            product_data.cost, inventory.quantity, inventory.discount, inventory.deduct
            FROM product_data
            JOIN inventory ON product_data.product_id = inventory.product_id
            WHERE inventory.quantity>0
            LIMIT ? OFFSET ?;
            """;

    public static final String SELECT_ALL_CATEGORIES_IN_INVENTORY = """
            SELECT product_data.category, COUNT(product_data.category) AS count
            FROM product_data
            JOIN inventory ON product_data.product_id = inventory.product_id
            WHERE inventory.quantity>0
            GROUP BY category
            """;

    public static final String SELECT_INVENTORY_BY_CATEGORY = """
            SELECT product_data.product_id,product_data.name_id,product_data.product_name,
            product_data.cost, inventory.quantity, inventory.discount, inventory.deduct
            FROM product_data
            JOIN inventory ON product_data.product_id = inventory.product_id
            WHERE inventory.quantity>0 AND product_data.category = ?
                """;

    public static final String SELECT_QUANTITY_OF_PRODUCT = """
            SELECT quantity FROM inventoRY WHERE product_id = ?
            """;

}
