export const inventoryURL = 'api/inventory/';
export const productURL = 'api/product/';
export const authURL = '/api/auth/';
export const userURL = '/api/user/';
export const salesURL = '/api/sales/';
export const cartURL = 'api/cart/';
export const orderURL = 'api/order/';
export const mapURL = 'api/map/';
export const locationURL = 'api/location/';

export const itemsPerPage: number = 10; //pagination vs offset done on server side
export const catPerPage: number = 15;

// export const productImgURL: String ='https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
export const productImgURL: string = 'https://tofu-pokemart.sgp1.digitaloceanspaces.com/tofu-pokemart/sprite/'
export const productImgType: string = '.png';
export const landingImgURL: string = "https://tofu-pokemart.sgp1.digitaloceanspaces.com/main/landing/";

export const TOKEN_KEY = 'jwt-token';
export const USER_KEY = 'jwt-user';
export const NAME_KEY = 'jwt-user-name';
export const THEME_KEY = 'user-theme';

export const STRIPE_PKEY="pk_test_51NVTe7LGmrz8ddRe7vonmfHtfKnjFEVYr1ayaL8BD0dUNc7SymwixZxTJesrBK9gHIPAu9mm17JG5XErLBI05QF100T3Z8Dq24"

export const HQ_ISS: google.maps.LatLngLiteral ={ lat: 1.2923021783210697, lng: 103.77657727116461}; 
export const GMAP_GEOCODE_URL: string = "https://maps.googleapis.com/maps/api/geocode/json?key="
export const GMAP_JS_URL: string = "https://maps.googleapis.com/maps/api/js?key="
export const API_KEY="AIzaSyAsBWXYc1Nj8K7BN7GSEyrmQAD_ClaU5C4"

export const interceptIgnore = [
    'https://maps.googleapis.com/maps',
    'https://tofu-pokemart.sgp1.digitaloceanspaces.com',
]
