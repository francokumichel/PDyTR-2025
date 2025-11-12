# Variables configurables por línea de comando
EJ ?= EJ1
MAIN ?= AgenteMovil

# Variables fijas
JAVAC=javac
JAVA=java
CLASSPATH=lib/jade.jar
SRC_DIR=agentes/$(EJ)
OUT_DIR=classes

compile:
	$(JAVAC) -classpath $(CLASSPATH) -d $(OUT_DIR) $(SRC_DIR)/$(MAIN).java

gui:
	$(JAVA) -cp $(CLASSPATH) jade.Boot -gui

agente:
	$(JAVA) -cp "$(CLASSPATH):$(OUT_DIR)" jade.Boot -gui -container -host localhost -agents mol:$(MAIN)

clean:
	rm -rf $(OUT_DIR)/*

.PHONY: compile gui agente clean
