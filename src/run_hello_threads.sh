#!/bin/bash

thread_inputs=("1" "2" "4" "8" "16" "32" "64" "128" "256")
output_dir="outputs"

# Create output directory if it doesn't exist
mkdir -p "$output_dir"

# Compile Java files
javac Exercise1/HelloThreads.java Exercise1/ComputationTask.java

# Run the Java program with each input
run_number=0
for input in "${thread_inputs[@]}"; do
    output_file="$output_dir/output$run_number.txt"
    java Exercise1.HelloThreads 4096 "$input" > "$output_file"
    ((run_number++))
done
