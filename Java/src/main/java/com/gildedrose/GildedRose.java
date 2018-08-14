package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            adjustQuality(i);
            adjustSellIn(i);
            if (items[i].sellIn < 0) {
                adjustQualityForExpiredItem(i);
            }

        }
    }

    private void adjustQuality(int i) {
        if (isLegendary(i)) {
            return;
        }

        if (isItemThatIncreasesInQuality(items[i])) {
            if (items[i].quality >= 50) {
                return;
            }
            items[i] = items[i].builder().withQuality(items[i].quality + 1).build();

            if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                items[i] = handleQualityForBackstagePass(items[i]);
            }
        } else {
            if (items[i].quality <= 0) {
                return;
            }
            items[i] = items[i].builder().withQuality(items[i].quality - 1).build();
            if (items[i].name.equals("Conjured")) {
                items[i] = items[i].builder().withQuality(items[i].quality - 1).build();
            }
        }
    }

    private void adjustSellIn(int i) {
        if (!isLegendary(i)) {
            items[i] = items[i].builder().withSellIn(items[i].sellIn - 1).build();
        }
    }

    private void adjustQualityForExpiredItem(int i) {
        if (items[i].name.equals("Aged Brie")) {
            if (items[i].quality < 50) {
                items[i] = items[i].builder().withQuality(items[i].quality + 1).build();
            }
        } else {
            if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                items[i] = items[i].builder().withQuality(0).build();
            } else {
                if (items[i].quality > 0) {
                    if (!isLegendary(i)) {
                        items[i] = items[i].builder().withQuality(items[i].quality - 1).build();
                    }
                }
            }
        }
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

    private boolean isLegendary(int i) {
        return items[i].name.equals("Sulfuras, Hand of Ragnaros");
    }

    private boolean isItemThatIncreasesInQuality(Item item) {
        return item.name.equals("Aged Brie")
                || item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }
}