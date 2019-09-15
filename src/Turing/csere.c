#include <stdio.h>

int main(){
	int a=4;
	int b=6;
	printf("Eredeti érték: a=%d és b=%d \n",a,b);

	//Különbséggel		
	a = a+b;		
	b = a-b;		
	a = a-b;
	printf("Felcserélt értékek: a=%d és b=%d \n",a,b);		

	//Szorzattal		
	a = a*b;		
	b = a/b;		
	a = a/b;
	printf("Felcserélt értékek: a=%d és b=%d \n",a,b);

	//exorral
	a = a^b; 
	b = b^a;
	a = a^b;
	printf("Felcserélt értékek: a=%d és b=%d \n",a,b);

	//segédváltozóval
	int csere;
	csere = a;
	a = b;
	b = csere;
	printf("Felcserélt értékek: a=%d és b=%d \n",a,b);
		
	printf("Felcserélt értékek: a=%d és b=%d \n",a,b);
}
