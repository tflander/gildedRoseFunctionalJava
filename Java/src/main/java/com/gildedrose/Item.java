package com.gildedrose;


public class Item {

    public final String name;
    public final int sellIn;
    public final int quality;

    public Item(String itemName, int sellIn, int quality) {
        name = itemName;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public Builder builder() {
        return Builder.create()
                .withName(name)
                .withSellIn(sellIn)
                .withQuality(quality);
    }

    public static class Builder {

        private String name;
        private int sellIn;
        private int quality;

        public static Builder create() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSellIn(int sellIn) {
            this.sellIn = sellIn;
            return this;
        }

        public Builder withQuality(int quality) {
            this.quality = quality;
            return this;
        }

        public Item build() {
            return new Item(name, sellIn, quality);
        }
    }
}
