package trabpo1bjr;

public class TrabPO1BJR {
    public static void executeSortMethodsInNode(){
        Lista lista = new Lista();
        for(int i = 1; i <= 16; i++)       
            lista.inserirNoInicio(i);
        
        lista.exibir();
//        lista.insertionSort();
//        lista.binaryInsertionSort();
//        lista.selectionSort();
//        lista.bubbleSort();
//        lista.shakeSort(); 
//        lista.heapSort();
//        lista.shellSort();
//        lista.quickSort();
//        lista.quickSortPivot();
//        lista.mergeSort();
//        lista.mergeSort2Way();
//        lista.countingSort(); 
//        lista.bucketSort();
//        lista.radixSort();
//          lista.combSort();
          lista.gnomeSort();;
        lista.timSort();
        lista.exibir();
    }
    
    public static void executeFileSortMethods(){
        Arquivo_Java file = new Arquivo_Java("d:\\arquivo.dat");
//        file.leArq();
//        file.exibirArq();
//        System.out.println("");
//        file.insertionSort();
//        file.binaryInsertionSort();
//        file.selectionSort();
//        file.bubbleSort();
//        file.shakeSort();
//        file.heapSort();
//        file.shellSort();
//        file.quickSort();
//        file.quickSortPivot();
        file.exibirArq();
    }
    
    public static void main(String[] args) {
        executeSortMethodsInNode();
//        executeFileSortMethods();
    }
    
}
