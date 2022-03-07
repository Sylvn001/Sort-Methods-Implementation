package trabpo1bjr;

public class TrabPO1BJR {
    public static void executeSortMethodsInNode(){
        Lista lista = new Lista(), l2;
        for(int i = 1; i < 10; i++)       
            lista.inserirNoInicio(i);
        
        lista.exibir();
        //Descomente o metodo que deseja testar
//        lista.insertionSort();
//        lista.SelectionSort();
//        lista.BubbleSort();

        lista.ShakeSort(); 
        lista.exibir();
    }
    
    public static void executeFileSortMethods(){
        Arquivo_Java file = new Arquivo_Java("d:\\arquivo.dat");
//        file.leArq();
//        file.exibirArq();
//        file.BubbleSort();
//        file.ShakeSort();
        file.exibirArq();
    }
    
    public static void main(String[] args) {
//        executeSortMethodsInNode();
        executeFileSortMethods();
    }
    
}
