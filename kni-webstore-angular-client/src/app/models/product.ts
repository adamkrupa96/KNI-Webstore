import { Feature } from './feature';

export class Product {
    id: number;
    brand: string;
    model: string;
    price: number;
    inStock: number;
    features: Feature[];
}
