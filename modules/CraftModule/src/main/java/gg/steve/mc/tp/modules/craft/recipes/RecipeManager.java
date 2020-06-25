package gg.steve.mc.tp.modules.craft.recipes;

import gg.steve.mc.tp.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.tp.modules.craft.CraftModule;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    public static List<Recipe> recipes;

    public static void loadRecipes() {
        recipes = new ArrayList<>();
        YamlConfiguration config = FileManagerUtil.get(CraftModule.moduleConfigId);
        for (String recipe : config.getStringList("recipes")) {
            recipes.add(new Recipe(recipe));
        }
    }

    public static void shutdown() {
        if (recipes != null && !recipes.isEmpty()) recipes.clear();
    }

    public static int doCrafting(List<Inventory> inventories) {
        int crafted = 0;
        for (Inventory inventory : inventories) {
            for (Recipe recipe : recipes) {
                crafted += recipe.doCrafting(inventory);
            }
        }
        return crafted;
    }
}
