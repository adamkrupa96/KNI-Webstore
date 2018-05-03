import { Feature } from './feature';

export class Product {
    id: number;

    constructor(
        public name?: string,
        public shortDescription?: string,
        public longDescription?: string,
        public brand?: string,
        public model?: string,
        public price?: number,
        public inStock?: number,
        public features?: Feature[],
    ) { }
}
