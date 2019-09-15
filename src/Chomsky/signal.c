#include<stdio.h>
#include<signal.h>
#include<unistd.h>

void jelkezelo(){
	printf("signal\n");
}

int main(void){
	if(signal(SIGINT, SIG_IGN)!=SIG_IGN){
		signal(SIGINT, jelkezelo);
	}


	while(1){	
			
	}
}
