import { Product } from './product';

export class SubCategory {
    id: number;
    constructor(
        public name?: string,
        public products?: Product[],
    ) { }
}
