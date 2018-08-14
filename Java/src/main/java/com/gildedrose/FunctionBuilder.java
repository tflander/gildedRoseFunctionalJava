package com.gildedrose;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionBuilder {

    public Function<Item, Item> adjustQuality(String itemName) {

        Map<Predicate<Item>, Function<Item, Item>> conditionalTransforms = new LinkedHashMap<>();
        conditionalTransforms.put(item -> isLegendary(item.name), Function.identity());
        conditionalTransforms.put(item -> item.name.equals("Backstage passes to a TAFKAL80ETC concert"), handleQualityForBackstagePass());
        conditionalTransforms.put(item -> isItemThatIncreasesInQuality(item.name), increaseQualityByOneToMax());
        conditionalTransforms.put(item -> isConjured(item.name), decreaseQualityByAmountToZero(2));
        conditionalTransforms.put(item -> true, decreaseQualityByAmountToZero(1));
        return firstMatchingTransform(conditionalTransforms);
    }

    public Function<Item, Item> adjustSellIn(String itemName) {

        if (isLegendary(itemName)) {
            return Function.identity();
        }
        return decrementSellIn();
    }

    public Function<Item, Item> adjustQualityForExpiredItem(String itemName) {

        Map<Predicate<Item>, Function<Item, Item>> conditionalTransforms = new LinkedHashMap<>();
        conditionalTransforms.put(item -> isLegendary(item.name) || item.sellIn >= 0, Function.identity());
        conditionalTransforms.put(item -> item.name.equals("Backstage passes to a TAFKAL80ETC concert"), setQualityToZero());
        conditionalTransforms.put(item -> item.name.equals("Aged Brie"), increaseQualityByOneToMax());
        conditionalTransforms.put(item -> true, decreaseQualityByAmountToZero(1));
        return firstMatchingTransform(conditionalTransforms);
    }

    private Function<Item, Item> setQualityToZero() {
        return item -> item.builder().withQuality(0).build();
    }

    private Function<Item, Item> handleQualityForBackstagePass() {
        return item -> {
            Item newItem = item.builder().withQuality(item.quality + 1).build();

            if (item.sellIn < 11) {
                if (item.quality < 50) {
                    newItem = newItem.builder().withQuality(newItem.quality + 1).build();
                }
            }

            if (item.sellIn < 6) {
                if (item.quality < 50) {
                    newItem = newItem.builder().withQuality(newItem.quality + 1).build();
                }
            }
            return newItem;

        };
    }

    private boolean isLegendary(String itemName) {
        return itemName.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isItemThatIncreasesInQuality(String itemName) {
        return itemName.equals("Aged Brie")
                || itemName.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private Function<Item, Item> decrementSellIn() {
        return item -> {
            return item.builder().withSellIn(item.sellIn - 1).build();
        };
    }

    private Function<Item, Item> decreaseQualityByAmountToZero(int amount) {
        return item -> {
            if (item.quality <= 0) {
                return item;
            }
            int quality = Math.max(0, item.quality - amount);
            return item.builder().withQuality(quality).build();
        };
    }

    private boolean isConjured(String itemName) {
        return itemName.equals("Conjured");
    }

    private Function<Item, Item> increaseQualityByOneToMax() {
        return item -> {
            if (item.quality >= 50) {
                return item;
            }
            return item.builder().withQuality(item.quality + 1).build();
        };
    }

    private Function<Item, Item> firstMatchingTransform(Map<Predicate<Item>, Function<Item, Item>> conditionalTransforms) {
        return item -> {

            Set<Map.Entry<Predicate<Item>, Function<Item, Item>>> foo = conditionalTransforms.entrySet();
            for (Map.Entry<Predicate<Item>, Function<Item, Item>> e : foo) {
                if (e.getKey().test(item)) {
                    return e.getValue().apply(item);
                }
            }
            throw new IllegalStateException("No matching transforms found");
        };
    }

}
