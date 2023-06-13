## CONTROLLERS

### Admin Controller

-

## SERVICES

### CentralDB Service

- Product DB stores all the product data retrieved from pokeAPI
- Accessed through Admin controller
- MAX_ITEM_ID is the range of products accessed from the pokeapi database

1. get JSON information from PokeApi based on random generated number
2. parse from JSON to DB Schema DAO
3. insert into DB using jdbc template
4. repeat based on size of database required

## UTILS
