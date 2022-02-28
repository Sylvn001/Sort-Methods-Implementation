package trabpo1bjr;

public class Lista implements Methods{
    private No inicio;
    private No fim;
    
    @Override
    public void inicializa(){
        inicio = fim = null;
    }
    
    @Override
    public void inserirNoInicio(int info){
        No no = new No(null, null, info);
        if(this.inicio != null){
            no.setProx(this.inicio);
            this.inicio.setAnt(no);
            this.inicio = no;
        }else{
            this.inicio = this.fim = no;
        }
    }
    
    @Override
    public void inserirNoFinal(int info){
        No no = new No(null, null, info);
        if(this.fim != null){
            this.fim.setProx(no);
            no.setAnt(this.fim);
            this.fim = no;
        }else{
            this.fim = this.inicio = no;
        }
    }
    
    @Override
    public void exibir(){
        No aux = this.inicio;
        while(aux != null){
            System.out.printf("| %d |->", aux.getInfo());
            aux = aux.getProx();
        }
        System.out.println("");
    }
    
    @Override
    public No busca_exaustiva(int info){
        No aux = this.inicio;
        while(aux != null && info != aux.getInfo())
            aux = aux.getProx();
        return aux;
    }
    
    @Override
    public void remover(int info){
        No pos = busca_exaustiva(info);
        
        if(pos != null){
            if(pos == inicio && pos == fim)
                inicializa();
            else{                
                if(pos == this.inicio){
                    this.inicio = this.inicio.getProx();
                    this.inicio.setAnt(null);
                }else{
                    if(this.fim == pos){
                        this.fim = this.fim.getAnt();
                        this.fim.setProx(null);
                    }else{
                        pos.getAnt().setProx(pos.getProx());
                        pos.getProx().setAnt(pos.getAnt());
                    }
                }
            }
        }else{
            System.out.println("Nenhum encontrado!");
        }
    }

    @Override
    public void insertionSort() {
        int aux;
        No pos, i = this.inicio;
        
        while(i != null){
            pos = i;
            aux = pos.getInfo();
            
            while(pos.getAnt() != null && aux < pos.getAnt().getInfo()){
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            
            i = i.getProx();
        }
    }

    @Override
    public void BubbleSort() {
        No pi, TL2 = fim;
        int aux;

        while(TL2.getAnt() != null)
        {
            pi = inicio;

            while(pi.getProx() != null)
            {
                if(pi.getInfo() > pi.getProx().getInfo()){
                    aux = pi.getInfo();
                    pi.setInfo(pi.getProx().getInfo());
                    pi.getProx().setInfo(aux);
                }
                pi = pi.getProx();
            }
            TL2 = TL2.getAnt();
        }
    }

    @Override
    public void ShakeSort() {
        No pInicio = this.inicio, pIndex, pFim = this.fim;
        int aux;
        
        while(pInicio != pFim)
        {
            pIndex = pInicio;
            while(pIndex != pFim){
                if(pIndex.getInfo() > pIndex.getProx().getInfo()){
                    aux = pIndex.getInfo();
                    pIndex.setInfo(pIndex.getProx().getInfo());
                    pIndex.getProx().setInfo(aux);
                }
                pIndex = pIndex.getProx();
            }

            pFim = pFim.getAnt();
            pIndex = pFim;
            while(pIndex != pInicio){
                if(pIndex.getInfo() < pIndex.getAnt().getInfo()){
                    aux = pIndex.getInfo();
                    pIndex.setInfo(pIndex.getAnt().getInfo());
                    pIndex.getAnt().setInfo(aux);
                }
                pIndex = pIndex.getAnt();
            }
            pInicio = pInicio.getProx();
        }

    }

    @Override
    public void SelectionSort() {
	int menor;
	No posMenor, posI = inicio, posJ;

        while(posI.getProx() != null){
            posMenor = posI;
            menor = posI.getInfo();

            posJ = posI.getProx();
            while(posJ != null){
                if(posJ.getInfo() < menor) 
                {
                    menor = posJ.getInfo();
                    posMenor = posJ;
                }
                posJ = posJ.getProx();
            }

            posMenor.setInfo(posI.getInfo());
            posI.setInfo(menor);

            posI = posI.getProx();
        }
    }
}
