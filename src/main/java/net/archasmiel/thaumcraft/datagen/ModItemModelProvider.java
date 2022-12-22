package org.bedracket.thaumcraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.bedracket.thaumcraft.ThaumcraftMod;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ThaumcraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
