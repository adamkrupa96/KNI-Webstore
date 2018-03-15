import { Feature } from './feature';

export class Product {
    id: number;
    brand: string;
    model: string;
    price: number;
    inStock: number;
    features: Feature[];

    constructor(brand: string, model: string, price: number, inStock: number, features: Feature[]) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.inStock = inStock;
        this.features = features;
    }
}
