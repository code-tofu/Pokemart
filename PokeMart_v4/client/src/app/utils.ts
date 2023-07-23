import { inventoryURL,productImgURL,productImgType,productURL, itemsPerPage } from 'src/app/endpoint.constants';

export class Utils{
    static generateImgURL(nameID: string):string{
    if (!(nameID.includes('tm') || nameID.includes('hm'))) {
      return productImgURL + nameID + productImgType;
    } else {
      return '/api/img/tm.png';
    }
  }
}