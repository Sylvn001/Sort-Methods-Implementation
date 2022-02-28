/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpo1bjr;

/**
 *
 * @author Aluno
 */
public class No {
    private No prox;
    private No ant;
    private int info;

    public No(No prox, No ant, int info) {
        this.prox = prox;
        this.ant = ant;
        this.info = info;
    }
    
    

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
    
    
}
