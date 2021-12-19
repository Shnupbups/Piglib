# Piglib
Tags for Piglin stuff.

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
modApi "com.shnupbups:Piglib:1.1.1+fabric-mc1.18"
include "com.shnupbups:Piglib:1.1.1+fabric-mc1.18"
```
