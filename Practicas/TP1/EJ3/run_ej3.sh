#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Uso: $0 <HOST> <PUERTO>"
    exit 1
fi

HOST=$1
PORT=$2

echo "Compilando cliente y servidor..."
gcc client3b.c -o client3b -lm
gcc server3b.c -o server3b -lm

./server3b $PORT &
SERVER_PID=$!
sleep 1

for n in {1..6}; do
    echo "=============================="
    echo "Ejecutando cliente con 10^$n"
    echo "$n" | ./client3b $HOST $PORT
    echo "=============================="
    sleep 1
done

kill $SERVER_PID
