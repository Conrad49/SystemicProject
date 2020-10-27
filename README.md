# SystemicProject

## Overall Goal
To make a systemic world which is a world full of creatures that interact with not only the player but with other creatures in the world. The goal is to create emergent gameplay where the player can be wandering around and find a fight between wolf packs for territory or see a bunny for a few moments just to see it eaten by something else. All of this would be unscripted and happening naturally all around the world. This is the end goal and we are not there yet and it is very possible we won't be there at the end. We have been working on this over the summer and have gotten world generation done but we have not started making any creatures yet. We also made a choice early on that we do not want to use any libraries or anything like that, we are making every piece not in the java library by ourselves which may be dumb but it's for learning purposes.

### World Generation
  This project uses octaved simplex noise to create the tilemap

An example of a map generated. Different colors represent different biomes. Each pixel represents one tile so this would be a very big world.
![map](/src/res/Map.png)

### Collision
  - Axis Aligned Rectangle Collision
    - Closely follows this video: https://www.youtube.com/watch?v=8JJ-4JgR7Dg&t=1704s
      There are still bugs and there are parts of the video that have yet to be implemented but it functions predictably


### Current goal
  - Infinite world generation through a chunk system.
