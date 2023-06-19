



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

## security/OncePerRequestFilterImpl

## utils/JWTUtils
- UserDetails interface is used instead of UserdDetailsImpl to allow for this util to be used by other implementations
- generateToken is overloaded to allow for custom claims to be used in the future