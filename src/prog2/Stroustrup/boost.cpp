#include <iostream>
#include <string>
#include <fstream>
#include <iomanip>
#include <vector>
#include <boost/filesystem.hpp>
<<<<<<< HEAD
using namespace std;

int db=0;

void read_file (boost::filesystem::path path, vector<string> folders)
=======
 
using namespace boost::system;
namespace filesys = boost::filesystem;
#ifndef USING_BOOST
#define USING_BOOST
#endif

std::vector<std::string> getAllFilesInDir(const std::string &dirPath, 	const std::vector<std::string> dirSkipList = { })
>>>>>>> 59d9f7d3a4b888825b5258625c84b02caf9653d7
{
	if(is_regular_file(path)){
		string ext(".java");
		if(!ext.compare(boost::filesystem::extension (path))) {
			string file=path.string();
			size_t end = file.find_last_of("/");
			string folder = file.substr(0,end);

 			folders.push_back(folder);
			cout << file << "\n";
			db++;
		}

 	}
	else if(is_directory(path))
 		for( boost::filesystem::directory_entry & entry : boost::filesystem::directory_iterator (path))
			 read_file(entry.path(), folders);
 	}

int main( int argc, char *argv[])
{
	string path="java";
	vector<string> folders;
	read_file(path, folders);
	cout << "A JDK osztályainak a száma: " << db << "\n";

}
