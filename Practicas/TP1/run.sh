#!/bin/bash

# Para mostrar ayudita :)
show_help() {
    echo "Uso: $0 [OPCIONES] <PUERTO>"
    echo ""
    echo "OPCIONES:"
    echo "  -e, --ejercicio <EJ>    Especificar ejercicio (ej: EJ3, EJ4)"
    echo "  -c, --cliente <FILE>    Especificar archivo cliente"
    echo "  -s, --servidor <FILE>   Especificar archivo servidor"
    echo "  -h, --help             Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0 8080                    # Auto-detectar archivos en directorio actual"
    echo "  $0 -e EJ3 8080            # Usar archivos de EJ3"
    echo "  $0 -c client_ej3_2.c -s server_ej3_2.c 8080  # Archivos específicos"
}

# Con esto detecto los archivos automaticamente si no se pasan por parametro
detect_files() {
    local dir="$1"
    
    CLIENT_FILE=$(find "$dir" -name "*client*.c" | head -1)
    SERVER_FILE=$(find "$dir" -name "*server*.c" | head -1)
}

EJERCICIO=""
CLIENT_FILE=""
SERVER_FILE=""
PORT=""

# Se parsean los argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        -e|--ejercicio)
            EJERCICIO="$2"
            shift 2
            ;;
        -c|--cliente)
            CLIENT_FILE="$2"
            shift 2
            ;;
        -s|--servidor)
            SERVER_FILE="$2"
            shift 2
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        -*)
            echo "Opción desconocida: $1"
            show_help
            exit 1
            ;;
        *)
            PORT="$1"
            shift
            ;;
    esac
done

# Verificamos que se especifico el puerto
if [ -z "$PORT" ]; then
    echo "Error: Debe especificar un puerto"
    show_help
    exit 1
fi

if [ -n "$EJERCICIO" ]; then
    WORK_DIR="$EJERCICIO"
    if [ ! -d "$WORK_DIR" ]; then
        echo "Error: El directorio $WORK_DIR no existe"
        exit 1
    fi
else
    WORK_DIR="."
fi

# Aca se detectan los archivos si no se pasaron por parametro
if [ -z "$CLIENT_FILE" ] || [ -z "$SERVER_FILE" ]; then
    detect_files "$WORK_DIR"
else
    # Hay que construir las rutas completas
    if [ "$WORK_DIR" != "." ]; then
        CLIENT_FILE="$WORK_DIR/$CLIENT_FILE"
        SERVER_FILE="$WORK_DIR/$SERVER_FILE"
    fi
fi

if [ -z "$CLIENT_FILE" ] || [ ! -f "$CLIENT_FILE" ]; then
    echo "Error: No se encontró archivo cliente: $CLIENT_FILE"
    echo "Archivos .c encontrados en $WORK_DIR:"
    find "$WORK_DIR" -name "*.c" 2>/dev/null || echo "  Ninguno"
    exit 1
fi

if [ -z "$SERVER_FILE" ] || [ ! -f "$SERVER_FILE" ]; then
    echo "Error: No se encontró archivo servidor: $SERVER_FILE"
    echo "Archivos .c encontrados en $WORK_DIR:"
    find "$WORK_DIR" -name "*.c" 2>/dev/null || echo "  Ninguno"
    exit 1
fi

echo "========== INFO =========="
echo "Cliente:  $CLIENT_FILE"
echo "Servidor: $SERVER_FILE"
echo "Puerto:   $PORT"
echo "=========================="

echo "Compilando cliente y servidor..."

if ! gcc -w "$CLIENT_FILE" -o client.out -lm; then
    echo "Error: Falló la compilación del cliente"
    exit 1
fi

if ! gcc -w "$SERVER_FILE" -o server.out -lm; then
    echo "Error: Falló la compilación del servidor"
    rm -f client.out
    exit 1
fi

cleanup() {
    echo "Limpiando..."
    pkill -f "server.out" 2>/dev/null
    rm -f client.out server.out
}

trap cleanup EXIT

pkill -f "server.out" 2>/dev/null

for n in {1..6}; do
    echo "=============================="
    echo "Ejecutando cliente con 10^$n"
    ./server.out "$PORT" &  
    sleep 1       
    echo "$n" | ./client.out localhost "$PORT"
    sleep 1
    echo "=============================="
done

echo "Ejecución completada."