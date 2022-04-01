package trabpo1bjr;


import java.io.RandomAccessFile;
import java.io.IOException;


//... classe Arquivo (onde vai estar o m�todo para ordernar, etc) ....
public class Arquivo_Java implements Methods
{
    private String nomearquivo;
    private RandomAccessFile arquivo;

    public Arquivo_Java(String nomearquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }

    public void truncate(long pos) //desloca eof
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }

    //semelhante ao feof() da linguagem C
    //verifica se o ponteiro esta no <EOF> do arquivo
    public boolean eof()  
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;                               
        } catch (IOException e)
        { }
        return (retorno);
    }
    
    public int filesize(){
        int size = 0;
        
        try{
            size = (int) (arquivo.length() /  Registro.length());
        }
        catch(Exception e){
            System.out.println("ss");
        }
        
        return size;
    }

    //insere um Registro no final do arquivo, passado por par�metro
    public void inserirRegNoFinal(Registro reg)
    {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }

    public void exibirArq()
    {
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
        while (!this.eof())
        {
            System.out.print("Posicao: " + i);
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
    }

    public void exibirUmRegistro(int pos)
    {
        Registro aux = new Registro();
        seekArq(pos);
        System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
        aux.exibirReg();
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }

    public void leArq()
    {
        int codigo;
        codigo = Entrada.leInteger("Digite o c�digo");
        while (codigo != 0)
        {
            inserirRegNoFinal(new Registro(codigo));
            codigo = Entrada.leInteger("Digite o c�digo");
        }
    }
    
    
    @Override
    public void insertionSort() {
        int i=1, pos, TL = filesize(); 
        Registro regAux = new Registro();
        Registro regAnt = new Registro();
        
        while(i < TL){
            pos = i;
            seekArq(i);
            regAux.leDoArq(arquivo);
            
            seekArq(pos-1);
            regAnt.leDoArq(arquivo);
            while(pos > 0 && regAux.getCodigo() < regAnt.getCodigo()){
                seekArq(pos);
                regAnt.gravaNoArq(arquivo);
                
                pos--;
                seekArq(pos-1);
                regAnt.leDoArq(arquivo);
            }
            
            seekArq(pos);
            regAux.gravaNoArq(arquivo);
                    
            i++;
        }
    }
    
    @Override
    public void binaryInsertionSort() {
        int i=1,j, pos, TL = filesize(); 
        Registro regAux = new Registro();
        Registro regJ = new Registro();
        
        while(i < TL){
            seekArq(i);
            regAux.leDoArq(arquivo);
            pos = binarySearch(regAux, i);
            
            j = i;
            while(j > pos){
                seekArq(j-1);
                regJ.leDoArq(arquivo);
                seekArq(j);
                regJ.gravaNoArq(arquivo);
                j--;
            }
            
            seekArq(pos);
            regAux.gravaNoArq(arquivo);
            i++;
        }
    }

    @Override
    public void selectionSort() {
        int posMenor = 0, i=0, j, TL = filesize();
        Registro regI = new Registro();
        Registro regJ = new Registro();
        Registro regMenor = new Registro();
        
        while(i < TL){
            seekArq(i);
            regI.leDoArq(arquivo);
            
            seekArq(i);
            regMenor.leDoArq(arquivo);
            posMenor = i;
            
            j = i+1;
            while(j < filesize()){
                seekArq(j);
                regJ.leDoArq(arquivo);

                if(regJ.getCodigo() < regMenor.getCodigo() )    
                {
                    posMenor = j;
                    seekArq(posMenor);
                    regMenor.leDoArq(arquivo);
                }
                j++;
            }
            
            seekArq(posMenor);
            regI.gravaNoArq(arquivo);
            seekArq(i);
            regMenor.gravaNoArq(arquivo);
            
            
            i++;
        }        
    }

    
    public void bubbleSort(){
        int TL2 = filesize();
        Registro reg1 = new Registro(), reg2 = new Registro();
        
        while(TL2 > 1){
            for(int i=0; i< TL2-1; i++){
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if(reg1.getCodigo() > reg2.getCodigo()){
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                }
            }
            TL2--;
        }
    }
        
    @Override
    public void shakeSort() {
        int inicio = 0, fim = filesize()-1;
        Registro reg1 = new Registro(), reg2 = new Registro();
        
        while(inicio < fim){
            for(int i=inicio; i< fim; i++){
                seekArq(i);
                reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                if(reg1.getCodigo() > reg2.getCodigo()){
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);
                }          
            }
            fim--;
            for(int i=fim; i > inicio; i--){
                seekArq(i-1);
                reg2.leDoArq(arquivo); // pos: (i-1)
                reg1.leDoArq(arquivo); // pos(i)
                if(reg1.getCodigo() < reg2.getCodigo()){
                    seekArq(i-1);
                    reg1.gravaNoArq(arquivo);
                    reg2.gravaNoArq(arquivo);
                }
            }
            inicio++;
        }
    }   

    public int binarySearch(Registro chave, int TL) {
        int inicio = 0, fim = TL-1, meio;
        Registro regMeio = new Registro();

        meio = fim/2;        
        seekArq(meio);
        regMeio.leDoArq(arquivo);
        
        while(inicio < fim && chave.getCodigo() != regMeio.getCodigo()){
            if(chave.getCodigo() > regMeio.getCodigo())
                inicio = meio +1;
            else
                fim = meio - 1;
            
            meio = (inicio + fim) /2;
            seekArq(meio);
            regMeio.leDoArq(arquivo);
        }
        
        if(chave.getCodigo() > regMeio.getCodigo())
            return meio+1;
        else
            return meio;
    }    

    @Override
    public void heapSort() {
        int posPai = 0, posFD, posFE, TL = filesize(), posMaiorF;
        Registro reg1 = new Registro(), reg2 = new Registro();
        while(TL > 0){
            posPai =  TL / 2 - 1; 
            
            while(posPai >= 0){
                posFE = posPai * 2+1;
                posFD = posFE+1;
                posMaiorF = posFE;
                
                seekArq(posFE);
                reg1.leDoArq(arquivo);
                seekArq(posFD);
                reg2.leDoArq(arquivo);
                               
                if(posFD < TL && reg1.getCodigo() < reg2.getCodigo()){
                    posMaiorF = posFD;
                }
                
                seekArq(posMaiorF);
                reg1.leDoArq(arquivo);
                seekArq(posPai);
                reg2.leDoArq(arquivo);
                
                if(reg1.getCodigo() > reg2.getCodigo()){
                    seekArq(posPai);
                    reg1.gravaNoArq(arquivo);
                    seekArq(posMaiorF);
                    reg2.gravaNoArq(arquivo);
                }

                posPai--;
            }
            
            TL--;

            seekArq(0);
            reg1.leDoArq(arquivo);    
            seekArq(TL);
            reg2.leDoArq(arquivo);
            
            seekArq(TL);
            reg1.gravaNoArq(arquivo);
            seekArq(0);
            reg2.gravaNoArq(arquivo);            
        }
            
    }

    @Override
    public void shellSort() {
        int dist = 4, i, j, k, TL = filesize();
        Registro reg = new Registro(), regDist = new Registro();
        while(dist > 0){
            i = 0;
            while( i < dist){
                j = i;
                while(j+dist < TL){
                    seekArq(j);
                    reg.leDoArq(arquivo);
                    seekArq(j+dist);
                    regDist.leDoArq(arquivo);
                    if(reg.getCodigo() > regDist.getCodigo())
                    {
                        seekArq(j);
                        regDist.gravaNoArq(arquivo);
                        seekArq(j+dist);
                        reg.gravaNoArq(arquivo);

                        k = j;
                        seekArq(k);
                        reg.leDoArq(arquivo);
                        seekArq(k-dist);
                        regDist.leDoArq(arquivo);
                        while(k - dist >= 0 && reg.getCodigo() < regDist.getCodigo()){   
                            //swap
                            seekArq(k);
                            regDist.gravaNoArq(arquivo);
                            seekArq(k-dist);
                            reg.gravaNoArq(arquivo);
                            
                            //leitura
                            k = k - dist;
                            seekArq(k);
                            reg.leDoArq(arquivo);
                            seekArq(k-dist);
                            regDist.leDoArq(arquivo);
                        }   
                    }
                    j = j+dist;
                }
                i++;
            }
            dist = dist / 2;
        }
    }
    
    public void quickSP(int inicio, int fim){
        int i = inicio, j = fim;
        Registro reg1 = new Registro(), reg2 = new Registro();

        while(i < j)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            while(i < j && reg1.getCodigo() <= reg2.getCodigo()){
                i++;      
                seekArq(i);
                reg1.leDoArq(arquivo);
            }

            seekArq(i);
            reg2.gravaNoArq(arquivo);
            seekArq(j);
            reg1.gravaNoArq(arquivo);

            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            while(i < j && reg2.getCodigo() >= reg1.getCodigo()){
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }

            seekArq(i);
            reg2.gravaNoArq(arquivo);
            seekArq(j);
            reg1.gravaNoArq(arquivo);
        }
        if(inicio < i-1)
            quickSP(inicio, i-1);
        if(j+1 < fim)
            quickSP(j+1, fim);
    }

    public void quickSort(){
        int inicio = 0, fim = filesize();
        quickSP(inicio, fim-1);
    }
    
    public void quickWP(int inicio, int fim){
        int i = inicio, j = fim, meio = (inicio + fim) / 2;
        Registro reg1 = new Registro(), reg2 = new Registro(), pivot = new Registro();
        seekArq(meio);
        pivot.leDoArq(arquivo);

        while(i < j)
        {
            seekArq(i);
            reg2.leDoArq(arquivo);
            while(reg1.getCodigo() < pivot.getCodigo()){
                i++;      
                seekArq(i);
                reg1.leDoArq(arquivo);
            }
            
            seekArq(j);
            reg2.leDoArq(arquivo);
            while(reg2.getCodigo() > pivot.getCodigo()){
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }
            if(i <= j ){
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
                i++;
                j--;                          
            }
        }
        if(inicio < j)
            quickWP(inicio, j);
        if(i < fim)
            quickWP(i, fim);
    }

    public void quickSortPivot(){
        int inicio = 0, fim = filesize();
        quickWP(inicio, fim-1);
    }

    @Override
    public void mergeSort() {
//        Lista lista1 = new Lista(), lista2 = new Lista();
//        int seq=1;
//
//        while(seq < filesize())
//            particao(vet1, vet2)
//            fusao(vet1,vet2,seq)
//            seq = seq*2;
//        )
    }

    @Override
    public void countingSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void fusao(int ini1, int fim1, int ini2, int fim2, int aux[]){
//        int k, i = ini1, j = ini2;
//
//        while(i <= fim1 && j <= fim2){
//            if(vet[i] < vet[j])
//                aux[k++] = vet[i++];
//            else
//                aux[k++] = vet[j++;]      
//        }
//
//        while(i <= fim1)
//            aux[k++] = vet[i++];
//
//        while(j <= fim2)
//            aux[k++] = vet[j++;]
//
//        for(i=0; i < k; i++){
//            vet[i+ini1] = aux[i];
//        }

    }

    public void merge2(int esq, int dir, int vet[]){
//        if(esq < dir){
//           int meio = (esq + dir) / 2;
//           merge2(esq, meio, aux);
//           merge2(meio+1, dir, aux);
//           fusao(esq, meio, meio+1, dir, aux);
//           //   ini1  fim1  ini2    fim2 
//        }
    }


    @Override
    public void mergeSort2Way() {
//        int aux[] = new int[TL];
//        merge2(0, TL-1, aux);
    }
    
    public void count_sort() {
        int TL = filesize();
        int maior;
        int menor;
        int i;
        int pos;
        Registro reg = new Registro();
        seekArq(0);

        reg.leDoArq(arquivo);
        maior = reg.getCodigo();
        menor = reg.getCodigo();
        for (i = 1; i < TL; i++) {
            reg.leDoArq(arquivo);
            compProg++;
            if (maior < reg.getCodigo()) {
                maior = reg.getCodigo();
            }
            compProg++;
            if (menor > reg.getCodigo()) {
                menor = reg.getCodigo();
            }
        }
        int aux[] = new int[maior + 1];

        for (i = 0; i < maior + 1; i++) {
            aux[i] = 0;
        }

        for (i = 0; i < TL; i++) {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = reg.getCodigo();
            aux[pos - menor] += 1;
        }

        for (i = 1; i < maior + 1; i++) {
            aux[i] += aux[i - 1];
        }
        int vet[] = new int[TL];

        for (i = 0; i < TL; i++) {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = reg.getCodigo();
            vet[aux[pos] - 1] = pos;
        }

        seekArq(0);
        for (i = 0; i < TL; i++) 
            reg.gravaNoArq(arquivo);   
    }

    public void bucket_sort() {
        Registro reg1 = new Registro();
        ArrayList<ArrayList<Integer>> lista = new ArrayList<>();
        int divisor;
        int TL = filesize();
        int j;
        int vet[] = new int[TL];
        int maior, menor;

        for (int i = 0; i < 10; i++) {
            lista.add(new ArrayList<>());
        }

        seekArq(0);
        reg1.leDoArq(arquivo);
        maior = reg1.getCodigo();
        menor = reg1.getCodigo();
        for (int i = 1; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            compProg++;
            if (maior < reg1.getCodigo()) {
                maior = reg1.getCodigo();
            }
            compProg++;
            if (menor > reg1.getCodigo()) {
                menor = reg1.getCodigo();
            }
        }
        divisor = (int) ((maior + 1) * 100) / 10;

        for (int i = 0; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            j = reg1.getCodigo() / divisor;
            lista.get(j).add(reg1.getCodigo());
        }
        for (int i = 0; i < 10; i++) {
            Collections.sort(lista.get(i));
        }
        int pos = 0;
        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < lista.get(i).size(); x++) {
                vet[pos] = lista.get(i).get(x);
                pos++;
            }
        }
        for (int i = 0; i < TL; i++) {
            movProg++;
            seekArq(i);
//            reg1.setNumero(vet[i]);
            reg1.gravaNoArq(arquivo);
        }
    }

    public void radix_sort() {
        Registro reg1 = new Registro();
        ArrayList<ArrayList<Integer>> lista = new ArrayList<>();
        int num;
        int TL = filesize();
        int pos = 0;
        int maiorNum = 0;
        int vet[] = new int[TL];
        String numero;
        for (int i = 0; i < 10; i++) {
            lista.add(new ArrayList<>());
        }
        for (int i = 0; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            numero = "" + reg1.getCodigo();
            compProg++;
            if (i == 0) {
                maiorNum = numero.length();
            }
            compProg++;
            if (maiorNum < numero.length()) {
                maiorNum = numero.length();
            }
        }
        for (int i = 0; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            numero = "" + reg1.getCodigo();
            for (int j = numero.length(); j < maiorNum; j++) {
                numero = "0" + numero;
            }
            num = Integer.parseInt("" + numero.charAt(pos));
            lista.get(num).add(Integer.parseInt(numero));
        }
        int lugar = 0;
        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < lista.get(i).size(); x++) {
                movProg++;
                num = lista.get(i).get(x);
                vet[lugar] = num;
                lugar++;
            }
            lista.get(i).clear();
        }
        for (pos = 1; pos < maiorNum; pos++) {

            for (int i = 0; i < TL; i++) {
                numero = "" + vet[i];
                for (int j = numero.length(); j < maiorNum; j++) {
                    numero = "0" + numero;
                }
                num = Integer.parseInt("" + numero.charAt(pos));
                lista.get(num).add(Integer.parseInt(numero));
            }
            lugar = 0;
            for (int i = 0; i < 10; i++) {
                for (int x = 0; x < lista.get(i).size(); x++) {
                    movProg++;
                    num = lista.get(i).get(x);
                    vet[lugar] = num;
                    lugar++;
                }
                lista.get(i).clear();
            }
        }
        for (int i = 0; i < TL; i++) {
            movProg++;
            seekArq(i);
//            reg1.setNumero(vet[i]);
            reg1.gravaNoArq(arquivo);
        }
    }

    public void gnome_sort() {
        int TL = filesize();
        int j;

        Registro reg = new Registro();
        Registro reg2 = new Registro();
        seekArq(0);
        for (int gnomo = 0; gnomo < TL - 1; gnomo++) {
            seekArq(gnomo);
            reg.leDoArq(arquivo);
            reg2.leDoArq(arquivo);
            compProg++;
            if (reg.getCodigo() > reg2.getCodigo()) {
                movProg++;
                seekArq(gnomo);
                reg2.gravaNoArq(arquivo);
                reg.gravaNoArq(arquivo);
                gnomo = -1;
                j = gnomo - 1;

                seekArq(j);
                reg.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                compProg++;
                while (j > 0 && reg.getCodigo() < reg2.getCodigo()) {
                    movProg++;
                    seekArq(gnomo);
                    reg2.gravaNoArq(arquivo);
                    reg.gravaNoArq(arquivo);
                    gnomo = -1;
                    j = gnomo - 1;
                    j--;
                }
            }
        }
    }

    public void comb_sort() {
        int TL = filesize();
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        seekArq(0);
        int gap = TL;

        while (gap >= 1) {
            for (int i = 0; i < TL; i++) {
                if (i + gap < TL) {
                    seekArq(i);
                    reg.leDoArq(arquivo);
                    seekArq(i + gap);
                    reg2.leDoArq(arquivo);
                    compProg++;
                    if (reg.getCodigo() > reg2.getCodigo()) {
                        seekArq(i);
                        reg2.gravaNoArq(arquivo);
                        seekArq(i + gap);
                        reg.gravaNoArq(arquivo);
                        movProg++;
                    }
                }
            }
            gap = (int) (gap / 1.3);
        }
    }


}
