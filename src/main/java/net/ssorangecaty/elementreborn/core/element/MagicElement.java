package net.ssorangecaty.elementreborn.core.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.ssorangecaty.elementreborn.ElementReborn;
import net.ssorangecaty.elementreborn.util.IResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class MagicElement {
    private final String name;
    private final ResourceLocation texture;
    public static final ResourceLocation UNKNOWN = IResourceLocation.create("textures/elements/blank.png");
    private final int blend;
    private final int color;
    private final Iterable<MagicElement> components;

    public MagicElement(String name, int color, @Nullable ResourceLocation texture, int blend, MagicElement... components) {
        this.name = name;
        this.components = List.of(components);
        this.color = color;
        this.texture = texture == null ? UNKNOWN : texture;
        this.blend = blend;
    }

    public MagicElement(String name, int color, @Nullable ResourceLocation texture, MagicElement... components) {
        this(name, color, texture == null ? UNKNOWN : texture, 1, components);
    }


    public MagicElement(String name, int color, int blend ,@Nullable ResourceLocation texture) {
        this(name, color, texture == null ? UNKNOWN : texture, blend);
    }

    public int getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public Component getTranslationText() {
        return Component.translatable("er.element." + this.name).withColor(this.color);
    }

    public Iterable<MagicElement> getComponents() {
        return this.components;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

    public int getBlend() {
        return this.blend;
    }

    public boolean isPrimal() {
        return this.getComponents() == null || ((Collection<MagicElement>) this.getComponents()).size() != 2;
    }

    public boolean isCompound() {
        return !this.isPrimal();
    }


    @Nullable
    public static MagicElement loadFromJson(JsonObject json) {
        if (!MagicElement.checkJson(json)) return null;
        String name = json.get("name").getAsString();
        ResourceLocation texture = ResourceLocation.parse(json.get("texture").getAsString());
        int color = json.get("color").getAsInt();
        int blend = json.get("blend").getAsInt();
        List<MagicElement> components = new ArrayList<>();
        if (json.has("components")) {
            for (JsonElement component : json.get("components").getAsJsonArray()) {
                components.add(ElementsRegistry.getElementByName(component.getAsString()));
            }
        }
        return new MagicElement(name, color, texture, blend, components.toArray(new MagicElement[2]));
    }

    public static boolean checkJson(JsonObject json) {
        if (!json.has("name") || !json.has("texture") || !json.has("color") || !json.has("blend")) {
            ElementReborn.LOGGER.error("Invalid JSON for MagicElement: " + json);
            return false;
        }
        return true;
    }
}
