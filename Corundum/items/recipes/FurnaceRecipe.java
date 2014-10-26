package Corundum.items.recipes;

import Corundum.utils.interfaces.IDedType;

public class FurnaceRecipe extends Recipe {
    private final IDedType cooking_material;

    public FurnaceRecipe(IDedType cooking_material, IDedType resulting_material) {
        super(new IDedType[] { cooking_material }, resulting_material, 1);

        this.cooking_material = cooking_material;
    }
}
