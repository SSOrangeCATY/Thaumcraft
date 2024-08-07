package net.ssorangecaty.elementreborn.core.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.ssorangecaty.elementreborn.element.ERMagicElements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageElements {
    public static final Codec<StorageElements> CODEC = ExtraCodecs.JSON.comapFlatMap(x -> {
        try {
            Map<MagicElement, Float> map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : x.getAsJsonObject().asMap().entrySet())
                map.put(ElementsRegistry.getElement(entry.getKey()), entry.getValue().getAsFloat());
            return DataResult.success(new StorageElements(map));
        } catch (Exception e) {
            return DataResult.error(() -> "Cannot parse Json: " + e);
        }
    }, elements -> {
        JsonObject obj = new JsonObject();
        for (Map.Entry<MagicElement, Float> entry : elements.elements.entrySet())
            obj.add(ElementsRegistry.getId(entry.getKey()).toString(), new JsonPrimitive(entry.getValue()));
        return obj;
    });
    public static final StreamCodec<FriendlyByteBuf, StorageElements> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull StorageElements decode(FriendlyByteBuf p_320376_) {
            return new StorageElements(p_320376_.readNbt());
        }

        @Override
        public void encode(FriendlyByteBuf p_320158_, StorageElements p_320396_) {
            CompoundTag tag = new CompoundTag();
            p_320396_.writeToNBT(tag);
            p_320158_.writeNbt(tag);
        }
    };

    private final Map<MagicElement, Float> elements;

    public StorageElements(Map<MagicElement, Float> elements) {
        this.elements = elements;
    }

    public StorageElements(CompoundTag tag) {
        this.elements = new HashMap<>();
        this.readFromNBT(tag);
    }

    public float getElementValue(MagicElement element) {
        return elements.get(element);
    }

    public float getOrDefault(MagicElement element) {
        return elements.getOrDefault(element, 0f);
    }

    public List<MagicElement> getElements() {
        return elements.keySet().stream().toList();
    }

    public boolean containsElement(MagicElement element) {
        return elements.containsKey(element);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }


    public void clear() {
        elements.clear();
    }

    public void addElement(MagicElement element, float value) {
        if (this.containsElement(element)) {
            elements.put(element, elements.get(element) + value);
        } else {
            elements.put(element, value);
        }
    }

    public void merge(MagicElement element, float amount) {
        float oldAmount;
        if (this.elements.containsKey(element) && amount < (oldAmount = this.elements.get(element))) {
            amount = oldAmount;
        }
        this.elements.put(element, amount);
    }

    public void merge(StorageElements other) {
        float oldAmount;
        for (Map.Entry<MagicElement, Float> entry : other.elements.entrySet()) {
            if (this.elements.containsKey(entry.getKey())) {
                if (entry.getValue() < (oldAmount = this.elements.get(entry.getKey()))) {
                    entry.setValue(oldAmount);
                }
                this.elements.put(entry.getKey(), this.elements.get(entry.getKey()) + entry.getValue());
            } else {
                this.elements.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public float getTotalValue() {
        return elements.values().stream().reduce(0f, Float::sum);
    }


    public void setElement(MagicElement element, float value) {
        elements.put(element, value);
    }

    public void addFrom(StorageElements other) {
        for (Map.Entry<MagicElement, Float> entry : other.elements.entrySet()) {
            if(!this.containsElement(entry.getKey())){
                this.addElement(entry.getKey(), entry.getValue());
            }else{
                this.merge(entry.getKey(), entry.getValue());
            }
        }
    }

    public boolean removeElement(MagicElement elementName, float value, boolean deleteIfZero) {
        if (this.containsElement(elementName)) {
            float currentValue = elements.get(elementName);
            if (currentValue - value >= 0) {
                elements.put(elementName, currentValue - value);
            } else {
                if (deleteIfZero) {
                    this.deleteElement(elementName);
                } else {
                    elements.put(elementName, 0f);
                }
            }
            return true;
        }
        return false;
    }

    public void deleteElement(MagicElement elementName) {
        if (this.containsElement(elementName)) {
            elements.remove(elementName);
        }
    }

    public void readFromNBT(CompoundTag nbt) {
        this.elements.clear();
        ListTag list = nbt.getList("MagicElements", 10);

        for (int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElementByName(rs.getString("key")), rs.getInt("amount"));
            }
        }
    };

    public void readFromNBT(CompoundTag nbt, String label) {
        this.elements.clear();
        ListTag list = nbt.getList(label, 10);

        for (int j = 0; j < list.size(); ++j) {
            CompoundTag rs = list.getCompound(j);
            if (rs.contains("key")) {
                this.addElement(ElementsRegistry.getElementByName(rs.getString("key")), rs.getInt("amount"));
            }
        }

    }

    public void writeToNBT(CompoundTag nbt) {
        ListTag list = new ListTag();
        elements.forEach((element, value) -> {
            CompoundTag n = new CompoundTag();
            n.putString("key", element.getName());
            n.putFloat("amount", value);
            list.add(n);
        });
        nbt.put("MagicElements", list);
    }

    public void writeToNBT(CompoundTag CompoundTag, String label) {
        ListTag list = new ListTag();
        elements.forEach((element, value) -> {
            CompoundTag n = new CompoundTag();
            n.putString("key", element.getName());
            n.putFloat("amount", value);
            list.add(n);
        });
        CompoundTag.put(label, list);
    }

    public String toString() {
        StringBuilder text = new StringBuilder("Storage Elements:\n");
        for (MagicElement element : this.getElements()) {
            text.append(element.getName()).append(" : ").append(this.getElementValue(element)).append("\n");
        }
        return text.toString();
    }

    public Component toComponent() {
        MutableComponent text = Component.literal("Storage Elements:").append("\n");
        for (MagicElement element : this.getElements()) {
            text.append(element.getTranslationText()).append(" : ").append(String.valueOf(this.getElementValue(element))).append("\n");
        }

        return text;
    }

    public void reduceRootElements(float vis) {
        for (MagicElement element : ERMagicElements.DEFAULT_ELEMENTS)
            if (this.elements.containsKey(element))
                this.elements.put(element, this.elements.get(element) - vis);
    }

    public static StorageElements createByElement(MagicElement element, float value) {
        return new StorageElements(Map.of(element, value));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof StorageElements another)) return false;
        if (this.size() != another.size()) return false;
        for (MagicElement element : this.getElements())
            if (this.getElementValue(element) != another.getOrDefault(element))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }

    public List<MagicElement> getPrimalElements() {
       return this.elements.keySet().stream().filter(MagicElement::isPrimal).toList();
    }

    public List<MagicElement> filterElements(List<MagicElement> elements, int min) {
        return this.elements.keySet().stream().filter(elements::contains).filter(element -> this.getElementValue(element) >= min).toList();
    }

    public List<MagicElement> filterElements(List<MagicElement> elements) {
        return this.elements.keySet().stream().filter(elements::contains).toList();
    }

    public List<MagicElement> sortValueElements() {
        return this.elements.keySet().stream().sorted((a, b) -> Float.compare(this.getElementValue(b), this.getElementValue(a))).toList();
    }
}
