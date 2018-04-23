cmake_minimum_required(VERSION 3.10)
project(kmeans)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -pthread")

include_directories(.)

add_executable(kmeans
        Kmeans.cpp
        Kmeans.h
        main.cpp
        )
