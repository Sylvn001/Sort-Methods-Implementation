package trabpo1bjr;

public class TrabPO1BJR {
    public static void executeSortMethodsInNode(){
        Lista lista = new Lista();
        for(int i = 1; i < 6; i++)       
            lista.inserirNoInicio(i);
        
        lista.exibir();
//        lista.insertionSort();
//        lista.binaryInsertionSort();
//        lista.selectionSort();
//        lista.bubbleSort();
//        lista.shakeSort(); 
        lista.heapSort();

        lista.exibir();
    }
    
    public static void executeFileSortMethods(){
        Arquivo_Java file = new Arquivo_Java("d:\\arquivo.dat");
//        file.leArq();
//        file.exibirArq();
//        file.BubbleSort();
//        file.ShakeSort();
//        file.SelectionSort();
        file.exibirArq();
    }
    
    public static void main(String[] args) {
        executeSortMethodsInNode();
//        executeFileSortMethods();
    }
    
}
