#include <fstream>
#include <sstream>
#include <cfloat>
#include "predictor.h"

#define max(x,y) ((x>y)?x:y)
#define min(x,y) ((x<y)?x:y)

void SVMrbfClassifier::testClassifier(const char* test_file, const char* results){

  ifstream in(test_file);
  assert(in.good());
  testClassifier(in, results);
}


void SVMrbfClassifier::testClassifier(istream& in, const char* results)
{
  int correct = 0;
  int total = 0;
  double error = 0, v;
  double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

  ofstream out(results);
  assert(out.good());

  int target = -1;
  string row;
  while(getline(in,row)){
    Data x(row);
    target = x.m_label;
    v = m_model.Predict(x);
    if(v == target)
      ++correct;
    error += (v-target)*(v-target);
    sumv += v;
    sumy += target;
    sumvv += v*v;
    sumyy += target*target;
    sumvy += v*target;
    ++total;

    out << v << endl;
  }

  cout << "Accuracy = " << (double)correct/total*100
       << "% (" << correct << "/" << total << ") (classification)" << endl;
}

int SVMrbfClassifier::classify(const string& example, double* scores) const
{
  return m_model.Predict(Data(example), scores);
}

int Predictor::subj_ex(const string& text) {
  stringstream out;
  if (ex.buildExample(out, text, 0)) {
    // out deve essere scalato all'intervallo specificato nel file
    // prodotto da svm-scale
    string scaled = svmc.scale(out);
    int classid = svmc.classify(scaled);
    return classid;
  }
  return -1;
}

string SVMrbfClassifier::scale(istream &example)
{
  // ripreso con poche modifiche da svm-scale.c
  int p, index;
  string line;
  double y_max = -DBL_MAX;
  double y_min = DBL_MAX;
  ostringstream output;

#define SKIP_TARGET\
	while(isspace(line[p])) ++p;\
	while(!isspace(line[p])) ++p;

#define SKIP_ELEMENT\
	while(line[p]!=':') ++p;\
	++p;\
	while(isspace(line[p])) ++p;\
	while((p < line.length()) && !isspace(line[p])) ++p;
	
  /* assumption: min index of attributes is 1 */
  /* pass 1: find out max index of attributes */
  int max_index = 0;
#ifdef PREDICTOR_DEBUG
  cout << "Vettore delle feature da normalizzare:" << endl;
#endif

  stringstream buffer;
  
  while(getline(example, line))
    {
#ifdef PREDICTOR_DEBUG
      cout << line << endl;
#endif
      buffer << line;
      p = 0;
      
      SKIP_TARGET

	while(sscanf(line.substr(p, line.length() - 1).c_str(),"%d:%*f",&index)==1)
	  {
	    max_index = max(max_index, index);
	    SKIP_ELEMENT
	      }		
    }

  feature_max = new(double[max_index + 1]);
  feature_min = new(double[max_index + 1]);

  for(int i=0;i<=max_index;i++)
    {
      feature_max[i]=-DBL_MAX;
      feature_min[i]=DBL_MAX;
    }

  stringstream buffer2;
  
  /* pass 2: find out min/max value */
  while(getline(buffer, line))
    {
      buffer2 << line;
      p = 0;
      int next_index=1;
      double target;
      double value;

      sscanf(line.substr(p, line.length() - 1).c_str(),"%lf",&target);
      y_max = max(y_max,target);
      y_min = min(y_min,target);
		
      SKIP_TARGET

	while(sscanf(line.substr(p, line.length() - 1).c_str(),"%d:%lf",&index,&value)==2)
	  {
	    for(int i=next_index;i<index;i++)
	      {
		feature_max[i]=max(feature_max[i],0);
		feature_min[i]=min(feature_min[i],0);
	      }
			
	    feature_max[index]=max(feature_max[index],value);
	    feature_min[index]=min(feature_min[index],value);

	    SKIP_ELEMENT
	      next_index=index+1;
	  }		

      for(int i=next_index;i<=max_index;i++)
	{
	  feature_max[i]=max(feature_max[i],0);
	  feature_min[i]=min(feature_min[i],0);
	}	
    }

  /* pass 2.5: save/restore feature_min/feature_max */
	
  int idx;
  double fmin, fmax;
  istringstream scaling_stream(svm_scale_out);
		
  scaling_stream >> lower;
  scaling_stream >> upper;
  
  while(true)
    {
      scaling_stream >> idx;
      scaling_stream >> fmin;
      scaling_stream >> fmax;
      if (scaling_stream.eof())
	break;
      if(idx<=max_index)
	{
	  feature_min[idx] = fmin;
	  feature_max[idx] = fmax;
	}
    }
	
  /* pass 3: scale */
  while(getline(buffer2, line))
    {
      p = 0;
      int next_index=1;
      int index;
      double target;
      double value;
		
      sscanf(line.substr(p, line.length() - 1).c_str(),"%lf",&target);
      output << target << " ";

      SKIP_TARGET

	while(sscanf(line.substr(p, line.length() - 1).c_str(),"%d:%lf",&index,&value)==2)
	  {
	    for(int i=next_index;i<index;i++)
	      output << store_str(i,0);
			
	    output << store_str(index,value);

	    SKIP_ELEMENT
	      next_index=index+1;
	  }		

      for(int i=next_index;i<=max_index;i++)
	output << store_str(i,0);

      output << endl;
    }

#ifdef PREDICTOR_DEBUG
  cout << "Vettore delle feature normalizzato:" << endl;
  cout << output.str() << endl;
#endif

  delete(feature_max);
  delete(feature_min);
  return output.str();
}

string SVMrbfClassifier::store_str(int index, double value)
{
/* skip single-valued attribute */
  string output = "";
  
  if(feature_max[index] == feature_min[index])
    return output;

  if(value == feature_min[index])
    value = lower;
  else if(value == feature_max[index])
    value = upper;
  else
    value = lower + (upper-lower) * 
      (value-feature_min[index])/
      (feature_max[index]-feature_min[index]);

  if(value != 0)
    output = string(gcvt(index, 4, new char)) + ":" + string(gcvt(value, 4, new char)) + " ";

  return output;
}
