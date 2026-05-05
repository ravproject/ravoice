cmake_minimum_required(VERSION 3.22.1)
project("ravoice-dsp")

add_library(ravoice-dsp SHARED ravoice-dsp.cpp)

find_library(log-lib log)
target_link_libraries(ravoice-dsp ${log-lib})
