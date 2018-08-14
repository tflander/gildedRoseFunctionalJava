package com.gildedrose;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionBuilder {

    public Function<Item, Item> adjustQuality() {

        Map<Predicate<Item>, Function<Item, Item>> conditionalTransforms = new LinkedHashMap<>();
        conditionalTransforms.put(isLegendary(), Function.identity());
        conditionalTransforms.put(isBackstagePass(), handleQualityForBackstagePass());
        conditionalTransforms.put(item -> isItemThatIncreasesInQuality(item.name), increaseQualityByOneToMax());
        conditionalTransforms.put(isConjured(), decreaseQualityByAmountToZero(2));
        conditionalTransforms.put(item -> true, decreaseQualityByAmountToZero(1));
        return firstMatchingTransform(conditionalTransforms);
    }

    public Function<Item, Item> adjustSellIn(String itemName) {

        if (isLegendary(itemName)) {
            return Function.identity();
        }
        return decrementSellIn();
    }

    public Function<Item, Item> adjustQualityForExpiredItem() {

        Map<Predicate<Item>, Function<Item, Item>> conditionalTransforms = new LinkedHashMap<>();
        conditionalTransforms.put(isLegendary().or(isExpired().negate()), Function.identity());
        conditionalTransforms.put(isBackstagePass(), setQualityToZero());
        conditionalTransforms.put(isAgedBrie(), increaseQualityByOneToMax());
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

    private Predicate<Item> isLegendary() {
        return item -> isLegendary(item.name);
    }

    private boolean isLegendary(String itemName) {
        return itemName.equals("Sulfuras, Hand of Ragnaros");
    }

    private Predicate<Item> isExpired() {
        return item -> item.sellIn < 0;
    }

    private boolean isConjured(String itemName) {
        return itemName.equals("Conjured");
    }

    private Predicate<Item> isConjured() {
        return item -> isConjured(item.name);
    }

    private Predicate<Item> isBackstagePass() {
        return item -> itemHasName(item, "Backstage passes to a TAFKAL80ETC concert");
    }

    private Predicate<Item> isAgedBrie() {
        return item -> itemHasName(item, "Aged Brie");
    }

    private boolean itemHasName(Item item, String expectedName) {
        return item.name.equals(expectedName);
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
