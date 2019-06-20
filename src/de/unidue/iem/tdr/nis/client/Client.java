package de.unidue.iem.tdr.nis.client;

import de.unidue.iem.tdr.nis.client.tasks.TaskSolver;
import de.unidue.iem.tdr.nis.client.tasks._01_Klartext;
import de.unidue.iem.tdr.nis.client.tasks._02_XOR;
import de.unidue.iem.tdr.nis.client.tasks._03_Modulo;
import de.unidue.iem.tdr.nis.client.tasks._04_Faktorisierung;
import de.unidue.iem.tdr.nis.client.tasks._05_Viginere;
import de.unidue.iem.tdr.nis.client.tasks._06_DES_Rundenschlüssel_Berechnung;
import de.unidue.iem.tdr.nis.client.tasks._07_DES_RBlock_Berechnung;
import de.unidue.iem.tdr.nis.client.tasks._08_DES_Feistel_Funktion;
import de.unidue.iem.tdr.nis.client.tasks._09_DES_Berechnung_einer_Runde;
import de.unidue.iem.tdr.nis.client.tasks._10_Multiplikation_in_GF8;
import de.unidue.iem.tdr.nis.client.tasks._11_AES_Schluesselgenerierung;
import de.unidue.iem.tdr.nis.client.tasks._12_AES_MixColumns;
import de.unidue.iem.tdr.nis.client.tasks._13_AES_ShiftRows_Subbytes_MixColumns;
import de.unidue.iem.tdr.nis.client.tasks._14_AES_Initiale_und_zwei_weitere_Runden;
import de.unidue.iem.tdr.nis.client.tasks._15_RC4_Generation_Loop;
import de.unidue.iem.tdr.nis.client.tasks._16_RC4_Keyscheduling;
import de.unidue.iem.tdr.nis.client.tasks._17_RC4_Verschluesselung;
import de.unidue.iem.tdr.nis.client.tasks._18_Diffie_Hellmann;
import de.unidue.iem.tdr.nis.client.tasks._19_RSA_Verschlüsselung;
import de.unidue.iem.tdr.nis.client.util.Logger;

import static de.unidue.iem.tdr.nis.client.tasks._20_RSA_Verschlüsselung.gen_key_pair;
import static de.unidue.iem.tdr.nis.client.util.Logger.logWrong;

/**
 * Diese Klasse ermöglicht das Abrufen von Aufgaben vom Server und die
 * Implementierung der dazugehörigen Lösungen.
 * <p>
 * Nähere Informationen zu den anderen Klassen und den einzelnen Aufgabentypen
 * entnehmen Sie bitte der entsprechenden Dokumentation im TMT und den Javadocs
 * zu den anderen Klassen.
 *
 * @see Connection
 * @see TaskObject
 */
public class Client implements TaskDefs {
    private Connection con;
    private TaskSolver[] solver;

    /* hier bitte das Token eintragen */
    private static final String token = "57ad92d043ecc7cb73429d1336b4a273110424558434bfe2c63f818367794cb9";

    /* Aufgaben, die bearbeitet werden sollen */
    private final int[] tasks = {TASK_CLEARTEXT, TASK_XOR, TASK_MODULO,
            TASK_FACTORIZATION, TASK_VIGENERE, TASK_DES_KEYSCHEDULE,
            TASK_DES_RBLOCK, TASK_DES_FEISTEL, TASK_DES_ROUND, TASK_AES_GF8,
            TASK_AES_KEYEXPANSION, TASK_AES_MIXCOLUMNS,
            TASK_AES_TRANSFORMATION, TASK_AES_3ROUNDS, TASK_RC4_LOOP,
            TASK_RC4_KEYSCHEDULE, TASK_RC4_ENCRYPTION, TASK_DIFFIEHELLMAN,
            TASK_RSA_ENCRYPTION, TASK_RSA_DECRYPTION, TASK_ELGAMAL_ENCRYPTION,
            TASK_ELGAMAL_DECRYPTION};

    private final String[] taskNames = {"Cleartext", "Xor", "Modulo",
            "Factorization", "Viginere", "DES Keyschedule",
            "DES RBlock", "DES Feistel", "DES Round", "AES GF8",
            "AES Keyexpansion", "AES Mixcolumns",
            "AES Transformation", "AES 3 Rounds", "RC4 Loop",
            "RC4 Keyschedule", "RC4 Encryption", "Diffie Hellmann",
            "RSA Encryption", "RSA Decryption", "Elgamal Encryption",
            "Elgamal Decryption"};

    /**
     * Klassenkonstruktor. Baut die Verbindung zum Server auf.
     */
    public Client() {
        solver = new TaskSolver[]{
                new _01_Klartext(),
                new _02_XOR(),
                new _03_Modulo(),
                new _04_Faktorisierung(),
                new _05_Viginere(),
                new _06_DES_Rundenschlüssel_Berechnung(),
                new _07_DES_RBlock_Berechnung(),
                new _08_DES_Feistel_Funktion(),
                new _09_DES_Berechnung_einer_Runde(),
                new _10_Multiplikation_in_GF8(),
                new _11_AES_Schluesselgenerierung(),
                new _12_AES_MixColumns(),
                new _13_AES_ShiftRows_Subbytes_MixColumns(),
                new _14_AES_Initiale_und_zwei_weitere_Runden(),
                new _15_RC4_Generation_Loop(),
                new _16_RC4_Keyscheduling(),
                new _17_RC4_Verschluesselung(),
                new _18_Diffie_Hellmann(),
                new _19_RSA_Verschlüsselung()
        };
        con = new Connection();
        if (con.auth(token))
            System.out.println("Anmeldung erfolgreich.");
        else
            System.out.println("Anmeldung nicht erfolgreich.");
    }

    /**
     * Durchläuft eine Liste von Aufgaben und fordert diese vom Server an.
     */
    public void taskLoop() {
        TaskObject currentTask;
        boolean[] results = new boolean[solver.length];
        for (int task : tasks) {
            if (task > solver.length) {
                continue; // no more task implementations;
            }
            Logger.logTask(task, taskNames[task -1], 37, "BEGIN");
            System.out.println();
            if (task == 20) {
                currentTask = con.getTask(task, gen_key_pair());
            } else
                currentTask = con.getTask(task);
            if (task == 18) {
                con.sendMoreParams(currentTask, _18_Diffie_Hellmann.getMoreParams(currentTask));
            }
            final String solution = solver[task - 1].solve(currentTask); // 0 to 1 offset due to exercises starting at 1
            System.out.println();
            if (con.sendSolution(solution)) {
                results[task - 1] = true;
                Logger.logCorrect(task, taskNames[task - 1]);
            }
            else {
                results[task - 1] = false;
                Logger.logWrong(task, taskNames[task - 1]);
            }
            Logger.printTaskEnd();
        }

        Logger.printResults(results, taskNames);
    }

    public static void main(String[] args) {
        Client c = new Client();
        if (c.con.isReady())
            c.taskLoop();
        c.con.close();
    }
}
