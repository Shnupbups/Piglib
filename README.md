# Piglib
Tags for Piglin stuff that Mojang were too silly to add themselves.

## Tags included
* `piglib:piglin_bartering_items`
  * Items in this tag, when picked up by a Piglin or right clicked with, will result in the Piglin bartering.
* `piglib:piglin_safe_armor`
  * Items in this tag, when worn in an armor slot, will prevent nearby Piglins from being angered.
* `piglib:piglin_loved_nuggets`
  * Items in this tag, when picked up by Piglins, will be picked up as a whole stack, like gold nuggets, rather than one at a time.
  * Piglins will not actively seek out these items. Items in this tag should ***not*** also be in the vanilla `minecraft:piglin_loved` tag.

Note that adding items to any of these tags doesn't automatically add them to `minecraft:piglin_loved`, so if you want Piglins to actively seek out the items when dropped on the ground, you will need to add to that as well.

## How to use in your mod
In your `build.gradle`, put this in the `repositories` block
```groovy
maven {
  url = "https://maven.shedaniel.me/"
  name = "Shedaniel"
}
```
and this in the `dependencies` block
```groovy
modApi "com.shnupbups:Piglib:1.2.0+fabric-mc1.20.4"
```

Making sure to replace the version number with the latest for the Minecraft version of choice.

You may also choose to `include` it if you don't want an external dependency.