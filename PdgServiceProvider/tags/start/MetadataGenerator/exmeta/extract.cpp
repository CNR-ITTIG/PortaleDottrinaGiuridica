#include "extract.h"
#include <string>
#include <sstream>
#include <regexx.hh>

using namespace std;

string Extractor::tag(const string& tag_name) const {
  string tag_content = "";
  for (tree<HTML::Node>::iterator iter = mHtmlTree.begin(); iter != mHtmlTree.end(); ++iter) {
#ifdef EXTRACT_DEBUG
    cout << iter->tagName() << endl;
#endif
    if (iter->isTag())
      if (strcasecmp((iter->tagName()).c_str(), tag_name.c_str()) == 0) {
#ifdef EXTRACT_DEBUG
	cout << "Tag found:" << endl;
#endif
	tree<HTML::Node> sub_tr(iter);
        tree<HTML::Node>::iterator iter2 = sub_tr.begin();
        ++iter2;
	while (iter2 != sub_tr.end()) {
#ifdef EXTRACT_DEBUG
	  cout << iter2->tagName() << endl;
#endif
          if (!(iter2->isTag())) {
	    tag_content += (iter2->text());
#ifdef EXTRACT_DEBUG
	    cout << "tag_content: " << tag_content << endl;
#endif
	  }
	  ++iter2;
	}
#ifdef EXTRACT_DEBUG
        cout << "End tag"       << endl;
        cout << "tag_content: " << tag_content << endl;
#endif
	break;
      };
  };
  return text(tag_content);
}

string Extractor::attribute(const string& tag_name, const string& attribute_name,
                                  const string& other_attribute_name,
                                  const string& other_attribute_content) const {
  string content = "";
  for (tree<HTML::Node>::iterator iter = mHtmlTree.begin(); iter != mHtmlTree.end(); ++iter)
    if (iter->isTag())
      if ((strcasecmp((iter->tagName()).c_str(), tag_name.c_str()) == 0) &&
	  ((other_attribute_name == "") ||
	   (strcasecmp(((iter->attributes())[other_attribute_name]).c_str(),
		       other_attribute_content.c_str())))) {
	content = (iter->attributes())[attribute_name];
	break;
      };
  return text(content);
}

string Extractor::text(const string& html) const{
  using namespace regexx;
  Regexx rxx;
  string text;

  text = rxx.replace(html, "&amp;", "&", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "&nbsp;", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "&quot;", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "[_\\n\\r]", " ", Regexx::global|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "<!--.*?-->", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "<script.*?</script>", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "<style.*?</style>", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "<.*?>", " ", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  text = rxx.replace(text, "\\s+", " ", Regexx::global|Regexx::nomatch|Regexx::study);
#ifdef EXTRACT_DEBUG
  cout << "Plain text:" << endl;
  cout << text << endl;
  cout << "End plain text" << endl;
#endif
  return text;
  
//   string notags = "";
//   for (tree<HTML::Node>::iterator iter = mHtmlTree.begin(); iter != mHtmlTree.end(); ++iter)
//     if (!(iter->isTag()) && !(iter->isComment())) {
//       notags += iter->text();
// #ifdef EXTRACT_DEBUG
//       cout << (iter->text()) << endl;
//       cout << *iter << endl;
// #endif
//     };
//   return notags;
}

string Extractor::txt2xml(const string& txt) const
{
  using namespace regexx;
  Regexx rxx;
  string xml;

  xml = rxx.replace(txt, "&", "&amp;", Regexx::global|Regexx::nocase|Regexx::nomatch|Regexx::study);
  return xml;
}
  
int Extractor::insert_tag(const string& tag_name, const string& new_tag,
                          const string& new_attributes) {
  int offset = 0;
  for (tree<HTML::Node>::iterator iter = mHtmlTree.begin(); iter != mHtmlTree.end(); ++iter)
    if (iter->isTag())
      if (strcasecmp((iter->tagName()).c_str(), tag_name.c_str()) == 0) {
        // si costruisce newnode da new_tag e new_attributes
        HTML::Node* newnode = new HTML::Node;
        offset = iter->offset() + iter->text().size();
        newnode->offset(offset);
        string constructed("<" + new_tag + " " + new_attributes + ">\n");
        newnode->text(constructed);
        newnode->length(constructed.size());
        newnode->tagName(new_tag);
        newnode->isTag(true);
        newnode->isComment(false);
        newnode->parseAttributes();
        mHtmlTree.append_child(iter, *newnode);
        break;
      };
  return offset;
};

string Extractor::rebuild() const {
  //  return string(mpStart, (mHtmlTree.begin())->length());
  ostringstream html; // usando la stringa si ha segmentation fault
  for (tree<HTML::Node>::sibling_iterator iter = mHtmlTree.begin(); iter !=
	 mHtmlTree.end(); iter = mHtmlTree.next_sibling(iter)) {
#ifdef EXTRACT_DEBUG
//    cout << (iter->text());
#endif
//    html << (iter->text());
    if (iter.number_of_children() != 0)
      children_recurse(iter, html); // invece di una soluzione ricorsiva si può usare "parent" per tornare al padre
#ifdef EXTRACT_DEBUG
//    cout << (iter->closingText());
#endif
//    html << (iter->closingText());
  };
  return html.str();
};

void Extractor::children_recurse(tree<HTML::Node>::iterator_base& iter,
                                 ostringstream& html) const {
  for (tree<HTML::Node>::sibling_iterator sibiter = iter.begin();
       sibiter != iter.end();
       sibiter = mHtmlTree.next_sibling(sibiter)) {
#ifdef EXTRACT_DEBUG
    cout << (sibiter->text());
#endif
    html << (sibiter->text());
    if (iter.number_of_children() != 0)
      children_recurse(sibiter, html);
#ifdef EXTRACT_DEBUG
    cout << (sibiter->closingText());
#endif
    html << (sibiter->closingText());
  };
};
