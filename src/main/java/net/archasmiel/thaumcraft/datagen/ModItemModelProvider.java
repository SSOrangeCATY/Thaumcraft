package net.archasmiel.thaumcraft.datagen;

import net.archasmiel.thaumcraft.ThaumcraftMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ThaumcraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
