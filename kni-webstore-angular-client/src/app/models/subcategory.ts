import { Product } from './product';

export class SubCategory {
    id: number;
    name: string;
    products: Product[];

    constructor(name: string) {
        this.name = name;
        this.products = [];
    }
}
