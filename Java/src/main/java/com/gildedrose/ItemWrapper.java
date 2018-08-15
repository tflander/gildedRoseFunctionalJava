package com.gildedrose;

import java.util.function.Function;
import java.util.function.Predicate;

public class ItemWrapper {

    private final Item item;
    private final Function<Item, Item> getAgedItem;

    public ItemWrapper(Item item) {
        this.item = item;
        getAgedItem = buildFunction(item);
    }

    private Function<Item, Item> buildFunction(Item item) {
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return Function.identity();
        } else if (item.name.equals("Aged Brie")) {
            return increaseQualityByOneToMax().andThen(decrementSellIn());
        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            return handleQualityForBackstagePass().andThen(decrementSellIn());
        } else if (item.name.equals("Conjured")) {
            return decreaseQualityByAmountToZero(2).andThen(decrementSellIn());
        } else {
            return decreaseQualityByAmountToZero(1)
                    .andThen(decrementSellIn())
                    .andThen(decreaseQualityIfExpired());
        }
    }

    public Item get() {
        return item;
    }

    public Item age() {
        return getAgedItem.apply(item);
    }

    private Function<Item, Item> decreaseQualityIfExpired() {
        return item -> {
            if (isExpired().test(item)) {
                return decreaseQualityByAmountToZero(1).apply(item);
            } else {
                return item;
            }
        };
    }

    private Function<Item, Item> decrementSellIn() {
        return item -> {
            return item.builder().withSellIn(item.sellIn - 1).build();
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

    private Function<Item, Item> handleQualityForBackstagePass() {
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

    private Function<Item, Item> decreaseQualityByAmountToZero(int amount) {
        return item -> {
            if (item.quality <= 0) {
                return item;
            }
            int quality = Math.max(0, item.quality - amount);
            return item.builder().withQuality(quality).build();
        };
    }

    private Predicate<Item> isExpired() {
        return item -> item.sellIn < 0;
    }

}
