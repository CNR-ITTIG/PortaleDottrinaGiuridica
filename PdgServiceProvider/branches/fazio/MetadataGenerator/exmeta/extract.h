/* Si occupa di estrarre l'albero dei tag HTML da un file;
   effettua le ricerche all'interno dell'albero e restituisce le informazioni
   richieste;
   può restituire la pagina come solo testo, senza metadati */
/* Per abilitare il debug settare EXTRACT_DEBUG */

#ifndef EXTRACT_H
#define EXTRACT_H

#include <iostream>
#include <sstream>
#include "../htmlcxx/html/Parser.h"
#include "../htmlcxx/html/Node.h"
#include "../htmlcxx/html/tree.h"

using namespace htmlcxx;

class Extractor : HTML::Parser {
  /* ricostruisce il codice html dal sotto-albero iter */
  void children_recurse(tree<HTML::Node>::iterator_base& iter, std::ostringstream& html) const;
  public:
  Extractor(const std::string& html) : HTML::Parser() {
    parse(html);
  }
  virtual ~Extractor() {}; // viene chiamato il distruttore di Parser?
  /* funzioni che restituiscono il contenuto del primo tag o dell'attributo
     attribute_name del primo tag tag_name; opzionalmente, è possibile specificare il
     contenuto di un altro attributo other_attribute_name che deve essere presente
     nel tag da ricercare */
  // poiché Extractor è legato ad una solo stringa HTML, i metodi potrebbero essere ottimizzati per restituire
  // velocemente i dati in caso di richieste successive uguali, oppure possono essere ampliati per restituire anche i tag
  // e gli attributi successivi al primo
  // si può anche definire un metodo che cambi le stringa HTML di Extractor e un costruttore senza parametri
  // rivedere i metodi virtual; è utile che lo siano?
  virtual std::string tag (const std::string& tag_name) const;
  virtual std::string attribute(const std::string& tag_name, const std::string& attribute_name,
                           const std::string& other_attribute_name = "",
                           const std::string& other_attribute_content = "") const;
  /* restituisce la stringa html senza tag, togliendo alcune entity e
     spazi inutili; si utilizzano le espressioni regolari, per la
     massima velocità e precisione
     usa l'intera pagina HTML se non è passato alcun valore */
  // non è ottimizzata per eseguire velocemente chiamate successive
  std::string text(const std::string& html) const;
  std::string text() const 
    {
      return text(std::string(mpStart, (mHtmlTree.begin())->length()));
    }

  /* rende compatibile la stringa str con la codifica xml */
  std::string txt2xml(const std::string& str) const;
  // text(string) e txt2xml possono essere definite come statiche,
  // nell'accezione Java del termine
  
  /* inserisce new_tag, con attributi new_attributes, come primo figlio di tag_name; restituisce 0 se non è stato inserito
        niente, altrimenti l'offset del nodo inserito all'interno della pagina */
  virtual int insert_tag(const std::string& tag_name, const std::string& new_tag,
                         const std::string& new_attributes = "");
  /* per ricostruire la pagina html */
  std::string rebuild() const;
  operator std::string() const { return rebuild(); }
  operator char*() const {
    std::string spage = rebuild();
    char* cppage = (char *)malloc(spage.size());
    return cppage;
  }
};

/* non serve, esiste l'operatore per avere casting implicito
std::ostream& operator<<(std::ostream& stream, const Extractor& right) {
  stream << right.rebuild();
  return stream;
}*/

#endif
