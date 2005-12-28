/* Il programma qualifica pagine HTML tramite meta-dati DC

Error codes:
  1:   wrong argument;
  2:   file/dir don't exist;
  4:   wrong switch in command line;
  8:   write to file error;
  16: read from file error;
  32: attribute recovering error;
  64: subject guess error
Il codice di errore ritornato è la somma dei codici degli errori che riscontrati.
*/

#include <sys/stat.h>
#include <ctime>
#include <iostream>
#include <fstream>
#include <sstream>
#include <getopt.h>
#include "extract.h"
#include "predictor.h"
#include "dirutils.h"
#include <regexx.hh>

using namespace std;

class DC_Extractor : public Extractor {
 public:
  DC_Extractor(const std::string& html) : Extractor(html) {}

  // ritorna il titolo
  string tag() const {
    return Extractor::tag("title");
  }

  // ritorna il body senza tag
  string body() const {
    return Extractor::tag("body");
  }

  // ritorna true se è presente dc:identifier
  // non serve più, usando output xml
/*   bool is_qualified() const { */
/*     if (Extractor::attribute("meta", "content", "name", "dc:identifier") != "") */
/*       return true; */
/*     return false; */
/*   } */

  // ritorna la descrizione
  string attribute() const {
    return Extractor::attribute("meta", "content", "name", "description");
  }
  
  // inserisce un meta
  // non serve più, usando output xml
/*   int insert_tag(const string& attributes) { */
/*     return Extractor::insert_tag("head", "meta", attributes); */
/*   } */
};

inline void usage(string message = "setmeta", int err = 0) {
  cout << "Usage: " << message  << " [-h] [-v] [-n] <directory>" << endl;
  cout << "-h, --help\tprint this message" << endl;
  cout << "-v, --verbose\tshow verbose informations" << endl;
  cout << "-n, --noact\tprint only what it should do, without perform any action;\n"
       << "\t\timplies -v" << endl;
  exit(err);
}

inline void print_msg(string message, bool err = false) {
  if (err)
    cerr << message << endl;
  else
    cout << message << endl;
}

inline string int2subj(int subject) {
  static string subj_list[] = {
    "ambiente",
    "amministrativo",
    "costituzionale",
    "ecclesiastico",
    "europa",
    "informatica",
    "internazionale",
    "lavoro",
    "penale",
    "privato",
    "tributario"
  };
  return subj_list[subject];
}

inline string to2digit(int num) {
  if (num < 10)
    return "0" + string(gcvt(num, 2, new char));
  else
    return string(gcvt(num, 2, new char));
}

