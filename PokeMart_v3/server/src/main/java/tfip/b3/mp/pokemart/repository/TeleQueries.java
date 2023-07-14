package tfip.b3.mp.pokemart.repository;

public class TeleQueries {

    public static final String EXISTS_PRODUCT_BY_PRODUCT_ID = """
    SELECT EXISTS(SELECT * FROM product_data WHERE product_id = ?)
    """;
    
}
