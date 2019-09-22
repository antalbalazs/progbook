#include<iostream>

class Madar{
public: 
	
};

class Sas : public Madar{
public:
	void repul() {
		std::cout << "Repül";
	}

};


int main(){

	Madar* sas = new Sas();
	Sas* sas2 = new Sas();

	sas->repul();
	sas2->repul();
	
}
