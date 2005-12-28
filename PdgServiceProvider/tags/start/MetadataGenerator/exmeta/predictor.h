/* Estrae il DC:subject dallo stream di dati in ingresso.
   E' richiesto:
   - un modello SVM;
   - un vocabolario delle feature;
   - un file di configurazione del FeatureExtractor;
   - un file di scaling.
   Le feature possono essere al massimo 9999 */

#ifndef PREDICTOR_H
#define PREDICTOR_H

#include <cstring>
#include "extract.h"
#include "../SVM/Data.h"
#include "../NaiveBayes/Classifier.h"
#include "../FeatureExtractor/TextFeatureExtractor.h"

class SVMrbfClassifier : public Classifier {
// meglio sarebbe definire una classe astratta, dalla quale derivare le classi per ogni kernel usato
  double lower, upper;
  double *feature_max, *feature_min;
  std::string svm_scale_out;

  std::string store_str(int index, double value);
  
 protected:
  class SVMrbfModel {
   public:
    double ** m_alphas;
    int * m_label;
    int m_class_number;
    int m_sv_number;
    Data * m_svs;
    float m_gamma;

    SVMrbfModel() {
      init();
    }
    ~SVMrbfModel(){
      if(m_alphas != NULL){
	for(int i = 0; i < m_sv_number; i++)
	  delete[] m_alphas[i];
	delete[] m_alphas;
      }
      if(m_label != NULL)
	delete[] m_label;
      if(m_svs != NULL)
	delete[] m_svs;
    }
    void init(){
      m_alphas = NULL;
      m_svs = NULL;
      m_label = NULL;
      m_class_number = m_sv_number = 0;
    }
    virtual double Kernel(Data x, Data y) const{
      double v = 0;
      for (Data::FeaturesIt i = x.m_features.begin(); i != x.m_features.end(); i++) {
	Data::FeaturesIt j = y.m_features.find(i->first);
        if (j != y.m_features.end())
          v += (i->second - j->second) * (i->second - j->second);
        // v rimane sempre 0, se il valore delle features è solo 1; vedi teoria SVM e TextFeatureExtractor
        // con quest'else considero parole presenti solo in i
        else
          v += i->second * i->second;
        // ma c'è sempre un problema! Devo iterare su ogni parola del dizionario (mancano i contributi delle parole
        // presenti solo in j)
      }
      for (Data::FeaturesIt j = y.m_features.begin(); j != y.m_features.end(); j++) {
        Data::FeaturesIt i = x.m_features.find(i->first);
        if (i != y.m_features.end())
          continue; // ignoro i contributi già considerati
        else
          v += j->second * j->second;
      }
      v = exp(-m_gamma * v);
      return v;
    }
    int Predict(Data x, double* margins = NULL) const{

      double * f = new double[m_class_number];
      for (int i=0;i<m_class_number;i++)
	f[i] = 0;

      for (int i=0;i<m_sv_number;i++)
	{
	  double kv = Kernel(x, m_svs[i]);
	  for (int j=0;j<m_class_number;j++)
	    f[j] += m_alphas[i][j]*kv;
	}

      int j = 0;
      for (int i=1;i<m_class_number;i++)
	if (f[i] > f[j])
	  j = i;

      if(margins != NULL)
	for (int i=0;i<m_class_number;i++)
	  margins[m_label[i]] = f[i];

      delete[] f;
      return m_label[j];
    }
    friend std::istream & operator>>(std::istream &in, SVMrbfModel& model){
      string buf;
      getline(in, buf);
      getline(in, buf);
      // il formato di modello per kernel non lineari è diverso
      // qui va estratta la gamma; è l'unica linea in più, il resto è uguale
      in >> buf >> model.m_gamma;
      in >> buf >> model.m_class_number;
      in >> buf >> model.m_sv_number;
      in >> buf;
      int* svs = new int[model.m_class_number];
      model.m_label = new int[model.m_class_number];
      for(int i = 0; i < model.m_class_number; i++)
	in >> model.m_label[i];
      in >> buf;
      for(int i = 0; i < model.m_class_number; i++)
	in >> svs[i];
      in >> buf;

      for(int i = 1; i < model.m_class_number; i++)
	svs[i] += svs[i-1];

      model.m_svs = new Data[model.m_sv_number];
      model.m_alphas = new (double*)[model.m_sv_number];
      int index = 0;
#ifdef PREDICTOR_DEBUG
      cout << "Modello:" << endl;
#endif
      for(int i = 0; i < model.m_sv_number; i++){
	if (i >= svs[index])
	  index++;
	model.m_alphas[i] = new double[model.m_class_number];
	char * pEnd;
	for(int j = 0; j < model.m_class_number; j++) {
	  in >> model.m_alphas[i][j];
#ifdef PREDICTOR_DEBUG
	  cout << model.m_alphas[i][j] << " "; // i valori stampati
					       // sono arrotondati
#endif
	}
	getline(in, buf);
	model.m_svs[i] = Data(buf, model.m_label[index]);
#ifdef PREDICTOR_DEBUG
	cout << model.m_svs[i];
#endif
      }
      delete []svs;
      return in;
    }
  };

  SVMrbfModel m_model;

 public:
  SVMrbfClassifier() : Classifier(), lower(-1.0), upper(1.0) {}
  SVMrbfClassifier(const char * in, int classes = 2) :
                                     Classifier(in, classes), lower(-1.0), upper(1.0) {}
  SVMrbfClassifier(std::istream& in_classifier, std::istream& scaling) : Classifier(),
    lower(-1.0), upper(1.0)
  {
    std::string buffer;
    svm_scale_out = "";

    while(getline(scaling, buffer))
      svm_scale_out += buffer + "\n";
#ifdef PREDICTOR_DEBUG
    cout << "Scaling:" << endl;
    cout << svm_scale_out << endl;
#endif
    in_classifier >> m_model;
  }
  virtual bool trainClassifier(const char* train_file, const char* train_stats,
                               const char * train_results, const char* command) {}
  virtual void testClassifier(const char* test_file, const char* results);
  virtual void testClassifier(std::istream& in, const char* results);
  virtual int classify(const std::string &example, double * scores = NULL) const;

  std::string scale(std::istream &example);
};

class Predictor {
  // si possono definire diversi tipi di classificatori SVM e stabilire quale inserire in Predictor a runtime
  TextFeatureExtractor ex;
  SVMrbfClassifier svmc;
 public:
  Predictor() : svmc(), ex() {};
  Predictor(std::istream& vocab, std::istream& config, std::istream& in_classifier, std::istream& scaling) :
    svmc(in_classifier, scaling), ex(config, vocab) {};
  ~Predictor() {};
  int subj_ex(const std::string& text);
};

#endif

