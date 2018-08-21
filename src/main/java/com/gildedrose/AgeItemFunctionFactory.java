package com.gildedrose;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.gildedrose.ItemTransforms.*;

public class AgeItemFunctionFactory {

    private static final Map<String, Function<Item, Item>> ageFunctionsByItemName = new HashMap<>();

    static {
        ageFunctionsByItemName.put("Sulfuras, Hand of Ragnaros", item -> item);
        ageFunctionsByItemName.put("Aged Brie", increaseQualityByOneToMax.andThen(decrementSellIn));
        ageFunctionsByItemName.put("Backstage passes to a TAFKAL80ETC concert", handleQualityForBackstagePass.andThen(decrementSellIn));
        ageFunctionsByItemName.put("Conjured", decreaseQualityByAmountToZero(2).andThen(decrementSellIn));
    }

    private static final Function<Item, Item> defaultAgeFunction = decreaseQualityByAmountToZero(1)
            .andThen(decrementSellIn)
            .andThen(decreaseQualityIfExpired);

    public Function<Item, Item> build(Item item) {
        return ageFunctionsByItemName.getOrDefault(item.name, defaultAgeFunction);
    }
}
