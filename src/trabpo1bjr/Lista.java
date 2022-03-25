package trabpo1bjr;

public class Lista implements Methods{
    private No inicio;
    private No fim;

    public void inicializa(){
        inicio = fim = null;
    }

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

    public void exibir(){
        No aux = this.inicio;
        while(aux != null){
            System.out.printf("| %d |->", aux.getInfo());
            aux = aux.getProx();
        }
        System.out.println("");
    }

    public No busca_exaustiva(int info){
        No aux = this.inicio;
        while(aux != null && info != aux.getInfo())
            aux = aux.getProx();
        return aux;
    }

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
    public void binaryInsertionSort() {
        int aux;
        No pos, j,i = this.inicio;
        int tamOrd = 0;

        while(i != null){
            aux = i.getInfo();
            pos = binarySearch(aux,tamOrd, i);

            j = i;
            while(j != pos){
                j.setInfo(j.getAnt().getInfo());
                j = j.getAnt();
            }
            pos.setInfo(aux);

            tamOrd++;
            i = i.getProx();
        }
    }

    @Override
    public void selectionSort() {
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

    @Override
    public void bubbleSort() {
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
    public void shakeSort() {
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

    public int length(){
        int size=0;
        No aux = this.inicio;

        while(aux != null){
            aux = aux.getProx();
            size++;
        }
        return size;
    }

    public No getMeio(No pinicio, int qtdAndar){
        No aux = pinicio;
        int i=0;
        while(aux != null && i < qtdAndar){
            aux = aux.getProx();
            i++;
        }
        return aux;
    }

    public No getNoByIndex(int qtdAndar){
        No aux = inicio;
        int i=0;

        while(aux != null && i != qtdAndar){
            i++;
            aux = aux.getProx();
        }
        return aux;
    }

    public No binarySearch(int chave, int TL, No ptl) {
        int inicio = 0, fim = TL, meio  = fim/2;
        No pini = this.inicio, pfim = ptl;
        No pmeio = getMeio(pini, meio);

        while (inicio < fim && chave != pmeio.getInfo()){
            if(chave < pmeio.getInfo()){
                fim = meio-1;
                pfim = pmeio.getAnt();
            }else{
                inicio = meio+1;
                pini = pmeio.getProx();
            }
            meio = (inicio+fim) / 2;
            pmeio = getMeio(pini, (meio - inicio));
        }

        if(chave > pmeio.getInfo())
            return pmeio.getProx();
        else
            return pmeio;
    }

    @Override
    public void heapSort() {
        int pai, FE, FD, TL2 = length(), aux;
        No pPai, pFE, pFD, maiorF, noTL2 = fim;

        while(noTL2.getAnt() != null){
            pai = TL2/2-1;
            while(pai >= 0 ){
                pPai = getNoByIndex(pai);

                FE = pai*2+1;
                FD = FE+1;

                pFE = getNoByIndex(FE);
                pFD = pFE.getProx();

                maiorF = pFE;

                if(FD < TL2 && pFD.getInfo() > maiorF.getInfo() )
                    maiorF = pFD;

                if(maiorF.getInfo() > pPai.getInfo()){
                    aux = pPai.getInfo();
                    pPai.setInfo(maiorF.getInfo());
                    maiorF.setInfo(aux);
                }
                pai--;
            }

            aux = noTL2.getInfo();
            noTL2.setInfo(this.inicio.getInfo());
            inicio.setInfo(aux);

            noTL2 = noTL2.getAnt();
            TL2--;
        }
    }

    @Override
    public void shellSort(){
        int dist = 4, i, j, k, aux, TL = length();
        No pI, pJ, pK, pDist;

        while(dist > 0)
        {
            pI = this.inicio;
            i =0;
            while(i < dist)
            {
                pJ = pI;
                j = i;
                while(j+dist < TL)//Permutacao entre as proximas distancias
                {
                    pDist = getNoByIndexWithInitialValue(pJ, dist, 1);
                    if(pJ.getInfo() > pDist.getInfo())
                    {
                        aux = pJ.getInfo();
                        pJ.setInfo(pDist.getInfo());
                        pDist.setInfo(aux);

                        k = j;
                        pK = pJ;
                        pDist = getNoByIndexWithInitialValue(pK , dist, 0);
                        while((k - dist) >= 0 && pK.getInfo() < pDist.getInfo()) //Permutacao do anterior
                        {
                            aux = pK.getInfo();
                            pK.setInfo(pDist.getInfo());
                            pDist.setInfo(aux);

                            k = k-dist;
                            pK = getNoByIndexWithInitialValue(pK , dist, 0);
                            pDist = getNoByIndexWithInitialValue(pK , dist, 0);
                        }
                    }
                    pJ = getNoByIndexWithInitialValue(pJ, dist, 1);
                    j = j+dist;
                }
                i++;
            }
            dist = dist/2;
        }
    }

    public No getNoByIndexWithInitialValue(No noInicio, int qtdAndar, int flag){
        int i=0;
        No aux = noInicio;

        if(flag == 1){
            while(aux != null && i < qtdAndar){
                i++;
                aux = aux.getProx();
            }
        }else{
            while(aux != null && qtdAndar != 0){
                aux = aux.getAnt();
                qtdAndar--;
            }
        }
        return aux;
    }
    
    public void quickSP(No posInicio, No posFim){
        int aux;
        No i = posInicio, j = posFim;
        while(i != j){
            while(i != j && i.getInfo() <= j.getInfo())
                i = i.getProx();
            
            aux = i.getInfo();
            i.setInfo(j.getInfo());
            j.setInfo(aux);                
            
            while(i != j && j.getInfo() >= i.getInfo()){
                j = j.getAnt();
            }
                
            aux = i.getInfo();
            i.setInfo(j.getInfo());
            j.setInfo(aux);
        }
        if(posInicio != i)
            quickSP(posInicio, i.getAnt());
        if(j != posFim)
           quickSP(j.getProx(), posFim);
    }

    @Override
    public void quickSort() {
        No inicio = this.inicio, fim = this.fim;
        quickSP(inicio, fim);
    }
    
    public void quickWP(No posInicio, No posFim){
        int aux, meio; 
        No i = posInicio, j = posFim, pivot; 
        meio = (0 + length()) / 2 ;
        System.out.println(meio);
        pivot = getMeio(posInicio, meio);
        
        while(i != j){
            while(i.getInfo() < pivot.getInfo())
                i = i.getProx();
            while(j.getInfo() > pivot.getInfo())
                j = j.getAnt();
            
            if(i != j){
                aux = i.getInfo();
                i.setInfo(j.getInfo());
                j.setInfo(aux);
                i = i.getProx();
                j = j.getAnt();
            }
        }
        if(posInicio != i)
            quickWP(posInicio, i.getAnt());
        if(j != posFim)
           quickWP(j.getProx(), posFim);
    }
    
    @Override
    public void quickSortPivot() {
        No inicio = this.inicio, fim = this.fim;
        quickWP(inicio, fim);
    }
    
    public void particao(){
        
    }
    
    public void fusao(){
        
    }

    @Override
    public void mergeSort() {
        Lista lista1, lista2;
        int seq = 1, TL;
//        while(seq < );
    }
    
    public void countingSort(){
        int maior = 0;
        No i = inicio;
        //Descobrir o maior valor
        while(i != null){
            if(i.getInfo() > maior)
                maior = i.getInfo();
        }
        //Criar um array com o maior valor
//        int countArray[] = new int[maior];

        //descobrir a frequencia
    }
    
  
    public void fusao(int ini1, int fim1, int ini2, int fim2, Lista aux){
        int k, i = ini1, j = ini2;

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
    
    public void merge2(int esq, int dir, Lista aux){
        if(esq < dir){
           int meio = (esq + dir) / 2;
           merge2(esq, meio, aux);
           merge2(meio+1, dir, aux);
           fusao(esq, meio, meio+1, dir, aux);
        }
    }

    @Override
    public void mergeSort2Way() {
        Lista aux = new Lista();
        merge2(0, length()-1, aux);
    }
    
}
