# GdxRealsense2

The main purpose of this project is to use the intel realsense sdk 2 with libgdx for sharing the same java code on desktop and android. For that I used the [gdx-jnigen][gdxjnigen] project for building the native realsense sdk for all the necessary platforms. I reused the [android wrapper][ajw] java classes to keep the same logic.

## Prerequisites

You will need to install :
- Ant
- MinGW

For better understanding how jnigen works, check this [link][jnigen]

## Structure

//TODO

## How to build

//TODO

[gdxjnigen]: <https://github.com/libgdx/gdx-jnigen>
[jnigen]: <https://github.com/libgdx/libgdx/wiki/jnigen>
[ajw]: <https://github.com/IntelRealSense/librealsense/tree/master/wrappers/android/librealsense>