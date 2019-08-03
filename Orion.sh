#!/bin/bash
cd Documentos/tutorials.Getting-Started
echo "Preparando o Orion, IoT-A e Mongo-DB ..."
docker-compose -p fiware down
docker-compose -p fiware up -d
echo "Criado com sucesso!"

