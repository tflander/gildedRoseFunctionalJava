package com.gildedrose;

import java.util.Arrays;
import java.util.stream.Collectors;

class GildedRose {
    ItemWrapper[] items;

    public GildedRose(Item[] items) {
        this.items = Arrays.stream(items)
                .map(item -> new ItemWrapper(item))
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
    }

    public void updateQuality() {

        items = Arrays.stream(items)
                .map(item -> item.age())
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
    }

}