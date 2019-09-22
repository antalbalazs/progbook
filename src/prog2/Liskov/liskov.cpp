#include <iostream>
using namespace std;

class Madar {
public:
     void repul() {
	cout << "Repül";}
};

class Sas : public Madar
{};

class Pingvin : public Madar 
{};

int main ( int argc, char **argv )
{
     Madar madar;
     madar.repul();
     cout << " a madár\n";

     Sas sas;
     sas.repul();
     cout << " a sas\n";

     Pingvin pingvin;
     pingvin.repul();
     cout << " a pingvin. De a pingvin nem tud repülni, ezért sérült a Liskov elv.\n";

}

