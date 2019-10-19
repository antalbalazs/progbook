#include <iostream>

class Lista {
   public:
	Lista(){
   	   std::cout << "ctor" << std::endl;
	   fej = new ListaElem();
	}

	~Lista(){
	   ListaElem* elem = fej;
	   while(elem){
		ListaElem* akt_elem = elem;
		elem = elem->kovetkezo;
		delete akt_elem;
	   }
	}

	Lista(Lista& regi){
	   std::cout << "copy ctor" << std::endl;
	   fej = masol(regi.fej);
	}

	Lista(Lista&& regi){
	   std::cout << "move ctor" << std::endl;
	   fej = std::move(regi.fej);
	   regi.fej = nullptr;
	}

	Lista& operator=(const Lista& regi){
	   std::cout << "copy assign" << std::endl;
	   fej = masol(regi.fej);
	   return *this;
	}

	Lista& operator=(Lista&& regi){
	   std::cout << "move assign" << std::endl;
	   fej = regi.fej;
	   regi.fej = nullptr;
	   return *this;
	}

	void beszur(int ertek){
	   ListaElem* kovElem = fej;
	   while(kovElem->kovetkezo != NULL){
		kovElem = kovElem->kovetkezo;
	   }
	   ListaElem* beszurElem = new ListaElem();
	   kovElem->kovetkezo = beszurElem;
	   beszurElem->adat = ertek;
	   beszurElem->kovetkezo = nullptr;
	}

	void kiir(){
	   ListaElem* elem = fej->kovetkezo;
	   while(elem != NULL){
		   std::cout << elem->adat << std::endl;
		   elem = elem->kovetkezo;
	   }
	}

	void kiir_memcim(){
	   ListaElem* elem = fej->kovetkezo;
	   while(elem != NULL){
	   	std::cout << elem->adat << "\t" << elem << std::endl;
	   	elem = elem->kovetkezo;
	   }
	}

	class ListaElem {
	   public:
		int adat = 0;
		ListaElem* kovetkezo = nullptr;
	};

private:
	ListaElem* fej = nullptr;
	ListaElem* masol(ListaElem* elem){
	   ListaElem* ujElem;
	   if(elem != NULL){
		ujElem = new ListaElem();
		ujElem->adat = elem->adat;
		if(elem->kovetkezo != NULL){
		   ujElem->kovetkezo=masol(elem->kovetkezo);
		}
		else{
		   ujElem->kovetkezo = nullptr;
		}		
	   }
		return ujElem;	
	}
};

int main(int argc, char* argv[]){
	std::cout << "Lista lista; //";
	Lista lista;

	lista.beszur(5);
	lista.beszur(3);
	lista.beszur(7);

	std::cout << "lista memóriacímek kiírása:" << std::endl;
	lista.kiir_memcim();

	std::cout << "\nLista lista2(lista); //";
	Lista lista2(lista);	
	std::cout << "lista2 memóriacímek kiírása:" << std::endl;
	lista2.kiir_memcim();

	std::cout << "\nLista lista3; //";
	Lista lista3;

	std::cout << "lista3=lista2; //";
	lista3=lista2;

	std::cout << "lista3 memóriacímek kiírása:" << std::endl;
	lista3.kiir_memcim();

	std::cout << "\nLista lista4=std::move(lista3); //";
	Lista lista4(std::move(lista3));

	std::cout << "lista4 memóriacímek kiírása:" << std::endl;
	lista4.kiir_memcim();

	std::cout << "\nLista lista5; //";
	Lista lista5;

	std::cout << "lista5 = std::move(lista4); //";
	lista5 = std::move(lista4);

	std::cout << "lista5 memóriacímek kiírása:" << std::endl;
	lista5.kiir_memcim();
	return 0;
}
