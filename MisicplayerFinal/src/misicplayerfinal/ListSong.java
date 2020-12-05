/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misicplayerfinal;

import java.io.FileInputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ListSong {

    NodoSong latest, start;

    public ListSong() {
        this.latest = null;
        this.start = null;
    }

    //Metodo para saber cuando la lista esta vasia
    public boolean thisEmpty() {
        return start == null;

    }

    /**
     * Metodo encargado de inserta nodos en la lista
     *
     * @param song
     */
    public void insert(NodoSong song) {
        if (start == null) {
            start = song;
            latest = song;
            latest.setSig(start);
        } else {
            song.setSig(start);
            start = song;
            latest.setSig(start);
        }
    }

    /**
     * Metodo para imprimir la listas
     */
    public void showList() {
        String conc = "";
        if (!thisEmpty()) {
            NodoSong aux = start;
            do {
                conc += aux.show() + "\n";
                aux = aux.getSig();
            } while (aux != start);
            JOptionPane.showMessageDialog(null, conc);
        } else {
            JOptionPane.showMessageDialog(null, "La lista no tiene elementos");
        }
    }

    public String showListSave() {
        String conc = "";
        if (!thisEmpty()) {
            NodoSong aux = start;
            do {
                conc += aux.getId()+"/"+aux.getName()+"/"+ aux.path +"/"+aux.sig+ "\n";
                aux = aux.getSig();
            } while (aux != start);
            return conc;
        } else {
            JOptionPane.showMessageDialog(null, "La lista no tiene elementos");
        }
        return conc;
    }

    /**
     * Busca si axiste la cancio
     *
     * @param referencia
     * @return
     */
    public boolean search(int referencia) {
        NodoSong aux = start;
        boolean found = false;
        do {
            if (referencia == aux.getId()) {
                found = true;
            } else {
                aux = aux.getSig();
            }
        } while (aux != start && found != true);
        return found;
    }

    /**
     * Metodo que se encarga de search la cancion y devolver sus datos para
     * poder editarlos
     *
     * @param referencia
     * @return
     */
    public NodoSong buscarSong(int referencia) {
        NodoSong aux = start;
        boolean found = false;
        do {
            if (referencia == aux.getId()) {
                found = true;
            } else {
                aux = aux.getSig();
            }
        } while (aux != start && found != true);
        return aux;
    }

    /**
     * Metodo para delete las canciones de la lista
     * @param elemento
     * @return 
     */
    public boolean delete(int elemento) {
        NodoSong current;
        boolean encontrado = false;
        current = latest;
        while (current.sig != latest && !encontrado) {
            encontrado = (current.sig.getId() == elemento);
            if (!encontrado) {
                current = current.sig;
            }
        }
        encontrado = (current.sig.getId() == elemento);
        if (encontrado) {
            NodoSong aux = current.sig;
            if (latest == latest.sig) {
                latest = null;
            } else {
                if (aux == latest) {
                    latest = current;
                }
                current.sig = aux.sig;
            }
            aux = null;
        }
        return encontrado == true;
    }

    /**
     * Metodo que se encarga de contar la cantidad de nodos que hay en la lista
     *
     * @return
     */
    public int size() {
        NodoSong current;
        if (!thisEmpty()) {
            current = start.sig;
            if (current == start.sig) {
                return 1;
            } else {
                int count = 1;
                while (current != start.sig) {
                    count++;
                    current = current.sig;
                }
                return count;
            }
        }
        return 0;
    }

    public void orderAlphabetically() {
        NodoSong aux = start.sig;
        do{
           
        }while(aux != start);
    }

}
