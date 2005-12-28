#Avviare prima Tomcat

#Harvesting
cd ../../OaiHarvester

java -cp ./k_notes/lib/xerces.jar:. k_notes.main.Caller 

cd ../Indexer/OaiSwish-eConf

#Indicizzarione dei file xml sotto XMLRecords
swish-e -c xmlIndex.config

#Indicizzazione dei file html della directory Mirrors, indicata nel file webIndex.config  - viene usato exmeta
swish-e -c webIndex.config -S prog

#Unione degli indici
swish-e -M ../web.index ../docs.index ../all.index
