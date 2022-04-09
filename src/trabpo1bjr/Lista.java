package trabpo1bjr;

public class Lista implements Methods{
    private No inicio;
    private No fim;

    public No getInicio() {
        return inicio;
    }

    public void setInicio(No inicio) {
        this.inicio = inicio;
    }

    public No getFim() {
        return fim;
    }

    public void setFim(No fim) {
        this.fim = fim;
    }
    
    

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
    
    public int sizeWithStartAndEnd(No inicio, No fim){
        int i=0;
        No aux = inicio;
        
        while(aux != fim){
            i++;
            aux = aux.getProx();
        }
        return i;
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
    
    public int lenList(No ini, No fim){
        No aux = ini;
        int i = 0;
        while(aux != fim){
            i++;
            aux = aux.getProx();
        }
        return i;
    }
        
    public void quickWP(No ini, No fim){
        No pi = ini, pj = fim;
        int meio;      
        int qtd = lenList(ini, fim), aux, pivo = 0;
         
        meio = qtd/2;     
        pivo = getMeio(ini, meio).getInfo();       

        while(pi != pj)
        {
            while(pi.getInfo() < pivo)
                pi = pi.getProx();
            while(pj.getInfo() > pivo)
                pj = pj.getAnt();
            
            aux = pi.getInfo();
            
            pi.setInfo(pj.getInfo());
            pj.setInfo(aux);
        }
        if(pj.getAnt() != null && pj != ini && ini != pj.getAnt())
            quickWP(ini, pj.getAnt());
        if(pi.getProx() != null && pi != fim &&  fim != pi.getProx())
            quickWP(pi.getProx(), fim);
    }
    
    @Override
    public void quickSortPivot() {
        quickWP(inicio, fim);
    }
    
    public void particao(Lista l1, Lista l2, int size){
        int tam = size / 2, i = 0;
        l1.inicializa();
        l2.inicializa();
        No aux = this.inicio;
        while(i < tam){
            l1.inserirNoFinal(aux.getInfo());
            aux = aux.getProx();
            i++;
        }
        while(aux != null){
            l2.inserirNoFinal(aux.getInfo());
            aux = aux.getProx();
        }
    }
    
    public void fusao(Lista l1, Lista l2, int seq){
        int i=0, j=0, k=0, tseq = seq, TL = length(); 
        No noK = this.inicio, no1 = l1.getInicio(), no2 = l2.getInicio();
        while(k < TL){
            while(i < seq && j < seq){
                if(no1.getInfo() < no2.getInfo()){
                    noK.setInfo(no1.getInfo());
                    noK = noK.getProx();
                    no1 = no1.getProx();
                    i++;
                    k++;
                }
                else{
                    noK.setInfo(no2.getInfo());
                    noK = noK.getProx();
                    no2 = no2.getProx() ;
                    j++;
                    k++;
                }
            }

            while(i < seq){
                noK.setInfo(no1.getInfo());
                noK = noK.getProx();
                no1 = no1.getProx();
                i++;
                k++;
            }

            while(j < seq){
                noK.setInfo(no2.getInfo());
                noK = noK.getProx();
                no2 = no2.getProx() ;
                j++;
                k++;
            }
            seq = seq + tseq;
        }
    }

    @Override
    public void mergeSort() {
        Lista lista1 = new Lista();
        Lista lista2 = new Lista();
        int seq=1, TL = length();

        while(seq < TL){
            particao(lista1, lista2, TL);
            fusao(lista1,lista2,seq);
            seq = seq*2;
        }
    }   
  
    public void fusao2(int ini1, int fim1, int ini2, int fim2, int aux[]){
        int k=0, i = ini1, j = ini2;
        No noI = getNoByIndex(ini1), noJ = getNoByIndex(ini2), noK = this.inicio; 

        while(i <= fim1 && j <= fim2){
            if(noI.getInfo() < noJ.getInfo()){
                aux[k] = noI.getInfo();
                noI = noI.getProx();
                i++;
                k++;
            }
            else{
                aux[k] = noJ.getInfo();
                noJ = noJ.getProx();
                k++;
                j++;
            }
        }

        while(i <= fim1){
           aux[k] = noI.getInfo();
           noI = noI.getProx();
           i++;
           k++;
        }

        while(j <= fim2){
            aux[k] = noJ.getInfo();
            noJ = noJ.getProx();
            k++;
            j++;
        }
        
        i=0;
        noK = getNoByIndex(ini1+i);
        while(i < k){
            noK.setInfo(aux[i]);
            i++;
            noK = noK.getProx();
        }
    }
    
    public void merge2(int esq, int dir, int aux[]){
        if(esq < dir){
           int meio = (esq + dir) / 2;
           merge2(esq, meio, aux);
           merge2(meio+1, dir, aux);
           fusao2(esq, meio, meio+1, dir, aux);
        }
    }

    @Override
    public void mergeSort2Way() {
        int TL = length();
        int vetAux[] = new int[TL];
        merge2(0, TL-1, vetAux);
    }
    
    public int max(){
        No aux = this.inicio;
        int maior = 0;
        while(aux != null ){
            if(aux.getInfo() > maior)
                maior = aux.getInfo();
            aux = aux.getProx();
        }
        return maior;
    }
    
    public int min(){
        No aux = this.inicio;
        int menor = 9999;
        while(aux != null ){
            if(aux.getInfo() < menor)
                menor = aux.getInfo();
            aux = aux.getProx();
        }
        return menor;
    }
    
    
    @Override
    public void countingSort(){
       int max = max(); //encontra o valor maximo
        int min = min();
        int size = length();
        int range = max - min + 1;
        int count[] = new int[range];
        
        /*
            Steps
            1 - determine number ranger
            2 - create empty array on size range
            3 - fill index array with the number of occurence 
            4 - sum up index array values with procedure values 
            5 - create empty array to store sorted values
            6 - map the input index-output-arrays
            wiki: https://www.youtube.com/watch?v=0B33As8jPgo
        */
        
        No aux;
        Lista output = new Lista();

        for (int i = 0; i < size; i++)//inica lista         
            output.inserirNoFinal(0);           
        aux = inicio;
        for (int i = 0; i < size; i++, aux = aux.getProx()) //soma a quantidade de ocurrencias de cada index
            count[aux.getInfo() - min]++;             
        for (int i = 1; i < count.length; i++) //soma com o valor anterior 
            count[i] += count[i - 1];                      
        aux = fim;
        for (int i = size - 1; i >= 0; i--) {
            output.getNoByIndex(count[aux.getInfo() - min] - 1).setInfo(aux.getInfo()); //
            count[aux.getInfo() - min]--;
            aux = aux.getAnt();
        }
        aux = inicio;
        No auxSaida = output.inicio;
        while (aux != null && auxSaida != null) {
            aux.setInfo(auxSaida.getInfo());
            aux = aux.getProx();
            auxSaida = auxSaida.getProx();
        }
  
    }
    
    public void bucketSort() {
        /*
            WIKI: https://pt.wikipedia.org/wiki/Bucket_sort#Exemplo_em_Java_com_LinkedList
           ~ ~ https://www.youtube.com/watch?v=VuXbEb5ywrU
            
        */
        int num_bucket = max() / 3, pos, i, j;
        No noAux;
        
        Lista bucket[] = new Lista[num_bucket];
        for (i = 0; i < num_bucket; i++) 
            bucket[i] = new Lista();
                
        noAux = inicio;
        while (noAux != null) {
            pos = (noAux.getInfo() - 1) / (num_bucket);
            bucket[pos].inserirNoInicio(noAux.getInfo());
            noAux = noAux.getProx();
        }
        
        this.inicializa();
        No auxn;
        
        j=0;
        while (j < num_bucket) {
            auxn = bucket[j].inicio;
            while (auxn != null) {
                this.inserirNoFinal(auxn.getInfo());
                auxn = auxn.getProx();
            }
            j++;
        }
    }


    @Override
    public void radixSort() { 

    }

    public void combSort() {
        /*
            Wiki: https://pt.wikipedia.org/wiki/Comb_sort
        */
        int TL = length(), i=0, aux;
        int gap = (int) (TL);
        No noI, noJ;
        
        while (gap > 0 && i != TL-1) {
            noI = inicio;
            while((i+gap) < TL) {
                noJ = getNoByIndex(i+gap);
                
                if (noI.getInfo() > noJ.getInfo()) {
                    aux = noI.getInfo();
                    noI.setInfo(noJ.getInfo());
                    noJ.setInfo(aux);
                }
                i++;
                noI = noI.getProx();
            }
            gap = (int) (gap / 1.3);
        }
    }

    public void gnomeSort() {
        /*
        Wiki: https://en.wikipedia.org/wiki/Gnome_sort
        */
        int size = length();
        int temp;
        No aux;
        No aux2;
        int i = 0;

        while (i < size-1) {
            aux = this.inicio;
            aux2 = aux.getAnt();
            if (aux.getInfo() >= aux2.getInfo()) 
                i++;
            else {
                temp = aux.getInfo();
                aux.setInfo(aux2.getInfo());
                aux2.setInfo(temp);
                i--;
            }
        }
    }

    public void timSort() {
        int n = length();
        int RUN = 32;
        
        /*
            TimSort use insertion sort and merge sot. InsertionSort is used to sort a partition of structure, and merge join the partitions, 
            Wiki: https://deinfo.uepg.br/~alunoso/2019/AEP/TIMSORT/REA-TimSort.htm
        */

        for (int i = 0; i < n; i += RUN) 
            timInsertionSort(i, (i + Math.min(i+RUN, n-1)));
        
   
        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                mergeTimSort(left, mid, right);
            }
        }

    }

    private void timInsertionSort(int left, int right) {
        int i = left + 1, aux, j;
        No noAux = getNoByIndex(i), noAnt; 
        
        while (i <= right ){
            aux = noAux.getInfo();
            j = i - 1;
            noAnt = noAux.getAnt();
            while (noAnt != null && noAnt.getInfo() > noAux.getInfo() && j >= left) {
                noAux.setInfo(noAnt.getInfo());
                j--;
                noAnt = noAnt.getAnt();
            }
            noAux.setInfo(aux);
            i++;
        }
    }
  
    private void mergeTimSort(int l, int m, int r) {
        int len1 = m - l + 1, len2 = r - m, x=0, i=0, j=0, k=l;
        int[] left = new int[len1];
        int[] right = new int[len2];
        No aux = getNoByIndex(l+x);
        
        while(x < len1) {
            left[x] = aux.getInfo();
            aux = aux.getProx();
            x++;
        }
        
        x=0;        
        aux = getNoByIndex(m + l + x);
        while(x < len2) {
            right[x++] = aux.getInfo();
            aux = aux.getProx();
        }
        
        aux = getNoByIndex(k);
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) 
                aux.setInfo(left[i++]);
            else 
                aux.setInfo(right[j++]);
            k++;
        }

        while (i < len1) {
            aux.setInfo(left[i++]);
            k++;
        }

        while (j < len2) {
            aux.setInfo(right[j++]);
            k++;
        }
    }
    
}
