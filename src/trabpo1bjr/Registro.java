package trabpo1bjr;

import java.io.IOException;
import java.io.RandomAccessFile;

class Registro {

    private int codigo;

    public Registro() {
    }

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return (codigo);
    }

    public void gravaNoArq(RandomAccessFile arquivo) {
        try {
            arquivo.writeInt(codigo);
        } catch (IOException e) {
        }
    }

    public void leDoArq(RandomAccessFile arquivo) {
        try {
            this.codigo = arquivo.readInt();
        } catch (IOException e) {
        }
    }

    public void exibirReg() {
        System.out.print(" - Valor: " + this.codigo + "\n");
    }

    static int length() {
        return (4);         
    }
}