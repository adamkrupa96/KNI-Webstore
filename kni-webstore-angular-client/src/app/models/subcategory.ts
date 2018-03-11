import { Product } from './Product';

export class SubCategory {
  constructor(
    public id?: number,
    public name?: string,
    public products?: Product[]
  ) {}
}
