/* Classe che amplia DIR del C */

#ifndef DIRUTILS_H
#define DIRUTILS_H

#include <dirent.h>
#include <string>
#include <vector>

class Dir_Extended {
  std::string root_dir_name; /* nome della directory, compreso il path */
  DIR* root_dir;
  std::vector<Dir_Extended> last_opendir;
  bool open;
 public:
  Dir_Extended() : open(false) {};
  Dir_Extended(const std::string& dir_name) : open(false) {
    set_dir(dir_name);
  }
  ~Dir_Extended();

  /* imposta la directory da aprire, chiudendo la precedente se necessario */
  void set_dir(const std::string& dir_name);

  /* Esplora la directory e tutte le sottodirectory ricorsivamente, ritornando il path
     relativo dei file contenuti; lavora su root_dir_name e ritorna sempre un nuovo
     file, finché la directory non è stata completamente esplorata, oppure la stringa "",
     se non ci sono nuovi file o non è stata aperta nessuna directory */
  std::string next_file();

  /* Riporta true se la classe è inizializzata con una directory valida */
  bool check_open() {
    return open;
  }

  /* Restituisce il nome della directory, senza path */
  std::string name() {
    int last_slash = root_dir_name.find_last_of('/');
    return root_dir_name.substr(last_slash + 1, root_dir_name.size());
  }

  std::string long_name() {
    return root_dir_name;
  }
};

#endif

