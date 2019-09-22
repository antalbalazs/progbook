#include <iostream>
using namespace std;

class Madar {
public:
     virtual void repul() {};
};

class Program {
public:
     void fgv ( Madar &madar ) {
          madar.repul();
     }
};

class Sas : public Madar
{};

class Pingvin : public Madar 
{};

int main ( int argc, char **argv )
{
     Program program;
     Madar madar;
     program.fgv ( madar );
     cout << "Repül a madár\n";

     Sas sas;
     program.fgv ( sas );
     cout << "Repül a sas\n";

     Pingvin pingvin;
     program.fgv ( pingvin );
     cout << "Repül a pingvin. De a pingvin nem tud repülni, ezért sérült a Liskov elv.\n";

}

