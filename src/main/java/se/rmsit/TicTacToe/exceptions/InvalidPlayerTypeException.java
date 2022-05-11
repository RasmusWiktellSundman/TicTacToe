package se.rmsit.TicTacToe.exceptions;

// Anger felaktig spelartyp vid drag (endast x och o är giltiga)
// Använder RuntimeException eftersom felet inte ska kunna ske om korrekt data skickas.
public class InvalidPlayerTypeException extends RuntimeException {}
