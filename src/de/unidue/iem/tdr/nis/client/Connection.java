package de.unidue.iem.tdr.nis.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Diese Klasse ermöglicht die Kommunikation mit dem Server. Verbindungsauf und
 * -abbau sind Ihnen in der Klasse {@link Client} schon vorgegeben. Für Sie sind
 * die Methoden {@link #sendSolution(String solution)}, {@link #getTask(int taskId)},
 * {@link #getTask(int taskId, String[] params)} und
 * {@link #sendMoreParams(TaskObject task, String[] params)} relevant.
 * <p>
 * Bitte verändern Sie diese Klasse nicht.
 *
 * @see Client
 */
public class Connection {
    /* packettypes */
    private final static int AUTHENTICATION = 1;
    private final static int TASKREQUEST = 2;
    private final static int SOLUTION = 3;
    private final static int MOREPARAMS = 4;
    private final static int BYE = Integer.MAX_VALUE;

    private static final String HOST = "tmt.tdr.uni-due.de";
    private static final int PORT = 40001;
    private Socket s = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean ready = false;
    private boolean serverClosed = false;

    /**
     * Zuletzt empfangenes Paket
     */
    private String[] input;

    /**
     * Klassenkonstruktor. Baut die Verbindung zum Server auf.
     */
    protected Connection() {
        try {
            /* Verbindungsinformationen */
            s = new Socket(HOST, PORT);
            s.setSoTimeout(3000);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), false);

            getResponse();
            for (int i = 1; i < input.length; i++)
                System.out.println(input[i]);
            ready = true;
        } catch (UnknownHostException e) {
            printErr(e, "Der angegebene Hostname kann nicht aufgeloest werden.", true);
        } catch (ConnectException e) {
            printErr(e, "Fehler beim Verbindungsaufbau.", true);
        } catch (IOException e) {
            printErr(e, "Der Server konnte nicht erreicht werden.", true);
        }
    }

    /**
     * Gibt an, ob die Verbindungs zum Server bereit ist.
     *
     * @return true, falls die Verbindung besteht, sonst false
     */
    boolean isReady() {
        return ready;
    }

    /**
     * Meldet einen Benutzer am Server an.
     *
     * @param token Token des Benutzers
     * @return true, wenn die Anmeldung erfolgreich war, ansonsten false.
     */
    boolean auth(String token) {
        String[] params = new String[1];
        params[0] = token;
        sendRequest(AUTHENTICATION, params);
        getResponse();
        return input.length == 2 && input[1].equalsIgnoreCase("STATUS_OK");
    }

    /**
     * Beendet die Verbindung mit dem Server.
     */
    void close() {
        try {
            if (!serverClosed) {
                out.println(BYE);
                out.flush();
            }
            in.close();
            out.close();
            s.close();
            System.out.println("Verbindung beendet.");
        } catch (IOException ignored) {
        }
    }

    /**
     * Ruft gezielt eine Aufgabe vom Server ab.
     *
     * @param taskId Identifikator der gewünschten Aufgabe
     * @return Aufgabe in Form von TaskObject
     */
    TaskObject getTask(int taskId) {
        sendRequest(TASKREQUEST, String.valueOf(taskId));
        getResponse();
        TaskObject task = new TaskObject();
        task.setType(taskId);
        parseTaskObject(task);
        return task;
    }

    /**
     * Ruft gezielt eine Aufgabe vom Server ab und sendet dabei Parameter mit.
     *
     * @param taskId Identifikator der gewünschten Aufgabe
     * @param params Parameterliste
     * @return Aufgabe in Form von TaskObject
     */
    TaskObject getTask(int taskId, String[] params) {
        String[] request = new String[params.length + 1];
        request[0] = String.valueOf(taskId);
        System.arraycopy(params, 0, request, 1, params.length);
        sendRequest(TASKREQUEST, request);
        getResponse();
        TaskObject task = new TaskObject();
        task.setType(taskId);
        parseTaskObject(task);
        return task;
    }

    /**
     * Sendet weitere Parameter an den Server
     *
     * @param task   TaskObject in welches die Antwort vom Server geparst wird
     * @param params Parameter-Liste
     */
    void sendMoreParams(TaskObject task, String[] params) {
        sendRequest(MOREPARAMS, params);
        getResponse();
        parseTaskObject(task);
    }

    /**
     * Sendet eine Lösung an den Server
     *
     * @param solution Lösung des Studierenden
     */
    boolean sendSolution(String solution) {
        String[] params = new String[1];
        params[0] = solution;
        sendRequest(SOLUTION, params);
        getResponse();
        return input[1].equalsIgnoreCase("SOLUTION_OK");
    }

    /**
     * Empfängt eine Antwort vom Server. Der Inhalt wird in this.input
     * gespeichert, wenn der Server keinen Fehler meldet. Ansonsten wird die
     * Fehlermeldung ausgegeben und die Verbindung vom Server beendet.
     */
    private void getResponse() {
        boolean error = false;
        int status;
        input = null;
        try {
            ArrayList<String> inputList = new ArrayList<>();
            String tmp = in.readLine();

            if (tmp.equals(String.valueOf(Integer.MAX_VALUE))) {
                serverClosed = true;
                close();
                System.exit(0);
            }

            status = Integer.parseInt(tmp.substring(0, 3));

            if (status != 200) {
                error = true;
                System.out.println("Fehler: " + tmp);
            }

            while (tmp != null && !tmp.isEmpty()) {
                inputList.add(tmp);
                tmp = in.readLine();
                if (error) {
                    System.out.println(tmp);
                }
            }

            input = new String[inputList.size()];
            inputList.toArray(input);

            /*
             * Server beendet bei einem Fehler immer die Verbindung.
             * -> BYE-Paket empfangen.
             */
            if (error)
                getResponse();

        } catch (IOException e) {
            printErr(e, "E/A-Fehler beim Empfang einer Servernachricht.", true);
        } catch (NumberFormatException e) {
            printErr(e, "Fehlerhaftes Paket empfangen.", true);
        }
    }

    /**
     * Gibt eine Fehlermeldung aus und beendet nach Bedarf das Programm.
     *
     * @param e     Exception
     * @param msg   Fehlermeldung
     * @param close true, wenn das Programm beendet werden soll, ansonsten false
     */
    private void printErr(Exception e, String msg, boolean close) {
        if (e != null)
            e.printStackTrace();
        System.err.println("Fehler: " + msg);
        if (close) {
            if (ready)
                close();
            System.exit(-1);
        }
    }

    /**
     * Sendet eine Anfrage mit einem Parameter
     *
     * @param packetType Pakettyp-Identifikator
     * @param param      Parameter
     */
    private void sendRequest(int packetType, String param) {
        String[] params = new String[1];
        params[0] = param;
        sendRequest(packetType, params);
    }

    /**
     * Sendet eine Anfrage (bestehend aus mindestens einem
     * Pakettyp-Identifikator (Int) und optionalen Parametern) an den Server.
     *
     * @param packetType Identifikator des Pakettyps
     * @param params     Liste der Parameter als String-Array
     */
    private void sendRequest(int packetType, String[] params) {
        out.println(packetType);
        if (params != null)
            for (String param : params)
                out.println(param);
        out.println();
        out.flush();
    }

    /**
     * Erzeugt aus den eingehenden Daten die Inhaltsarrays eines TasksObjects
     * Die Funktion geht davon aus, dass sie nur aufgerufen wird, wenn wirklich
     * ein TaskObject übertragen wurde
     *
     * @param task TaskObject, das mit Daten gefüllt werden soll
     */
    private void parseTaskObject(TaskObject task) {
        for (int i = 1; i < input.length; ) {
            StringTokenizer st = new StringTokenizer(input[i], "=");
            if (st.countTokens() != 2) {
                printErr(null, "Fehlerhaftes Paket erhalten.", true);
            }

            String tmp = st.nextToken();
            if (tmp.equalsIgnoreCase("STRINGARRAY")) {
                i++;
                int length = Integer.parseInt(st.nextToken());
                task.setStringArray(new String[length]);
                for (int j = 0; j < length; j++) {
                    task.setStringArray(input[i + j], j);
                }
                i += length;
            } else if (tmp.equalsIgnoreCase("INTARRAY")) {
                i++;
                int length = Integer.parseInt(st.nextToken());
                task.setIntArray(new int[length]);
                for (int j = 0; j < length; j++) {
                    task.setIntArray(Integer.parseInt(input[i + j]), j);
                }
                i += length;
            } else if (tmp.equalsIgnoreCase("DOUBLEARRAY")) {
                i++;
                int length = Integer.parseInt(st.nextToken());
                task.setDoubleArray(new double[length]);
                for (int j = 0; j < length; j++) {
                    task.setDoubleArray(Double.parseDouble(input[i + j]), j);
                }
                i += length;
            }
        }
    }
}
