package com.gildedrose;

import java.util.function.Function;

public interface ItemTransforms {

    static Function<Item, Item> decreaseQualityIfExpired() {
        return item -> {
            if (item.sellIn < 0) {
                return decreaseQualityByAmountToZero(1).apply(item);
            } else {
                return item;
            }
        };
    }

    static Function<Item, Item> decrementSellIn() {
        return item -> item.builder().withSellIn(item.sellIn - 1).build();
    }

    static Function<Item, Item> increaseQualityByOneToMax() {
        return item -> {
            if (item.quality >= 50) {
                return item;
            }
            return item.builder().withQuality(item.quality + 1).build();
        };
    }

    static Function<Item, Item> handleQualityForBackstagePass() {
        return item -> {
            if (item.sellIn <= 0) {
                return item.builder().withQuality(0).build();
            }

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

    static Function<Item, Item> decreaseQualityByAmountToZero(int amount) {
        return item -> {
            if (item.quality <= 0) {
                return item;
            }
            int quality = Math.max(0, item.quality - amount);
            return item.builder().withQuality(quality).build();
        };
    }

}
