import { SubCategory } from './subcategory';

export class Category {
    id?: number;
    name?: string;
    subCategories?: SubCategory[];

    constructor(name: string) {
        this.name = name;
        this.subCategories = [];
    }

}
