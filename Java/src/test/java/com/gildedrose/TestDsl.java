package com.gildedrose;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDsl {
    private String itemName;
    private int sellIn;
    private int quality;
    private ItemWrapper item;

    public TestDsl withName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public TestDsl withSellIn(int sellIn) {
        this.sellIn = sellIn;
        return this;
    }


    public TestDsl withQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public TestDsl whenNextDay() {
        Item baseItem = new Item(itemName, sellIn, quality);
        GildedRose app = new GildedRose(new Item[]{baseItem});
        app.updateQuality();
        item = app.items[0];
        return this;
    }

    public TestDsl thenExpectSellIn(int expectedSellIn) {
        assertThat(item.get().sellIn).isEqualTo(expectedSellIn);
        return this;
    }

    public void andExpectQuality(int expectedQuality) {
        assertThat(item.get().quality).isEqualTo(expectedQuality);
    }
}