int main(int argc, char* argv[]) {
  bool w2f_err    = false;
  bool rfromf_err = false;
  bool date_err   = false;
  bool subj_err   = false;
  int err_code = 0;
  bool verbose = false;
  bool noact = false;
  string dirname;
  Dir_Extended mirror;
  struct option option_list[] = {
    {"verbose", no_argument, NULL, 'v'},
    {"help",    no_argument, NULL, 'h'},
    {"noact",   no_argument, NULL, 'n'},
    {0, 0, 0, 0}
  };
  int opt;
  int* idx;
  extern int optind;
  while((opt = getopt_long(argc, argv, "vhn", option_list, idx)) != -1)
    switch (opt) {
    case 'h':
      usage(string(argv[0]));
      break;
    case 'v':
      verbose = true;
      break;
    case 'n':
      noact = true;
      verbose = true;
      break;
    case '?':
    default:
      usage(string(argv[0]), err_code + 4);
      break;
    }
  if (optind >= argc)
    usage(string(argv[0]), err_code + 4);
  ifstream config("supportFiles/config.inf");
  ifstream vocab("supportFiles/vocabulary");
  ifstream model("supportFiles/modello");
  ifstream scaling("supportFiles/scaling");
  Predictor guess_meta(vocab, config, model, scaling);
  dirname = argv[optind];
  mirror.set_dir(dirname);
  if (!mirror.check_open())
    usage(argv[0], err_code + 1);
  string dir_entry = mirror.next_file();
  int offset;
  for (; dir_entry != ""; dir_entry =
	 mirror.next_file()) {
    // considero solo i file .htm e .html; meglio però prevedere una verifica sul contenuto del file
    if (!(dir_entry.substr(dir_entry.size() - 4, dir_entry.size() - 1) == ".htm" ||
          dir_entry.substr(dir_entry.size() - 5, dir_entry.size() - 1) == ".html")) {
      if (verbose)
         print_msg("Skipping file: " + dir_entry);
      continue;
    }
    string html_string = "";
    string temp;
    if (verbose)
      print_msg("Processing file " + dir_entry);
    ifstream html_stream((mirror.long_name() + "/" + dir_entry).c_str());
    if (html_stream == NULL) {
      print_msg("Can't read from " + mirror.long_name() + "/" + dir_entry, true);
      rfromf_err = true;
      continue;
    }
    // usare solo un fstream invece di un ifstream e di un ofstream?
    while (getline(html_stream, temp))
      html_string += temp + "\n";
    DC_Extractor html(html_string);
//    if (html.is_qualified())
//      continue;

    ostringstream html_out;
    html_out << "<oai_dc:dc xmlns:dc=\"http://purl.org/dc/elements/1.1/\" ";
    html_out << "xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" ";
    html_out << "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ";
    html_out << "xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/";
    html_out << endl << "http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">" << endl;

    // danno noia le lettere accentate nei nomi, non vengono indicizzate bene da swish-e
    if (verbose)
      print_msg("Inserting meta dc:identifier: " + dir_entry);
    if (!noact)
      //offset = html.insert_tag("name=\"dc:identifier\" content=\"" + dir_entry + "\"");
      html_out << "  <dc:identifier>" << dir_entry << "</dc:identifier>" << endl;
    if (verbose)
      print_msg("Meta inserted!"); // togliere tutti questi messaggi, sono eccessivi
    temp = html.tag();
    if (verbose)
      print_msg("Inserting meta dc:title: " + temp);
    if (!noact)
      //offset = html.insert_tag("name=\"dc:title\" content=\"" + temp + "\"");
      html_out << "  <dc:title>" << temp << "</dc:title>" << endl;
    if (verbose)
      print_msg("Meta inserted!");
    // è meglio, per swish-e, usare il formato timestamp e PropertyNamesDate
    struct stat* html_attributes = new struct stat;
    if (stat((dirname + "/" + dir_entry).c_str(), html_attributes)) {
      cerr << "Error recovering file " + dir_entry + " attributes: date inserted "
        " probably wrong" << endl;
      date_err = true;
    }
    if (verbose) {
      temp = html_attributes->st_mtime;
      print_msg("Inserting meta dc:date: " + temp);
    }
    struct tm* html_time = localtime(&(html_attributes->st_mtime));
    	int html_year = html_time->tm_year + 1900;
        ostringstream out_date;
        out_date << html_year << "-" <<  to2digit((html_time->tm_mon + 1)) <<
            "-" << to2digit(html_time->tm_mday) << "T" <<
            to2digit(html_time->tm_hour) << ":" << to2digit(html_time->tm_min)
            << ":" << to2digit(html_time->tm_sec) << "Z";
    if (!noact)
      //offset = html.insert_tag("name=\"dc:date\" content=\"" + out_date.str() + "\"");
      html_out << "  <dc:date>" << out_date.str() << "</dc:date>" << endl;
    delete html_attributes;
    if (verbose)
      print_msg("Meta inserted!");
    ostringstream out_subj;
    int subject = guess_meta.subj_ex(html.text());
    if (subject != -1) {
      out_subj << int2subj(subject);
      if (verbose)
	print_msg("Inserting meta dc:subject: " + out_subj.str());
      if (!noact)
	//offset = html.insert_tag("name=\"dc:subject\" content=\"" + out_subj.str() + "\"");
	html_out << "  <dc:subject>" << out_subj.str() << "</dc:subject>" << endl;
      if (verbose)
        print_msg("Meta inserted!");
    }
    else {
      print_msg("Can't guess subject", true); // inserire lo stesso un meta?
      subj_err = true;
    }
    if (verbose)
      print_msg("Inserting meta dc:type: Web document");
    if (!noact)
      //offset = html.insert_tag("name=\"dc:type\" content=\"Web document\"");
      html_out << "  <dc:type>Web document</dc:type>" << endl;
    if (verbose)
      print_msg("Meta inserted!");
    int offset = dir_entry.find_first_of("/");
    if (verbose)
      print_msg("Inserting meta dc:publisher: " + dir_entry.substr(0, offset));
    if (!noact)
      //offset = html.insert_tag("name=\"dc:publisher\" content=\"" + dir_entry.substr(0, offset) +
      //              "\"");
      html_out << "  <dc:publisher>" << dir_entry.substr(0, offset) << "</dc:publisher>" << endl;
    if (verbose)
      print_msg("Meta inserted!");
    temp = html.attribute(); // si considera il body se manca la description?
    if (temp == "")
      temp = html.body();
    if (verbose)
      print_msg("Inserting meta dc:description: " + temp);
    if (!noact)
      //offset = html.insert_tag("name=\"dc:description\" content=\"" + temp + "\"");
      html_out << "  <dc:description>" << temp << "</dc:description>";
    html_out << endl << "</oai_dc:dc>" << endl;
    if (verbose)
      print_msg("Meta inserted!");
    if (!noact) {
      /*       ofstream html_ostream((dirname + "/" + dir_entry).c_str()); */
      /*       if (html_ostream == NULL) { */
      /*         print_msg("Can't write to " + dirname + "/" + dir_entry, true); */
      /*         w2f_err = true; */
      /*       } */
      /*       else */
      /*      cout << html; */
      /*       exit(0); */
      ostringstream xml_out;
      // l'output deve avere il giusto encoding e non devono esserci
      // caratteri '&', ma "&amp;"; deve essere però rivisto (toglie
      // anche i caratteri che dovrebbero restare)
      //string html_ok = html.txt2xml(html_out.str());
      xml_out << "Path-Name: " << dir_entry << endl;
      xml_out << "Content-Length: " << html_out.str().size() << endl << endl;
      cout << xml_out.str() << html_out.str();
      //html_ostream << html;
    }
    if (verbose)
      print_msg("File processed!");
  }
  if (w2f_err)
    err_code += 8;
  if (rfromf_err)
    err_code += 16;
  if (date_err)
    err_code += 32;
  if (subj_err)
    err_code += 64;
  exit(err_code);
}


