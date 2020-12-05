/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misicplayerfinal;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Esta aplicacion costa de un pequeño reproductor con la api de javazoom. Las
 * funciones que tiene constan de el poder cargar archivos .mp3 los cuales se
 * guardan en una lista circular. Puede reproducir, detener, reanudar y parar
 * del todo la reproducciondel audio
 *
 * @author Isaac Abarca
 */
public class MisicplayerFinal {

    static ListSong songList = new ListSong();
    static int cant = 0, d = 0;
    static Player player = null;
    static Thread run, threadMusic;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Bienvenido espero disfutes tus canciones");
        try {
            mainMenu();
        } catch (JavaLayerException ex) {
            JOptionPane.showMessageDialog(null, "Solo se permiten numeros");
            try {
                mainMenu();
            } catch (JavaLayerException ex1) {
                JOptionPane.showMessageDialog(null, "Lo sentimos ubieron problemas al abrir el archivo .mp3\n");
            }
        }
    }

    /**
     * Este es el metodo que controla el menu principal donde se encuentran las
     * obciones principales del sistema.
     *
     * @throws JavaLayerException
     */
    private static void mainMenu() throws JavaLayerException {
        int opc = 0;
        opc = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Menu principal\n"
                + "1. Cargar archivos .mp3\n"
                + "2. Reproducirlos\n"
                + "3. Editar\n"
                + "4. Guardar lista\n"
                + "5. Ayuda\n"
                + "6. Salir\n"
                + "Que deseas hacer???", JOptionPane.INFORMATION_MESSAGE));

        switch (opc) {
            case 1: {
                try {
                    cant = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Cuantas canciones deseas agregar a la lista"));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Solo se permiten numeros");
                    mainMenu();
                }
                for (int i = 0; i < cant; i++) {
                    NodoSong newSong = insertNewSong();
                    songList.insert(newSong);
                    songList.showList();
                }

                mainMenu();
                break;
            }
            case 2: {
                if (!songList.thisEmpty()) {
                    searchSongPlay();
                }else{
                    JOptionPane.showMessageDialog(null, "No hay canciones cargadas\n"
                            + "\n"
                            + "Favor carge alguna para poder reproducirlas", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    mainMenu();
                }
                break;
            }
            case 3: {
                if (!songList.thisEmpty()) {
                    editFile();
                }else{
                    JOptionPane.showMessageDialog(null,"Lo sentimos aun no has registrado ninguna cancion");
                    mainMenu();
                }
                
                break;
            }
            case 4: {
                if (!songList.thisEmpty()) {
                    saveList();
                } else {
                    JOptionPane.showMessageDialog(null, "La lista no tiene canciones");
                    mainMenu();
                }
                break;
            }
            case 5: {
                help();
                break;
            }
            case 6: {
                JOptionPane.showMessageDialog(null,"Que tengas lindo dia", "Hasta prono", JOptionPane.INFORMATION_MESSAGE);
                System.exit(opc = 6);
                break;
            }
            default: {
                JOptionPane.showMessageDialog(null, "Opcion invalida, favor dijete otra");
                mainMenu();
                break;
            }
        }

    }

    /**
     * Este es el metodo utilizado para la insercion de un nuevo nodo en la
     * lista
     *
     * @return
     */
    private static NodoSong insertNewSong() {
        String fileName = "";
        int id;
        NodoSong song = new NodoSong();
        FileFilter filter = new FileNameExtensionFilter("MP3 file", "mp3");
        JFileChooser file = new JFileChooser();
        file.addChoosableFileFilter(filter);
        file.showOpenDialog(null);
        File open = file.getSelectedFile();

        try {
            FileReader files = new FileReader(open);
            BufferedReader read = new BufferedReader(files);
            fileName = read.readLine();
            song.path = new FileInputStream(open.getAbsolutePath());
            song.setName(open.getName());
            id = Integer.parseInt(JOptionPane.showInputDialog(null, "Favor registre un identificador a la cancion"));
            song.setId(id);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No seleccioo ningun archivo");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lo sentimos\n"
                    + "Ocurrio un error");
        }
        return song;

    }

    /**
     * Este emtodo consite en un mini menu donde se muestran las opciones
     * disponibles para editar los archivos que ya tenemos ingresados
     * previamente
     */
    private static void editFile() {
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Que deseas editar\n"
                + "1. Editar el nombre de la cancion\n"
                + "2. Eliminar cancion\n"
                + "3. Ayuda\n"
                + "4. Atras\n"
                + "5. Salir\n"
                + "Quedeas realizar?"));
        switch (opc) {
            case 1: {
                if (!songList.thisEmpty()) {
                    editNameSong();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay canciones registadas en la lista\n");
                    editFile();
                }

                break;
            }
            case 2: {
                if (!songList.thisEmpty()) {
                    deleteSong();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay canciones registadas en la lista\n");
                    editFile();
                }
                break;
            }
            case 3: {
                help();
                break;
            }
            case 4: {
                try {
                    mainMenu();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MisicplayerFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case 5:{
                System.exit(opc=5);
                break;
            }
            default:{
                JOptionPane.showMessageDialog(null, "Lo sentimos ocion invalida. \n"
                        + "\n"
                        + "\n"
                        + "Vuelve a inentar por favor");
                editFile();
                break;
            }

        }
    }

    /**
     * El metodo help es el encargado de la ayuda con rspecto a las funcines del
     * sistema y en como se utilizan
     */
    private static void help() {
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Para que apartado nesesitas ayuda?\n"
                + "1. Cargar audio .mp3\n"
                + "2. Reproducirlo\n"
                + "3. Editar\n"
                + "4. Guardar\n"
                + "5. Atras\n"
                + "6. Salir"));

        switch (opc) {
            case 1: {
                JOptionPane.showMessageDialog(null, "La opcion de cargar archivos consiste\n"
                        + "agregar las canciones que estan en su conputadora al\n"
                        + "al programa para reproducirlas");
                JOptionPane.showMessageDialog(null, "El procedimiento es: \n"
                        + "1. Se le abrira una ventana en la cual aparecen los documentos de tu computadora\n"
                        + "2. Busca la carpeta donde tengas la cancion que quieras cargar\n"
                        + "3. Dale clic y  agregala\n"
                        + "4. Registra un codigo para luego  poder buscarla con facilidad\n"
                        + "Esto seria todo.\n"
                        + "Que disfrutes");
                help();
                break;
            }
            case 2: {
                JOptionPane.showMessageDialog(null, "El procedimiento para reproducir es: \n"
                        + "1. Dijitar el codigo de la cancion que deseas escuhar\n"
                        + "2. Escuchar tu cancion\n"
                        + "3. Te saldran las opciones de detener y parar la cancion\n"
                        + "para cuando las nesecites");
                help();
                break;
            }
            case 3: {
                JOptionPane.showMessageDialog(null, "Para el apartado de editar\n"
                        + "lo que se ofrece es el poder cambiar de nombre las canciones\n"
                        + "y el poder eliminarlas de la lista");
                help();
                break;
            }
            case 4: {
                JOptionPane.showMessageDialog(null, "La funcion guardar lo que hace es\n"
                        + "guardar la lista de reproduccion que subiste para que despues \n"
                        + "la puedas volver a escuchar");
                help();
                break;
            }
            case 5: {
                try {
                    mainMenu();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MisicplayerFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case 6: {
                System.exit(opc = 6);
            }
            default: {
                JOptionPane.showMessageDialog(null, "Lo sentiomos opcion invalida\n"
                        + "fabor dijete una de las que se muestran\n"
                        + "Gracias!!!");
                help();
            }
        }
    }

    /**
     * Este metodo es el que se encarga de guardar tu lista para que despues la
     * puedas escuchar
     *
     */
    private static void saveList() {
        try {
            String newListSong = songList.showListSave();
            JFileChooser file = new JFileChooser();
            file.showSaveDialog(null);
            File guarda = file.getSelectedFile();
            String n = "";
            if (guarda != null) {
                FileWriter save = new FileWriter(guarda + ".txt");
                save.write(newListSong);
                save.close();
                JOptionPane.showMessageDialog(null,
                        "El archivo se a guardado Exitosamente",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                mainMenu();
            } catch (JavaLayerException ex) {
                Logger.getLogger(MisicplayerFinal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Su archivo no se ha guardado",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Este medoto funciona con el mini menu de editar y es el encargado de
     * cambiar los nombres de las canciones
     */
    private static void editNameSong() {

        songList.showList();
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Cual cancion deseas editar"));
        NodoSong aux = songList.buscarSong(opc);
        String newName = JOptionPane.showInputDialog(null, "Cual es el nombre nuevo que le quieres dar a la cancion " + aux.getName());
        aux.setName(newName);
        editFile();
//            int op = Integer.parseInt(JOptionPane.showInputDialog(null, "Deseas editar otra cancion?"));

    }

    /**
     * Este metodo funciona con el de mini menu y se encarga de borrar canciones
     * de la lista
     *
     */
    private static void deleteSong() {
        songList.showList();
        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, "Dijite el codigo de la cancion que deas eliminar"));
        if (songList.delete(opc)) {
            JOptionPane.showMessageDialog(null, "La cancion se a eliminado corecctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos la cancion\n"
                    + "no se a eliminado");

        }
        editFile();
    }

    /**
     * Mini menu encargado de las opciones de de reproduccion de la musica.
     *
     * @param d
     */
    private static void initPlayerSong(int d) {

        int opc = Integer.parseInt(JOptionPane.showInputDialog(null, ""
                + "1.Reproducir\n"
                + "2. Pausar\n"
                + "3. Reanudar\n"
                + "4. stop\n"
                + "5. Atras\n"
                + "6. Salir\n"
                + "Que deas relizar?", "Opciones del reproductor", JOptionPane.INFORMATION_MESSAGE));

        switch (opc) {
            case 1: {
                playMusic();
                initPlayerSong(d);
                break;
            }
            case 2: {
                pauseMusic();
                initPlayerSong(d);
                break;
            }
            case 3: {
                resumMusic();
                initPlayerSong(d);
                break;
            }
            case 4: {
                stopMusic();
                initPlayerSong(d);
                break;
            }
            case 5: {
                try {
                    stopMusic();
                    mainMenu();                   
                    
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MisicplayerFinal.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case 6: {
                JOptionPane.showMessageDialog(null,"Que tengas lindo dia", "Hasta prono", JOptionPane.INFORMATION_MESSAGE);
                System.exit(opc = 6);
                break;
            }
            default: {
                JOptionPane.showMessageDialog(null, "Opcion invalida", "Error", JOptionPane.ERROR_MESSAGE);
                initPlayerSong(d);
                break;
            }
        }

    }

    /**
     * Metodo encargado de crear el hilo para la reproduccion de la musica.
     */
    private static void playMusic() {
        try {
            NodoSong aux = songList.buscarSong(d);
            BufferedInputStream bis = new BufferedInputStream(aux.path);
            player = new Player(bis);
            aux = aux.sig;
            run = new Thread() {
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(MisicplayerFinal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            };
            threadMusic = new Thread(run);
            threadMusic.setDaemon(true);
            threadMusic.start();

        } catch (Exception ex) {
            Logger.getLogger(ListSong.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error con el hilo", "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Metodo que se encarga de reanudar la repoduccion despues de que se
     * utilice el metodo pauseMusic
     */
    private static void resumMusic() {
        threadMusic.resume();

    }

    /**
     * Metodo que se encarga de detener la musica
     */
    private static void stopMusic() {
        threadMusic.stop();
    }

    /**
     * Medodo que detiene la musica por un momento y cuando uno quiere la puede
     * volver a poner reproducir con el metodo resumMusic
     */
    private static void pauseMusic() {
        threadMusic.suspend();
    }

    private static void searchSongPlay() {
        songList.showList();
        d = Integer.parseInt(JOptionPane.showInputDialog(null, "Cual es el codigo de la cancion\n"
                + " que deseas escuchar"));
        if (songList.search(d)) {
            JOptionPane.showMessageDialog(null, "Se encontro la cancion");

            initPlayerSong(d);

        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos no se encontro ninguna\n"
                    + "cancion registrada con ese codigo\n"
                    + "\n"
                    + "Favor vuelve a intentarlo", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
            searchSongPlay();
        }
    }

}
