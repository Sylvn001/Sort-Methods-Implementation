package trabpo1bjr;


import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


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
        int seq=1, size = filesize();        
        Arquivo_Java file1 = new Arquivo_Java("d:\\tmp_file1.dat");
        Arquivo_Java file2 = new Arquivo_Java("d:\\temp_file2.dat");

        while(seq < filesize()){
            particao(file1, file2, size);
            fusao(file1,file2,seq, size);
            seq = seq*2;
        }
    }
    
    public void particao(Arquivo_Java file1, Arquivo_Java file2, int TL){
        int i, tam = TL/2;
        Registro regArquivo = new Registro();            
        
        i=0;
        while(i < tam/2){
            this.seekArq(i);
            regArquivo.leDoArq(arquivo);
            
            file1.seekArq(0);
            file1.inserirRegNoFinal(regArquivo);
            
            this.seekArq(i*2);
            regArquivo.leDoArq(arquivo);
            
            file2.seekArq(i);
            file2.inserirRegNoFinal(regArquivo);
            i++;
        }
    }
    
    public void fusao(Arquivo_Java file1, Arquivo_Java file2, int seq, int TL){
       int k = 0, i=0, j=0, tseq = seq; 
       Registro reg1 = new Registro(), reg2 = new Registro();            

       while(k < TL){
           while(i < seq && j < seq){
               this.seekArq(k);
               file1.seekArq(i);
               reg1.leDoArq(file1.arquivo);
               
               file2.seekArq(j);
               reg2.leDoArq(file2.arquivo);
               if(reg1.getCodigo() < reg2.getCodigo()){
                   reg1.gravaNoArq(arquivo);
                   k++;
                   i++;
               }
               else{
                   reg2.gravaNoArq(arquivo);
                   k++;
                   j++;
               }
           }

           while(i < seq){
               file1.seekArq(i);
               reg1.leDoArq(file1.arquivo);          
               this.seekArq(k);
               reg1.gravaNoArq(arquivo);
               i++;
               k++;
           }

           while(j < seq){
               file2.seekArq(j);
               reg2.leDoArq(file2.arquivo);
               this.seekArq(j);
               reg2.gravaNoArq(arquivo);
               j++;
               k++;
           }
           
           seq = seq+tseq;
       }
   }
    
    
    public void fusao2(int ini1, int fim1, int ini2, int fim2, Arquivo_Java aux){
        int k=0, i = ini1, j = ini2;
        Registro regJ = new Registro(), regI = new Registro();
        
        while(i <= fim1 && j <= fim2){
            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);
            
            aux.seekArq(k);
            if(regI.getCodigo() < regJ.getCodigo()){
                regI.gravaNoArq(aux.arquivo);
                k++;
                i++;
            }
            else{
                regJ.gravaNoArq(aux.arquivo);
                k++;
                i++;
            }
        }

        while(i <= fim1){
            aux.seekArq(k);
            regJ.gravaNoArq(aux.arquivo);
            k++;
            i++;
        }

        while(j <= fim2){
            aux.seekArq(k);
            regJ.gravaNoArq(aux.arquivo);
            k++;
            i++;
        }

        for(i=0; i < k; i++){
            aux.seekArq(i);
            regI.leDoArq(aux.arquivo);
            
            this.seekArq(i+ini1);
            regI.gravaNoArq(arquivo);
        }

    }


    public void merge2(int esq, int dir, Arquivo_Java aux){;
        if(esq < dir){
           int meio = (esq + dir) / 2;
           
           merge2(esq, meio, aux);
           merge2(meio+1, dir, aux);
           fusao2(esq, meio, meio+1, dir, aux);
        }
    }


    @Override
    public void mergeSort2Way() {
        Arquivo_Java aux = new Arquivo_Java("d:\\temp1.dat");
        int TL = filesize();
        
        merge2(0, TL-1, aux);
    }
    
    public int getMaior(){
        int maior = 0;
        Registro reg = new Registro();
        seekArq(0);
        while(!eof()){
            reg.leDoArq(arquivo);
            if(reg.getCodigo() > maior)
                maior = reg.getCodigo();
        }
        return maior;
    }
    
    public int getMenor(){
        int menor = 9999;
        Registro reg = new Registro();
        seekArq(0);
        while(!eof()){
            reg.leDoArq(arquivo);
            if(reg.getCodigo() < menor)
                menor = reg.getCodigo();
        }
        return menor;
    }

    @Override
    public void combSort() {
        int TL = filesize(), i=0, gap = TL;
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        
        seekArq(0);
        while (gap >= 1) {
            while(i < TL) {
                if (i + gap < TL) {
                    seekArq(i);
                    reg.leDoArq(arquivo);
                    seekArq(i + gap);
                    reg2.leDoArq(arquivo);
                    if (reg.getCodigo() > reg2.getCodigo()) {
                        seekArq(i);
                        reg2.gravaNoArq(arquivo);
                        seekArq(i + gap);
                        reg.gravaNoArq(arquivo);
                    }
                }
                i++;
            }
            gap = (int) (gap / 1.3);
        }
    }

    @Override
    public void countingSort() {
    }

    @Override
    public void bucketSort() {
    }

    @Override
    public void radixSort() {
    }
    
    @Override
    public void gnomeSort(){
        
    }
    
    public void timSort(){
        
    }


}
