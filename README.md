# Gilded Rose Refactoring Kata

This is my solution to the popular refactoring kata.
My goal was to use Java8 Functional programming as effectivly as my skill allows.

My take on this kata is that the problem is best solved using polymorphism, which is illegal according to kata rules.

I'm following the rules by creating an ItemWrapper, allowing me to leave Item untouched.

The item wrapper associates a function to the item that will return an aged copy of the item.

This function is build using a factory that constructs the appropriate age function based on the item name.

If the logic was more complex, I would refactor the factory to construct ItemWrapper sub-types.
Given the current level of complexity, I would prefer this functional approach over polymorphism.