## API JSON Reference
See https://pokeapi.co/docs/v2#items-section and https://pokeapi.co/api/v2/item/1

https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/master-ball.png
https://tofu-pokemart.sgp1.digitaloceanspaces.com/tofu-pokemart/sprite/stardust

## model/UserDetailsImpl
Note that Interface UserDetails has a reference implementation Class User:
* String username
* String password
* boolean enabled
* boolean accountNonExpired
* boolean credentialsNonExpired
* boolean accountNonLocked
* Collection<? extends GrantedAuthority> authorities

UserDetailsImpl references this and adds userID to match the database schema.
- Note that authorities is a list but the implementation is only single-valued i.e. based on role, as indicated in the database schema.

## config/WebSecurityConfig
DaoAuthenticationProvider daoAuthProvider():
Bcrypt is the preferred implementation of PasswordEncoder

SecurityFilterChain filterChain():
* csrf() methods are deprecated and now use (Customizer)
* authorizeHttpRequests() methods are deprecated and now use (Customizer)
* sessionManagement() methods are deprecated and now use (Customizer)
* exceptionHandling() methods are deprecated and now use (Customizer)
* antmatchers are deprecated for request matchers

## security/OncePerRequestFilterImpl

## utils/JWTUtils
- UserDetails interface is used instead of UserdDetailsImpl to allow for this util to be used by other implementations
- generateToken is overloaded to allow for custom claims to be used in the future

## service/UserService
- userService is the implementation for UserDetailsService, but also has other methods withr regards to UserProfiles

## model/CatalogueItemDTO
* quantity can be refer to either inventory stock or quantity purchased


## repository/CartRepository
OpsForHash.delete returns the number of fields that were removed.
OpsForHash.increment returns value at field after increment


## nav-top
router.events do not need to be unsubscribed  
