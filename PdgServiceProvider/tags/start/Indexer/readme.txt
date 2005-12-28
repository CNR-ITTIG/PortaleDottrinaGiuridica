La cartella OaiSwish-eConf contiene i file di configurazione che servono a swish-e per l'indicizzazione.


COME CREARE L'INDICE:
 
1) posizionarsi nella directory OaiSwish-eConf
2) lanciare il file start.sh

il file start.sh fa la seguente:
 - si posiziona nella cartella /OaiHarvester ed effettua l'harvesting dei dati. (La servlet OaiDataProvider deve funzionare).
 - lancia swish-e utilizzando il file di configurazione xmlIndex.conf e creando il file indice nella directory Indexer
 - lancia swish-e utilizzando il file di configurazione webIndex.config e creando il file indice nella directory Indexer
	(ATTENZIONE: nel file di configurazione bisogna indicare la directory del mirror dei file html da indicizzare)
	(ATTENZIONE: la cartella /MetadataGenerator/exmeta/supportFiles ed il file MetadataGenerator/exmeta/stopwords.txt devono essere copiati dentro Indexer/OaiSwish-eConf)
