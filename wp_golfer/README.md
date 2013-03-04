# wp_golfer

A utility for wikipedia golf, made as a teach-myself-Clojure project. 
Wikipedia golf is a game for any number of players in which one Wikipedia article is chosen as the tee and one as the hole. 
The winner is the player who finds the shortest path from the tee to the hole, where all navigation is done by clicking article links.
Obviously, using a brute-force breadth-first search as this does is kind of cheating, as the intended purpose is to be a test of knowledge and creative connection-building.
Therefore, the recommended application is for referees to use this to find difficult courses.

## Usage

If using the source, do "lein run" to open the GUI, or for example "lein run Truth Beauty" to perform a single search for a path between the articles Truth and Beauty. 
Make sure to include exactly two arguments here; use quotes to enclose multi-word articles. Articles may be written as they appear in the URL (e.g. "Theory_of_forms") or with spaces (e.g. "Theory of forms").
The jar file works the same way with regards to arguments, but start it with "java -jar /path/to/file" as usual.

## License

Copyright © 2013 Magnus Grimstvedt Saltnes

Distributed under the Eclipse Public License, the same as Clojure.
