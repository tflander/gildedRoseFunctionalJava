package com.gildedrose;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class GildedRose {
    private final FunctionBuilder functionBuilder;
    ItemWrapper[] items;

    public GildedRose(Item[] items) {
        this.items = Arrays.stream(items)
                .map(item -> new ItemWrapper(item))
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
        functionBuilder = new FunctionBuilder();
    }

    public void updateQuality() {

        Function<Item, Item> composedFunction = functionBuilder.adjustQuality()
                .andThen(functionBuilder.adjustSellIn()
                        .andThen(functionBuilder.adjustQualityForExpiredItem()));

        List<Item> foo = Arrays.stream(items)
                .map(item -> composedFunction.apply(item.get()))
                .collect(Collectors.toList());

        items = foo.stream()
                .map(item -> new ItemWrapper(item))
                .collect(Collectors.toList())
                .toArray(new ItemWrapper[0]);
    }

}