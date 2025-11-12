param (
    [string]$target,
    [string]$package = "EJ1",   # Ejercicio por defecto
    [string]$main = "AgenteMovil"  # Clase por defecto
)

# Variables
$JAVAC = "javac"
$JAVA = "java"
$CLASSPATH = "lib/jade.jar"
$SRC_DIR = "agentes/$package"
$OUT_DIR = "classes"
$MAIN_CLASS = $main

function Compile {
    Write-Output "Compiling $SRC_DIR/$MAIN_CLASS.java ..."
    & $JAVAC -classpath $CLASSPATH -d $OUT_DIR "$SRC_DIR/$MAIN_CLASS.java"
}

function Gui {
    Write-Output "Starting JADE with GUI..."
    & $JAVA -cp $CLASSPATH jade.Boot -gui
}

function Agente_Win {
    Write-Output "Starting JADE agent $MAIN_CLASS from $package ..."
    & $JAVA -cp "lib/jade.jar;$OUT_DIR" jade.Boot -gui -container -host localhost -agents "mol:$MAIN_CLASS"
}

switch ($target) {
    "compile" { Compile }
    "gui" { Gui }
    "agente" { Agente_Win }
    default { Write-Output "Usage: .\jade.ps1 <target> [package] [main]" }
}
