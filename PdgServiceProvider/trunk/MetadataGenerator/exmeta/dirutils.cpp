#include <iostream>
#include "dirutils.h"

using namespace std;

void Dir_Extended::set_dir(const std::string& dir_name) {
  unsigned int last_slash = dir_name.find_last_of('/');
  if (last_slash == dir_name.size() - 1)
    root_dir_name = dir_name.substr(0, dir_name.size() - 1);
  else
    root_dir_name = dir_name;
  last_opendir.clear();
  root_dir = opendir(root_dir_name.c_str());
  if (root_dir != NULL)
    open = true;
}

Dir_Extended::~Dir_Extended() {
  closedir(root_dir);
  /* last_opendir viene deallocato automaticamente da ~vector */
}

std::string Dir_Extended::next_file() {
  std::string temp_dir;
  if (!check_open())
    return "";
  while (!last_opendir.empty()) {
    temp_dir = last_opendir.back().next_file();
    if (temp_dir != "")
      return last_opendir.back().name() + "/" + temp_dir;
    last_opendir.pop_back();
  }

  struct dirent* dirent_p;
  while((dirent_p = readdir(root_dir))) {
    if (!strcmp(dirent_p->d_name,".") || !strcmp(dirent_p->d_name, ".."))
      continue;
    Dir_Extended* subdir = new Dir_Extended(root_dir_name + "/" + dirent_p->d_name);
    if (subdir->check_open()) {
      temp_dir = subdir->next_file();
      if (temp_dir != "") {
	last_opendir.push_back(*subdir);
	return subdir->name() + "/" + temp_dir;
      }
    }
    delete subdir;
    return string(dirent_p->d_name);
  }
  return "";
}
