import { LatLng } from "./model/order.model";

export const inventoryURL = 'api/inventory/';
export const productURL = 'api/product/';
export const authURL = '/api/auth/';
export const userURL = '/api/user/';
export const salesURL = '/api/sales/';
export const cartURL = 'api/cart/';
export const orderURL = 'api/order/';

export const itemsPerPage: number = 10; //pagination vs offset done on server side
export const catPerPage: number = 15;

export const productImgURL: String = 
  'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/';
export const productImgType: String = '.png';

export const center_ISS: LatLng ={ lat: 1.2922698534387833, lng: 103.77658830919331}; 
export const API_KEY="AIzaSyAsBWXYc1Nj8K7BN7GSEyrmQAD_ClaU5C4"

export const GMAP_GEOCODE_URL: String = "https://maps.googleapis.com/maps/api/geocode/json?key="
export const GMAP_JS_URL: String = "https://maps.googleapis.com/maps/api/js?key="