package de.unidue.iem.tdr.nis.client;

/**
 * Stellt Konstanten für die verschiedenen Aufgabentypen zur Verfügung.
 */
public interface TaskDefs {
    int TASK_CLEARTEXT = 1;
    int TASK_XOR = 2;
    int TASK_MODULO = 3;
    int TASK_FACTORIZATION = 4;
    int TASK_VIGENERE = 5;
    int TASK_VIGENERE_AUTOCORRELATION = 23;
    int TASK_DES_KEYSCHEDULE = 6;
    int TASK_DES_RBLOCK = 7;
    int TASK_DES_FEISTEL = 8;
    int TASK_DES_ROUND = 9;
    int TASK_AES_GF8 = 10;
    int TASK_AES_KEYEXPANSION = 11;
    int TASK_AES_MIXCOLUMNS = 12;
    int TASK_AES_TRANSFORMATION = 13;
    int TASK_AES_3ROUNDS = 14;
    int TASK_RC4_LOOP = 15;
    int TASK_RC4_KEYSCHEDULE = 16;
    int TASK_RC4_ENCRYPTION = 17;
    int TASK_DIFFIEHELLMAN = 18;
    int TASK_RSA_ENCRYPTION = 19;
    int TASK_RSA_DECRYPTION = 20;
    int TASK_ELGAMAL_ENCRYPTION = 21;
    int TASK_ELGAMAL_DECRYPTION = 22;
}
