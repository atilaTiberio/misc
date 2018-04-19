cmake_minimum_required(VERSION 3.9)
project(kmeans)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")
add_executable(kmeans main.cpp Kmeans.cpp Kmeans.h pbarrier.h)