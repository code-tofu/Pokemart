export interface StoreDTO {
    storeId: number,
    storeAddress: string,
    storeName: string,
    storePhone: string,
    storeLat: number,
    storeLng: number,
}

export interface DistanceDTO{
    distance: number,
    defaultCost: number,
    expressCost: number,
    duration: number,
}

export enum Shipping {
    DEFAULT,
    EXPRESS,
    SELFCOLLECT,
  }

export const SHIPPINGSTR = ['DEFAULT', 'EXPRESS', 'SELFCOLLECT'];