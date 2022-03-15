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

}
