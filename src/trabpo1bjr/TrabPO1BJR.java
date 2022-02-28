package trabpo1bjr;

public class TrabPO1BJR {
    public static void main(String[] args) {
//        Arquivo_Java a = new Arquivo_Java("c:\\arquivo.dat");;;
        Lista lista = new Lista();
        
        for(int i = 1; i < 10; i++)       
            lista.inserirNoInicio(i);
  
        lista.exibir();  
//        lista.insertionSort();
//        lista.SelectionSort();
//        lista.BubbleSort();
        lista.ShakeSort();
        lista.exibir();  

    }
    
}
