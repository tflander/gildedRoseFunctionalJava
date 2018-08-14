package com.gildedrose;

import java.util.function.Function;

class GildedRose {
    private final FunctionBuilder functionBuilder;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
        functionBuilder = new FunctionBuilder();
    }

    public void updateQuality() {

        for (int i = 0; i < items.length; i++) {
            String itemName = items[i].name;
            Function<Item, Item> composedFunction = functionBuilder.adjustQuality(itemName)
                    .andThen(functionBuilder.adjustSellIn(itemName)
                            .andThen(functionBuilder.adjustQualityForExpiredItem(itemName)));

            items[i] = composedFunction.apply(items[i]);
        }
    }

}