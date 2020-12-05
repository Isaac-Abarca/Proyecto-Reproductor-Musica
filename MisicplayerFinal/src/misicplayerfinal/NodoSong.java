/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misicplayerfinal;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import static misicplayerfinal.MisicplayerFinal.songList;

/**
 *
 * @author Usuario
 */
public class NodoSong {
    private String name;
    FileInputStream path;
    private int id;
    
    NodoSong sig;
    

    public NodoSong(String name, FileInputStream path, NodoSong sig, int id) {
        this.name = name;
        this.path = path;
        this.sig = null;
        this.id= id;
        
    }

    public NodoSong() {
        this.sig = this;
    }
    
    /**
     * Sirve para mostrar las canciones y el codigo de b
     * @return 
     */
    public String show() {
        String everything = "";
        everything = "Codigo: "+this.id+" Cancion: "+ this.getName();
        return everything;
    }
    
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    

    /**
     * @return the sig
     */
    public NodoSong getSig() {
        return sig;
    }

    /**
     * @param sig the sig to set
     */
    public void setSig(NodoSong sig) {
        this.sig = sig;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

   
    
    
}
