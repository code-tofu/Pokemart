PokeMart Alpha
- first implementation of ecommerce design as part of TFIP-PAF Revision
- Data Entities/DTO shown below

public class Login

	private String email;
	private String password;
	+----------+-------------+------+-----+---------+-------+
	| Field    | Type        | Null | Key | Default | Extra |
	+----------+-------------+------+-----+---------+-------+
	| email    | varchar(30) | NO   |     | NULL    |       |
	| password | varchar(30) | NO   |     | NULL    |       |
	+----------+-------------+------+-----+---------+-------+
	2 rows in set 

public class item	

	private int item_id;
	private String name_id;
	private String name;
	private double cost;
	private String description;
	private String category;
	+-------------+--------------+------+-----+---------+-------+
	| Field       | Type         | Null | Key | Default | Extra |
	+-------------+--------------+------+-----+---------+-------+
	| item_id     | int          | NO   | PRI | NULL    |       |
	| name_id     | varchar(50)  | NO   |     | NULL    |       |
	| name        | varchar(50)  | NO   |     | NULL    |       |
	| cost        | double(8,2)  | NO   |     | 0.00    |       |
	| description | varchar(250) | YES  |     | NULL    |       |
	| category    | varchar(50)  | YES  |     | NULL    |       |
	+-------------+--------------+------+-----+---------+-------+
	6 rows in set 

public class Quantity

    private int item_id;
    private int item_qty;
	+----------+------+------+-----+---------+-------+
	| Field    | Type | Null | Key | Default | Extra |
	+----------+------+------+-----+---------+-------+
	| item_id  | int  | NO   | PRI | NULL    |       |
	| quantity | int  | NO   |     | NULL    |       |
	+----------+------+------+-----+---------+-------+
	2 rows in set

PokeApi Item Json Structure

	{
	    "cost": 0,
	    "category": {
	    "name": "standard-balls",
	    }
	    "flavor_text_entries": [
	            {
	                "language": {
	                    "name": "en",
	                    "url": "https://pokeapi.co/api/v2/language/9/"
	                },
	                "text": "The best BALL that\ncatches a POKéMON\nwithout fail.",
	                "version_group": {
	                    "name": "ruby-sapphire",
	                    "url": "https://pokeapi.co/api/v2/version-group/5/"
	                }
	    }, ...
	    ],
	    "id": 1,
	    "name": "master-ball",
	    "names": [
	    {
	        "language": {
	            "name": "en",
	            "url": "https://pokeapi.co/api/v2/language/9/"
	        },
	        "name": "Master Ball"
	    },...
	    ],
	    "sprites": {
	    "default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/master-ball.png"
	    }
	}
