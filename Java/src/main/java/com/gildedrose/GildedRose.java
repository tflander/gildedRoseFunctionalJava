package com.gildedrose;

import java.util.function.Function;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        for (int i = 0; i < items.length; i++) {

            Function<Item, Item> adjustQuality = adjustQuality();
            Function<Item, Item> adjustSellIn = adjustSellIn();
            Function<Item, Item> adjustQualityForExpiredItem = Function.identity();

            items[i] = adjustQuality.apply(items[i]);
            items[i] = adjustSellIn.apply(items[i]);
            if (items[i].sellIn < 0) {
                adjustQualityForExpiredItem = adjustQualityForExpiredItem();
            }
            items[i] = adjustQualityForExpiredItem.apply(items[i]);

        }
    }

    private Function<Item, Item> adjustQuality() {
        return item -> {
            if (isLegendary(item)) {
                return item;
            }

            Item newItem = item;

            if (isItemThatIncreasesInQuality(item)) {
                if (newItem.quality >= 50) {
                    return item;
                }
                newItem = newItem.builder().withQuality(newItem.quality + 1).build();

                if (newItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    newItem = handleQualityForBackstagePass(newItem);
                }
            } else {
                if (newItem.quality <= 0) {
                    return newItem;
                }
                newItem = newItem.builder().withQuality(newItem.quality - 1).build();
                if (newItem.name.equals("Conjured")) {
                    newItem = newItem.builder().withQuality(newItem.quality - 1).build();
                }
            }
            return newItem;
        };

    }

    private Function<Item, Item> adjustSellIn() {
        return item -> {
            if (!isLegendary(item)) {
                return item.builder().withSellIn(item.sellIn - 1).build();
            }
            return item;
        };
    }

    private Function<Item, Item> adjustQualityForExpiredItem() {
        return item -> {
            Item newItem = item;
            if (newItem.name.equals("Aged Brie")) {
                if (newItem.quality < 50) {
                    newItem = newItem.builder().withQuality(newItem.quality + 1).build();
                }
            } else {
                if (newItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    newItem = newItem.builder().withQuality(0).build();
                } else {
                    if (newItem.quality > 0) {
                        if (!isLegendary(newItem)) {
                            newItem = newItem.builder().withQuality(newItem.quality - 1).build();
                        }
                    }
                }
            }
            return newItem;
        };
    }

    private Item handleQualityForBackstagePass(Item item) {
        Item newItem = item.builder().build();
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
    }

    private boolean isLegendary(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isItemThatIncreasesInQuality(Item item) {
        return item.name.equals("Aged Brie")
                || item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }
}