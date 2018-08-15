package com.gildedrose;

import java.util.Arrays;
import java.util.stream.Collectors;

class GildedRose {
    ItemWrapper[] items;

    public GildedRose(Item[] items) {
        this.items = wrapItemsInItemWrapper(items);
    }

    public void updateQuality() {

        items = Arrays.stream(items)
                .map(item -> new ItemWrapper(item.age()))
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
    }

    private ItemWrapper[] wrapItemsInItemWrapper(Item[] items) {
        return Arrays.stream(items)
                .map(item -> new ItemWrapper(item))
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
    }

}