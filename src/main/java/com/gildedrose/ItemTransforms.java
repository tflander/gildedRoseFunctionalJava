package com.gildedrose;

import java.util.function.Function;

public interface ItemTransforms {

    Function<Item, Item> decreaseQualityIfExpired =
        item -> {
            if (item.sellIn < 0) {
                return decreaseQualityByAmountToZero(1).apply(item);
            } else {
                return item;
            }
        };

    Function<Item, Item> decrementSellIn =
        item -> item.builder().withSellIn(item.sellIn - 1).build();


    Function<Item, Item> increaseQualityByOneToMax =
        item -> {
            if (item.quality >= 50) {
                return item;
            }
            return item.builder().withQuality(item.quality + 1).build();
        };

    Function<Item, Item> handleQualityForBackstagePass =
         item -> {
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

    static Function<Item, Item> decreaseQualityByAmountToZero(int amount) {
        return item -> {
            int quality = Math.max(0, item.quality - amount);
            return item.builder().withQuality(quality).build();
        };
    }

}
