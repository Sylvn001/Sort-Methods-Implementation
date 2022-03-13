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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void binaryInsertionSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public int binarySearch(int chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public void heapSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shellSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
