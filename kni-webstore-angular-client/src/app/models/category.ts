import { SubCategory } from './subcategory';

export class Category {
    id: number;
    constructor(
        public name?: string,
        public subCategories?: SubCategory[],
    ) { }
}
